package org.battleshipprojectp2p.GUI.gameView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.battleshipprojectp2p.game.ship.ShipType;

import java.util.ArrayList;
import java.util.List;

import static org.battleshipprojectp2p.game.ship.ShipType.getAllShipClasses;

public class SetupMenu extends VBox {

    @FXML
    private Label label;
    @FXML
    private ButtonBar setupButtonBar;
    @FXML
    private Button readyButton;
    @FXML
    private Button startButton;

    @FXML
    private VBox shipsInitContainer;

    public void initialize() {
        label = new Label();
        label.setText("Game Setup Menu");
        setupButtonBar = new ButtonBar();
        setupButtonBar.setStyle("-fx-alignment: center;");
        readyButton = new Button("Ready");
        startButton = new Button("Start");
        setupButtonBar.getButtons().addAll(readyButton, startButton);
        shipsInitContainer = new VBox();
        shipsInitContainer.setSpacing(10);
        var shipClassContainers = fillShipsInitContainer();
        shipsInitContainer.getChildren().addAll(shipClassContainers);
        this.setSpacing(10);
        this.getChildren().addAll(setupButtonBar, shipsInitContainer);
        this.setStyle("-fx-alignment: center-right;");
    }

    private List<ShipClassInitContainer> fillShipsInitContainer() {
        List<ShipClassInitContainer> shipsInitContainers = new ArrayList<>();
        List<ShipType> shipTypes = getAllShipClasses();
        for (ShipType shipType : shipTypes) {
            if (shipType != ShipType.UNDEFINED) {
                var shipClassContainer = new ShipClassInitContainer(shipType);
                shipClassContainer.initialize();
                shipsInitContainers.add(shipClassContainer);
            }
        }
        return shipsInitContainers;
    }
}
