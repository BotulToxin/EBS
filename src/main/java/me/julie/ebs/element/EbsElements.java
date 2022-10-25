package me.julie.ebs.element;

import me.julie.ebs.type.EbsNumberType;
import me.julie.ebs.type.EbsType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;

public final class EbsElements {
    private EbsElements() {}

    /* ----------------------------- CONSTANTS ------------------------------ */

    public static final int DEFAULT_SIZE = 16;

    public static final Collector<EbsElement, EbsArray, EbsArray> ARRAY_COLLECTOR = Collector.of(
            EbsElements::newArray, EbsArray::add, EbsArray::merge
    );

    @Nonnull
    public static <T extends EbsElement> Collector<T, EbsArray<T>, EbsArray<T>> toEbsArray() {
        Collector collector = ARRAY_COLLECTOR;
        return (Collector<T, EbsArray<T>, EbsArray<T>>) collector;
    }

    /* ----------------------------- COMPOUNDS ------------------------------ */

    /**
     * Creates a new compound element. This calls
     * {@link #newCompound(int)} with {@link #DEFAULT_SIZE}
     *
     * @return The created element
     */
    @Nonnull
    public static EbsCompound newCompound() {
        return newCompound(DEFAULT_SIZE);
    }

    /**
     * Creates a new compound element with the given size
     * @param expectedSize The expected size to create the compound with
     * @return The created element
     */
    @Nonnull
    public static EbsCompound newCompound(int expectedSize) {
        return new EbsCompoundImpl(expectedSize);
    }

    @Nonnull
    public static EbsCompound of(@Nonnull Map<String, EbsElement> map) {
        var compound = newCompound(
                Objects.requireNonNull(map, "Null map").size()
        );

        map.forEach(compound::put);
        return compound;
    }

    /* ----------------------------- OBJECTS ------------------------------ */

    @Nonnull
    public static EbsString of(@Nonnull String s) {
        return new EbsString(Objects.requireNonNull(s, "Null string"));
    }

    @Nonnull
    public static EbsUUID of(@Nonnull UUID uuid) {
        return new EbsUUID(Objects.requireNonNull(uuid, "Null UUID"));
    }

    /* ----------------------------- CLONING ------------------------------ */

    /**
     * Creates a deep clone of the given element.
     * <p>
     * For most elements, this will just call {@link EbsElement#clone()},
     * however for {@link EbsArray} and {@link EbsCompound} this calls
     * their deep clone methods, essentially, instead of just creating
     * a new instance of the container object and placing the entries
     * of the parent object into the created one, it places the clone
     * of each entry into the created container object.
     *
     * @param element The element to clone
     * @return The cloned element
     */
    @Nonnull
    public static EbsElement deepClone(@Nonnull EbsElement element) {
        Objects.requireNonNull(element, "Cannot deep clone null element");

        if (element instanceof EbsArray<?> arr) {
            return arr.deepClone();
        }

        if (element instanceof EbsCompound compound) {
            return compound.deepClone();
        }

        return element.clone();
    }

    /* ----------------------------- BOOLEANS ------------------------------ */

    /**
     * Gets a byte number for the given boolean
     * @param b The boolean val
     * @return A number element with a value of 1 or 0 depending on the boolean value
     */
    @Nonnull
    public static EbsBoolean of(boolean b) {
        return b ? EbsBoolean.TRUE : EbsBoolean.FALSE;
    }

    /* ----------------------------- NUMERICAL VALUES ------------------------------ */

    /**
     * Creates a byte number
     * @param val The number value
     * @return The created number element
     */
    @Nonnull
    public static EbsNumber of(byte val) {
        return new EbsNumber(val, EbsNumberType.BYTE);
    }

    /**
     * Creates a short number
     * @param val The number value
     * @return The created number element
     */
    @Nonnull
    public static EbsNumber of(short val) {
        return new EbsNumber(val, EbsNumberType.SHORT);
    }

    /**
     * Creates an int number
     * @param val The number value
     * @return The created number element
     */
    @Nonnull
    public static EbsNumber of(int val) {
        return new EbsNumber(val, EbsNumberType.INTEGER);
    }

    /**
     * Creates a long number
     * @param val The number value
     * @return The created number element
     */
    @Nonnull
    public static EbsNumber of(long val) {
        return new EbsNumber(val, EbsNumberType.LONG);
    }

    /**
     * Creates a float number
     * @param val The number value
     * @return The created number element
     */
    @Nonnull
    public static EbsNumber of(float val) {
        return new EbsNumber(val, EbsNumberType.FLOAT);
    }

    /**
     * Creates a double number
     * @param val The number value
     * @return The created number element
     */
    @Nonnull
    public static EbsNumber of(double val) {
        return new EbsNumber(val, EbsNumberType.DOUBLE);
    }

    /**
     * Creates a big integer number
     * @param val The number value
     * @return The created number element
     */
    @Nonnull
    public static EbsNumber of(@Nonnull BigInteger val) {
        return new EbsNumber(
                Objects.requireNonNull(val, "Null BigInteger"),
                EbsNumberType.BIG_INTEGER
        );
    }

    /**
     * Creates a number element from the given number
     * @param number The number
     * @return A wrapped number element
     */
    @Nonnull
    public static EbsNumber of(@Nonnull Number number) {
        Objects.requireNonNull(number, "Null number");

        if (number instanceof EbsNumber n) {
            return n.clone();
        }

        var type = Objects.requireNonNull(
                EbsNumberType.BY_TYPE.get(number.getClass()),
                "Unsupported number type: " + number.getClass().getName()
        );

        return new EbsNumber(number, type);
    }

    /**
     * Converts a boolean to a byte
     * @param b The boolean value
     * @return 1 if the given boolean is true, 0 otherwise
     */
    public static byte boolToByte(boolean b) {
        return (byte) (b ? 1 : 0);
    }

    /* ----------------------------- ARRAYS ------------------------------ */

    /**
     * Creates a new array element
     * @param type The array's type
     * @param expectedSize The expected size of the array
     * @param <T> The array's type
     * @return The created element
     */
    @Nonnull
    public static <T extends EbsElement> EbsArray<T> newArray(@Nullable EbsType<T> type, int expectedSize) {
        return new EbsArrayImpl<>(expectedSize, type);
    }

    /**
     * Creates a new array
     * @param type The array's type
     * @param <T> The array's element type
     * @return The created element
     */
    @Nonnull
    public static <T extends EbsElement> EbsArray<T> newArray(@Nullable EbsType<T> type) {
        return newArray(type, DEFAULT_SIZE);
    }

    @Nonnull
    public static EbsArray newArray(int size) {
        return new EbsArrayImpl<>(size);
    }

    @Nonnull
    public static EbsArray newArray() {
        return newArray(DEFAULT_SIZE);
    }

    /**
     * Turns the given number array into an int array
     * @param array The EBS array
     * @return The converted array
     */
    public static int[] toIntArray(EbsArray<EbsNumber> array) {
        int[] result = new int[array.size()];

        for (int i = 0; i < array.size(); i++) {
            result[i] = array.get(i).intValue();
        }

        return result;
    }

    /**
     * Turns the given number array into a float array
     * @param array The EBS array
     * @return The converted array
     */
    public static float[] toFloatArray(EbsArray<EbsNumber> array) {
        float[] result = new float[array.size()];

        for (int i = 0; i < array.size(); i++) {
            result[i] = array.get(i).floatValue();
        }

        return result;
    }

    /**
     * Turns the given number array into a double array
     * @param array The EBS array
     * @return The converted array
     */
    public static double[] toDoubleArray(EbsArray<EbsNumber> array) {
        double[] result = new double[array.size()];

        for (int i = 0; i < array.size(); i++) {
            result[i] = array.get(i).doubleValue();
        }

        return result;
    }

    /**
     * Turns the given number array into a long array
     * @param array The EBS array
     * @return The converted array
     */
    public static long[] toLongArray(EbsArray<EbsNumber> array) {
        long[] result = new long[array.size()];

        for (int i = 0; i < array.size(); i++) {
            result[i] = array.get(i).longValue();
        }

        return result;
    }

    /**
     * Turns the given number array into a byte array
     * @param array The EBS array
     * @return The converted array
     */
    public static byte[] toByteArray(EbsArray<EbsNumber> array) {
        byte[] result = new byte[array.size()];

        for (int i = 0; i < array.size(); i++) {
            result[i] = array.get(i).byteValue();
        }

        return result;
    }

    /**
     * Turns the given number array into a short array
     * @param array The EBS array
     * @return The converted array
     */
    public static short[] toShortArray(EbsArray<EbsNumber> array) {
        short[] result = new short[array.size()];

        for (int i = 0; i < array.size(); i++) {
            result[i] = array.get(i).shortValue();
        }

        return result;
    }

    /**
     * Turns the given primitive array into an EBS array
     * @param arr The array to convert
     * @return The created
     */
    public static EbsArray<EbsNumber> fromIntArray(int... arr) {
        EbsArray<EbsNumber> result = newArray(EbsNumberType.INTEGER, arr.length);

        for (int i: arr) {
            result.add(of(i));
        }

        return result;
    }

    /**
     * Turns the given primitive array into an EBS array
     * @param arr The array to convert
     * @return The created
     */
    public static EbsArray<EbsNumber> fromLongArray(long... arr) {
        EbsArray<EbsNumber> result = newArray(EbsNumberType.LONG, arr.length);

        for (long i: arr) {
            result.add(of(i));
        }

        return result;
    }

    /**
     * Turns the given primitive array into an EBS array
     * @param arr The array to convert
     * @return The created
     */
    public static EbsArray<EbsNumber> fromFloatArray(float... arr) {
        EbsArray<EbsNumber> result = newArray(EbsNumberType.FLOAT, arr.length);

        for (float i: arr) {
            result.add(of(i));
        }

        return result;
    }

    /**
     * Turns the given primitive array into an EBS array
     * @param arr The array to convert
     * @return The created
     */
    public static EbsArray<EbsNumber> fromDoubleArray(double... arr) {
        EbsArray<EbsNumber> result = newArray(EbsNumberType.DOUBLE, arr.length);

        for (double i: arr) {
            result.add(of(i));
        }

        return result;
    }

    /**
     * Turns the given primitive array into an EBS array
     * @param arr The array to convert
     * @return The created
     */
    public static EbsArray<EbsNumber> fromByteArray(byte... arr) {
        EbsArray<EbsNumber> result = newArray(EbsNumberType.BYTE, arr.length);

        for (byte i: arr) {
            result.add(of(i));
        }

        return result;
    }

    /**
     * Turns the given primitive array into an EBS array
     * @param arr The array to convert
     * @return The created
     */
    public static EbsArray<EbsNumber> fromShortArray(short... arr) {
        EbsArray<EbsNumber> result = newArray(EbsNumberType.SHORT, arr.length);

        for (short i: arr) {
            result.add(of(i));
        }

        return result;
    }

    /**
     * Converts a primitive array into an EBS array
     * @param converter The conversion function to use
     * @param type The array's type
     * @param arr The array to convert
     * @param <T> The array's type
     * @param <P> The primitive type
     * @return The converted array
     */
    @SafeVarargs
    public static <T extends EbsElement, P> EbsArray<T> fromPrimitive(Function<P, T> converter, EbsType<T> type, P... arr) {
        return fromPrimitive(Arrays.asList(arr), converter, type);
    }

    /**
     * Converts a primitive array into an EBS array
     * @param collection The array to convert
     * @param converter The conversion function to use
     * @param type The array's type
     * @param <T> The array's type
     * @param <P> The primitive type
     * @return The converted array
     */
    public static <T extends EbsElement, P> EbsArray<T> fromPrimitive(Collection<P> collection, Function<P, T> converter, EbsType<T> type) {
        EbsArray<T> result = newArray(type, collection.size());

        for (P p: collection) {
            result.add(converter.apply(p));
        }

        return result;
    }
}