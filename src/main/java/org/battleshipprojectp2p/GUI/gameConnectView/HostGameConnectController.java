package org.battleshipprojectp2p.GUI.gameConnectView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.battleshipprojectp2p.game.GameManager;
import org.battleshipprojectp2p.game.gameDto.GameSetup;
import org.battleshipprojectp2p.game.player.Player;

import static java.lang.Integer.parseInt;
import static org.battleshipprojectp2p.GUI.utils.AlertService.showAlert;
import static org.battleshipprojectp2p.GUI.ViewLoader.loadGameView;

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
    private void onNameInput(KeyEvent actionEvent) {
        var source = actionEvent.getSource();
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


    @FXML
    private void onNumberInput(KeyEvent actionEvent) {
        var source = actionEvent.getSource();
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

    public void submitGameSetup(ActionEvent actionEvent) {
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
            showAlert(Alert.AlertType.WARNING, "Wrong input", "Please enter valid values");
        } else {
            GameSetup setup = new GameSetup(
                    new Player(name),
                    new Player("Opponent"),
                    parseInt(rows),
                    parseInt(cols),
                    true
            );
            GameManager manager = new GameManager(setup);
            loadGameView(manager);
        }
    }

    private static boolean checkIsNumber(String text) {
        return text.matches("^\\d+$");
    }

    private static boolean checkName(String text) {
        return text.matches("^[a-zA-Z\\d@!&]+$");
    }

    private static void outlineWrongInput(boolean isOk, TextField field) {
        if (!isOk) {
            field.setStyle("-fx-border-color: red;");
        } else {
            field.setStyle("-fx-border-color: black;");
        }
    }
}
