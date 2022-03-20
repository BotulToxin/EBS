package me.julie.ebs.element;

import me.julie.ebs.type.EbsType;
import me.julie.ebs.visitor.EbsVisitor;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Represents an enum element
 * @param <E> The enum type
 */
public class EbsEnum<E extends Enum<E>> implements EbsValuedElement<E> {

    public static final EbsType<EbsEnum> TYPE = new EbsType<>() {
        @Override
        public EbsEnum read(DataInput input) throws IOException {
            String className = input.readUTF();
            String constantName = input.readUTF();

            try {
                Class<? extends Enum> clazz = (Class<? extends Enum>) Class.forName(className);
                Enum e = Enum.valueOf(clazz, constantName);

                return new EbsEnum(e, clazz);
            } catch (Exception e) {
                throw new IOException("Could not find enum class '" + className + "' for constant '" + constantName + "'");
            }
        }

        @Override
        public void write(DataOutput output, EbsEnum val) throws IOException {
            output.writeUTF(val.clazz.getName());
            output.writeUTF(val.value.name());
        }
    };

    private final E value;
    private final Class<E> clazz;

    public EbsEnum(E value) {
        this(value, value.getDeclaringClass());
    }

    public EbsEnum(E value, Class<E> clazz) {
        this.value = value;
        this.clazz = clazz;
    }

    @Override
    public EbsType<EbsEnum> getType() {
        return TYPE;
    }

    @Override
    public EbsEnum<E> clone() {
        return new EbsEnum<>(value, clazz);
    }

    @Override
    public E value() {
        return value;
    }

    public Class<E> getEnumClass() {
        return clazz;
    }

    @Override
    public void accept(EbsVisitor visitor) {
        visitor.visitEnum(this);
    }
}
