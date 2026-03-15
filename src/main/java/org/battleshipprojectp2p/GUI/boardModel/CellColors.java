package org.battleshipprojectp2p.GUI.boardModel;

import javafx.scene.paint.Color;

public enum CellColors {

    EMPTY(Color.AQUA),
    MISSED(Color.BLUEVIOLET),
    SHIP(Color.GRAY),
    HIT(Color.RED),
    KILLED(Color.BLACK),
    ;

    final Color color;

    CellColors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
