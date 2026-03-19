package org.battleshipprojectp2p.GUI.models;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.battleshipprojectp2p.GUI.models.shipModel.ShipChooser;
import org.battleshipprojectp2p.GUI.models.shipModel.ShipChooserPane;
import org.battleshipprojectp2p.game.ship.ShipType;

import java.util.ArrayList;
import java.util.List;

import static org.battleshipprojectp2p.game.ship.ShipType.getAllShipClasses;

public class SetupMenu extends VBox {

    private Label label;
    private Button readyButton;
    private Button startButton;
    @FXML
    private ButtonBar setupButtonBar;
    @FXML
    private ShipChooserPane shipChoosers;

    public void initialize() {
        this.label = new Label();
        this.label.setText("Game Setup Menu");
        this.setupButtonBar = new ButtonBar();
        this.readyButton = new Button("Ready");
        this.startButton = new Button("Start");
        this.setupButtonBar.getButtons().addAll(readyButton, startButton);
        this.shipChoosers = new ShipChooserPane(e -> {
        });
        this.getChildren().addAll(setupButtonBar, shipChoosers);
        this.setSpacing(10);
    }
}
