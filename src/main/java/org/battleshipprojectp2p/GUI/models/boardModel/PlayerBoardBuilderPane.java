package org.battleshipprojectp2p.GUI.models.boardModel;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import org.battleshipprojectp2p.game.board.BoardData;
import org.battleshipprojectp2p.game.ship.Ship;
import org.battleshipprojectp2p.game.ship.ShipType;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiConsumer;

public class PlayerBoardBuilderPane extends BoardPane {

    private Ship choosenShip = null;

    public PlayerBoardBuilderPane(BoardData board, BiConsumer<PaintableBoardCell, MouseEvent> onClick) {
        super(board, onClick);
        this.setEventHandler(ScrollEvent.ANY, onScroll());
    }

    public void setChosenShip(ShipType type) {
        if (type == null) {
            this.choosenShip = null;
            return;
        }

        if (this.choosenShip != null) return;

        this.choosenShip = new Ship(type);
    }

    public Optional<Ship> getChosenShip() {
        return Optional.ofNullable(choosenShip);
    }


    private void rotateShip() {
        if (this.choosenShip != null) {
            this.choosenShip = this.choosenShip.setVertical(!this.choosenShip.isVertical());
        }
    }

    private EventHandler<ScrollEvent> onScroll() {
        return event -> {
            this.update(this.currentBoard);
            rotateShip();
            if (event.getTarget() instanceof PaintableBoardCell) {
                paintShipForChoice(((PaintableBoardCell) event.getTarget()).getPosition());
            }
        };
    }

    public void paintShipForChoice(CellPoint firstPoint) {
        if (firstPoint == null) return;

        if (this.choosenShip == null) return;

        final var points = getCellsToPaint(firstPoint);

        Arrays.stream(points).forEach(point -> {
            if (point != null) {
                this.cells.get(point).setFill(CellColors.SHIP.getColor());
            }
        });
    }

    private CellPoint[] getCellsToPaint(CellPoint firstPoint) {

        final var length = choosenShip.type().getLength();
        final CellPoint[] points = new CellPoint[length];
        final var isVertical = choosenShip.isVertical();
        final var y = firstPoint.y();
        final var x = firstPoint.x();
        int counter = 0;

        if (!isVertical) {

            for (int i = y; i < y + length; i++) {
                if (i < currentBoard.colsCount() && counter < length) {
                    IO.println("new y added" + i);
                    points[counter] = new CellPoint(x, i);
                    counter++;
                }
            }
        } else {

            for (int i = x; i < x + length; i++) {
                if (i < currentBoard.rowsCount() && counter < length) {
                    points[counter] = new CellPoint(i, y);
                    counter++;
                }
            }
        }
        return points;
    }
}
