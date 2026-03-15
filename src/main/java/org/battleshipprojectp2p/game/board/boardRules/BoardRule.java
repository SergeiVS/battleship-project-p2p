package org.battleshipprojectp2p.game.board.boardRules;

import org.battleshipprojectp2p.error.BrokenRuleException;
import org.battleshipprojectp2p.game.board.Board;
import org.battleshipprojectp2p.game.ship.Ship;

public interface BoardRule {
    void verifyRule(Board Board, Ship ship) throws BrokenRuleException;
}
