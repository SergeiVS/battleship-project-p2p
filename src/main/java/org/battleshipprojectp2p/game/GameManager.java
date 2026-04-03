package org.battleshipprojectp2p.game;

import org.battleshipprojectp2p.common.AttackSide;
import org.battleshipprojectp2p.common.GameState;
import org.battleshipprojectp2p.error.BrokenRuleException;
import org.battleshipprojectp2p.game.board.Board;
import org.battleshipprojectp2p.game.board.OpponentBoard;
import org.battleshipprojectp2p.game.board.PlayerBoard;
import org.battleshipprojectp2p.game.board.BoardData;
import org.battleshipprojectp2p.game.board.boardRules.BoardRule;
import org.battleshipprojectp2p.game.board.boardRules.ShipAllowedPositionRule;
import org.battleshipprojectp2p.game.board.boardRules.ShipAmountRule;
import org.battleshipprojectp2p.game.gameDto.AttackDto;
import org.battleshipprojectp2p.game.gameDto.AttackResponseDto;
import org.battleshipprojectp2p.game.gameDto.GameData;
import org.battleshipprojectp2p.game.gameDto.GameSetup;
import org.battleshipprojectp2p.game.observer.GameObserver;
import org.battleshipprojectp2p.game.observer.GameSubject;
import org.battleshipprojectp2p.game.ship.Ship;

import java.util.ArrayList;
import java.util.List;

import static org.battleshipprojectp2p.game.ship.ShipType.getTotalShipsAmount;

public class GameManager {
    private final int rows;
    private final int columns;
    private final PlayerBoard playerBoard;
    private final OpponentBoard opponentBoard;
    private final GameSubject subject = new GameSubject();
    private final StateManager stateManager;
    private final CoinFlipManager coinFlipManager;
    private final AttackSideManager attackSideManager;

    public GameManager(GameSetup setup) {
        this.rows = setup.rows();
        this.columns = setup.columns();
        List<BoardRule> rules = new ArrayList<>(List.of(new ShipAllowedPositionRule(), new ShipAmountRule()));
        this.playerBoard = new PlayerBoard(setup.rows(), setup.columns(), setup.player(), rules);
        this.opponentBoard = new OpponentBoard(setup.rows(), setup.columns(), setup.opponent(), rules);
        this.coinFlipManager = new CoinFlipManager(setup.isHost());
        this.stateManager = new StateManager();
        this.attackSideManager = new AttackSideManager();
    }

    public void ready(boolean opponentFlip) {
        verifyGameState(GameState.SETUP);

        final boolean isFirst = coinFlipManager.isFirstMove(opponentFlip);

        if (isFirst) {
            attackSideManager.setCurrentSide(AttackSide.PLAYER);
        } else {
            attackSideManager.setCurrentSide(AttackSide.OPPONENT);
        }
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
//        verifyAttackSide(AttackSide.OPPONENT);
        final var response = playerBoard.markAttack(attackDto);

        attackSideManager.changeSide();
        verifyGame(playerBoard);
        notifyUpdate();

        return response;
    }

    public void markPlayerAttack(AttackDto attackDto, AttackResponseDto attackResponse) throws BrokenRuleException {
        verifyGameState(GameState.PLAYING);
//        verifyAttackSide(AttackSide.PLAYER);

        opponentBoard.markAttack(attackDto.row(), attackDto.column(), attackResponse);

        verifyGame(opponentBoard);
        attackSideManager.changeSide();
        notifyUpdate();
    }

    public void verifyGame(Board board) {
        if (board instanceof PlayerBoard) {
            final var isLose = board.getFleet().stream()
                    .allMatch(Ship::isSunk);
            if (isLose) {
                stateManager.setState(GameState.GAME_OVER);
                notifyUpdate();
            }
        } else {
            if (opponentBoard.getFleet().size() == getTotalShipsAmount()) {
                stateManager.setState(GameState.GAME_OVER);
                notifyUpdate();
            }
        }
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

    private void notifyUpdate() {
        subject.notify(getGameData());
    }


    public void registerObserver(GameObserver<GameData> gameObserver) {
        subject.subscribe(gameObserver);
    }


    private void verifyGameState(GameState gameState) {
        if (stateManager.state != gameState) {
            throw new IllegalStateException("Game is not in " + gameState.name() + " phase");
        }
    }

    private void verifyAttackSide(AttackSide side) {
        if (attackSideManager.getCurrentSide() != side) {
            throw new IllegalStateException("It`s not your turn to strike");
        }
    }
}
