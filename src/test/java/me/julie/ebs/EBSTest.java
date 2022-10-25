package me.julie.ebs;

import me.julie.ebs.element.EbsCompound;
import me.julie.ebs.element.EbsElements;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class EBSTest {
    @Test
    void write() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EbsCompound compound = EbsElements.newCompound();
        compound.putString("string_1", "string");
        compound.putInt("test_int", 1341);
        compound.putFloat("test_float", 1564.546f);

        assertDoesNotThrow(() -> {
            EbsIo.write(compound, outputStream);
        });

        byte[] bytes = outputStream.toByteArray();
        AtomicReference<EbsCompound> secondRef = new AtomicReference<>(null);

        assertDoesNotThrow(() -> {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            secondRef.set((EbsCompound) EbsIo.read(inputStream));
        });

        var second = secondRef.get();
        assertNotNull(second);

        assertEquals(second.size(), compound.size());

        assertEquals(second.getString("string_1"), compound.getString("string_1"));
        assertEquals(second.getInt("test_int"), compound.getInt("test_int"));
        assertEquals(second.getFloat("test_float"), compound.getFloat("test_float"));
    }
}