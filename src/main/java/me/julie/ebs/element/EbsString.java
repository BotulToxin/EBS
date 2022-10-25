package me.julie.ebs.element;

import me.julie.ebs.EbsTypeRegistry;
import me.julie.ebs.type.EbsType;
import me.julie.ebs.visitor.EbsVisitor;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.stream.IntStream;

public class EbsString extends AbstractValuedElement<String> implements CharSequence {
    public static final EbsType<EbsString> TYPE = new EbsType<>() {
        @Override
        public EbsString read(EbsTypeRegistry registry, DataInput input) throws IOException {
            return new EbsString(input.readUTF());
        }

        @Override
        public void write(EbsTypeRegistry registry, DataOutput output, EbsString val) throws IOException {
            output.writeUTF(val.value());
        }
    };

    public EbsString(String value) {
        super(value);
    }

    @Override
    public EbsType<EbsString> getType() {
        return TYPE;
    }

    @Override
    public EbsString clone() {
        return new EbsString(value);
    }

    @Override
    public int length() {
        return value.length();
    }

    @Override
    public char charAt(int index) {
        return value.charAt(index);
    }

    @Override
    public boolean isEmpty() {
        return value.isEmpty();
    }

    @Override
    public EbsString subSequence(int start, int end) {
        return new EbsString(value.substring(start, end));
    }

    @Override
    public IntStream chars() {
        return value.chars();
    }

    @Override
    public IntStream codePoints() {
        return value.codePoints();
    }

    @Override
    public void accept(EbsVisitor visitor) {
        visitor.visitString(this);
    }

    @Override
    public String toString() {
        return value;
    }
}