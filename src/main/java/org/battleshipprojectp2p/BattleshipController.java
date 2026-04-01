package org.battleshipprojectp2p;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.util.Objects;

public class BattleshipController {

    @FXML
    private BorderPane root;
    @FXML
    private Label mottoLabel;


    @FXML
    void initialize() {
        mottoLabel.setText("Welcome to The Battle");
        loadView("start-view.fxml");
    }

    public void loadView(String fxmlFile) {
        try {
            Node view = FXMLLoader.load(Objects.requireNonNull(BattleshipApplication.class.getResource(fxmlFile)));
            root.setCenter(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

