package org.battleshipprojectp2p.GUI.boardModel;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.battleshipprojectp2p.game.board.BoardCell;


public class Cell extends Rectangle {
    private final Point position;
    private boolean isTouched;


    public Cell(Point position) {
        super(20, 20);
        this.position = position;
        isTouched = false;
        this.setFill(CellColors.EMPTY.getColor());
        this.setStroke(Color.BLACK);
    }

    public Point getPosition() {
        return position;
    }

    public boolean isTouched() {
        return isTouched;
    }

    public void setColor(CellColors color) {
        this.setFill(color.getColor());
        isTouched = true;
    }

    public void clearCell() {
        this.setFill(CellColors.EMPTY.getColor());
        isTouched = false;
    }
}
