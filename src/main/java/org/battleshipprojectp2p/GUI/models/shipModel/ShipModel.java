package org.battleshipprojectp2p.GUI.models.shipModel;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.battleshipprojectp2p.GUI.models.boardModel.CellColors;
import org.battleshipprojectp2p.GUI.models.boardModel.PaintableBoardCell;
import org.battleshipprojectp2p.game.ship.ShipType;

public class ShipModel extends Pane {
    private final ShipType shipType;

    private final PaintableBoardCell[] ship;


    public ShipModel(ShipType shipType) {
        this.shipType = shipType;
        this.ship = new PaintableBoardCell[this.shipType.getLength()];
        fillShip();
        this.getChildren().add(setHorizontal());
    }

    public void rotate() {
        if (!this.getChildren().isEmpty()) {
            if (this.getChildren().getFirst() instanceof HBox) {
                this.getChildren().removeFirst();
                this.getChildren().add(setVertical());
            }
            if (this.getChildren().getFirst() instanceof VBox) {
                this.getChildren().removeFirst();
                this.getChildren().add(setHorizontal());
            }
        }
    }

    private void fillShip() {
        for (int i = 0; i < this.shipType.getLength(); i++) {
            PaintableBoardCell pBoardCell = new PaintableBoardCell(CellColors.SHIP);
            this.ship[i] = pBoardCell;
        }
    }

    private HBox setHorizontal() {
        HBox hBox = new HBox();
        hBox.getChildren().addAll(ship);
        return hBox;
    }

    private VBox setVertical() {
        VBox vBox = new VBox();
        vBox.getChildren().addAll(ship);
        return vBox;
    }
}
