package me.julie.ebs.visitor;

import me.julie.ebs.element.*;
import me.julie.ebs.element.EbsArray;
import me.julie.ebs.element.EbsCompound;

/**
 * An EBS visitor that acts as a supported Consumer for EbsElements
 */
public interface EbsVisitor {
    /**
     * Visit an unknown element. Unknown elements are any elements that are not
     * built into EBS
     * @param element The element to visit
     */
    void visitUnknown(EbsElement element);

    /**
     * Visit an unknown valued element.
     * @see EbsVisitor#visitUnknown(EbsElement)
     * @param valuedElement The element to visit
     */
    default void visitUnknownValued(EbsValuedElement<?> valuedElement) {
        visitUnknown(valuedElement);
    }

    /**
     * Visits an array element
     * @param array The element to visit
     */
    void visitArray(EbsArray array);

    /**
     * Visits a compound element
     * @param compound The element to visit
     */
    void visitCompound(EbsCompound compound);

    /**
     * Visits a number element
     * @param number The element to visit
     */
    void visitNumber(EbsNumber number);

    /**
     * Visits a UUID element
     * @param uuid The element to visit
     */
    void visitUUID(EbsUUID uuid);

    /**
     * Visits a string element
     * @param string The element to visit
     */
    void visitString(EbsString string);

    /**
     * Visits a boolean element
     * @param bool The element
     */
    void visitBool(EbsBoolean bool);
}