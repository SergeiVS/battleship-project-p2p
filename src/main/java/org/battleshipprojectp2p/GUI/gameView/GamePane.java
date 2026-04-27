package org.battleshipprojectp2p.GUI.gameView;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.battleshipprojectp2p.GUI.models.boardModel.*;
import org.battleshipprojectp2p.common.GameState;
import org.battleshipprojectp2p.error.BrokenRuleException;
import org.battleshipprojectp2p.game.gameDto.GameData;
import org.battleshipprojectp2p.game.observer.GameObserver;
import org.battleshipprojectp2p.service.AbstractService;

import java.io.IOException;

import static org.battleshipprojectp2p.GUI.utils.AlertService.showAlert;

public class GamePane extends HBox implements GameObserver<GameData> {

    private final Label attackSide = new Label();
    private final VBox playerBox = new VBox();
    private final Label playerName = new Label();
    private final VBox opponentBox = new VBox();
    private final Label opponentName = new Label();
    private final AbstractService service;
    private final BoardPane playersBoard;
    private final BoardPane opponentsBoard;
    private boolean gameOver = false;


    public GamePane(AbstractService service) {
        this.service = service;
        this.playersBoard = new PlayerBoardPane(this.service.getPlayerBoard(), this::handlePlayersBoardClick);
        this.opponentsBoard = new OpponentBoardPane(this.service.getOpponentBoard(), this::handleOpponentBoartClick);
        service.registerObserver(this);
    }

    public void initialize() {
        playerName.setText(this.service.getPlayerBoard().player().name());
        opponentName.setText(this.service.getOpponentBoard().player().name());

        playerBox.getChildren().addAll(playerName, playersBoard);
        opponentBox.getChildren().addAll(opponentName, opponentsBoard);

        setBoxStyles(playerBox);
        setBoxStyles(opponentBox);
        setSideLabel();

        this.getChildren().addAll(attackSide, playerBox, opponentBox);
        this.setSpacing(40);
    }


    public void handlePlayersBoardClick(PaintableBoardCell cell, MouseEvent event) {
        if (service.getGameState() == GameState.GAME_OVER) {
            return;
        }
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            showAlert(Alert.AlertType.WARNING, "FRIENDLY FIRE", "Dont shut your self");
        }

    }

    public void handleOpponentBoartClick(PaintableBoardCell cell, MouseEvent event) {

        cell.setTouched();
        if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
            cell.setStroke(Color.LIGHTBLUE);
        }
        if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
            cell.setStroke(Color.BLACK);
        }
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (cell.getFill() == CellColors.EMPTY.getColor()) {
                final var point = cell.getPosition();
                try {
                    service.handlePlyerAttack(point);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (BrokenRuleException e) {
                    showAlert(Alert.AlertType.WARNING, e.getRule(), e.getMessage());
                }
            }
        }
    }

    private void setSideLabel() {
        final var side = service.getAttackSide();
        if (side != null) {
            attackSide.setText(side.name());
        }
    }

    @Override
    public void update(GameData data) {
        Platform.runLater(() -> {
            playersBoard.update(data.board());
            opponentsBoard.update(data.opponentBoard());

            if (service.getGameState() == GameState.GAME_OVER && !gameOver) {
                gameOver = true;
                showGameOverAlert();
                return;
            }
            setSideLabel();
        });

    }

    private void showGameOverAlert() {
        if (service.getIsWon()) {
            showAlert(Alert.AlertType.CONFIRMATION, "GAME OVER", playerName.getText() + ", you Won!");
        } else {
            showAlert(Alert.AlertType.CONFIRMATION, "GAME OVER", playerName.getText() + ", you Lose!");
        }
    }

    private void setBoxStyles(Node node) {
        if (node instanceof VBox) {
            ((VBox) node).setPadding(new Insets(10));
            ((VBox) node).setSpacing(15);
            ((VBox) node).setAlignment(Pos.BASELINE_CENTER);
        }
    }
}
