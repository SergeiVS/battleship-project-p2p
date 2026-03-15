package org.battleshipprojectp2p.GUI.models.boardModel;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class PaintableBoardCell extends Rectangle {

    private static final int SIZE = 25;
    private final Point position;
    private boolean isTouched;


    public PaintableBoardCell(Point position) {
        super(SIZE, SIZE);
        this.position = position;
        isTouched = false;
        this.setFill(CellColors.EMPTY.getColor());
        this.setStroke(Color.BLACK);
    }

    /**
     * Used for symbolize a ship outside the board and by adding new ship to the board
     */
    public PaintableBoardCell(CellColors color) {
        super(SIZE, SIZE);
        this.position = null;
        this.isTouched = false;
        this.setFill(color.getColor());
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
