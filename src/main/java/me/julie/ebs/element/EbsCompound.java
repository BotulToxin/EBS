package me.julie.ebs.element;

import me.julie.ebs.type.EbsType;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface EbsCompound extends CompoundSetter, EbsElement {
    /**
     * Creates a compound array with a size of 16
     * @return The created array
     */
    static EbsCompound create() {
        return create(16);
    }

    /**
     * Creates a compound with the given size
     * @param expectedSize The size to create the compound with
     * @return The created compound
     */
    static EbsCompound create(int expectedSize) {
        return new EbsCompoundImpl(expectedSize);
    }

    /**
     * Gets the entry set of the compound
     * @return The compound's entry set
     */
    Set<Map.Entry<String, EbsElement>> entrySet();

    /**
     * Gets the key set of this compound
     * @return The key set
     */
    Set<String> keySet();

    /**
     * Gets all values held by this compound
     * @return This compound's values
     */
    Collection<EbsElement> values();

    /**
     * Clears this compound
     */
    void clear();

    @Override
    EbsType<EbsCompound> getType();

    /**
     * Makes a simple clone of the compound and places all
     * this compounds elements into it
     * @return The cloned compound
     */
    EbsCompound clone();

    /**
     * Makes a deep clone of this compound.
     * Creates a new compound and clones each
     * entry of this compound and places it into
     * the clone
     *
     * @return The cloned compound
     */
    EbsCompound deepClone();
}
