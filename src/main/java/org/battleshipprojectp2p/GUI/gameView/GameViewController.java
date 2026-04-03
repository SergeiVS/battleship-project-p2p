package org.battleshipprojectp2p.GUI.gameView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.battleshipprojectp2p.common.GameState;
import org.battleshipprojectp2p.game.GameManager;

import static org.battleshipprojectp2p.GUI.ViewLoader.loadNewView;

public class GameViewController {
    //
    private GameManager game;

    private PlayerBoardSetupPane setupPane;

    private GamePane gamePane;
    @FXML
    private VBox showBox;
    @FXML
    public HBox buttonBar;
    @FXML
    public Button backToStart;
    @FXML
    public Button gameControlButton;

    public GameViewController() {
    }

    public void initData(GameManager game) {
        this.game = game;
        this.setupPane = new PlayerBoardSetupPane(game);
        this.setupPane.initialize();
        this.showBox.getChildren().add(setupPane);
        buttonBar.setSpacing(10);
        buttonBar.setPadding(new Insets(40));
        backToStart.setMinHeight(75);
        backToStart.setMinWidth(200);
        gameControlButton.setMinHeight(75);
        gameControlButton.setMinWidth(200);
        gameControlButton.setText("READY");
    }


    public void onBackButtonClick(ActionEvent event) {
        this.game = null;
        loadNewView("start-view.fxml");
    }

    public void onGameControlClick(ActionEvent event) {
        if (this.game.getState() == GameState.SETUP) {
            game.ready(true);
            initGamePane();
            showBox.getChildren().clear();
            showBox.getChildren().add(gamePane);
            gameControlButton.setText("START");
        } else if (this.game.getState() == GameState.READY) {
            game.start();
            gameControlButton.setVisible(false);
            IO.println(game.getState().toString());
        }
    }

    private void initGamePane() {
        if (this.game.getState() == GameState.READY) {
            if (this.gamePane == null) {
                this.gamePane = new GamePane(this.game);
            }
        }
    }
}
