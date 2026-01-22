package org.battleshipprojectp2p.game;

import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.game.board.Board;
import org.battleshipprojectp2p.game.gameDto.*;

public class Game implements GameInterface{
    private final Board boardLocal;
    private final Board boardRemote;


    public Game(Player playerLocal, Player playerRemote,  int rows, int columns) {
        this.boardLocal = new Board(rows, columns, playerLocal);
        this.boardRemote = new Board(rows, columns, playerRemote);
    }


    /**
     * @param setupDto
     * @return
     */
    @Override
    public GameSetupResponse gameSetup(GameSetupDto setupDto) {
        return null;
    }

    /**
     * @param ship
     */
    @Override
    public void setShip(Ship ship) {

    }

    /**
     * @param ship
     */
    @Override
    public void removeShip(Ship ship) {

    }

    /**
     * @return
     */
    @Override
    public boolean flipCoin() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public BoardInitialStateDto startGame() {
        return null;
    }

    /**
     * @param attackDto
     * @return
     */
    @Override
    public AttackStatus incomingAttack(AttackDto attackDto) {
        return null;
    }

    /**
     * @param result
     */
    @Override
    public void fixAttackResult(AttackResultDto result) {

    }

    /**
     * @return
     */
    @Override
    public boolean isVictory() {
        return false;
    }
}
