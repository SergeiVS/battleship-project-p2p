package org.battleshipprojectp2p.GUI.gameSetupView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class GuestGameSetupController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField ipField;
    @FXML
    private Button submitGuest;

    @FXML
    public void onSubmit(KeyEvent keyEvent) {
    }
}
