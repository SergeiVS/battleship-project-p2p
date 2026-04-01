package org.battleshipprojectp2p.GUI.gameConnectView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.battleshipprojectp2p.game.GameManager;
import org.battleshipprojectp2p.game.gameDto.GameSetup;
import org.battleshipprojectp2p.game.player.Player;

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
    private Button submitGuest;
    public Button leavePageButton;

    @FXML
    public void initialize() {
        this.submitGuest.setMinWidth(200);
        this.leavePageButton.setMinWidth(200);
    }

    @FXML
    public void connectGame(ActionEvent event) {
        final var name = nameField.getText();
        final var isNameValid = checkName(name);
        outlineWrongInput(isNameValid, nameField);
        final var ip = "ipField.getText()";
        final var cols = 10;
        final var rows = 10;

        if (isNameValid) {
            GameManager game = new GameManager(
                    new GameSetup(
                            new Player(name),
                            new Player("Opponent"),
                            rows,
                            cols,
                            false
                    )
            );
            loadGameView(game);
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
        var source = keyEvent.getSource();
        assert (source instanceof TextField);
        var field = (TextField) source;
        var text = field.getText();
        var isOk = checkName(text);
        outlineWrongInput(isOk, field);
        IO.println("Name input ok: " + isOk);

        if (!text.isEmpty()) {
            if (!isOk) {
                showAlert(Alert.AlertType.WARNING, "Wrong input", "Please enter a valid character");
            }
        }
    }
}
