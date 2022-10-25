package me.julie.ebs.type;

import me.julie.ebs.EbsTypeRegistry;
import me.julie.ebs.element.EbsArray;
import me.julie.ebs.element.EbsElement;
import me.julie.ebs.element.EbsElements;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class EbsArrayType implements EbsType<EbsArray> {
    static final EbsArrayType INSTANCE = new EbsArrayType();
    static final int UNSET_TYPE = -1;

    public static EbsArrayType getInstance() {
        return INSTANCE;
    }

    // ---------------------------------------
    //            Array IO format
    // ---------------------------------------
    //
    // Just like the compound, this follows a
    // simple format:
    // Just like the compound, the first 4 bytes
    // are the size integer, the next 4 bytes are
    // ID of the type the array stores. After the
    // array element ID, there's as many elements
    // as the size states
    //

    @Override
    public EbsArray read(EbsTypeRegistry registry,DataInput input) throws IOException {
        // Read the size
        int size = input.readInt();
        // Read the type
        EbsArray array = EbsElements.newArray(size);
        int id = input.readInt();

        if (id == UNSET_TYPE) {
            return array;
        }

        EbsType type = registry.get(id);

        if (type == null) {
            throw new IOException("Unknown type: " + id);
        }

        // Read the elements, if they exist
        if(size > 0) {
            for (int i = 0; i < size; i++) {
                array.add(type.read(registry, input));
            }
        }

        return array;
    }

    @Override
    public void write(EbsTypeRegistry registry, DataOutput output, EbsArray val) throws IOException {
        // I love type parameters
        EbsArray<EbsElement> arr = val;

        // Write the size
        output.writeInt(val.size());
        EbsType<EbsElement> type = arr.arrayType();

        // Write the type
        if (type == null) {
            output.writeInt(UNSET_TYPE);
        } else {
            registry.writeType(type, output);

            // Write each element
            for (EbsElement e: arr) {
                type.write(registry, output, e);
            }
        }
    }
}