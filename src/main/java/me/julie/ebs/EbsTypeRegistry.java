package me.julie.ebs;

import it.unimi.dsi.fastutil.objects.ObjectArrays;
import me.julie.ebs.element.*;
import me.julie.ebs.type.EbsArrayType;
import me.julie.ebs.type.EbsCompoundType;
import me.julie.ebs.type.EbsNumberType;
import me.julie.ebs.type.EbsType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * A Type registry is used during IO operations to read and write
 * element types, by mapping them to an integer ID.
 * <p>
 * While you can create custom element type registries, the global
 * registry is accessible with {@link EbsIo#globalTypes()}, this
 * contains all inbuilt EBS types.
 * <p>
 * When a registry is created, it will automatically contain all
 * in-built element types, these types are specified in the
 * {@link #BUILT_IN_TYPES} array.
 *
 * @see #write(EbsElement, OutputStream) For general writing operations
 * @see #read(InputStream) For general reading operations
 *
 * @see #writeFile(EbsElement, Path)
 * @see #readFile(Path)
 * @see #writeFile(EbsElement, File)
 * @see #readFile(File)
 */
public class EbsTypeRegistry {
    /** Array if built-in types which always exist in every type registry */
    private static final EbsType[] BUILT_IN_TYPES = {
            EbsArrayType.getInstance(),
            EbsCompoundType.getInstance(),
            EbsString.TYPE,
            EbsUUID.TYPE,
            EbsNumberType.BYTE,
            EbsNumberType.SHORT,
            EbsNumberType.INTEGER,
            EbsNumberType.LONG,
            EbsNumberType.FLOAT,
            EbsNumberType.DOUBLE,
            EbsNumberType.BIG_INTEGER,
            EbsBoolean.TYPE
    };

    private TypeEntry[] types;
    private int size;

    public EbsTypeRegistry() {
        this.types = new TypeEntry[3];
        size = 0;

        // Register all built in types
        for (EbsType t: BUILT_IN_TYPES) {
            register(t);
        }
    }

    /* ----------------------------- IO ------------------------------ */

    /**
     * Reads an element's type from the given input.
     * @param input The input to read from
     * @return The read type
     * @throws IOException If the type could not be read
     * @throws NullPointerException If the type ID was read, but no matching type was registered
     * @see #writeType(EbsType, DataOutput) for documentation on how types are stored
     */
    public EbsType<EbsElement> readType(DataInput input) throws IOException, NullPointerException {
        int id = input.readInt();
        EbsType type = get(id);

        Objects.requireNonNull(type, "Unknown type ID: " + id);
        return type;
    }

    /**
     * Writes a given type to the given output
     * <p>
     * Types are written by finding their mapped ID with {@link #identifierOf(EbsType)}
     * and then writing the resulting integer.
     *
     * @param type The type to write
     * @param output The output to write to
     *
     * @throws IOException If the type couldn't be written, or if the type is not
     *                     registered in this registry
     */
    public void writeType(EbsType type, DataOutput output) throws IOException {
        int id = identifierOf(type);

        if (id == -1) {
            throw new IOException("Given type " + type + " is not registered");
        }

        output.writeInt(id);
    }

    /**
     * Writes the given element to the given stream.
     * <p>
     * The given stream is wrapped with a {@link DataOutputStream}
     * unless the given stream IS a {@link DataOutputStream}, in
     * which case the given stream is cast instead.
     * <p>
     * This uses {@link #writeType(EbsType, DataOutput)} to write the
     * element's type and then uses the same type to write the element's
     * data.
     *
     * @param element The element to write
     * @param stream The stream to write to
     *
     * @throws IOException If the element cannot be written
     * @throws NullPointerException If the element, stream or element's type are null
     *
     * @see #writeType(EbsType, DataOutput) for documentation on writing element types
     */
    public void write(EbsElement element, OutputStream stream) throws IOException, NullPointerException {
        Objects.requireNonNull(element, "Null element");
        Objects.requireNonNull(stream, "Null stream");

        DataOutputStream dataOutput = stream instanceof DataOutputStream dOutput ?
                dOutput : new DataOutputStream(stream);

        EbsType type = Objects.requireNonNull(element.getType(), "Null type on element");

        writeType(type, dataOutput);
        type.write(this, dataOutput, element);
    }

    /**
     * Reads an element from the given stream.
     * <p>
     * The given stream will be wrapped with a {@link DataInputStream}
     * unless the given stream IS a {@link DataInputStream}, in which
     * case it'll simply be cast.
     * <p>
     * This method uses {@link #readType(DataInput)} to read the element's
     * type, then uses the read type to read the element's data
     *
     * @param stream The stream to read from
     * @return The read element
     *
     * @throws IOException If the element cannot be read
     * @throws NullPointerException If the stream is null, or if the read
     *                              element's type is not known to this registry
     *
     * @see #readType(DataInput) for documentation on reading element types
     */
    public EbsElement read(InputStream stream) throws IOException, NullPointerException {
        Objects.requireNonNull(stream, "Null stream");

        DataInputStream input = stream instanceof DataInputStream dInput ?
                dInput : new DataInputStream(stream);

        EbsType<EbsElement> type = readType(input);

        return type.read(this, input);
    }

    public void writeFile(EbsElement element, File f) throws IOException {
        try (FileOutputStream stream = new FileOutputStream(f)) {
            write(element, stream);
        }
    }

    public EbsElement readFile(File f) throws IOException {
        try (FileInputStream input = new FileInputStream(f)) {
            var element = read(input);
            input.close();
            return element;
        }
    }

    public void writeFile(EbsElement element, Path path) throws IOException {
        try (OutputStream output = Files.newOutputStream(path)) {
            write(element, output);
        }
    }

    public EbsElement readFile(Path path) throws IOException {
        try (InputStream input = Files.newInputStream(path)) {
            var element = read(input);
            input.close();
            return element;
        }
    }

    /* ----------------------------- TYPE REGISTRATION ------------------------------ */

    /**
     * Gets the size of the array
     * @return The registry's size
     */
    public int size() {
        return size;
    }

    /**
     * Gets the serialization ID of the given type
     * @param type The type to get the ID of
     * @return The type's ID, or -1, if the type is not registered
     */
    public int identifierOf(EbsType type) {
        TypeEntry entry = entry(type);
        return entry == null ? -1 : entry.identifier;
    }

    /**
     * Gets the type by the given ID
     * @param id The ID to get the type of
     * @return The type, null, if there's no type with the given ID registered.
     */
    public EbsType get(int id) {
        return types[id].type;
    }

    /**
     * Registers the given type
     * @param type The type to register
     * @throws IllegalArgumentException If the given type is already registered
     */
    public int register(EbsType type) {
        TypeEntry entry = entry(type);

        if (entry != null) {
            throw new IllegalArgumentException("Type " + type + " is already registered with ID " + entry.identifier);
        }

        int placeIndex = size;
        entry = new TypeEntry(type, placeIndex);
        size++;

        types = ObjectArrays.ensureCapacity(types, size);
        types[placeIndex] = entry;
        return placeIndex;
    }

    /**
     * Checks if the given type is registered
     * @param type The type to check
     * @return True, if the type is registered, false otherwise
     */
    public boolean isRegistered(EbsType type) {
        return entry(type) != null;
    }

    // Gets an entry for a given type
    private TypeEntry entry(EbsType type) {
        // Just run a for loop through the array
        // to find the entry
        for (TypeEntry e: types) {
            if (e == null) {
                continue;
            }

            if (e.type.equals(type)) {
                return e;
            }
        }

        return null;
    }

    private record TypeEntry(EbsType type, int identifier) {}
}