package me.julie.ebs.type;

import me.julie.ebs.element.EbsNumber;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigInteger;

/**
 * An interface that stores number type constants
 */
public interface EbsNumericalTypes {
    EbsType<EbsNumber>
    BYTE = new EbsType<>() {
        @Override
        public EbsNumber read(DataInput input) throws IOException {
            return EbsNumber.ofByte(input.readByte());
        }

        @Override
        public void write(DataOutput output, EbsNumber val) throws IOException {
            output.writeByte(val.byteValue());
        }
    },

    SHORT = new EbsType<>() {
        @Override
        public EbsNumber read(DataInput input) throws IOException {
            return EbsNumber.ofShort(input.readShort());
        }

        @Override
        public void write(DataOutput output, EbsNumber val) throws IOException {
            output.writeShort(val.shortValue());
        }
    },

    INTEGER = new EbsType<>() {
        @Override
        public EbsNumber read(DataInput input) throws IOException {
            return EbsNumber.ofInt(input.readInt());
        }

        @Override
        public void write(DataOutput output, EbsNumber val) throws IOException {
            output.writeInt(val.intValue());
        }
    },

    LONG = new EbsType<>() {
        @Override
        public EbsNumber read(DataInput input) throws IOException {
            return EbsNumber.ofLong(input.readLong());
        }

        @Override
        public void write(DataOutput output, EbsNumber val) throws IOException {
            output.writeLong(val.longValue());
        }
    },

    FLOAT = new EbsType<>() {
        @Override
        public EbsNumber read(DataInput input) throws IOException {
            return EbsNumber.ofFloat(input.readFloat());
        }

        @Override
        public void write(DataOutput output, EbsNumber val) throws IOException {
            output.writeFloat(val.floatValue());
        }
    },

    DOUBLE = new EbsType<>() {
        @Override
        public EbsNumber read(DataInput input) throws IOException {
            return EbsNumber.ofDouble(input.readDouble());
        }

        @Override
        public void write(DataOutput output, EbsNumber val) throws IOException {
            output.writeDouble(val.doubleValue());
        }
    },

    BIG_INTEGER = new EbsType<>() {
        @Override
        public EbsNumber read(DataInput input) throws IOException {
            int length = input.readInt();
            byte[] arr = new byte[length];

            for (int i = 0; i < length; i++) {
                arr[i] = input.readByte();
            }

            return EbsNumber.ofBigInt(new BigInteger(arr));
        }

        @Override
        public void write(DataOutput output, EbsNumber val) throws IOException {
            BigInteger bigInt = (BigInteger) val.value();
            byte[] arr = bigInt.toByteArray();
            output.writeInt(arr.length);

            for (byte b: arr) {
                output.writeByte(b);
            }
        }
    };
}
