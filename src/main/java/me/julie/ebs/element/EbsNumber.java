package me.julie.ebs.element;

import me.julie.ebs.type.EbsNumericalTypes;
import me.julie.ebs.type.EbsType;
import me.julie.ebs.visitor.EbsVisitor;

import java.math.BigInteger;

public class EbsNumber extends Number implements EbsValuedElement<Number> {
    private final Number number;
    private final EbsType<EbsNumber> type;

    private EbsNumber(Number number, EbsType<EbsNumber> type) {
        this.number = number;
        this.type = type;
    }

    /**
     * Gets a byte number for the given boolean
     * @param b The boolean val
     * @return A number element with a value of 1 or 0 depending on the boolean value
     */
    public static EbsNumber ofBool(boolean b) {
        return ofByte(boolToByte(b));
    }

    /**
     * Creates a byte number
     * @param val The number value
     * @return The created number element
     */
    public static EbsNumber ofByte(byte val) {
        return new EbsNumber(val, EbsNumericalTypes.BYTE);
    }

    /**
     * Creates a short number
     * @param val The number value
     * @return The created number element
     */
    public static EbsNumber ofShort(short val) {
        return new EbsNumber(val, EbsNumericalTypes.SHORT);
    }

    /**
     * Creates an int number
     * @param val The number value
     * @return The created number element
     */
    public static EbsNumber ofInt(int val) {
        return new EbsNumber(val, EbsNumericalTypes.INTEGER);
    }

    /**
     * Creates a long number
     * @param val The number value
     * @return The created number element
     */
    public static EbsNumber ofLong(long val) {
        return new EbsNumber(val, EbsNumericalTypes.LONG);
    }

    /**
     * Creates a float number
     * @param val The number value
     * @return The created number element
     */
    public static EbsNumber ofFloat(float val) {
        return new EbsNumber(val, EbsNumericalTypes.FLOAT);
    }

    /**
     * Creates a double number
     * @param val The number value
     * @return The created number element
     */
    public static EbsNumber ofDouble(double val) {
        return new EbsNumber(val, EbsNumericalTypes.DOUBLE);
    }

    /**
     * Creates a big integer number
     * @param val The number value
     * @return The created number element
     */
    public static EbsNumber ofBigInt(BigInteger val) {
        return new EbsNumber(val, EbsNumericalTypes.BIG_INTEGER);
    }

    /**
     * Creates a number element from the given number
     * @param number The number
     * @return A wrapped number element
     */
    public static EbsNumber of(Number number) {
        if (number instanceof Integer i) return ofInt(i);
        if (number instanceof Float f) return ofFloat(f);
        if (number instanceof Double d) return ofDouble(d);
        if (number instanceof Byte b) return ofByte(b);
        if (number instanceof Short s) return ofShort(s);
        if (number instanceof BigInteger bigInt) return ofBigInt(bigInt);
        if (number instanceof EbsNumber n) return n.clone();

        throw new IllegalArgumentException("Unsupported number type: " + number.getClass().getSimpleName());
    }

    /**
     * Converts a boolean to a byte
     * @param b The boolean value
     * @return 1 if the given boolean is true, 0 otherwise
     */
    public static byte boolToByte(boolean b) {
        return (byte) (b ? 1 : 0);
    }

    @Override
    public Number value() {
        return number;
    }

    @Override
    public EbsType<EbsNumber> getType() {
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
