package org.battleshipprojectp2p.GUI.startView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static org.battleshipprojectp2p.GUI.ViewLoader.loadNewView;

public class StartController {
    @FXML
    private VBox startLayout;
    @FXML
    private Button hostStartButton;
    @FXML
    private Button guestStartButton;
    @FXML
    public Button closeButton;

    public StartController() {
    }

    @FXML
    public void initialize() {
        startLayout.setBackground(Background.EMPTY);
        startLayout.setSpacing(10);
        startLayout.setAlignment(Pos.CENTER_RIGHT);
        startLayout.setPadding(new Insets(150));
        hostStartButton.setMinWidth(200);
        hostStartButton.setMinHeight(70);
        guestStartButton.setMinWidth(200);
        guestStartButton.setMinHeight(70);
        closeButton.setMinWidth(200);
        closeButton.setMinHeight(70);
    }

    @FXML
    public void startHost(ActionEvent actionEvent) {
        loadNewView("host-game-connect-view.fxml");
    }

    @FXML
    public void startGuest(ActionEvent actionEvent) {
        loadNewView("guest-game-connect-view.fxml");
    }


    @FXML
    public void closeApp(ActionEvent actionEvent) {
        ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();
    }

}