package org.battleshipprojectp2p.GUI.gameSetupView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.battleshipprojectp2p.GUI.gameView.GameViewController;
import org.battleshipprojectp2p.game.GameManager;
import org.battleshipprojectp2p.game.gameDto.GameSetup;
import org.battleshipprojectp2p.game.player.Player;

import static java.lang.Integer.parseInt;
import static org.battleshipprojectp2p.GUI.AlertService.showAlert;
import static org.battleshipprojectp2p.GUI.ViewLoader.loadGameView;
import static org.battleshipprojectp2p.GUI.ViewLoader.loadNewView;

public class HostGameSetupController {


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
    private void onNameInsert(KeyEvent actionEvent) {
        if (!nameField.getText().isEmpty() && nameField.getText() != null) {
            var text = nameField.getText();
            if (!text.matches("^[a-zA-Z\\d@!&]+$")) {
                showAlert(Alert.AlertType.WARNING, "Wrong input", "Please enter a valid character");
            }
        }
    }

    @FXML
    private void checkIsNumber(KeyEvent actionEvent) {
        var source = actionEvent.getSource();

        if (source instanceof TextField field && (source.equals(rowsInput) || source.equals(colsInput))) {
            if (!field.getText().isEmpty() && field.getText() != null) {
                var text = field.getText();
                if (!text.matches("^\\d+$")) {
                    showAlert(Alert.AlertType.WARNING, "Wrong input", "Please enter a number");
                }
            }
        }
    }

    public void submitGameSetup(ActionEvent actionEvent) {
        IO.println(nameField.getText());

        GameSetup setup = new GameSetup(
                new Player(nameField.getText()),
                new Player("Opponent"),
                parseInt(rowsInput.getText()),
                parseInt(colsInput.getText()),
                true
        );

        GameManager manager = new GameManager(setup);
        loadGameView(manager);
    }
}
