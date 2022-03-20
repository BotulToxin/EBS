package me.julie.ebs;

import me.julie.ebs.element.EbsElement;
import me.julie.ebs.type.EbsType;

import java.io.*;

/**
 * A central class to access some IO functions for EBS
 */
public interface EBS {
    /**
     * Gets the EBS type registry
     * @return The type registry instance
     */
    static ElementTypeRegistry typeRegistry() {
        return TypeRegistryImpl.INSTANCE;
    }

    /**
     * Writes the gvien type into the output
     *
     * @param output The output to write to
     * @param type The type to write
     *
     * @throws IOException Can be thrown by writing, but will also
     *                     be thrown if the type is not registered
     */
    static void writeType(DataOutput output, EbsType type) throws IOException {
        int id = typeRegistry().identifierOf(type);

        if(id == -1) {
            throw new IOException("Found unregistered element while writing type, type: " + type);
        }

        output.writeInt(id);
    }

    /**
     * Reads a type from the given input
     *
     * @param input The input to read from
     * @return The read input
     *
     * @throws IOException Can be thrown by reading from the input,
     *                     but will also be thrown if the found type
     *                     is unknown
     */
    static EbsType readType(DataInput input) throws IOException {
        int id = input.readInt();
        EbsType type = typeRegistry().get(id);

        if(type == null) {
            throw new IOException("Found unknown type ID while reading EBS, id: " + id);
        }

        return type;
    }

    /**
     * Writes the given EBS element into the output stream
     * @param element The element to write
     * @param stream The stream to write to
     * @throws IOException When any error occurs
     */
    static void write(EbsElement element, OutputStream stream) throws IOException {
        DataOutputStream output = new DataOutputStream(stream);
        EbsType type = element.getType();

        writeType(output, type);
        type.write(output, element);
    }

    /**
     * Reads an EBS element from the given input stream
     * @param stream The stream to read from
     * @return The read element
     * @throws IOException When any error occurs
     */
    static EbsElement read(InputStream stream) throws IOException {
        DataInput input = new DataInputStream(stream);
        EbsType type = readType(input);

        return type.read(input);
    }

    /**
     * Writes the given element into the given file
     * @param element The element to write
     * @param file The file to write to
     * @throws IOException When any error occurs
     */
    static void writeFile(EbsElement element, File file) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);
        write(element, outputStream);
        outputStream.close();
    }

    /**
     * Reads an element from the given file
     * @param file The file to read from
     * @return The read element
     * @throws IOException When any error occurs
     */
    static EbsElement readFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);

        EbsElement element = read(inputStream);
        inputStream.close();

        return element;
    }
}
