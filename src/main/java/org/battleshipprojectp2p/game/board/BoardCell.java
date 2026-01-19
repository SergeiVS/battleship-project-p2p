package org.battleshipprojectp2p.game.board;

import org.battleshipprojectp2p.common.CellValue;

public class BoardCell implements  BoardCellInterface{
    private CellValue cellValue;
    private boolean isAttacked;

    public BoardCell() {
        this.cellValue = CellValue.E;
        this.isAttacked = false;
    }

    @Override
    public BoardCell getCell() {
        return null;
    }

    @Override
    public BoardCell setNewCellValue(CellValue newCellValue) {
        return null;
    }

    @Override
    public BoardCell setIsAttacked(boolean isAttacked) {
        return null;
    }
}
