package me.julie.ebs.element;

import me.julie.ebs.EbsTypeRegistry;
import me.julie.ebs.type.EbsType;
import me.julie.ebs.visitor.EbsVisitor;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.UUID;

/**
 * Represents a UUID
 */
public class EbsUUID extends AbstractValuedElement<UUID> {
    /**
     * A type which just reads/writes the most and least
     * significant bits of the UUID as 2 longs
     */
    public static final EbsType<EbsUUID> TYPE = new EbsType<>() {
        @Override
        public EbsUUID read(EbsTypeRegistry registry,DataInput input) throws IOException {
            return new EbsUUID(input.readLong(), input.readLong());
        }

        @Override
        public void write(EbsTypeRegistry registry, DataOutput output, EbsUUID val) throws IOException {
            output.writeLong(val.value().getMostSignificantBits());
            output.writeLong(val.value().getLeastSignificantBits());
        }
    };

    public EbsUUID(long most, long least) {
        this(new UUID(most, least));
    }

    public EbsUUID(UUID value) {
        super(value);
    }

    @Override
    public EbsType<EbsUUID> getType() {
        return TYPE;
    }

    @Override
    public EbsUUID clone() {
        return new EbsUUID(value());
    }

    @Override
    public void accept(EbsVisitor visitor) {
        visitor.visitUUID(this);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}