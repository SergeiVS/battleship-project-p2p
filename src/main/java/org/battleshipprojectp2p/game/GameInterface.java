package org.battleshipprojectp2p.game;

import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.game.gameDto.*;

public interface GameInterface {

    GameSetupResponse gameSetup(GameSetupDto setupDto);
    void setShip(Ship ship);
    void removeShip(Ship ship);
    boolean flipCoin();
    BoardInitialStateDto startGame();
    AttackStatus incomingAttack(AttackDto attackDto);
    void fixAttackResult(AttackResultDto result);
    boolean isVictory();
}
