package org.battleshipprojectp2p.security;

import org.battleshipprojectp2p.error.EncryptionException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OpponentIvServiceTest {

    private OpponentIvService service;

    @BeforeEach
    void setUp() {
        service = new OpponentIvService();
    }

    @AfterEach
    void tearDown() {
        service = null;
    }


    @Test
    public void shouldAddNewIvToList() {
        var string1 = "1";
        var string2 = "2";

        service.addNewIv(string1);
        String result1 = service.getCurrentIv();
        assertEquals(string1, result1);

        service.addNewIv(string2);
        String result2 = service.getCurrentIv();
        assertEquals(string2, result2);
    }

    @Test
    public void shouldThrowIfIvAlreadyExist() {
        var string = "1";
        service.addNewIv(string);

        assertEquals(string, service.getCurrentIv());

        assertThrows(EncryptionException.class, () -> service.addNewIv(string));
    }

    @Test
    public void shouldThrowIfNoIvSavedYet() {
        assertThrows(EncryptionException.class, () -> service.getCurrentIv());
    }
}