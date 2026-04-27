package org.battleshipprojectp2p.game;

import org.battleshipprojectp2p.common.AttackSide;
import org.battleshipprojectp2p.common.GameState;
import org.battleshipprojectp2p.error.BrokenRuleException;
import org.battleshipprojectp2p.game.board.Board;
import org.battleshipprojectp2p.game.board.BoardData;
import org.battleshipprojectp2p.game.board.OpponentBoard;
import org.battleshipprojectp2p.game.board.PlayerBoard;
import org.battleshipprojectp2p.game.board.boardRules.BoardRule;
import org.battleshipprojectp2p.game.board.boardRules.ShipAllowedPositionRule;
import org.battleshipprojectp2p.game.board.boardRules.ShipAmountRule;
import org.battleshipprojectp2p.game.gameDto.AttackDto;
import org.battleshipprojectp2p.game.gameDto.AttackResponseDto;
import org.battleshipprojectp2p.game.gameDto.GameData;
import org.battleshipprojectp2p.game.gameDto.GameSetup;
import org.battleshipprojectp2p.game.observer.GameSubject;
import org.battleshipprojectp2p.game.ship.Ship;

import java.util.ArrayList;
import java.util.List;

import static org.battleshipprojectp2p.game.ship.ShipType.getTotalShipsAmount;

public class GameManager {
    private final PlayerBoard playerBoard;
    private final OpponentBoard opponentBoard;
    private final GameSubject subject;
    private final StateManager stateManager;
    private final CoinFlipManager coinFlipManager;
    private final AttackSideManager attackSideManager;
    private final int rows;
    private final int columns;

    public GameManager(GameSetup setup, GameSubject subject) {
        this.rows = setup.rows();
        this.columns = setup.columns();
        this.subject = subject;
        List<BoardRule> rules = new ArrayList<>(List.of(new ShipAllowedPositionRule(), new ShipAmountRule()));
        this.playerBoard = new PlayerBoard(this.rows, this.columns, setup.player(), rules);
        this.opponentBoard = new OpponentBoard(this.rows, this.columns, setup.opponent(), rules);
        this.coinFlipManager = new CoinFlipManager(setup.isHost());
        this.stateManager = new StateManager();
        this.attackSideManager = new AttackSideManager();
    }

    public boolean getCoinFlip() {
        return coinFlipManager.getCoinFlip();
    }

    public void setAttackSide(boolean opponentFlip) {
        final boolean isFirst = coinFlipManager.isFirstMove(opponentFlip);

        if (isFirst) {
            attackSideManager.setCurrentSide(AttackSide.PLAYER);
        } else {
            attackSideManager.setCurrentSide(AttackSide.OPPONENT);
        }
    }

    public void ready() {
        verifyGameState(GameState.SETUP);
        stateManager.setState(GameState.READY);
        notifyUpdate();
    }

    public void start() {
        verifyGameState(GameState.READY);
        stateManager.setState(GameState.PLAYING);
        notifyUpdate();
    }

    public void addShip(Ship ship) throws BrokenRuleException {
        verifyGameState(GameState.SETUP);
        playerBoard.addShip(ship);
        notifyUpdate();
    }

    public void removeShip(Ship ship) {
        verifyGameState(GameState.SETUP);
        playerBoard.removeShip(ship);
        notifyUpdate();
    }

    public AttackResponseDto markOpponentAttack(AttackDto attackDto) throws BrokenRuleException {
        verifyGameState(GameState.PLAYING);
        verifyAttackSide(AttackSide.OPPONENT);
        final var response = playerBoard.markAttack(attackDto);

        attackSideManager.changeSide();
        verifyGame(playerBoard);
        notifyUpdate();

        return response;
    }

    public void markPlayerAttack(AttackDto attackDto, AttackResponseDto attackResponse) throws BrokenRuleException {
        verifyGameState(GameState.PLAYING);
        verifyAttackSide(AttackSide.PLAYER);

        opponentBoard.markAttack(attackDto.row(), attackDto.column(), attackResponse);

        verifyGame(opponentBoard);
        attackSideManager.changeSide();
        notifyUpdate();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public BoardData getPlayerBoard() {
        return new BoardData(playerBoard);
    }

    public BoardData getOpponentBoard() {
        return new BoardData(opponentBoard);
    }

    public GameState getState() {
        return stateManager.getState();
    }

    public AttackSide getSide() {
        return attackSideManager.getCurrentSide();
    }

    public GameData getGameData() {
        return new GameData(this);
    }


    public void setGameOver(boolean isWon) {
        if (stateManager.getState() != GameState.GAME_OVER) {
            stateManager.setWon(!isWon);
            stateManager.setState(GameState.GAME_OVER);
            notifyUpdate();
        }
    }

    public boolean isFleetComplete() {
        return playerBoard.getFleet().size() == getTotalShipsAmount();
    }

    public boolean isWon() {
        return stateManager.getIsWon();
    }

    private void notifyUpdate() {
        subject.notify(getGameData());
    }


    private void verifyGameState(GameState gameState) {
        if (stateManager.getState() != gameState) {
            throw new IllegalStateException("Game is not in " + gameState.name() + " phase");
        }
    }

    public void verifyGame(Board board) {
        if (stateManager.getState() == GameState.GAME_OVER) {
            return;
        }
        if (board instanceof PlayerBoard) {
            final var isLose = board.getFleet().stream().allMatch(Ship::isSunk);
            if (isLose) {
                stateManager.setState(GameState.GAME_OVER);
                stateManager.setWon(false);
                notifyUpdate();
            }
        } else {
            if (opponentBoard.getFleet().size() == getTotalShipsAmount()) {
                stateManager.setState(GameState.GAME_OVER);
                stateManager.setWon(true);
                notifyUpdate();
            }
        }
    }

    private void verifyAttackSide(AttackSide side) {
        if (attackSideManager.getCurrentSide() != side) {
            throw new IllegalStateException("It`s not your turn to strike");
        }
    }
}
