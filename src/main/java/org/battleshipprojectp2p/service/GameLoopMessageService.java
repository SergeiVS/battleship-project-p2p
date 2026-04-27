package org.battleshipprojectp2p.service;

import javafx.scene.control.Alert;
import org.battleshipprojectp2p.GUI.models.boardModel.CellPoint;
import org.battleshipprojectp2p.common.AttackSide;
import org.battleshipprojectp2p.common.GameState;
import org.battleshipprojectp2p.error.BrokenRuleException;
import org.battleshipprojectp2p.game.GameManager;
import org.battleshipprojectp2p.game.gameDto.AttackDto;
import org.battleshipprojectp2p.game.gameDto.AttackResponseDto;
import org.battleshipprojectp2p.networking.client.ClientSocket;
import org.battleshipprojectp2p.networking.networkingDto.AttackMessage;
import org.battleshipprojectp2p.networking.networkingDto.AttackResponseMessage;
import org.battleshipprojectp2p.networking.networkingDto.GameOverMessage;
import org.battleshipprojectp2p.networking.networkingDto.MessagePayload;
import org.battleshipprojectp2p.service.mappers.BaseMessageMapper;
import org.battleshipprojectp2p.service.mappers.JSONMapper;

import java.io.IOException;

import static org.battleshipprojectp2p.GUI.utils.AlertService.showAlert;

public class GameLoopMessageService {

    protected final BaseMessageMapper messageMapper;
    protected final JSONMapper jsonMapper;
    private volatile GameManager game;

    private CellPoint attackedPoint = null;
    protected ClientSocket session;

    public GameLoopMessageService(BaseMessageMapper messageMapper, JSONMapper jsonMapper) {
        this.messageMapper = messageMapper;
        this.jsonMapper = jsonMapper;
    }

    protected void setSession(ClientSocket session) {
        this.session = session;
    }

    protected void setGame(GameManager game) {
        this.game = game;
    }

    public void handlePlyerAttack(CellPoint point) throws IOException {
        if (game.getSide() == AttackSide.OPPONENT) {
            showAlert(Alert.AlertType.WARNING, "FALSE TURN", "It is not your turn");
            return;
        }

        this.attackedPoint = point;
        final var attackDto = new AttackMessage(point.x(), point.y());
        final var attackJson = jsonMapper.toJson(messageMapper.buildMessage(attackDto));
        try {
            session.sendMessage(attackJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void handleOpponentAttack(MessagePayload payload) throws BrokenRuleException {

        if (payload instanceof AttackMessage) {
            final var attackDto = new AttackDto(
                    ((AttackMessage) payload).row(),
                    ((AttackMessage) payload).column()
            );
            try {
                final var res = game.markOpponentAttack(attackDto);
                final var attackResponse = new AttackResponseMessage(res.attackStatus(), res.cellValue());
                final var json = jsonMapper.toJson(messageMapper.buildMessage(attackResponse));
                session.sendMessage(json);
                notifyGameOver();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void handleAttackResponse(MessagePayload payload) {
        if (payload instanceof AttackResponseMessage) {
            final var attackRes = new AttackResponseDto((AttackResponseMessage) payload);
            final var attackDto = new AttackDto(attackedPoint.x(), attackedPoint.y());
            try {
                game.markPlayerAttack(attackDto, attackRes);
            } catch (BrokenRuleException e) {
                throw new RuntimeException(e);
            }
        }
        this.attackedPoint = null;
    }

    protected void handleGameOverMessage(MessagePayload payload) throws IOException {
        if (payload instanceof GameOverMessage) {
            if (game.getState() != GameState.GAME_OVER) {
                game.setGameOver(((GameOverMessage) payload).won());
            } else {
                if (((GameOverMessage) payload).won() == game.isWon()) {
                    throw new RuntimeException("Check Game over State");
                }
            }
        }
    }

    public void notifyGameOver() throws IOException {
        if (game.getState() != GameState.GAME_OVER) {
            return;
        }
        final var isWon = game.isWon();
        final var board = "";
        final var message = messageMapper.buildMessage(new GameOverMessage(isWon, board));
        final var json = jsonMapper.toJson(message);
        session.sendMessage(json);
    }

}
