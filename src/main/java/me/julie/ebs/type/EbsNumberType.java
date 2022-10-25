package me.julie.ebs.type;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.RequiredArgsConstructor;
import me.julie.ebs.EbsTypeRegistry;
import me.julie.ebs.element.EbsElements;
import me.julie.ebs.element.EbsNumber;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

/**
 * An enum representing the {@link EbsType} instances for
 * all supported numerical types
 */
@RequiredArgsConstructor
public enum EbsNumberType implements EbsType<EbsNumber> {
    BYTE (Byte.class) {
        @Override
        public EbsNumber read(EbsTypeRegistry registry, DataInput input) throws IOException {
            return EbsElements.of(input.readByte());
        }

        @Override
        public void write(EbsTypeRegistry registry, DataOutput output, EbsNumber val) throws IOException {
            output.writeByte(val.byteValue());
        }
    },

    SHORT (Short.class) {
        @Override
        public EbsNumber read(EbsTypeRegistry registry, DataInput input) throws IOException {
            return EbsElements.of(input.readShort());
        }

        @Override
        public void write(EbsTypeRegistry registry, DataOutput output, EbsNumber val) throws IOException {
            output.writeShort(val.shortValue());
        }
    },

    INTEGER (Integer.class) {
        @Override
        public EbsNumber read(EbsTypeRegistry registry, DataInput input) throws IOException {
            return EbsElements.of(input.readInt());
        }

        @Override
        public void write(EbsTypeRegistry registry, DataOutput output, EbsNumber val) throws IOException {
            output.writeInt(val.intValue());
        }
    },

    LONG (Long.class) {
        @Override
        public EbsNumber read(EbsTypeRegistry registry, DataInput input) throws IOException {
            return EbsElements.of(input.readLong());
        }

        @Override
        public void write(EbsTypeRegistry registry, DataOutput output, EbsNumber val) throws IOException {
            output.writeLong(val.longValue());
        }
    },

    FLOAT (Float.class) {
        @Override
        public EbsNumber read(EbsTypeRegistry registry, DataInput input) throws IOException {
            return EbsElements.of(input.readFloat());
        }

        @Override
        public void write(EbsTypeRegistry registry, DataOutput output, EbsNumber val) throws IOException {
            output.writeFloat(val.floatValue());
        }
    },

    DOUBLE (Double.class) {
        @Override
        public EbsNumber read(EbsTypeRegistry registry, DataInput input) throws IOException {
            return EbsElements.of(input.readDouble());
        }

        @Override
        public void write(EbsTypeRegistry registry, DataOutput output, EbsNumber val) throws IOException {
            output.writeDouble(val.doubleValue());
        }
    },

    BIG_INTEGER (BigInteger.class) {
        @Override
        public EbsNumber read(EbsTypeRegistry registry, DataInput input) throws IOException {
            int length = input.readInt();
            byte[] arr = new byte[length];

            for (int i = 0; i < length; i++) {
                arr[i] = input.readByte();
            }

            return EbsElements.of(new BigInteger(arr));
        }

        @Override
        public void write(EbsTypeRegistry registry, DataOutput output, EbsNumber val) throws IOException {
            BigInteger bigInt = (BigInteger) val.value();
            byte[] arr = bigInt.toByteArray();
            output.writeInt(arr.length);

            for (byte b: arr) {
                output.writeByte(b);
            }
        }
    };

    public static final Map<Class<?>, EbsNumberType> BY_TYPE;

    static {
        Object2ObjectMap<Class<?>, EbsNumberType> map = new Object2ObjectOpenHashMap<>();

        for (var t: values()) {
            map.put(t.numberClass, t);
        }

        BY_TYPE = Object2ObjectMaps.unmodifiable(map);
    }

    private final Class<? extends Number> numberClass;
}