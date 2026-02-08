package org.battleshipprojectp2p.game.gameDto;

import org.battleshipprojectp2p.common.AttackSide;
import org.battleshipprojectp2p.common.GameState;
import org.battleshipprojectp2p.game.GameManager;
import org.battleshipprojectp2p.game.board.BoardData;

public record GameData(BoardData board, BoardData opponentBoard, GameState state, AttackSide attackSide) {
    public GameData(GameManager game) {
        this(game.getPlayerBoard(), game.getOpponentBoard(), game.getState(), game.getSide());
    }
}
