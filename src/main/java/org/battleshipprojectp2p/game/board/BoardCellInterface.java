package org.battleshipprojectp2p.game.board;

import org.battleshipprojectp2p.common.CellValue;

public interface BoardCellInterface {

    BoardCell getCell();
    BoardCell setNewCellValue(CellValue newCellValue);
    BoardCell setIsAttacked(boolean isAttacked);


}
