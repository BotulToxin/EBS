package me.julie.ebs.element;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EbsCompoundTest {

    @Test
    void putString() {
        EbsCompound compound = EbsElements.newCompound();
        compound.putString("key", "value");

        assertFalse(compound.isEmpty());
        assertEquals(compound.size(), 1);

        assertEquals(compound.getString("key"), "value");
    }

    @Test
    void putUUID() {
        UUID uuid = UUID.randomUUID();
        EbsCompound compound = EbsElements.newCompound();

        compound.putUUID("key", uuid);

        assertEquals(compound.size(), 1);
        assertEquals(uuid, compound.getUUID("key"));
    }

    @Test
    void isEmpty() {
        EbsCompound compound = EbsElements.newCompound();
        assertTrue(compound.isEmpty());

        compound.putInt("test", 12);

        assertFalse(compound.isEmpty());
    }

    @Test
    void contains() {
        EbsCompound compound = EbsElements.newCompound();
        compound.putString("string", "stringss");
        compound.putUUID("uuid", UUID.randomUUID());

        assertTrue(compound.contains("string"));

        assertFalse(compound.contains("string", EbsUUID.TYPE));
        assertTrue(compound.contains("string", EbsString.TYPE));

        assertTrue(compound.contains("uuid", EbsUUID.TYPE));
        assertFalse(compound.contains("uuid", EbsString.TYPE));
    }
}