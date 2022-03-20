package me.julie.ebs;

import me.julie.ebs.type.EbsType;

/**
 * The registry of EBS element types
 */
public interface ElementTypeRegistry {
    /**
     * Gets the size of the array
     * @return The registry's size
     */
    int size();

    /**
     * Gets the serialization ID of the given type
     * @param type The type to get the ID of
     * @return The type's ID, or -1, if the type is not registered
     */
    int identifierOf(EbsType type);

    /**
     * Gets the type by the given ID
     * @param id The ID to get the type of
     * @return The type, null, if there's no type with the given ID registered.
     */
    EbsType get(int id);

    /**
     * Registers the given type
     * @param type The type to register
     * @throws IllegalArgumentException If the given type is already registered
     */
    void register(EbsType type) throws IllegalArgumentException;

    /**
     * Checks if the given type is registered
     * @param type The type to check
     * @return True, if the type is registered, false otherwise
     */
    boolean isRegistered(EbsType type);
}
