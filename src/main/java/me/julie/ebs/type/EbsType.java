package me.julie.ebs.type;

import me.julie.ebs.element.EbsElement;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * An EBS type
 * @param <T> The type's element type
 */
public interface EbsType<T extends EbsElement> {
    /**
     * Reads the element from the given input
     * @param input The input to read from
     * @return The read element
     * @throws IOException When any error occurs
     */
    T read(DataInput input) throws IOException;

    /**
     * Writes the element into the given output
     * @param output The output to write to
     * @param val The value to write
     * @throws IOException When any error occurs
     */
    void write(DataOutput output, T val) throws IOException;
}
