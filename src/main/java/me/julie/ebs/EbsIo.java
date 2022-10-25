package me.julie.ebs;

import me.julie.ebs.element.EbsElement;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * Utility class to provide easy and quick access to
 * writing and reading {@link EbsElement}s using a global
 * type registry.
 */
public final class EbsIo {
    private EbsIo() {}

    private static final EbsTypeRegistry GLOBAL_TYPE_REGISTRY = new EbsTypeRegistry();

    /**
     * Gets the global EBS type registry
     * @return The type registry instance
     */
    public static EbsTypeRegistry globalTypes() {
        return GLOBAL_TYPE_REGISTRY;
    }

    /** @see EbsTypeRegistry#write(EbsElement, OutputStream) */
    public static void write(EbsElement element, OutputStream stream) throws IOException {
        globalTypes().write(element, stream);
    }

    /** @see EbsTypeRegistry#read(InputStream) */
    public static EbsElement read(InputStream stream) throws IOException {
        return globalTypes().read(stream);
    }

    /** @see EbsTypeRegistry#writeFile(EbsElement, File) */
    public static void writeFile(EbsElement element, File file) throws IOException {
        globalTypes().writeFile(element, file);
    }

    /** @see EbsTypeRegistry#writeFile(EbsElement, Path) */
    public static void writeFile(EbsElement element, Path path) throws IOException {
        globalTypes().writeFile(element, path);
    }

    /** @see EbsTypeRegistry#readFile(File) */
    public static EbsElement readFile(File file) throws IOException {
        return globalTypes().readFile(file);
    }

    /** @see EbsTypeRegistry#readFile(Path)  */
    public static EbsElement readFile(Path path) throws IOException {
        return globalTypes().readFile(path);
    }
}