package org.battleshipprojectp2p.game.board;

import org.battleshipprojectp2p.common.CellValue;

import java.util.function.IntPredicate;

public class BoardCell implements BoardCellInterface {
    private final int index;
    private CellValue cellValue;
    private boolean isAttacked;

    public BoardCell(int index) {
        this.index = index;
        this.cellValue = CellValue.E;
        this.isAttacked = false;
    }

    public int getIndex() {
        return index;
    }

    public void setAttacked() {
        isAttacked = true;
    }

    @Override
    public void setCellValue(CellValue newCellValue) {
        this.cellValue = newCellValue;
    }

    @Override
    public void setIsAttacked(boolean newIsAttacked) {
        this.isAttacked = newIsAttacked;
    }

    @Override
    public CellValue getCellValue() {
        return this.cellValue;
    }

    @Override
    public boolean isAttacked() {
        return this.isAttacked;
    }
}
