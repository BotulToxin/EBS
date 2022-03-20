package me.julie.ebs.element;

import me.julie.ebs.type.EbsType;
import me.julie.ebs.visitor.EbsVisitor;

/**
 * A single EBS element, it must be cloneable and specify a {@link EbsType} type
 * that allows for the element to be written directly to a {@link java.io.DataOutput} and
 * read from a {@link java.io.DataInput}
 */
public interface EbsElement extends Cloneable {
    /**
     * Gets this element's type
     * @return The element's type
     */
    EbsType<? extends EbsElement> getType();

    /**
     * Clones the element
     * @return The cloned element
     */
    EbsElement clone();

    /**
     * Accepts the given visitor
     * @param visitor The visitor that's visiting this element
     */
    default void accept(EbsVisitor visitor) {
        visitor.visitUnknown(this);
    }
}
