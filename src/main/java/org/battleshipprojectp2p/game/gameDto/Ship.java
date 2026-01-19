package org.battleshipprojectp2p.game.gameDto;

import org.battleshipprojectp2p.common.CellValue;

public record Ship(
        CellValue shipType,
        int startRow,
        int startCol,

        /*
          True if ship is placed horizontal
          False if ship is in vertical position
         */
        boolean isHorizontal
) {
    public Ship(CellValue shipType, int startRow, int startCol, boolean isHorizontal) {
        this.shipType = shipType;
        this.startRow = startRow;
        this.startCol = startCol;
        this.isHorizontal = isHorizontal;
    }
}
