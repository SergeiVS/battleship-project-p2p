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
import org.battleshipprojectp2p.game.gameDto.AttackDto;
import org.battleshipprojectp2p.game.gameDto.AttackResponseDto;
import org.battleshipprojectp2p.game.gameDto.GameData;
import org.battleshipprojectp2p.game.observer.GameObserver;
import org.battleshipprojectp2p.service.AbstractService;

public class GamePane extends HBox implements GameObserver<GameData> {

    private final VBox playerBox = new VBox();
    private final Label playerName = new Label();
    private final VBox opponentBox = new VBox();
    private final Label opponentName = new Label();
    private final AbstractService service;
    private final BoardPane playersBoard;
    private final BoardPane opponentsBoard;


    public GamePane(AbstractService service) {
        this.service = service;
        this.playersBoard = new PlayerBoardPane(this.service.getPlayerBoard(), this::handlePlayersBoardClick);
        this.opponentsBoard = new OpponentBoardPane(this.service.getOpponentBoard(), this::handleOpponentBoartClick);

        playerName.setText(this.service.getPlayerBoard().player().name());
        opponentName.setText(this.service.getOpponentBoard().player().name());

        playerBox.getChildren().addAll(playerName, playersBoard);
        opponentBox.getChildren().addAll(opponentName, opponentsBoard);
        setBoxStyles(playerBox);
        setBoxStyles(opponentBox);

        this.getChildren().addAll(playerBox, opponentBox);
        this.setSpacing(40);

        service.registerObserver(this);
    }


    public void handlePlayersBoardClick(PaintableBoardCell cell, MouseEvent event) {
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
                final var player = service.getPlayerBoard().player();
                final var attackDto = new AttackDto(player, point.x(), point.y());
                final var status = playersBoard.getCell(point).getFill() == CellColors.EMPTY.getColor() ? AttackStatus.MISS : AttackStatus.HIT;
                final var cellValue = service.getPlayerBoard().board()[point.x()][point.y()].getCellValue();
                final var attackResponse = new AttackResponseDto(status, cellValue);
//                try {
//                    game.markPlayerAttack(attackDto, attackResponse);
//                } catch (BrokenRuleException e) {
//                    e.getRule();
//                }

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
