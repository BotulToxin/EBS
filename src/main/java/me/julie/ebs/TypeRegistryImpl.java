package me.julie.ebs;

import me.julie.ebs.element.EbsEnum;
import me.julie.ebs.element.EbsString;
import me.julie.ebs.element.EbsUUID;
import me.julie.ebs.type.EbsArrayType;
import me.julie.ebs.type.EbsCompoundType;
import me.julie.ebs.type.EbsNumericalTypes;
import me.julie.ebs.type.EbsType;

import java.util.concurrent.atomic.AtomicInteger;

class TypeRegistryImpl implements ElementTypeRegistry {
    private static final EbsType[] BUILT_IN_TYPES = {
            EbsArrayType.getInstance(),
            EbsCompoundType.getInstance(),
            EbsString.TYPE,
            EbsUUID.TYPE,
            EbsNumericalTypes.BYTE,
            EbsNumericalTypes.SHORT,
            EbsNumericalTypes.INTEGER,
            EbsNumericalTypes.LONG,
            EbsNumericalTypes.FLOAT,
            EbsNumericalTypes.DOUBLE,
            EbsNumericalTypes.BIG_INTEGER,
            EbsEnum.TYPE
    };

    private static final int SIZE_INCREASE = 15;
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    public static final TypeRegistryImpl INSTANCE = new TypeRegistryImpl();

    private TypeEntry[] types;
    private int size;

    public TypeRegistryImpl() {
        this.types = new TypeEntry[3];
        size = 0;

        // Register all built in types
        for (EbsType t: BUILT_IN_TYPES) {
            register(t);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int identifierOf(EbsType type) {
        TypeEntry entry = entry(type);
        return entry == null ? -1 : entry.identifier;
    }

    @Override
    public EbsType get(int id) {
        for (TypeEntry e: types) {
            if(e == null) continue;
            if(e.identifier == id) return e.type;
        }

        return null;
    }

    @Override
    public void register(EbsType type) {
        TypeEntry entry = entry(type);

        if(entry != null) {
            throw new IllegalArgumentException("Type " + type + " is already registered with ID " + entry.identifier);
        }

        // create a new entry with a new ID from the generator
        entry = new TypeEntry(type, ID_GENERATOR.getAndIncrement());
        int placeIndex = size;
        size++;

        // If we should increase the array's size
        if(types.length <= size) {
            int newLength = ((size / SIZE_INCREASE) + 1) * SIZE_INCREASE;

            TypeEntry[] old = types;
            types = new TypeEntry[newLength];

            // Copy entries from old array to new
            System.arraycopy(old, 0, types, 0, old.length);
        }

        // Set the entry
        types[placeIndex] = entry;
    }

    @Override
    public boolean isRegistered(EbsType type) {
        return entry(type) != null;
    }

    // Gets an entry for a given type
    private TypeEntry entry(EbsType type) {
        // Just run a for loop through the array
        // to find the entry
        for (TypeEntry e: types) {
            if(e == null) continue;
            if(e.type.equals(type)) return e;
        }

        return null;
    }

    private record TypeEntry(EbsType type, int identifier) {}
}
