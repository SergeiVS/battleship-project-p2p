package org.battleshipprojectp2p.GUI.gameView;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.battleshipprojectp2p.GUI.models.SetupMenu;
import org.battleshipprojectp2p.GUI.models.boardModel.BoardModel;
import org.battleshipprojectp2p.common.AttackSide;
import org.battleshipprojectp2p.common.GameState;
import org.battleshipprojectp2p.game.GameManager;
import org.battleshipprojectp2p.game.gameDto.GameData;
import org.battleshipprojectp2p.game.observer.GameObserver;

public class GameViewController implements GameObserver<GameData> {
    //
    private GameManager game;

    private int rows;
    private int cols;

    private BoardModel playerBoard;
    private BoardModel opponentBoard;

    private GameState gameState;
    private AttackSide attackSide;

    @FXML
    private HBox gamePane;
    @FXML
    private VBox playerBox;
    @FXML
    private VBox opponentBox;
    @FXML
    private VBox setupBox;
    @FXML
    private Label playerName;
    @FXML
    private Label opponentName;

    public GameViewController() {
    }

    public void initData(GameManager gameManager) {
        SetupMenu setupMenu = new SetupMenu();
        setupMenu.initialize();
        IO.println(setupMenu.getChildren());
        this.game = gameManager;
        this.rows = game.getRows();
        this.cols = game.getColumns();


        this.playerName.setText(gameManager.getPlayerBoard().player().name());
        this.opponentName.setText(gameManager.getOpponentBoard().player().name());

        this.playerBoard = new BoardModel(event -> {
        }, game.getPlayerBoard());
        playerBoard.setOnMouseEntered(mouseEntered(playerBoard));
        playerBoard.setOnMouseExited(mouseExited(playerBoard));

        this.opponentBoard = new BoardModel(event -> {
        }, game.getOpponentBoard());
        opponentBoard.setOnMouseEntered(mouseEntered(opponentBoard));
        opponentBoard.setOnMouseExited(mouseExited(opponentBoard));

//        this.setupBox = new VBox();
        this.setupBox.getChildren().add(setupMenu);
        this.playerBox.getChildren().add(this.playerBoard);
        this.opponentBox.getChildren().add(this.opponentBoard);
        this.opponentBox.setVisible(false);

        IO.println(setupBox.getChildren());
        this.game.registerObserver(this);
        IO.println("Setup menu view order" + setupMenu.getAccessibleRole());
    }

    private EventHandler<MouseEvent> mouseEntered(Node board) {
        DropShadow shadow = new DropShadow();
        shadow.setRadius(5.0);
        shadow.setOffsetX(3.0);
        shadow.setOffsetY(3.0);
        shadow.setColor(Color.SIENNA);

        return event -> {
            board.setEffect(shadow);
        };
    }

    private EventHandler<MouseEvent> mouseExited(Node board) {

        return event -> {
            board.setEffect(null);
        };
    }

    ;

    @Override
    public void update(GameData data) {
        this.playerBoard.refreshBoard(data.board());
        this.opponentBoard.refreshBoard(data.opponentBoard());
        this.gameState = data.state();
        this.attackSide = data.attackSide();
        this.playerName.setText(data.board().player().name());
        this.opponentName.setText(data.opponentBoard().player().name());
    }
}
