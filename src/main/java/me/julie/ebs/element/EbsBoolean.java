package me.julie.ebs.element;

import me.julie.ebs.EbsTypeRegistry;
import me.julie.ebs.type.EbsType;
import me.julie.ebs.visitor.EbsVisitor;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class EbsBoolean extends AbstractValuedElement<Boolean> {
    public static final EbsType<EbsBoolean> TYPE = new EbsType<>() {
        @Override
        public EbsBoolean read(EbsTypeRegistry registry, DataInput input) throws IOException {
            return EbsElements.of(input.readBoolean());
        }

        @Override
        public void write(EbsTypeRegistry registry, DataOutput output, EbsBoolean val) throws IOException {
            output.writeBoolean(val.value());
        }
    };

    public static final EbsBoolean TRUE = new EbsBoolean(true);
    public static final EbsBoolean FALSE = new EbsBoolean(false);

    private EbsBoolean(boolean value) {
        super(value);
    }

    @Override
    public EbsType<EbsBoolean> getType() {
        return TYPE;
    }

    @Override
    public AbstractValuedElement<Boolean> clone() {
        return this;
    }

    @Override
    public void accept(EbsVisitor visitor) {
        visitor.visitBool(this);
    }
}