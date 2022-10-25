package me.julie.ebs.element;

import me.julie.ebs.type.EbsArrayType;
import me.julie.ebs.type.EbsCompoundType;
import me.julie.ebs.type.EbsNumberType;
import me.julie.ebs.type.EbsType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public interface CompoundGetter {
    /**
     * Gets the size of the compound
     * @return The compound's size
     */
    int size();

    /**
     * Gets an element in the compound
     * @param name The name of the element to get
     * @return The gotten element, null, if not found
     */
    @Nullable EbsElement get(@Nonnull String name);

    /**
     * Checks if the compound is empty
     * @return True, if the size < 1, false otherwise
     */
    default boolean isEmpty() {
        return size() < 1;
    }

    /**
     * Checks if this contains an element with the given name
     * @param name The name to check
     * @return True, if an element with the given name was found, false otherwise
     */
    default boolean contains(@Nonnull String name) {
        return get(name) != null;
    }

    /**
     * Checks if this contains an element with the given name
     * and type
     * @param name The name to check
     * @param type The type to check
     * @return True, if an element was found that matched the name and type, false otherwise
     */
    default boolean contains(@Nonnull String name, @Nonnull EbsType type) {
        return get(name, type) != null;
    }

    /**
     * Gets an element with the given name and type
     * @param name The element's name
     * @param type The type the element must have
     *
     * @param <T> The element's type
     *
     * @return The gotten element, null, if no element was
     *         found or the found element's type did not
     *         match the given one
     */
    @Nullable
    default <T extends EbsElement> T get(@Nonnull String name, @Nonnull EbsType<T> type) {
        EbsElement element = get(name);

        if (element == null) {
            return null;
        }

        return element.getType().equals(type) ? (T) element : null;
    }

    /**
     * Gets a numerical element
     * @param name The name of the element
     * @return The number, null, if the element wasn't found
     *         or if the element was not a number
     */
    @Nullable
    default EbsNumber getNumber(@Nonnull String name) {
        EbsElement element = get(name);
        return (element instanceof EbsNumber number) ? number : null;
    }

    /**
     * Gets a numerical element with the given numerical type.
     * @see EbsNumberType
     * @param name The name of the element
     * @param numberType The numerical type
     * @return The gotten element, null, if not found or if the number type did not match
     */
    @Nullable
    default EbsNumber getNumber(@Nonnull String name, @Nonnull EbsType<EbsNumber> numberType) {
        EbsNumber num = getNumber(name);

        if (num == null) {
            return null;
        }

        return num.getType().equals(numberType) ? num : null;
    }

    /**
     * Same as {@link CompoundGetter#getBool(String, boolean)}  with def as 'false'
     * @see CompoundGetter#getBool(String, boolean)
     */
    default boolean getBool(@Nonnull String name) {
        return getBool(name, false);
    }

    /**
     * Gets a boolean
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default boolean getBool(@Nonnull String name, boolean def) {
        return getByte(name, EbsElements.boolToByte(def)) != 0;
    }

    /**
     * Same as {@link CompoundGetter#getByte(String, byte)}  with def as '0'
     * @see CompoundGetter#getByte(String, byte)
     */
    default byte getByte(@Nonnull String name) {
        return getByte(name, (byte) 0);
    }

    /**
     * Gets a byte
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default byte getByte(@Nonnull String name, byte def) {
        EbsNumber num = getNumber(name, EbsNumberType.BYTE);
        return num == null ? def : num.byteValue();
    }

    /**
     * Same as {@link CompoundGetter#getShort(String, short)} with def as '0'
     * @see CompoundGetter#getShort(String, short)
     */
    default short getShort(@Nonnull String name) {
        return getShort(name, (short) 0);
    }

    /**
     * Gets a short
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default short getShort(@Nonnull String name, short def) {
        EbsNumber num = getNumber(name, EbsNumberType.SHORT);
        return num == null ? def : num.shortValue();
    }

    /**
     * Same as {@link CompoundGetter#getInt(String, int)} with def as '0'
     * @see CompoundGetter#getInt(String, int)
     */
    default int getInt(@Nonnull String name) {
        return getInt(name, 0);
    }

    /**
     * Gets an integer
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default int getInt(@Nonnull String name, int def) {
        EbsNumber num = getNumber(name, EbsNumberType.INTEGER);
        return num == null ? def : num.intValue();
    }

    /**
     * Same as {@link CompoundGetter#getLong(String, long)} with def as '0'
     * @see CompoundGetter#getLong(String, long)
     */
    default long getLong(@Nonnull String name) {
        return getLong(name, 0L);
    }

    /**
     * Gets a long
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default long getLong(@Nonnull String name, long def) {
        EbsNumber num = getNumber(name, EbsNumberType.LONG);
        return num == null ? def : num.longValue();
    }

    /**
     * Same as {@link CompoundGetter#getFloat(String, float)} with def as '0'
     * @see CompoundGetter#getFloat(String, float)
     */
    default float getFloat(@Nonnull String name) {
        return getFloat(name, 0F);
    }

    /**
     * Gets a float
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default float getFloat(@Nonnull String name, float def) {
        EbsNumber num = getNumber(name, EbsNumberType.FLOAT);
        return num == null ? def : num.floatValue();
    }

    /**
     * Same as {@link CompoundGetter#getDouble(String, double)} with def as '0'
     * @see CompoundGetter#getDouble(String, double)
     */
    default double getDouble(@Nonnull String name) {
        return getDouble(name, 0D);
    }

    /**
     * Gets a double
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default double getDouble(@Nonnull String name, double def) {
        EbsNumber num = getNumber(name, EbsNumberType.DOUBLE);
        return num == null ? def : num.doubleValue();
    }

    /**
     * Same as {@link CompoundGetter#getBigInteger(String, BigInteger)} with def as {@link BigInteger#ZERO}
     * @see CompoundGetter#getBigInteger(String, BigInteger)
     */
    default BigInteger getBigInteger(@Nonnull String name) {
        return getBigInteger(name, BigInteger.ZERO);
    }

    /**
     * Gets a big integer
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default BigInteger getBigInteger(@Nonnull String name, BigInteger def) {
        EbsNumber num = getNumber(name, EbsNumberType.BIG_INTEGER);
        return num == null ? def : (BigInteger) num.value();
    }

    /**
     * Same as {@link CompoundGetter#getString(String, String)} with def as 'null'
     * @see CompoundGetter#getString(String, String)
     */
    default String getString(@Nonnull String name) {
        return getString(name, null);
    }

    /**
     * Gets a string
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default String getString(@Nonnull String name, @Nullable String def) {
        EbsElement element = get(name, EbsString.TYPE);
        return element == null ? def : ((EbsString) element).value();
    }

    /**
     * Same as {@link CompoundGetter#getUUID(String, UUID)}  with def as 'null'
     * @see CompoundGetter#getUUID(String, UUID)
     */
    default UUID getUUID(@Nonnull String name) {
        return getUUID(name, null);
    }

    /**
     * Gets a UUID
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default UUID getUUID(@Nonnull String name, @Nullable UUID def) {
        EbsElement element = get(name, EbsUUID.TYPE);
        return element == null ? def : ((EbsUUID) element).value();
    }

    /**
     * Gets an array element
     *
     * @param name The name of the element
     *
     * @return The gotten array, null, if no element
     *         with the given name exists or the found
     *         element is not an array
     */
    @Nullable
    default EbsArray getArray(@Nonnull String name) {
        return get(name, EbsArrayType.getInstance());
    }

    /**
     * Gets an array with the given name and type
     * @param name The array name
     * @param elementType The array element's type
     *
     * @param <T> The type of array
     *
     * @return The found array, null, if the element doesn't exist,
     *         if the element isn't an array
     *         or the array's type is no the given type
     */
    @Nullable
    default <T extends EbsElement> EbsArray<T> getArray(@Nonnull String name, @Nonnull EbsType<T> elementType) {
        EbsArray array = getArray(name);

        if (array == null) {
            return null;
        }

        return array.arrayType().equals(elementType) ? array : null;
    }

    /**
     * Gets an integer array
     * @param name The name of the array
     *
     * @return The found array, null, if the
     *         element doesn't exist or isn't an int array
     */
    @Nullable
    default int[] getIntArray(@Nonnull String name) {
        EbsArray<EbsNumber> arr = getArray(name, EbsNumberType.INTEGER);
        return arr == null ? null : EbsElements.toIntArray(arr);
    }

    /**
     * Gets a long array
     * @param name The name of the array
     *
     * @return The found array, null, if the
     *         element doesn't exist or isn't a long array
     */
    @Nullable
    default long[] getLongArray(@Nonnull String name) {
        EbsArray<EbsNumber> arr = getArray(name, EbsNumberType.LONG);
        return arr == null ? null : EbsElements.toLongArray(arr);
    }

    /**
     * Gets a double array
     * @param name The name of the array
     *
     * @return The found array, null, if the
     *         element doesn't exist or isn't a double array
     */
    @Nullable
    default double[] getDoubleArray(@Nonnull String name) {
        EbsArray<EbsNumber> arr = getArray(name, EbsNumberType.DOUBLE);
        return arr == null ? null : EbsElements.toDoubleArray(arr);
    }

    /**
     * Gets a float array
     * @param name The name of the array
     *
     * @return The found array, null, if the
     *         element doesn't exist or isn't a float array
     */
    @Nullable
    default float[] getFloatArray(@Nonnull String name) {
        EbsArray<EbsNumber> arr = getArray(name, EbsNumberType.FLOAT);
        return arr == null ? null : EbsElements.toFloatArray(arr);
    }

    /**
     * Gets a byte array
     * @param name The name of the array
     *
     * @return The found array, null, if the
     *         element doesn't exist or isn't a byte array
     */
    @Nullable
    default byte[] getByteArray(@Nonnull String name) {
        EbsArray<EbsNumber> arr = getArray(name, EbsNumberType.BYTE);
        return arr == null ? null : EbsElements.toByteArray(arr);
    }

    /**
     * Gets a short array
     * @param name The name of the array
     *
     * @return The found array, null, if the
     *         element doesn't exist or isn't a short array
     */
    @Nullable
    default short[] getShortArray(@Nonnull String name) {
        EbsArray<EbsNumber> arr = getArray(name, EbsNumberType.SHORT);
        return arr == null ? null : EbsElements.toShortArray(arr);
    }

    /**
     * Gets a list of strings
     * @param name The name of the array
     *
     * @return The found array, empty list, if the
     *         element doesn't exist or isn't a string array
     */
    @Nonnull
    default List<String> getStringList(@Nonnull String name) {
        EbsArray<EbsString> arr = getArray(name, EbsString.TYPE);
        return arr == null ? Collections.emptyList() : arr.toPrimitive(AbstractValuedElement::value);
    }

    /**
     * Gets a list of UUIDs
     * @param name The name of the array
     *
     * @return The found array, empty list, if the
     *         element doesn't exist or isn't a UUID array
     */
    @Nonnull
    default List<UUID> getUUIDList(@Nonnull String name) {
        EbsArray<EbsUUID> arr = getArray(name, EbsUUID.TYPE);
        return arr == null ? Collections.emptyList() : arr.toPrimitive(AbstractValuedElement::value);
    }

    /**
     * Gets a compound object
     * @param name The name of the compound
     *
     * @return The gotten compound, null, if not found,
     *         or if the given entry isn't a compound
     */
    default @Nullable EbsCompound getCompound(@Nonnull String name) {
        return get(name, EbsCompoundType.getInstance());
    }
}