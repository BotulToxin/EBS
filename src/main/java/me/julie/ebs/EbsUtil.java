package me.julie.ebs;

import me.julie.ebs.element.EbsNumber;
import me.julie.ebs.element.EbsArray;
import me.julie.ebs.type.EbsNumericalTypes;

public final class EbsUtil {
    private EbsUtil() {}

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
        EbsArray<EbsNumber> result = EbsArray.create(EbsNumericalTypes.INTEGER, arr.length);

        for (int i: arr) {
            result.add(EbsNumber.ofInt(i));
        }

        return result;
    }

    /**
     * Turns the given primitive array into an EBS array
     * @param arr The array to convert
     * @return The created
     */
    public static EbsArray<EbsNumber> fromLongArray(long... arr) {
        EbsArray<EbsNumber> result = EbsArray.create(EbsNumericalTypes.LONG, arr.length);

        for (long i: arr) {
            result.add(EbsNumber.ofLong(i));
        }

        return result;
    }

    /**
     * Turns the given primitive array into an EBS array
     * @param arr The array to convert
     * @return The created
     */
    public static EbsArray<EbsNumber> fromFloatArray(float... arr) {
        EbsArray<EbsNumber> result = EbsArray.create(EbsNumericalTypes.FLOAT, arr.length);

        for (float i: arr) {
            result.add(EbsNumber.ofFloat(i));
        }

        return result;
    }

    /**
     * Turns the given primitive array into an EBS array
     * @param arr The array to convert
     * @return The created
     */
    public static EbsArray<EbsNumber> fromDoubleArray(double... arr) {
        EbsArray<EbsNumber> result = EbsArray.create(EbsNumericalTypes.DOUBLE, arr.length);

        for (double i: arr) {
            result.add(EbsNumber.ofDouble(i));
        }

        return result;
    }

    /**
     * Turns the given primitive array into an EBS array
     * @param arr The array to convert
     * @return The created
     */
    public static EbsArray<EbsNumber> fromByteArray(byte... arr) {
        EbsArray<EbsNumber> result = EbsArray.create(EbsNumericalTypes.BYTE, arr.length);

        for (byte i: arr) {
            result.add(EbsNumber.ofByte(i));
        }

        return result;
    }

    /**
     * Turns the given primitive array into an EBS array
     * @param arr The array to convert
     * @return The created
     */
    public static EbsArray<EbsNumber> fromShortArray(short... arr) {
        EbsArray<EbsNumber> result = EbsArray.create(EbsNumericalTypes.SHORT, arr.length);

        for (short i: arr) {
            result.add(EbsNumber.ofShort(i));
        }

        return result;
    }
}