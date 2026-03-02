package org.battleshipprojectp2p.GUI.startView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.IOException;

import static org.battleshipprojectp2p.GUI.ViewLoader.loadNewView;

public class StartController {
    @FXML
    private HBox startLayout;
    @FXML
    private Button hostStart;
    @FXML
    private Button guestStart;

    @FXML
    public void startHost(ActionEvent actionEvent) throws IOException {
        loadNewView("host-game-setup-view.fxml");
    }

    @FXML
    public void startGuest(ActionEvent actionEvent) {
        loadNewView("guest-game-setup-view.fxml");
    }
}
