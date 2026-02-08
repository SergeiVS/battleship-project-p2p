package org.battleshipprojectp2p.game.board.boardRules;

import org.battleshipprojectp2p.common.CellValue;
import org.battleshipprojectp2p.error.BrokenRuleException;
import org.battleshipprojectp2p.game.board.PlayerBoard;
import org.battleshipprojectp2p.game.ship.Ship;
import org.battleshipprojectp2p.game.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipAmountRuleTest {

    ShipAmountRule shipAmountRule;
    PlayerBoard playerBoard;

    Player player;

    int rows = 10;
    int columns = 10;

    @BeforeEach
    void setUp() {
        shipAmountRule = new ShipAmountRule();
        player = new Player("player");
        playerBoard = new PlayerBoard(rows, columns, player, List.of(shipAmountRule));
    }

    @AfterEach
    void tearDown() {
        playerBoard = null;
        shipAmountRule = null;
        player = null;
    }

    @Test
    void shouldThrowByTooManyShipsOfSomeType() throws BrokenRuleException {
        int[] pos1 = {2, 3, 4, 5, 6};
        int[] pos2 = {8, 18, 28, 38, 48};

        Ship carrier1 = new Ship(CellValue.C, pos1, false, false);
        Ship carrier2 = new Ship(CellValue.C, pos2, true, false);

        playerBoard.addShip(carrier1);

        assertThrows(BrokenRuleException.class, () -> shipAmountRule.verifyRule(playerBoard, carrier2));
    }
}