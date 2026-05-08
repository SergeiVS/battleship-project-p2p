package org.battleshipprojectp2p.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.battleshipprojectp2p.GUI.models.boardModel.CellPoint;
import org.battleshipprojectp2p.common.AttackSide;
import org.battleshipprojectp2p.common.GameState;
import org.battleshipprojectp2p.error.BrokenRuleException;
import org.battleshipprojectp2p.game.GameManager;
import org.battleshipprojectp2p.game.board.BoardData;
import org.battleshipprojectp2p.game.gameDto.GameData;
import org.battleshipprojectp2p.game.gameDto.GameSetup;
import org.battleshipprojectp2p.game.observer.GameObserver;
import org.battleshipprojectp2p.game.observer.GameSubject;
import org.battleshipprojectp2p.game.ship.Ship;
import org.battleshipprojectp2p.networking.client.ClientSocket;
import org.battleshipprojectp2p.networking.dto.gameLoopDto.CoinFlipMessage;
import org.battleshipprojectp2p.networking.dto.gameLoopDto.MessagePayload;
import org.battleshipprojectp2p.networking.dto.gameLoopDto.PayloadType;
import org.battleshipprojectp2p.service.mappers.BaseMessageMapper;
import org.battleshipprojectp2p.service.mappers.JSONMapper;

import java.io.IOException;

public abstract class AbstractService {


    private final GameSubject subject;
    private volatile GameManager game;
    private final GameLoopMessageService messageService;
    protected ClientSocket session;
    protected final BaseMessageMapper messageMapper = new BaseMessageMapper();
    protected final JSONMapper jsonMapper = new JSONMapper();
    private final boolean isHost;
    private boolean opponentFlipped = false;

    protected AbstractService(boolean isHost) {
        this.isHost = isHost;
        this.subject = new GameSubject();
        this.messageService = new GameLoopMessageService(messageMapper, jsonMapper);
    }

    protected abstract void createGame(MessagePayload payload) throws IOException;

    public abstract void closeConnection();

    protected void setSession(ClientSocket session) {
        this.session = session;
        messageService.setSession(this.session);
    }

    public GameManager getGame() {
        if (this.game == null) {
            throw new RuntimeException("This game is not yet created");
        }
        return this.game;
    }

    public BoardData getPlayerBoard() {
        return game.getPlayerBoard();
    }

    public BoardData getOpponentBoard() {
        return game.getOpponentBoard();
    }

    public synchronized void setGame(GameSetup setup) {
        if (this.game == null) {
            this.game = new GameManager(setup, this.subject);
            messageService.setGame(this.game);
        }
        subject.notify(getGameData());
    }

    public GameData getGameData() {
        return game.getGameData();
    }

    public GameState getGameState() {
        return game.getState();
    }

    public boolean getIsHost() {
        return isHost;
    }


    public AttackSide getAttackSide() {
        return game.getSide();
    }

    public boolean getIsWon() {
        return game.isWon();
    }

    public void registerObserver(GameObserver<GameData> parent) {
        this.subject.subscribe(parent);
    }

    public void addShip(Ship ship) throws BrokenRuleException {
        game.addShip(ship);
    }

    public void removeShip(Ship ship) {
        game.removeShip(ship);
    }

    public void gameReady() {
        try {
            session.sendMessage(getCoinFlipMessageJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        game.ready();
        if (opponentFlipped) {
            game.start();
        }
    }

    protected void handleOpponentsCoinFlip(MessagePayload payload) {
        if (payload.type() == PayloadType.COIN_FLIP) {
            final var coinFlip = ((CoinFlipMessage) payload).coinFlip();
            game.setAttackSide(coinFlip);
        }
        opponentFlipped = true;
        if (game.getState() == GameState.READY) {
            game.start();
        }
    }

    private String getCoinFlipMessageJson() throws JsonProcessingException {
        if (game != null) {
            final var coinFlip = game.getCoinFlip();
            final var message = messageMapper.buildMessage(new CoinFlipMessage(coinFlip));
            return jsonMapper.baseMessageToJson(message);
        } else {
            throw new RuntimeException("Game is null");
        }
    }

    void handleIncomingMessage(String msg) {
        final var message = jsonMapper.toBaseMessage(msg);
        try {
            switch (message.type()) {
                case CONNECT, GAME_SETUP_DATA -> createGame(message.payload());
                case COIN_FLIP -> handleOpponentsCoinFlip(message.payload());
                case ATTACK -> messageService.handleOpponentAttack(message.payload());
                case ATTACK_RESPONSE -> messageService.handleAttackResponse(message.payload());
                case GAME_OVER -> messageService.handleGameOverMessage(message.payload());
            }
        } catch (IOException | BrokenRuleException e) {
            throw new RuntimeException(e);
        }
    }

    public void handlePlyerAttack(CellPoint point) throws IOException, BrokenRuleException {
        messageService.handlePlyerAttack(point);
    }

    public boolean isFleetComplete() {
        return game.isFleetComplete();
    }

    protected void closeGame() {
        this.game = null;
    }
}
