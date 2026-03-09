package org.battleshipprojectp2p.GUI.gameView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.battleshipprojectp2p.common.ShipClass;

public class ShipClassInitContainer extends HBox {

    private final ShipClass shipClass;
    private int restShipsAmount;
    @FXML
    private Label shipNameField;
    @FXML
    private Button addShipButton;
    @FXML
    private Button removeShipButton;
    @FXML
    private Text amountField;

    public ShipClassInitContainer(ShipClass shipClass) {
        this.shipClass = shipClass;
        this.restShipsAmount = shipClass.getTotalAmount();
        IO.println(shipClass);
    }

    @FXML
    public void initialize() {
        initButtons();
        shipNameField = new Label(shipClass.name());
        amountField = new Text(restShipsAmount + "/" + shipClass.getTotalAmount());
        this.getChildren().addAll(shipNameField, addShipButton, removeShipButton, amountField);
        IO.println(this.toString());
    }

    private void initButtons() {
        addShipButton = new Button("Add Ship");
        addShipButton.setOnAction(addShip());
        removeShipButton = new Button("Remove Last");
        removeShipButton.setOnAction(removeShip());
    }

    //    TODO
    @FXML
    public EventHandler<ActionEvent> addShip() {
        return null;
    }

    //    TODO
    @FXML
    public EventHandler<ActionEvent> removeShip() {
        return null;
    }
}
