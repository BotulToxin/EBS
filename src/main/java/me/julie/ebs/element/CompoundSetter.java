package me.julie.ebs.element;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public interface CompoundSetter extends CompoundGetter {
    /**
     * Puts a name into the compound
     * @param name The name of the element
     * @param element The element to put
     */
    void put(@Nonnull String name, @Nonnull EbsElement element);

    /**
     * Puts all the entries of the given compound into this compound
     * @param compound The compound to merge with
     */
    void putAll(@Nonnull EbsCompound compound);

    /**
     * Removes the given element
     * @param name the name of the element to remove
     */
    void remove(@Nonnull String name);

    /**
     * Puts a string into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putString(@Nonnull String name, @Nonnull String val) {
        put(name, new EbsString(val));
    }

    /**
     * Puts a UUID into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putUUID(@Nonnull String name, @Nonnull UUID val) {
        put(name, new EbsUUID(val));
    }

    /**
     * Puts a boolean into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putBool(@Nonnull String name, boolean val) {
        put(name, EbsElements.of(val));
    }

    /**
     * Puts a number into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putNumber(@Nonnull String name, Number val) {
        put(name, EbsElements.of(val));
    }

    /**
     * Puts a byte into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putByte(@Nonnull String name, byte val) {
        put(name, EbsElements.of(val));
    }

    /**
     * Puts a short into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putShort(@Nonnull String name, short val) {
        put(name, EbsElements.of(val));
    }

    /**
     * Puts an int into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putInt(@Nonnull String name, int val) {
        put(name, EbsElements.of(val));
    }

    /**
     * Puts a long into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putLong(@Nonnull String name, long val) {
        put(name, EbsElements.of(val));
    }

    /**
     * Puts a float into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putFloat(@Nonnull String name, float val) {
        put(name, EbsElements.of(val));
    }

    /**
     * Puts a double into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putDouble(@Nonnull String name, double val) {
        put(name, EbsElements.of(val));
    }


    /**
     * Puts a string list into this compound
     * @param name The name of the element
     * @param val The value of the element
     */
    default void putStringList(@Nonnull String name, @Nonnull Collection<String> val) {
        put(name, EbsElements.fromPrimitive(val, EbsString::new, EbsString.TYPE));
    }

    /**
     * Puts a string array into this compound
     * @param name The name of the array
     * @param arr The array elements
     */
    default void putStringArray(@Nonnull String name, @Nonnull String... arr) {
        putStringList(name, Arrays.asList(arr));
    }

    /**
     * Puts a UUID list into this compound
     * @param name The name of the array
     * @param uuids The list elements
     */
    default void putUUIDList(@Nonnull String name, @Nonnull Collection<UUID> uuids) {
        put(name, EbsElements.fromPrimitive(uuids, EbsUUID::new, EbsUUID.TYPE));
    }

    /**
     * Puts a UUID array into this compound
     * @param name The name of the array
     * @param arr The array elements
     */
    default void putUUIDArray(@Nonnull String name, @Nonnull UUID... arr) {
        putUUIDList(name, Arrays.asList(arr));
    }

    /**
     * Puts an int array into this compound
     * @param name The name of the array
     * @param arr The elements of the array
     */
    default void putIntArray(@Nonnull String name, @Nonnull int... arr) {
        put(name, EbsElements.fromIntArray(arr));
    }

    /**
     * Puts a long array into this compound
     * @param name The name of the array
     * @param arr The elements of the array
     */
    default void putLongArray(@Nonnull String name, @Nonnull long... arr) {
        put(name, EbsElements.fromLongArray(arr));
    }

    /**
     * Puts a float array into this compound
     * @param name The name of the array
     * @param arr The elements of the array
     */
    default void putFloatArray(@Nonnull String name, @Nonnull float... arr) {
        put(name, EbsElements.fromFloatArray(arr));
    }

    /**
     * Puts a double array into this compound
     * @param name The name of the array
     * @param arr The elements of the array
     */
    default void putDoubleArray(@Nonnull String name, @Nonnull double... arr) {
        put(name, EbsElements.fromDoubleArray(arr));
    }

    /**
     * Puts a byte array into this compound
     * @param name The name of the array
     * @param arr The elements of the array
     */
    default void putByteArray(@Nonnull String name, @Nonnull byte... arr) {
        put(name, EbsElements.fromByteArray(arr));
    }

    /**
     * Puts a short array into this compound
     * @param name The name of the array
     * @param arr The elements of the array
     */
    default void putShortArray(@Nonnull String name, @Nonnull short... arr) {
        put(name, EbsElements.fromShortArray(arr));
    }
}