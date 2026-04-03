package org.battleshipprojectp2p.GUI.gameView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.battleshipprojectp2p.GUI.models.boardModel.*;
import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.error.BrokenRuleException;
import org.battleshipprojectp2p.game.GameManager;
import org.battleshipprojectp2p.game.gameDto.AttackDto;
import org.battleshipprojectp2p.game.gameDto.AttackResponseDto;
import org.battleshipprojectp2p.game.gameDto.GameData;
import org.battleshipprojectp2p.game.observer.GameObserver;

public class GamePane extends HBox implements GameObserver<GameData> {

    private final VBox playerBox = new VBox();
    private final Label playerName = new Label();
    private final VBox opponentBox = new VBox();
    private final Label opponentName = new Label();
    private final GameManager game;
    private final BoardPane playersBoard;

    private final BoardPane opponentsBoard;

    public GamePane(GameManager game) {
        this.game = game;
        this.playersBoard = new PlayerBoardPane(this.game.getPlayerBoard(), this::handlePlayersBoardClick);
        this.opponentsBoard = new OpponentBoardPane(this.game.getOpponentBoard(), this::handleOpponentBoartClick);

        playerName.setText(game.getPlayerBoard().player().name());
        opponentName.setText(game.getOpponentBoard().player().name());

        playerBox.getChildren().addAll(playerName, playersBoard);
        opponentBox.getChildren().addAll(opponentName, opponentsBoard);
        setBoxStyles(playerBox);
        setBoxStyles(opponentBox);

        this.getChildren().addAll(playerBox, opponentBox);
        this.setSpacing(40);

        game.registerObserver(this);

    }


    public void handlePlayersBoardClick(PaintableBoardCell cell, MouseEvent event) {
    }

    public void handleOpponentBoartClick(PaintableBoardCell cell, MouseEvent event) {
        if (cell.isTouched()) {
            IO.println("cell is touched");
        }
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
                final var player = game.getPlayerBoard().player();
                final var attackDto = new AttackDto(player, point.x(), point.y());
                final var status = playersBoard.getCell(point).getFill() == CellColors.EMPTY.getColor() ? AttackStatus.MISS : AttackStatus.HIT;
                final var cellValue = game.getPlayerBoard().board()[point.x()][point.y()].getCellValue();
                final var attackResponse = new AttackResponseDto(status, cellValue);
                try {
                    game.markPlayerAttack(attackDto, attackResponse);
                } catch (BrokenRuleException e) {
                    e.getRule();
                }

            }
        }
    }

    @Override
    public void update(GameData data) {
        playersBoard.update(data.board());
        opponentsBoard.update(data.opponentBoard());
    }

    private void setBoxStyles(Node node) {
        if (node instanceof VBox) {
            ((VBox) node).setPadding(new Insets(10));
            ((VBox) node).setSpacing(15);
            ((VBox) node).setAlignment(Pos.BASELINE_CENTER);
        }
    }
}
