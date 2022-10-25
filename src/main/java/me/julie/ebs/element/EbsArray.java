package me.julie.ebs.element;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.julie.ebs.type.EbsType;

import java.util.List;
import java.util.function.Function;

public interface EbsArray<T extends EbsElement> extends List<T>, EbsElement {
    /**
     * Gets the type of elements the array stores
     * @return
     */
    EbsType<T> arrayType();

    @Override
    EbsType<EbsArray> getType();

    EbsArray<T> clone();

    EbsArray<T> deepClone();

    EbsArray<T> merge(EbsArray<T> other);

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
}