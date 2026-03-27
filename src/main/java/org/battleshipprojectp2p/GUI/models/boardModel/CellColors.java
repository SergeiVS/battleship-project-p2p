package org.battleshipprojectp2p.GUI.models.boardModel;

import javafx.scene.paint.Color;

public enum CellColors {

    EMPTY(Color.AQUA),
    MISSED(Color.BLUEVIOLET),
    SHIP(Color.GRAY),
    HIT(Color.RED),
    KILLED(Color.BLACK),
    MOUSE_ENTERED(Color.LIGHTBLUE);

    final Color color;

    CellColors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
