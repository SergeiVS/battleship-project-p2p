package org.battleshipprojectp2p.game.board;

import org.battleshipprojectp2p.error.BrokenRuleException;
import org.battleshipprojectp2p.game.board.boardRules.BoardRule;
import org.battleshipprojectp2p.game.player.Player;
import org.battleshipprojectp2p.game.ship.Ship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    protected final Player owner;
    protected final int rowsCount;
    protected final int columnsCount;
    protected final BoardCell[] board;
    protected final List<Ship> fleet;
    protected final List<BoardRule> rules;

    public Board(Player owner, int rows, int columns, List<BoardRule> rules) {
        this.owner = owner;
        this.rowsCount = rows;
        this.columnsCount = columns;
        this.board = new BoardCell[rows * columns];
        this.fleet = new ArrayList<>();
        this.rules = rules;

        for (int i = 0; i < board.length; i++) {
            board[i] = new BoardCell(i);
        }
    }

    public Player getOwner() {
        return owner;
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

    public List<Ship> getFleet() {
        return fleet;
    }

    public void verifyRules(Ship ship) throws BrokenRuleException {
        for (BoardRule r : rules) {
            r.verifyRule(this, ship);
        }
    }

    public BoardCell[][] getBoard2D() {
        BoardCell[][] board2D = new BoardCell[rowsCount][columnsCount];
        for (int x = 0; x < rowsCount; x++) {
            System.arraycopy(this.board, x * this.rowsCount, board2D[x], 0, this.columnsCount);
        }
        return board2D;
    }

    protected int getCellIndexByCoordinates(int row, int column) {
        return (row * columnsCount) + column;
    }


}
