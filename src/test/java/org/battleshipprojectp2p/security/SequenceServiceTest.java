package org.battleshipprojectp2p.security;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SequenceServiceTest {

    private SequenceService service;

    @BeforeEach
    void setUp() {
        service = new SequenceService();
    }

    @AfterEach
    void tearDown() {
        service = null;
    }

    @Test
    void shouldReturnNextNumber() {
        var expectedResult = 1;
        var result1 = service.getCurrentSequence();
        var result2 = service.getCurrentSequence();

        assertEquals(expectedResult, result1);
        expectedResult++;
        assertEquals(expectedResult, result2);
    }
}