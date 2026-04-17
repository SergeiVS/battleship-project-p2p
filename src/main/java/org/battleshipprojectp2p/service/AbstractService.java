package org.battleshipprojectp2p.service;

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

public abstract class AbstractService {


    private volatile GameManager game;
    private final GameSubject subject;
    private final boolean isHost;

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
            IO.println("game is still null: ");
            this.game = new GameManager(setup, this.subject);
        }
        game.notifyUpdate();
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

    // TODO add CoinFlip
    public void gameReady() {
        game.ready(true);
    }

    public boolean getIsHost() {
        return isHost;
    }

    public AttackSide getAttackSide() {
        return game.getSide();
    }
}
