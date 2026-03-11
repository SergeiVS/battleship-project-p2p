package org.battleshipprojectp2p.game.board.boardRules;

import org.battleshipprojectp2p.error.BrokenRuleException;
import org.battleshipprojectp2p.game.board.Board;
import org.battleshipprojectp2p.game.ship.Ship;

import java.util.HashMap;
import java.util.Map;

import static org.battleshipprojectp2p.game.ship.ShipType.getAllShipClasses;

public class ShipAmountRule implements BoardRule {

    private static final Map<String, Integer> shipAmountRules = new HashMap<>();
    private static int totalAmount;

    public ShipAmountRule() {
        getAllShipClasses().forEach(c -> {
            shipAmountRules.put(c.name(), c.getTotalAmount());
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
            throw new BrokenRuleException(this.getClass(), "Amount of " + type.name() + " is reached");
        }
    }
}
