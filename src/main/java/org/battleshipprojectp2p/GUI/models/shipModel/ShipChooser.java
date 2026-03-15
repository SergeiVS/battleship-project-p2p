package org.battleshipprojectp2p.GUI.models.shipModel;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.battleshipprojectp2p.game.ship.ShipType;

public class ShipChooser extends HBox {

    private final ShipType shipType;

    private final int totalAmount;
    private int restShipsAmount = 0;
    @FXML
    private Label shipNameField;
    @FXML
    private ShipModel shipModel;
    @FXML
    private Text amountField;

    public ShipChooser(ShipType shipType) {
        this.shipType = shipType;
        this.totalAmount = shipType.getTotalAmount();

    }

    @FXML
    public void initialize() {
        this.setFillHeight(true);
        this.shipModel = new ShipModel(shipType);
        shipNameField = new Label(shipType.name());
        amountField = new Text();
        setAmountField();
        this.setSpacing(10);
        this.getChildren().addAll(shipNameField, shipModel, amountField);
        this.setOnMouseClicked(choseShip());
    }


    private void incrementShipsAmount() {
        this.restShipsAmount++;
    }

    private void decrementShipsAmount() {
        this.restShipsAmount--;
    }

    private EventHandler<MouseEvent> choseShip() {
        return event -> {
            if (this.restShipsAmount < this.totalAmount || !this.shipModel.isDisabled()) {
                incrementShipsAmount();
                setAmountField();
                if (this.restShipsAmount == this.totalAmount) {
                    this.shipModel.setDisable(true);
                }
            }
        };
    }

    private void setAmountField() {
        this.amountField.setText(restShipsAmount + "/" + totalAmount);
    }
}
