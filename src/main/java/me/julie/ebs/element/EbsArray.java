package me.julie.ebs.element;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.julie.ebs.type.EbsType;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public interface EbsArray<T extends EbsElement> extends List<T>, EbsElement {
    /**
     * Creates an array with the given element type and size
     * @param type The array's type
     * @param expectedSize The expected size of the array
     * @param <T> The array's type
     * @return The created array
     */
    static <T extends EbsElement> EbsArray<T> create(EbsType<T> type, int expectedSize) {
        return new EbsArrayImpl<>(expectedSize, type);
    }

    /**
     * Creates an array with the given type and a size of 16
     * @param type The type of the array
     * @param <T> The array's type
     * @return The created array
     */
    static <T extends EbsElement> EbsArray<T> create(EbsType<T> type) {
        return create(type, 16);
    }

    /**
     * Gets the type of elements the array stores
     * @return
     */
    EbsType<T> arrayType();

    @Override
    EbsType<EbsArray> getType();
    EbsArray<T> clone();

    /**
     * Converts this array into a primitive array
     * @param converter The function to use for conversion
     * @param <P> The primitive type
     * @return The converted list
     */
    default <P> List<P> toPrimitive(Function<T, P> converter) {
        List<P> result = new ObjectArrayList<>();

        for (T t: this) {
            result.add(converter.apply(t));
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
    static <T extends EbsElement, P> EbsArray<T> fromPrimitive(Function<P, T> converter, EbsType<T> type, P... arr) {
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
    static <T extends EbsElement, P> EbsArray<T> fromPrimitive(Collection<P> collection, Function<P, T> converter, EbsType<T> type) {
        EbsArray<T> result = create(type, collection.size());

        for (P p: collection) {
            result.add(converter.apply(p));
        }

        return result;
    }
}
