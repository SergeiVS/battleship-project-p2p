package org.battleshipprojectp2p.GUI.gameView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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
        this.getChildren().addAll(setupButtonBar, shipsInitContainer);
    }
}
