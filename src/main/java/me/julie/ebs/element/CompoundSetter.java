package me.julie.ebs.element;

import me.julie.ebs.EbsUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public interface CompoundSetter extends CompoundGetter {
    /**
     * Puts a name into the compound
     * @param name The name of the element
     * @param element The element to put
     */
    void put(String name, EbsElement element);

    /**
     * Puts all the entries of the given compound into this compound
     * @param compound The compound to merge with
     */
    void putAll(EbsCompound compound);

    /**
     * Removes the given element
     * @param name the name of the element to remove
     */
    void remove(String name);

    /**
     * Puts a string into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putString(String name, String val) {
        put(name, new EbsString(val));
    }

    /**
     * Puts a UUID into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putUUID(String name, UUID val) {
        put(name, new EbsUUID(val));
    }

    /**
     * Puts a boolean into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putBool(String name, boolean val) {
        put(name, EbsNumber.ofBool(val));
    }

    /**
     * Puts an enum into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putEnum(String name, Enum val) {
        put(name, new EbsEnum<>(val));
    }

    /**
     * Puts a number into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putNumber(String name, Number val) { put(name, EbsNumber.of(val)); }

    /**
     * Puts a byte into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putByte(String name, byte val) { put(name, EbsNumber.ofByte(val)); }

    /**
     * Puts a short into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putShort(String name, short val) { put(name, EbsNumber.ofShort(val)); }

    /**
     * Puts an int into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putInt(String name, int val) { put(name, EbsNumber.ofInt(val)); }

    /**
     * Puts a long into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putLong(String name, long val) { put(name, EbsNumber.ofLong(val)); }

    /**
     * Puts a float into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putFloat(String name, float val) { put(name, EbsNumber.ofFloat(val)); }

    /**
     * Puts a double into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putDouble(String name, double val) { put(name, EbsNumber.ofDouble(val)); }


    /**
     * Puts a string list into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putStringList(String name, Collection<String> val) {
        put(name, EbsArray.fromPrimitive(val, EbsString::new, EbsString.TYPE));
    }

    /**
     * Puts a string array into this compound
     * @param name The name of the array
     * @param arr The array elements
     */
    default void putStringArray(String name, String... arr) {
        putStringList(name, Arrays.asList(arr));
    }

    /**
     * Puts a UUID list into this compound
     * @param name The name of the array
     * @param uuids The list elements
     */
    default void putUUIDList(String name, Collection<UUID> uuids) {
        put(name, EbsArray.fromPrimitive(uuids, EbsUUID::new, EbsUUID.TYPE));
    }

    /**
     * Puts a UUID array into this compound
     * @param name The name of the array
     * @param arr The array elements
     */
    default void putUUIDArray(String name, UUID... arr) {
        putUUIDList(name, Arrays.asList(arr));
    }

    /**
     * Puts an int array into this compound
     * @param name The name of the array
     * @param arr The elements of the array
     */
    default void putIntArray(String name, int... arr) {
        put(name, EbsUtil.fromIntArray(arr));
    }

    /**
     * Puts a long array into this compound
     * @param name The name of the array
     * @param arr The elements of the array
     */
    default void putLongArray(String name, long... arr) {
        put(name, EbsUtil.fromLongArray(arr));
    }

    /**
     * Puts a float array into this compound
     * @param name The name of the array
     * @param arr The elements of the array
     */
    default void putFloatArray(String name, float... arr) {
        put(name, EbsUtil.fromFloatArray(arr));
    }

    /**
     * Puts a double array into this compound
     * @param name The name of the array
     * @param arr The elements of the array
     */
    default void putDoubleArray(String name, double... arr) {
        put(name, EbsUtil.fromDoubleArray(arr));
    }

    /**
     * Puts a byte array into this compound
     * @param name The name of the array
     * @param arr The elements of the array
     */
    default void putByteArray(String name, byte... arr) {
        put(name, EbsUtil.fromByteArray(arr));
    }

    /**
     * Puts a short array into this compound
     * @param name The name of the array
     * @param arr The elements of the array
     */
    default void putShortArray(String name, short... arr) {
        put(name, EbsUtil.fromShortArray(arr));
    }
}
