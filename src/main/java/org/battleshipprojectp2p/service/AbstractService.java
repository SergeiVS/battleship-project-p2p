package org.battleshipprojectp2p.service;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.battleshipprojectp2p.networking.networkingDto.CoinFlipMessage;
import org.battleshipprojectp2p.networking.networkingDto.MessagePayload;
import org.battleshipprojectp2p.networking.networkingDto.PayloadType;
import org.battleshipprojectp2p.service.mappers.BaseMessageMapper;
import org.battleshipprojectp2p.service.mappers.JSONMapper;

public abstract class AbstractService {


    private volatile GameManager game;
    private final GameSubject subject;
    private final boolean isHost;

    protected final BaseMessageMapper messageMapper = new BaseMessageMapper();
    protected final JSONMapper jsonMapper = new JSONMapper();

    protected AbstractService(boolean isHost) {
        this.isHost = isHost;
        this.subject = new GameSubject();
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

    public void registerObserver(GameObserver<GameData> parent) {
        this.subject.subscribe(parent);
    }

    public void removeObserver(GameObserver<GameData> parent) {
        this.subject.unsubscribe(parent);
    }

    public synchronized void setGame(GameSetup setup) {
        if (this.game == null) {
            this.game = new GameManager(setup, this.subject);
        }
        subject.notify(getGameData());
    }

    public GameData getGameData() {
        return game.getGameData();
    }

    public void addShip(Ship ship) throws BrokenRuleException {
        game.addShip(ship);
    }

    public void removeShip(Ship ship) {
        game.removeShip(ship);
    }

    public void startGame() {
        game.start();
    }

    public GameState getGameState() {
        return game.getState();
    }

    public void gameReady() {
        game.ready();
    }

    protected String getCoinFlipMessageJson() throws JsonProcessingException {
        if (game != null) {
            final var coinFlip = game.getCoinFlip();
            final var message = messageMapper.buildMessage(new CoinFlipMessage(coinFlip));
            return jsonMapper.toJson(message);
        } else {
            throw new RuntimeException("Game is null");
        }
    }

    protected void handleOpponentsCoinFlip(MessagePayload payload) {
        if (payload.type() == PayloadType.COIN_FLIP) {
            final var coinFlip = ((CoinFlipMessage) payload).coinFlip();
            IO.println("opponent flip: " + coinFlip);
            game.setAttackSide(coinFlip);
        }
        IO.println(game.getSide());
    }

    public boolean getIsHost() {
        return isHost;
    }


    public AttackSide getAttackSide() {
        return game.getSide();
    }
}
