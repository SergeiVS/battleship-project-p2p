package org.battleshipprojectp2p.GUI.gameView;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.battleshipprojectp2p.game.GameManager;

import java.util.Arrays;

public class GameViewController {
    //
    private GameManager game;

    private PlayerBoardSetupPane setupPane;
    @FXML
    private HBox gamePane;
    @FXML
    private VBox showBox;

    public GameViewController() {
    }

    public void initData(GameManager game) {
        this.game = game;
        this.setupPane = new PlayerBoardSetupPane(game);
        this.setupPane.initialize();
        this.showBox.getChildren().add(setupPane);
        IO.println("isVisible " + gamePane.isVisible());
        IO.println(gamePane.getChildren().toString());
    }

}
