package org.battleshipprojectp2p.GUI.gameView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.battleshipprojectp2p.common.GameState;
import org.battleshipprojectp2p.game.gameDto.GameData;
import org.battleshipprojectp2p.game.observer.GameObserver;
import org.battleshipprojectp2p.service.AbstractService;
import org.battleshipprojectp2p.service.HostService;

import static org.battleshipprojectp2p.GUI.ViewLoader.loadNewView;

public class GameViewController implements GameObserver<GameData> {

    private final AbstractService service;

    private PlayerBoardSetupPane boardSetupPane;

    private GamePane gamePane;
    @FXML
    private VBox showBox;

    private HostConnectionWaitingMask hostWaitingMask;
    private GuestConnectionWaitingMask guestWaitingMask;
    @FXML
    public HBox buttonBar;
    @FXML
    public Button backToStart;
    @FXML
    public Button gameControlButton;

    public GameViewController(AbstractService service) {
        this.service = service;
        this.service.registerObserver(this);
    }

    @FXML
    public void initialize() {
        if (service.getIsHost()) {
            final var connection = ((HostService) service).getConnectionData();
            this.showBox.getChildren().add(new HostConnectionWaitingMask(connection));
        } else {
            this.showBox.getChildren().add(new GuestConnectionWaitingMask());
        }

        buttonBar.setSpacing(10);
        buttonBar.setPadding(new Insets(40));
        backToStart.setMinHeight(75);
        backToStart.setMinWidth(200);
        gameControlButton.setMinHeight(75);
        gameControlButton.setMinWidth(200);
        gameControlButton.setText("Start");
        gameControlButton.setDisable(true);
    }


    public void onBackButtonClick(ActionEvent event) {
        loadNewView("start-view.fxml");
    }

    public void onGameControlClick(ActionEvent event) {
        if (service.getGameState() == GameState.SETUP) {
            service.gameReady();
            initGamePane();
            showBox.getChildren().clear();
            showBox.getChildren().add(gamePane);
            service.startGame();
            gameControlButton.setVisible(false);
        }
    }

    protected synchronized void showBoardSetup() {
        Platform.runLater(() -> {
            if (boardSetupPane == null) {
                boardSetupPane = new PlayerBoardSetupPane(service);
                boardSetupPane.initialize();
                showBox.getChildren().clear();
                showBox.getChildren().add(boardSetupPane);
                gameControlButton.setDisable(false);
            }
        });
    }

    private void initGamePane() {
        if (service.getGameState() == GameState.READY) {
            if (this.gamePane == null) {
                this.gamePane = new GamePane(this.service);
            }
        }
    }

    @Override
    public void update(GameData data) {
        if (boardSetupPane == null) {
            service.removeObserver(this);
            showBoardSetup();
        }
    }
}
