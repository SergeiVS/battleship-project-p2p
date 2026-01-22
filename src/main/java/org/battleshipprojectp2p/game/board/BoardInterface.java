package org.battleshipprojectp2p.game.board;
import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.game.Player;
import org.battleshipprojectp2p.game.gameDto.AttackDto;
import org.battleshipprojectp2p.game.gameDto.Ship;

public interface BoardInterface {
    BoardInterface placeSip(Ship ship);

    BoardInterface removeShip(Ship ship);

    BoardInterface fixBoard();

    AttackStatus attackEnemy(AttackDto attackDto);
    BoardInterface attackSelf(int row, int column, AttackStatus  status);
}
