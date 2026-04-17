package org.battleshipprojectp2p.GUI.gameConnectView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.battleshipprojectp2p.service.HostService;
import org.battleshipprojectp2p.service.dto.HostSetupDto;

import java.io.IOException;

import static java.lang.Integer.parseInt;
import static org.battleshipprojectp2p.GUI.ViewLoader.loadGameView;
import static org.battleshipprojectp2p.GUI.ViewLoader.loadNewView;
import static org.battleshipprojectp2p.GUI.utils.AlertService.showAlert;
import static org.battleshipprojectp2p.GUI.utils.InputsCheckUtil.*;

public class HostGameConnectController {


    @FXML
    private AnchorPane GameSetupView;
    @FXML
    private TextField rowsInput;
    @FXML
    private TextField colsInput;
    @FXML
    private TextField nameField;
    @FXML
    private Button setupSubmitButton;
    @FXML
    public Button backButton;


    @FXML
    public void initialize() {
        setupSubmitButton.setMinWidth(200);
        backButton.setMinWidth(200);
    }

    @FXML
    private void onNameInput(KeyEvent event) {
        GuestGameConnectController.getSource(event);
    }


    @FXML
    private void onNumberInput(KeyEvent event) {
        var source = event.getSource();
        assert (source instanceof TextField);
        TextField field = (TextField) source;
        var text = field.getText();
        var isOk = checkIsNumber(text);
        outlineWrongInput(isOk, field);

        if (field.equals(rowsInput) || field.equals(colsInput)) {
            if (!text.isEmpty()) {
                if (!isOk) {
                    showAlert(Alert.AlertType.WARNING, "Wrong input", "Please enter a number");
                }
            }
        }
    }

    @FXML
    public void submitGameSetup(ActionEvent event) throws IOException {
        var name = nameField.getText();
        var isNameValid = checkName(name);
        outlineWrongInput(isNameValid, nameField);
        var rows = rowsInput.getText();
        var isRowsValid = checkIsNumber(rows);
        outlineWrongInput(isRowsValid, rowsInput);
        var cols = colsInput.getText();
        var isColsValid = checkIsNumber(cols);
        outlineWrongInput(isColsValid, colsInput);

        if (!isNameValid || !isRowsValid || !isColsValid) {
            showAlert(Alert.AlertType.ERROR, "Wrong input", "Please enter valid values");
        } else {
            HostSetupDto setup = new HostSetupDto(name, parseInt(cols), parseInt(rows));
            final var service = new HostService(true, setup);
            loadGameView(service);
        }
    }

    @FXML
    public void toPreviousPage(ActionEvent actionEvent) {
        loadNewView("start-view.fxml");
    }
}
