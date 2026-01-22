package org.battleshipprojectp2p.game.board;

import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.game.Player;
import org.battleshipprojectp2p.game.gameDto.AttackDto;
import org.battleshipprojectp2p.game.gameDto.Ship;

import java.util.Arrays;
import java.util.Objects;

public class Board implements BoardInterface {

    private final Player boardOwner;
    private final int rowsCount;
    private final int columnsCount;

    private final BoardCell[] board;
    /*
     *    True if all ships are placed and accepted by player ships should not be moved boardOwner- HOST
     *    False if board is not yet ready, or boardOwner-ENEMY
     */
    private boolean isFixed;


    public Board(int rows, int columns, Player boardOwner) {
        this.boardOwner = boardOwner;
        this.rowsCount = rows;
        this.columnsCount = columns;
        this.board = new BoardCell[rows * columns];
        this.isFixed = false;

        for (int i = 0; i < board.length; i++) {
            board[i] = new BoardCell(i);
        }
    }

    public Player getBoardOwner() {
        return boardOwner;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public int getColumnsCount() {
        return columnsCount;
    }

    public BoardCell[] getBoard() {
        return board;
    }

    public boolean isFixed() {
        return isFixed;
    }
    @Override
    public BoardInterface placeSip(Ship ship) {
        return null;
    }

    @Override
    public BoardInterface removeShip(Ship ship) {
        return null;
    }

    @Override
    public BoardInterface fixBoard() {
        return null;
    }

    @Override
    public AttackStatus attackEnemy(AttackDto attackDto) {
        return null;
    }

    @Override
    public BoardInterface attackSelf(int row, int column, AttackStatus status) {
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Board board1 = (Board) o;
        return rowsCount == board1.rowsCount && columnsCount == board1.columnsCount && isFixed == board1.isFixed && Objects.equals(boardOwner, board1.boardOwner) && Objects.deepEquals(board, board1.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardOwner, rowsCount, columnsCount, Arrays.hashCode(board), isFixed);
    }
}