package org.battleshipprojectp2p.game.board;

import org.battleshipprojectp2p.common.CellValue;

public interface BoardCellInterface {
     void setCellValue(CellValue newCellValue);
     void setIsAttacked(boolean isAttacked);

     CellValue getCellValue();
     boolean isAttacked();
}
