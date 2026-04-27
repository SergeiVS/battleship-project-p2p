package org.battleshipprojectp2p.GUI.gameConnectView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.battleshipprojectp2p.service.GuestService;
import org.battleshipprojectp2p.service.dto.GuestSetupDto;

import java.io.IOException;

import static java.lang.Integer.parseInt;
import static org.battleshipprojectp2p.GUI.ViewLoader.loadGameView;
import static org.battleshipprojectp2p.GUI.ViewLoader.loadNewView;
import static org.battleshipprojectp2p.GUI.utils.AlertService.showAlert;
import static org.battleshipprojectp2p.GUI.utils.InputsCheckUtil.checkName;
import static org.battleshipprojectp2p.GUI.utils.InputsCheckUtil.outlineWrongInput;

public class GuestGameConnectController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField ipField;
    @FXML
    public TextField portField;
    @FXML
    private Button submitGuest;
    public Button leavePageButton;

    @FXML
    public void initialize() {
        this.submitGuest.setMinWidth(200);
        this.leavePageButton.setMinWidth(200);
    }

    @FXML
    public void connectGame(ActionEvent event) throws IOException, InterruptedException {
        final var name = nameField.getText();
        final var isNameValid = checkName(name);
        outlineWrongInput(isNameValid, nameField);
        final var ip = ipField.getText();
        final var port = parseInt(portField.getText());

        if (isNameValid) {
            final var setup = new GuestSetupDto(name, ip, port);
            final var service = new GuestService(false, setup);

            loadGameView(service);
        } else {
            showAlert(Alert.AlertType.ERROR, "Wrong input", "Please enter valid values");
        }
    }

    @FXML
    public void backToPrevPage(ActionEvent event) {
        loadNewView("start-view.fxml");
    }

    @FXML
    public void onNameInput(KeyEvent keyEvent) {
        getSource(keyEvent);
    }

    static void getSource(KeyEvent keyEvent) {
        var source = keyEvent.getSource();
        assert (source instanceof TextField);
        var field = (TextField) source;
        var text = field.getText();
        var isOk = checkName(text);
        outlineWrongInput(isOk, field);

        if (!text.isEmpty()) {
            if (!isOk) {
                showAlert(Alert.AlertType.WARNING, "Wrong input", "Please enter a valid character");
            }
        }
    }
}
