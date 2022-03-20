package me.julie.ebs.element;

import me.julie.ebs.visitor.EbsVisitor;

/**
 * An EBS element that holds a single immutable value
 * @param <T> The type of value
 */
public interface EbsValuedElement<T> extends EbsElement {
    /**
     * Gets the value held by this element
     * @return The value held by this element
     */
    T value();

    @Override
    default void accept(EbsVisitor visitor) {
        visitor.visitUnknownValued(this);
    }
}
