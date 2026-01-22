package org.battleshipprojectp2p.game.board;

import com.fasterxml.jackson.databind.util.ArrayBuilders;
import org.battleshipprojectp2p.common.CellValue;
import org.battleshipprojectp2p.game.Player;
import org.battleshipprojectp2p.game.gameDto.Ship;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.Array;
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

    int[] position1 = {1, 2, 3};
    int[] position2 = {8, 12, 16};
    Ship ship1 = new Ship(1, CellValue.F, position1 );
    Ship ship2 = new Ship(2, CellValue.F, position2 );


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
        assertEquals(p1, board1.getBoardOwner());
        assertEquals(p2, board2.getBoardOwner());
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
    void placeSip() {

        board1 = (Board) board1.placeSip(ship1);
        board1 = (Board) board1.placeSip(ship2);

        int[] expectedPositions =  IntStream.concat(Arrays.stream(position1), Arrays.stream(position2)).toArray();

        int[] resultPositions = Arrays.stream(board1.getBoard())
               .filter(  cell-> cell.getCellValue().equals(ship1.type()) || cell.getCellValue().equals(ship2.type()))
               .mapToInt(BoardCell::getIndex).toArray();

        assertEquals(position1, resultPositions);
    }

    @Test
    void removeShip() {
    }

    @Test
    void fixBoard() {
    }

    @Test
    void attackEnemy() {
    }

    @Test
    void attackSelf() {
    }
}