package org.battleshipprojectp2p.game.gameDto;

import org.battleshipprojectp2p.common.CellValue;

public record Ship(
        int id,
        CellValue type,
        int[] position,
        boolean isVertical
) {
    public Ship(int id, CellValue type, int[] position,  boolean isVertical) {
        if(CellValue.E.equals(type) || CellValue.X.equals(type)){
            throw new IllegalArgumentException("Invalid cell type. Ship could not be of Type empy or attacked");
        }

        this.id = id;
        this.type = type;
        this.position = position;
        this.isVertical = isVertical;
    }
}
