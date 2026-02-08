package org.battleshipprojectp2p.game.board;

import org.battleshipprojectp2p.common.CellValue;

public class BoardCell {
    private final int index;
    private CellValue cellValue;
    private boolean isHit;

    public BoardCell(int index) {
        this.index = index;
        this.cellValue = CellValue.E;
        this.isHit = false;
    }

    public BoardCell(int index, CellValue cellValue) {
        this.index = index;
        this.cellValue = cellValue;
        this.isHit = false;
    }

    public BoardCell(int index, CellValue cellValue, boolean isHit) {
        this.index = index;
        this.cellValue = cellValue;
        this.isHit = isHit;
    }

    public int getIndex() {
        return index;
    }

    public void setAttacked() {
        isHit = true;
    }


    public void setCellValue(CellValue newCellValue) {
        this.cellValue = newCellValue;
    }


    public CellValue getCellValue() {
        return this.cellValue;
    }


    public boolean isHit() {
        return this.isHit;
    }
}
