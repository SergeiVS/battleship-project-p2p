package org.battleshipprojectp2p.game.board.boardRules;

import org.battleshipprojectp2p.common.CellValue;
import org.battleshipprojectp2p.error.BrokenRuleException;
import org.battleshipprojectp2p.game.board.Board;
import org.battleshipprojectp2p.game.board.PlayerBoard;
import org.battleshipprojectp2p.game.ship.Ship;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ShipAmountRule implements BoardRule {

    private static final Map<String, Integer> shipAmountRules = new HashMap<>();
    private static int totalAmount;

    public ShipAmountRule() {
        Arrays.stream(CellValue.values()).forEach(val -> {
            if (val != CellValue.E && val != CellValue.X) {
                shipAmountRules.put(val.name(), val.getMaxCount());
            }
        });
        totalAmount = shipAmountRules.values().stream().mapToInt(Integer::intValue).sum();
    }

    @Override
    public void verifyRule(Board board, Ship ship) throws BrokenRuleException {
        final var fleet = board.getFleet();
        final var type = ship.type();

        if (fleet.size() >= totalAmount) {
            throw new BrokenRuleException(this.getClass(), "Fleet size is reached");
        }

        final boolean tooManyShipsByType = fleet.stream()
                .filter((s -> s.type() == type)).count() + 1 > shipAmountRules.get(ship.type().name());

        if (tooManyShipsByType) {
            throw new BrokenRuleException(this.getClass(), "Amount of " + type.getName() + " is reached");
        }
    }
}
