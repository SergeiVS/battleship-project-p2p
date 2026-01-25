package org.battleshipprojectp2p.game;

import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.game.board.Board;
import org.battleshipprojectp2p.game.gameDto.*;

import java.util.Random;

public class Game implements GameInterface{
    private final Board boardLocal;
    private final Board boardRemote;

    public Game(Player playerLocal, Player playerRemote,  int rows, int columns) {
        this.boardLocal = new Board(rows, columns, playerLocal);
        this.boardRemote = new Board(rows, columns, playerRemote);
    }

    @Override
    public void setShip(Ship ship, BoardDto boardDto) {
      var player = boardDto.player();
      if(player.isLocal()&& !boardLocal.isFixed()) boardLocal.placeShip(ship);
    }
    @Override
    public void removeShip(Ship ship, BoardDto boardDto) {
        var player = boardDto.player();
        if(player.isLocal()&& !boardLocal.isFixed()) boardLocal.removeShip(ship);
    }

    @Override
    public boolean flipCoin() {
        Random rand = new Random();
        return rand.nextBoolean();
    }

    @Override
    public BoardInitialStateDto startGame() {
        if(boardLocal.isFixed()) {
            return new BoardInitialStateDto(boardLocal);
        }
        throw new RuntimeException("Cannot start game, board is not fixed");
    }


    @Override
    public AttackResponseDto incomingAttack(AttackDto attackDto) {
        return boardLocal.attackFromEnemy(attackDto);
    }
    @Override
    public void fixAttackResult(int row, int column, AttackResponseDto result) {
    boardRemote.attackSelf(row, column, result);
    }

    @Override
    public boolean isVictory() {
        return false;
    }
}
