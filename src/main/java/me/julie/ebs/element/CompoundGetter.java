package me.julie.ebs.element;

import me.julie.ebs.EbsUtil;
import me.julie.ebs.type.EbsArrayType;
import me.julie.ebs.type.EbsNumericalTypes;
import me.julie.ebs.type.EbsType;

import java.math.BigInteger;
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
    EbsElement get(String name);

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
    default boolean contains(String name) {
        return get(name) != null;
    }

    /**
     * Checks if this contains an element with the given name
     * and type
     * @param name The name to check
     * @param type The type to check
     * @return True, if an element was found that matched the name and type, false otherwise
     */
    default boolean contains(String name, EbsType type) {
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
    default <T extends EbsElement> T get(String name, EbsType<T> type) {
        EbsElement element = get(name);
        return element.getType().equals(type) ? (T) element : null;
    }

    /**
     * Gets a numerical element
     * @param name The name of the element
     * @return The number, null, if the element wasn't found
     *         or if the element was not a number
     */
    default EbsNumber getNumber(String name) {
        EbsElement element = get(name);
        return (element instanceof EbsNumber number) ? number : null;
    }

    /**
     * Gets a numerical element with the given numerical type.
     * @see EbsNumericalTypes
     * @param name The name of the element
     * @param numberType The numerical type
     * @return The gotten element, null, if not found or if the number type did not match
     */
    default EbsNumber getNumber(String name, EbsType<EbsNumber> numberType) {
        EbsNumber num = getNumber(name);
        if(num == null) return null;

        return num.getType().equals(numberType) ? num : null;
    }

    /**
     * Same as {@link CompoundGetter#getBool(String, boolean)}  with def as 'false'
     * @see CompoundGetter#getBool(String, boolean)
     */
    default boolean getBool(String name) { return getBool(name, false); }

    /**
     * Gets a boolean
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default boolean getBool(String name, boolean def) {
        return getByte(name, EbsNumber.boolToByte(def)) != 0;
    }

    /**
     * Same as {@link CompoundGetter#getByte(String, byte)}  with def as '0'
     * @see CompoundGetter#getByte(String, byte)
     */
    default byte getByte(String name) { return getByte(name, (byte) 0); }

    /**
     * Gets a byte
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default byte getByte(String name, byte def) {
        EbsNumber num = getNumber(name, EbsNumericalTypes.BYTE);
        return num == null ? def : num.byteValue();
    }

    /**
     * Same as {@link CompoundGetter#getShort(String, short)} with def as '0'
     * @see CompoundGetter#getShort(String, short)
     */
    default short getShort(String name) { return getShort(name, (short) 0); }

    /**
     * Gets a short
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default short getShort(String name, short def) {
        EbsNumber num = getNumber(name, EbsNumericalTypes.SHORT);
        return num == null ? def : num.shortValue();
    }

    /**
     * Same as {@link CompoundGetter#getInt(String, int)} with def as '0'
     * @see CompoundGetter#getInt(String, int)
     */
    default int getInt(String name) { return getInt(name, 0); }

    /**
     * Gets an integer
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default int getInt(String name, int def) {
        EbsNumber num = getNumber(name, EbsNumericalTypes.INTEGER);
        return num == null ? def : num.intValue();
    }

    /**
     * Same as {@link CompoundGetter#getLong(String, long)} with def as '0'
     * @see CompoundGetter#getLong(String, long)
     */
    default long getLong(String name) { return getLong(name, 0L); }

    /**
     * Gets a long
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default long getLong(String name, long def) {
        EbsNumber num = getNumber(name, EbsNumericalTypes.LONG);
        return num == null ? def : num.longValue();
    }

    /**
     * Same as {@link CompoundGetter#getFloat(String, float)} with def as '0'
     * @see CompoundGetter#getFloat(String, float)
     */
    default float getFloat(String name) { return getFloat(name, 0F); }

    /**
     * Gets a float
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default float getFloat(String name, float def) {
        EbsNumber num = getNumber(name, EbsNumericalTypes.FLOAT);
        return num == null ? def : num.floatValue();
    }

    /**
     * Same as {@link CompoundGetter#getDouble(String, double)} with def as '0'
     * @see CompoundGetter#getDouble(String, double)
     */
    default double getDouble(String name) { return getDouble(name, 0D); }

    /**
     * Gets a double
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default double getDouble(String name, double def) {
        EbsNumber num = getNumber(name, EbsNumericalTypes.DOUBLE);
        return num == null ? def : num.doubleValue();
    }

    /**
     * Same as {@link CompoundGetter#getBigInteger(String, BigInteger)} with def as {@link BigInteger#ZERO}
     * @see CompoundGetter#getBigInteger(String, BigInteger)
     */
    default BigInteger getBigInteger(String name) { return getBigInteger(name, BigInteger.ZERO); }

    /**
     * Gets a big integer
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default BigInteger getBigInteger(String name, BigInteger def) {
        EbsNumber num = getNumber(name, EbsNumericalTypes.BIG_INTEGER);
        return num == null ? def : (BigInteger) num.value();
    }

    /**
     * Same as {@link CompoundGetter#getString(String, String)} with def as 'null'
     * @see CompoundGetter#getString(String, String)
     */
    default String getString(String name) { return getString(name, null); }

    /**
     * Gets a string
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default String getString(String name, String def) {
        EbsElement element = get(name, EbsString.TYPE);
        return element == null ? def : ((EbsString) element).value();
    }

    /**
     * Same as {@link CompoundGetter#getUUID(String, UUID)}  with def as 'null'
     * @see CompoundGetter#getUUID(String, UUID)
     */
    default UUID getUUID(String name) { return getUUID(name, null); }

    /**
     * Gets a UUID
     * @param name The name of the element
     * @param def The default return value
     * @return Either the value of the gotten element or def
     */
    default UUID getUUID(String name, UUID def) {
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
    default EbsArray getArray(String name) {
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
    default <T extends EbsElement> EbsArray<T> getArray(String name, EbsType<T> elementType) {
        EbsArray array = getArray(name);
        if(array == null) return null;
        return array.arrayType().equals(elementType) ? array : null;
    }

    /**
     * Gets an integer array
     * @param name The name of the array
     *
     * @return The found array, null, if the
     *         element doesn't exist or isn't an int array
     */
    default int[] getIntArray(String name) {
        EbsArray<EbsNumber> arr = getArray(name, EbsNumericalTypes.INTEGER);
        return arr == null ? null : EbsUtil.toIntArray(arr);
    }

    /**
     * Gets a long array
     * @param name The name of the array
     *
     * @return The found array, null, if the
     *         element doesn't exist or isn't a long array
     */
    default long[] getLongArray(String name) {
        EbsArray<EbsNumber> arr = getArray(name, EbsNumericalTypes.LONG);
        return arr == null ? null : EbsUtil.toLongArray(arr);
    }

    /**
     * Gets a double array
     * @param name The name of the array
     *
     * @return The found array, null, if the
     *         element doesn't exist or isn't a double array
     */
    default double[] getDoubleArray(String name) {
        EbsArray<EbsNumber> arr = getArray(name, EbsNumericalTypes.DOUBLE);
        return arr == null ? null : EbsUtil.toDoubleArray(arr);
    }

    /**
     * Gets a float array
     * @param name The name of the array
     *
     * @return The found array, null, if the
     *         element doesn't exist or isn't a float array
     */
    default float[] getFloatArray(String name) {
        EbsArray<EbsNumber> arr = getArray(name, EbsNumericalTypes.FLOAT);
        return arr == null ? null : EbsUtil.toFloatArray(arr);
    }

    /**
     * Gets a byte array
     * @param name The name of the array
     *
     * @return The found array, null, if the
     *         element doesn't exist or isn't a byte array
     */
    default byte[] getByteArray(String name) {
        EbsArray<EbsNumber> arr = getArray(name, EbsNumericalTypes.BYTE);
        return arr == null ? null : EbsUtil.toByteArray(arr);
    }

    /**
     * Gets a short array
     * @param name The name of the array
     *
     * @return The found array, null, if the
     *         element doesn't exist or isn't a short array
     */
    default short[] getShortArray(String name) {
        EbsArray<EbsNumber> arr = getArray(name, EbsNumericalTypes.SHORT);
        return arr == null ? null : EbsUtil.toShortArray(arr);
    }

    /**
     * Gets a list of strings
     * @param name The name of the array
     *
     * @return The found array, null, if the
     *         element doesn't exist or isn't a string array
     */
    default List<String> getStringList(String name) {
        EbsArray<EbsString> arr = getArray(name, EbsString.TYPE);
        return arr == null ? null : arr.toPrimitive(AbstractValuedElement::value);
    }

    /**
     * Gets a list of UUIDs
     * @param name The name of the array
     *
     * @return The found array, null, if the
     *         element doesn't exist or isn't a UUID array
     */
    default List<UUID> getUUIDList(String name) {
        EbsArray<EbsUUID> arr = getArray(name, EbsUUID.TYPE);
        return arr == null ? null : arr.toPrimitive(AbstractValuedElement::value);
    }

    /**
     * Same as {@link CompoundGetter#getEnum(String, Class, Enum)}  with def as 'null'
     * @see CompoundGetter#getEnum(String, Class, Enum)
     */
    default <E extends Enum<E>> E getEnum(String name, Class<E> clazz) { return getEnum(name, clazz, null); }

    /**
     * Gets an enum
     * @param name The name of the element
     * @param clazz The enum's class
     * @param def The default return value
     * @param <E> The enum type
     * @return The enum, null, if the element doesn't exist or isn't an enum
     */
    default <E extends Enum<E>> E getEnum(String name, Class<E> clazz, E def) {
        EbsEnum ebsEnum = get(name, EbsEnum.TYPE);
        if(ebsEnum == null) return def;

        return ebsEnum.getEnumClass() == clazz ? (E) ebsEnum.value() : def;
    }
}
