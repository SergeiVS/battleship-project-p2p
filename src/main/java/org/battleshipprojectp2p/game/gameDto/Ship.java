package org.battleshipprojectp2p.game.gameDto;

import org.battleshipprojectp2p.common.CellValue;

public record Ship(
        int id,
        CellValue type,
        int[] position
) {
    public Ship(int id, CellValue type, int[] position) {
        if(CellValue.E.equals(type) || CellValue.X.equals(type)){
            throw new IllegalArgumentException("Invalid cell type. Ship could not be of Type empy or attacked");
        }

        this.id = id;
        this.type = type;
        this.position = position;
    }
}
