package org.battleshipprojectp2p.game.board;

import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.game.gameDto.Ship;

public interface BoardInterface {

    AttackStatus attack(int row, int col);
    Board getBoardArray();
    Board placeShip(Ship ship);

    Board removeShip(Ship ship);

    Board fixBoard(Board board);

}
