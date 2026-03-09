package org.battleshipprojectp2p.GUI.gameView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import org.battleshipprojectp2p.common.ShipClass;

import java.util.ArrayList;
import java.util.List;

import static org.battleshipprojectp2p.common.ShipClass.getAllShipClasses;

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
        readyButton = new Button("Ready");
        startButton = new Button("Start");
        setupButtonBar.getButtons().addAll(readyButton, startButton);
        shipsInitContainer = new VBox();
        var shipClassContainers = fillShipsInitContainer();
        shipsInitContainer.getChildren().addAll(shipClassContainers);
        this.getChildren().addAll(setupButtonBar, shipsInitContainer);
    }

    private List<ShipClassInitContainer> fillShipsInitContainer() {
        List<ShipClassInitContainer> shipsInitContainers = new ArrayList<>();
        List<ShipClass> shipClasses = getAllShipClasses();
        for (ShipClass shipClass : shipClasses) {
            var shipClassContainer = new ShipClassInitContainer(shipClass);
            shipClassContainer.initialize();
            shipsInitContainers.add(shipClassContainer);
            this.setStyle("-fx-padding: 10px");
        }
        return shipsInitContainers;
    }
}
