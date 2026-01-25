package org.battleshipprojectp2p.game.board;
import org.battleshipprojectp2p.game.gameDto.AttackDto;
import org.battleshipprojectp2p.game.gameDto.AttackResponseDto;
import org.battleshipprojectp2p.game.gameDto.Ship;

public interface BoardInterface {
    void placeShip(Ship ship);

    void removeShip(Ship ship);

    void fixBoard();

    AttackResponseDto attackFromEnemy(AttackDto attackDto);
    void attackSelf(int row, int column, AttackResponseDto attackResponse);
}
