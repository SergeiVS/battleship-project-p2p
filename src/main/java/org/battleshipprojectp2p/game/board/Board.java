package org.battleshipprojectp2p.game.board;

import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.common.BoardSide;
import org.battleshipprojectp2p.game.gameDto.Ship;

public class Board implements BoardInterface {

    private final BoardSide boardOwner;
    private final int rowsCount;
    private final int columnsCount;

    private final BoardCell[] board;
        /*
        *    True if all ships are placed and accepted by player ships should not be moved boardOwner- HOST
        *    False if board is not yet ready, or boardOwner-ENEMY
        */
    private boolean isFixed;


    public Board(int rows, int columns,  BoardSide boardOwner) {
        this.boardOwner = boardOwner;
        this.rowsCount = rows;
        this.columnsCount = columns;
        this.board = new BoardCell[rows * columns];
        this.isFixed = false;

        for (int i = 0; i < board.length; i++) {
            board[i] = new BoardCell();
        }
    }

    @Override
    public AttackStatus attack(int row, int col) {
        return null;
    }

    @Override
    public Board getBoardArray() {
        return null;
    }

    @Override
    public Board placeShip(Ship ship) {
        return null;
    }

    @Override
    public Board removeShip(Ship ship) {
        return null;
    }

    @Override
    public Board fixBoard(Board board) {
        return null;
    }
}
