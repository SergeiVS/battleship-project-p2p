package org.battleshipprojectp2p.game.board;

import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.common.CellValue;
import org.battleshipprojectp2p.error.BrokenRuleException;
import org.battleshipprojectp2p.error.InvalidMoveException;
import org.battleshipprojectp2p.game.board.boardRules.BoardRule;
import org.battleshipprojectp2p.game.board.boardRules.ShipAllowedPositionRule;
import org.battleshipprojectp2p.game.board.boardRules.ShipAmountRule;
import org.battleshipprojectp2p.game.gameDto.AttackResponseDto;
import org.battleshipprojectp2p.game.player.Player;
import org.battleshipprojectp2p.game.ship.Ship;
import org.battleshipprojectp2p.game.ship.ShipType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.battleshipprojectp2p.game.ship.ShipType.getCellValueFromShipClass;
import static org.junit.jupiter.api.Assertions.*;

class OpponentBoardTest {

    Player player;
    OpponentBoard board;

    Ship ship1;

    Ship ship2;

    int[] position1;
    int[] position2;

    List<BoardRule> rules = List.of(new ShipAllowedPositionRule(), new ShipAmountRule());

    @BeforeEach
    void setUp() {
        player = new Player("player");
        board = new OpponentBoard(10, 10, player, rules);
        position1 = new int[]{0, 1, 2};
        position2 = new int[]{20, 30, 40};
        ship1 = new Ship(ShipType.FRIGATE, position1, false, true);
        ship2 = new Ship(ShipType.FRIGATE, position2, true, false);
    }

    @AfterEach
    void tearDown() {
        player = null;
        board = null;
        ship1 = null;
        ship2 = null;
    }

    @Test
    void shouldChangeCellStateIsAttackedAndCellValueIfHit() throws BrokenRuleException {

        assertEquals(CellValue.E, board.getBoard()[0].getCellValue());
        assertFalse(board.getBoard()[0].isHit());

        assertEquals(CellValue.E, board.getBoard()[6].getCellValue());
        assertFalse(board.getBoard()[6].isHit());

        board.markAttack(0, 0, new AttackResponseDto(AttackStatus.HIT, CellValue.X));
        board.markAttack(0, 6, new AttackResponseDto(AttackStatus.MISS, CellValue.E));

        assertEquals(CellValue.X, board.getBoard()[0].getCellValue());
        assertTrue(board.getBoard()[0].isHit());

        assertEquals(CellValue.E, board.getBoard()[6].getCellValue());
        assertTrue(board.getBoard()[6].isHit());
    }

    @Test
    void shouldAddNewShipToTheFleetIfSunk() throws BrokenRuleException {

        board.markAttack(0, 0, new AttackResponseDto(AttackStatus.HIT, CellValue.X));
        board.markAttack(0, 1, new AttackResponseDto(AttackStatus.HIT, CellValue.X));
        CellValue cellValue = getCellValueFromShipClass(ship1.type());

        assertEquals(CellValue.X, board.getBoard()[0].getCellValue());
        assertTrue(board.getBoard()[0].isHit());

        assertEquals(CellValue.X, board.getBoard()[1].getCellValue());
        assertTrue(board.getBoard()[1].isHit());

        board.markAttack(0, 2, new AttackResponseDto(AttackStatus.SINK, CellValue.F));

        assertEquals(board.getFleet().getFirst().type(), ship1.type());
        assertTrue(board.getFleet().getFirst().isSunk());

        for (int p : position1) {
            assertEquals(cellValue, board.getBoard()[p].getCellValue());
        }
    }

    @Test
    void shouldThrowOnDoubleTap() throws BrokenRuleException {

        board.markAttack(0, 0, new AttackResponseDto(AttackStatus.HIT, CellValue.X));
        board.markAttack(0, 1, new AttackResponseDto(AttackStatus.HIT, CellValue.X));

        assertThrows(InvalidMoveException.class, () -> board.markAttack(0, 1, new AttackResponseDto(AttackStatus.MISS, CellValue.E)));
    }

    @Test
    void shouldThrowRowOrColumnOutOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> board.markAttack(-1, 1, new AttackResponseDto(AttackStatus.MISS, CellValue.E)));
        assertThrows(IllegalArgumentException.class, () -> board.markAttack(11, 1, new AttackResponseDto(AttackStatus.MISS, CellValue.E)));

        assertThrows(IllegalArgumentException.class, () -> board.markAttack(0, -1, new AttackResponseDto(AttackStatus.MISS, CellValue.E)));
        assertThrows(IllegalArgumentException.class, () -> board.markAttack(0, 11, new AttackResponseDto(AttackStatus.MISS, CellValue.E)));
    }

    @Test
    void shouldThrowIfHitOverlapsShip() throws BrokenRuleException {

        board.markAttack(0, 0, new AttackResponseDto(AttackStatus.HIT, CellValue.X));
        board.markAttack(0, 1, new AttackResponseDto(AttackStatus.HIT, CellValue.X));
        board.markAttack(0, 2, new AttackResponseDto(AttackStatus.SINK, CellValue.F));

        assertThrows(BrokenRuleException.class, () -> board.markAttack(0, 3, new AttackResponseDto(AttackStatus.HIT, CellValue.X)));
        assertThrows(BrokenRuleException.class, () -> board.markAttack(1, 3, new AttackResponseDto(AttackStatus.HIT, CellValue.X)));
        assertThrows(BrokenRuleException.class, () -> board.markAttack(1, 1, new AttackResponseDto(AttackStatus.HIT, CellValue.X)));
        assertDoesNotThrow(() -> board.markAttack(1, 0, new AttackResponseDto(AttackStatus.MISS, CellValue.E)));
    }
}