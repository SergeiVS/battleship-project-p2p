package org.battleshipprojectp2p.GUI.gameView;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.battleshipprojectp2p.GUI.boardModel.CellColors;
import org.battleshipprojectp2p.game.ship.ShipType;

public class ShipClassInitContainer extends HBox {

    private final ShipType shipType;

    private int totalAmount;
    private int restShipsAmount;
    @FXML
    private Label shipNameField;

    @FXML
    private StackPane ships;
    @FXML
    private Text amountField;

    public ShipClassInitContainer(ShipType shipType) {
        this.shipType = shipType;
        this.restShipsAmount = shipType.getTotalAmount();
        this.totalAmount = shipType.getTotalAmount();

    }

    @FXML
    public void initialize() {
        this.setFillHeight(true);
        this.ships = initSips();
        shipNameField = new Label(shipType.name());
        shipNameField.setStyle("-fx-alignment: left-center;");
        amountField = new Text();
        changeAmount();
        amountField.setStyle("-fx-alignment: right-center;");
        this.setSpacing(10);
        this.setStyle("-fx-alignment: center-right;");
    }

    private StackPane initSips() {
        StackPane stack = new StackPane();

        for (int i = 0; i < shipType.getTotalAmount(); i++) {
            HBox hBox = new HBox();
            for (int j = 0; j < shipType.getLength(); j++) {
                Rectangle rectangle = new Rectangle(20, 20);
                rectangle.setStroke(Color.BLACK);
                rectangle.setFill(CellColors.SHIP.getColor());
                hBox.getChildren().add(rectangle);
            }
            stack.getChildren().add(hBox);
        }
        return stack;
    }

    private void changeAmount() {
        this.restShipsAmount = ships.getChildren().size();
        amountField.setText(restShipsAmount + "/" + totalAmount);
    }
}
