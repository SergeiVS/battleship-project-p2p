package org.battleshipprojectp2p.game;

import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.game.gameDto.*;

public interface GameInterface {
    void setShip(Ship ship, BoardDto boardDto);
    void removeShip(Ship ship, BoardDto boardDto);
    boolean flipCoin();
    BoardInitialStateDto startGame();
    AttackResponseDto incomingAttack(AttackDto attackDto);
    void fixAttackResult(int row, int column, AttackResponseDto result);
    boolean isVictory();
}
