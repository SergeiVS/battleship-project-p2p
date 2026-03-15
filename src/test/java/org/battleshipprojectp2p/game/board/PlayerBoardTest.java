package org.battleshipprojectp2p.game.board;

import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.common.CellValue;
import org.battleshipprojectp2p.error.BrokenRuleException;
import org.battleshipprojectp2p.game.board.boardRules.BoardRule;
import org.battleshipprojectp2p.game.board.boardRules.ShipAllowedPositionRule;
import org.battleshipprojectp2p.game.board.boardRules.ShipAmountRule;
import org.battleshipprojectp2p.game.gameDto.AttackDto;
import org.battleshipprojectp2p.game.gameDto.AttackResponseDto;
import org.battleshipprojectp2p.game.ship.Ship;
import org.battleshipprojectp2p.game.player.Player;
import org.battleshipprojectp2p.game.ship.ShipType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.battleshipprojectp2p.game.ship.ShipType.getCellValueFromShipClass;
import static org.junit.jupiter.api.Assertions.*;


class PlayerBoardTest {

    Player p1;
    Player p2;
    int rows;
    int cols;
    PlayerBoard playerBoard1;
    PlayerBoard playerBoard2;

    int[] position1 = {1, 2};
    int[] position2 = {10, 14, 18};
    Ship ship1 = new Ship(ShipType.DESTROYER, position1, false, false);
    Ship ship2 = new Ship(ShipType.FRIGATE, position2, true, false);

    List<BoardRule> rules = new ArrayList<>(List.of(new ShipAmountRule(), new ShipAllowedPositionRule()));


    @BeforeEach
    void setUp() {
        p1 = new Player("p1");
        p2 = new Player("p2");
        rows = 4;
        cols = 6;
        playerBoard1 = new PlayerBoard(rows, cols, p1, rules);
        playerBoard2 = new PlayerBoard(rows, cols, p2, rules);
    }

    @AfterEach
    void tearDown() {
        p1 = null;
        p2 = null;
        playerBoard1 = null;
        playerBoard2 = null;
        rows = 0;
        cols = 0;
        ship1 = null;
        ship2 = null;
    }

    @Test
    void getBoardOwner() {
        assertEquals(p1, playerBoard1.getOwner());
        assertEquals(p2, playerBoard2.getOwner());
    }

    @Test
    void getRowsCount() {
        assertEquals(rows, playerBoard1.getRowsCount());
    }

    @Test
    void getColumnsCount() {
        assertEquals(cols, playerBoard1.getColumnsCount());
    }

    @Test
    void getBoard() {
        assertEquals(rows * cols, playerBoard1.getBoard().length);
    }

    @Test
    void shouldAddShipSuccess() throws BrokenRuleException {

        CellValue cellValue1 = getCellValueFromShipClass(ship1.type());
        CellValue cellValue2 = getCellValueFromShipClass(ship2.type());

        playerBoard1.addShip(ship1);
        playerBoard1.addShip(ship2);

        int[] expectedPositions = IntStream.concat(Arrays.stream(position1), Arrays.stream(position2)).toArray();

        int[] resultPositions = Arrays.stream(playerBoard1.getBoard())
                .filter(cell -> cell.getCellValue().equals(cellValue1) || cell.getCellValue().equals(cellValue2))
                .mapToInt(BoardCell::getIndex).toArray();

        assertArrayEquals(expectedPositions, resultPositions);
    }

    @Test
    void shouldThrowBrokenRuleException() throws BrokenRuleException {

        Ship ship3 = new Ship(ShipType.DESTROYER, new int[]{3, 4}, false, false);
        Ship ship4 = new Ship(ShipType.DESTROYER, new int[]{13, 17}, true, false);

        playerBoard1.addShip(ship1);
        playerBoard1.addShip(ship2);

        assertThrows(BrokenRuleException.class, () -> playerBoard1.addShip(ship3));
        assertThrows(BrokenRuleException.class, () -> playerBoard1.addShip(ship4));
    }

    @Test
    void removeShip() throws BrokenRuleException {
        playerBoard1.addShip(ship1);
        playerBoard1.addShip(ship2);

        CellValue cellValue1 = getCellValueFromShipClass(ship1.type());

        playerBoard1.removeShip(ship2);
        int[] resultPositions = Arrays.stream(playerBoard1.getBoard())
                .filter(cell -> cell.getCellValue().equals(cellValue1))
                .mapToInt(BoardCell::getIndex).toArray();


        assertArrayEquals(position1, resultPositions);
        assertFalse(Arrays.equals(position2, resultPositions));
    }

    @Test
    void markAttackEnemy() throws BrokenRuleException {
        playerBoard1.addShip(ship1);
        var attack1 = new AttackDto(p1, 0, 1);
        var attack2 = new AttackDto(p1, 0, 0);
        var attack3 = new AttackDto(p1, 0, 2);
        var attack4 = new AttackDto(p2, 0, 0);
        var attack5 = new AttackDto(p1, 10, 0);
        var attack6 = new AttackDto(p1, 0, 10);

        var result1 = playerBoard1.markAttack(attack1);
        var result2 = playerBoard1.markAttack(attack2);
        var result3 = playerBoard1.markAttack(attack3);

        assertEquals(new AttackResponseDto(AttackStatus.HIT, CellValue.X), result1);
        assertEquals(new AttackResponseDto(AttackStatus.MISS, CellValue.E), result2);
        assertEquals(new AttackResponseDto(AttackStatus.SINK, getCellValueFromShipClass(ship1.type())), result3);
        assertThrows(IllegalArgumentException.class, () -> playerBoard1.markAttack(attack4), "Player is not equal to this player");
        assertThrows(IllegalArgumentException.class, () -> playerBoard1.markAttack(attack5), "Invalid row");
        assertThrows(IllegalArgumentException.class, () -> playerBoard1.markAttack(attack6), "Invalid column");
    }
}