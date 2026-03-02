package org.battleshipprojectp2p;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Objects;

public class BattleshipController {

    @FXML
    private BorderPane root;
    @FXML
    private Label MottoLabel;
    @FXML
    private HBox StartLayout;
    @FXML
    private AnchorPane GameSetupView;


    @FXML
    void initialize() {
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

