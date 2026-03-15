package org.battleshipprojectp2p.error;

import org.battleshipprojectp2p.game.board.boardRules.BoardRule;

public class BrokenRuleException extends Exception {

    private final String rule;

    public BrokenRuleException(Class<? extends BoardRule> rule, String message) {
        this.rule = rule.getSimpleName();
        super(message);
    }

    public String getRule() {
        return rule;
    }

}
