package org.battleshipprojectp2p.game;

import org.battleshipprojectp2p.common.AttackSide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttackSideManagerTest {

    private AttackSideManager manager;

    @BeforeEach
    void setUp() {
        manager = new AttackSideManager();
        manager.setCurrentSide(AttackSide.PLAYER);
    }

    @AfterEach
    void tearDown() {
        manager = null;
    }

    @Test
    void changeSide() {
        assertEquals(AttackSide.PLAYER, manager.getCurrentSide());

        manager.changeSide();

        assertEquals(AttackSide.OPPONENT, manager.getCurrentSide());

        manager.changeSide();

        assertEquals(AttackSide.PLAYER, manager.getCurrentSide());
    }
}