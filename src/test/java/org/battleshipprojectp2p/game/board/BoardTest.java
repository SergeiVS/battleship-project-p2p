package org.battleshipprojectp2p.game.board;

import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.common.CellValue;
import org.battleshipprojectp2p.game.Player;
import org.battleshipprojectp2p.game.gameDto.AttackDto;
import org.battleshipprojectp2p.game.gameDto.AttackResponseDto;
import org.battleshipprojectp2p.game.gameDto.PlayerDto;
import org.battleshipprojectp2p.game.gameDto.Ship;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


class BoardTest {

    Player p1;
    Player p2;
    int rows;
    int cols;
    Board board1;
    Board board2;

    int[] position1 = {1, 2};
    int[] position2 = {10, 14, 18};
    Ship ship1 = new Ship(1, CellValue.D, position1, false );
    Ship ship2 = new Ship(2, CellValue.F, position2, true );


    @BeforeEach
    void setUp() {
        p1 = new Player("p1", true);
        p2 = new Player("p2", false);
        rows = 4;
        cols = 6;
        board1 = new Board(rows, cols, p1);
        board2 = new Board(rows, cols, p2);
    }

    @AfterEach
    void tearDown() {
        p1 = null;
        p2 = null;
        board1 = null;
        board2 = null;
        rows = 0;
        cols = 0;
    }

    @Test
    void getBoardOwner() {
        assertEquals(new PlayerDto(p1), board1.getBoardOwner());
        assertEquals(new PlayerDto(p2), board2.getBoardOwner());
    }

    @Test
    void getRowsCount() {
        assertEquals(rows, board1.getRowsCount());
    }

    @Test
    void getColumnsCount() {
        assertEquals(cols, board1.getColumnsCount());
    }

    @Test
    void getBoard() {
        assertEquals(rows*cols, board1.getBoard().length);
    }

    @Test
    void isFixed() {
        assertFalse(board1.isFixed());
        assertFalse(board2.isFixed());
    }

    @Test
    void placeShip() {
        Ship ship3 = new Ship(3, CellValue.D, new int[]{3, 4}, false);
        Ship ship4 = new Ship(4, CellValue.D, new int[]{13, 17}, true);

         board1.placeShip(ship1);
         board1.placeShip(ship2);

        int[] expectedPositions =  IntStream.concat(Arrays.stream(position1), Arrays.stream(position2)).toArray();

        int[] resultPositions = Arrays.stream(board1.getBoard())
               .filter(  cell-> cell.getCellValue().equals(ship1.type()) || cell.getCellValue().equals(ship2.type()))
               .mapToInt(BoardCell::getIndex).toArray();

        assertArrayEquals(expectedPositions, resultPositions);
        assertThrows(RuntimeException.class,()->board1.placeShip(ship3));
        assertThrows(RuntimeException.class,()->board1.placeShip(ship4));
    }

    @Test
    void removeShip() {
        board1.placeShip(ship1);
        board1.placeShip(ship2);

        board1.removeShip(ship2);
        int[] resultPositions = Arrays.stream(board1.getBoard())
                .filter(  cell-> cell.getCellValue().equals(ship1.type()))
                .mapToInt(BoardCell::getIndex).toArray();


        assertArrayEquals(position1, resultPositions);
        assertFalse(Arrays.equals(position2, resultPositions));
    }

    @Test
    void fixBoard() {

        assertFalse(board1.isFixed());

        board1.fixBoard();

        assertTrue(board1.isFixed());
    }

    @Test
    void attackEnemy() {
        board1.placeShip(ship1);
        var attack1 = new AttackDto(new PlayerDto(p1), 0, 1);
        var attack2 = new AttackDto(new PlayerDto(p1), 0, 0);
        var attack3 = new AttackDto(new PlayerDto(p1), 0, 2);
        var attack4 = new AttackDto(new PlayerDto(p2), 0, 0);
        var attack5 = new AttackDto(new PlayerDto(p1), 10, 10);

        var result1 = board1.attackFromEnemy(attack1);
        var result2 = board1.attackFromEnemy(attack2);
        var result3 = board1.attackFromEnemy(attack3);

        assertEquals(new AttackResponseDto(AttackStatus.HIT, null), result1);
        assertEquals(new AttackResponseDto(AttackStatus.MISS, CellValue.E), result2);
        assertEquals(new AttackResponseDto(AttackStatus.SINK, ship1.type()), result3);
        assertThrows(IllegalArgumentException.class,()-> board1.attackFromEnemy(attack4),"Player is not equal to this player");
        assertThrows(IllegalArgumentException.class, ()-> board1.attackFromEnemy(attack5), "Invalid row");
    }

    @Test
    void attackSelf() {
        board2.attackSelf(0,0, new AttackResponseDto(AttackStatus.MISS, CellValue.E));
        board2.attackSelf(0,1, new AttackResponseDto(AttackStatus.HIT, CellValue.E));

        assertTrue(board2.getBoard()[0].isAttacked());
        assertTrue(board2.getBoard()[1].isAttacked());

        assertEquals(CellValue.E, board2.getBoard()[0].getCellValue());
        assertEquals(CellValue.X, board2.getBoard()[1].getCellValue());

        board2.attackSelf(0,2, new AttackResponseDto(AttackStatus.SINK, ship1.type()));
        assertTrue(board2.getBoard()[2].isAttacked());

        assertEquals(ship1.type(), board2.getBoard()[2].getCellValue());
        assertEquals(1, board2.getFleet().size());
    }
}