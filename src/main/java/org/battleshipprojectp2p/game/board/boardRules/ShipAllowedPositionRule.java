package org.battleshipprojectp2p.game.board.boardRules;

import org.battleshipprojectp2p.common.CellValue;
import org.battleshipprojectp2p.error.BrokenRuleException;
import org.battleshipprojectp2p.game.board.Board;
import org.battleshipprojectp2p.game.board.PlayerBoard;
import org.battleshipprojectp2p.game.ship.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ShipAllowedPositionRule implements BoardRule {

    @Override
    public void verifyRule(Board board, Ship ship) throws BrokenRuleException {
        final var position = IntStream.of(ship.position()).sorted().boxed().toList();

        throwIfPositionOutOfBounds(board, position);

        final var positionSurround = getShipSurround(board, position, ship.isVertical());

        throwIfPositionNotFree(board, positionSurround);
    }

    private void throwIfPositionOutOfBounds(Board board, List<Integer> position) throws BrokenRuleException {
        final var boardArray = board.getBoard();

        for (int p : position) {
            if (p < 0 || p >= boardArray.length) {
                throw new BrokenRuleException(this.getClass(), "Ship position is out of bounds");
            }
        }
    }

    private List<Integer> getShipSurround(Board board, List<Integer> position, boolean vertical) throws BrokenRuleException {
        List<Integer> surround = new ArrayList<>();
        final var colCount = board.getColumnsCount();

        if (!vertical) {
            var firstPosition = position.getFirst() - 1;
            var lastPosition = position.getLast() + 1;
            surround.addAll(List.of(
                    firstPosition,
                    firstPosition + colCount,
                    firstPosition - colCount,
                    lastPosition,
                    lastPosition + colCount,
                    lastPosition - colCount
            ));
            for (int p : position) {
                surround.addAll(List.of(p - colCount, p + colCount));
            }
        } else {
            var firstPosition = position.getFirst() - colCount;
            var lastPosition = position.getLast() + colCount;
            surround.addAll(List.of(
                    firstPosition,
                    firstPosition + 1,
                    firstPosition - 1,
                    lastPosition,
                    lastPosition - 1,
                    lastPosition + 1
            ));

            for (int i = 1; i < position.size(); ++i) {
                int p = position.get(i);
                surround.addAll(List.of(p - 1, p + 1));
            }
        }
        return surround;
    }

    private void throwIfPositionNotFree(Board board, List<Integer> surround) throws BrokenRuleException {
        final var boardArray = board.getBoard();
        final var boardLength = boardArray.length;

        for (int i : surround) {
            if (i >= 0 && i < boardLength) {
                if (boardArray[i].getCellValue() != CellValue.E) {
                    throw new BrokenRuleException(this.getClass(), "Ship overlaps with another ship position, min distance 1 cell in ary direction");
                }
            }
        }
    }
}
