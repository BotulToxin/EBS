package me.julie.ebs.element;

import me.julie.ebs.type.EbsNumberType;
import me.julie.ebs.visitor.EbsVisitor;

public class EbsNumber extends Number implements EbsValuedElement<Number> {
    private final Number number;
    private final EbsNumberType type;

    EbsNumber(Number number, EbsNumberType type) {
        this.number = number;
        this.type = type;
    }

    @Override
    public Number value() {
        return number;
    }

    @Override
    public EbsNumberType getType() {
        return type;
    }

    @Override
    public EbsNumber clone() {
        return new EbsNumber(value(), getType());
    }

    @Override
    public int intValue() {
        return value().intValue();
    }

    @Override
    public long longValue() {
        return value().longValue();
    }

    @Override
    public float floatValue() {
        return value().floatValue();
    }

    @Override
    public double doubleValue() {
        return value().doubleValue();
    }

    @Override
    public byte byteValue() {
        return value().byteValue();
    }

    @Override
    public short shortValue() {
        return value().shortValue();
    }

    @Override
    public void accept(EbsVisitor visitor) {
        visitor.visitNumber(this);
    }

    @Override
    public String toString() {
        return number.toString();
    }
}