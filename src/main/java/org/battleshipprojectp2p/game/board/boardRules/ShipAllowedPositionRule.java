package org.battleshipprojectp2p.game.board.boardRules;

import org.battleshipprojectp2p.common.CellValue;
import org.battleshipprojectp2p.error.BrokenRuleException;
import org.battleshipprojectp2p.game.board.Board;
import org.battleshipprojectp2p.game.ship.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ShipAllowedPositionRule implements BoardRule {

    @Override
    public void verifyRule(Board board, Ship ship) throws BrokenRuleException {
        final var position = IntStream.of(ship.position()).sorted().boxed().toList();

        IO.println("Position: " + position);

        throwIfPositionOutOfBounds(board, ship);

        final var positionSurround = getShipSurround(board, position, ship.isVertical());

        IO.println("Surround: " + positionSurround);

        throwIfPositionNotFree(board, positionSurround);
    }

    private void throwIfPositionOutOfBounds(Board board, Ship ship) throws BrokenRuleException {
        final var boardArray = board.getBoard();
        final var position = ship.position();
        final var shipLength = ship.position().length;
        final var isVertical = ship.isVertical();
        final var firstCell = position[0];

        if (!isVertical && (board.getColumnsCount() - (firstCell % board.getColumnsCount())) < shipLength) {

            IO.println("Position out of bounds: " + firstCell);
            throw new BrokenRuleException(this.getClass(), "Ship position is out of bounds");
        }

        if (isVertical && (board.getRowsCount() - (firstCell / board.getColumnsCount())) < shipLength) {

            IO.println("isVertical : " + (board.getRowsCount() - (firstCell / board.getRowsCount())) + ", Shiplength: " + shipLength);
            IO.println("Position out of bounds: " + firstCell);
            throw new BrokenRuleException(this.getClass(), "Ship position is out of bounds");
        }

        for (int p : position) {
            if (p < 0 || p >= boardArray.length) {
                throw new BrokenRuleException(this.getClass(), "Ship position is out of bounds");
            }
        }
    }

    private void throwIfPositionNotFree(Board board, List<Integer> surround) throws BrokenRuleException {
        final var boardArray = board.getBoard();
        final var boardLength = boardArray.length;

        for (int p : surround) {
            if (p >= 0 && p < boardLength) {
                if (boardArray[p].getCellValue() != CellValue.E) {
                    IO.println("Position not free: " + p);
                    throw new BrokenRuleException(this.getClass(), "Ship overlaps with another ship position, min distance 1 cell in ary direction");
                }
            }
        }
    }

    private List<Integer> getShipSurround(Board board, List<Integer> position, boolean vertical) throws BrokenRuleException {
        List<Integer> surround = new ArrayList<>();
        List<Integer> surroundTemp = new ArrayList<>();
        final var colCount = board.getColumnsCount();
        var firstPosition = position.getFirst();
        var lastPosition = position.getLast();

        if (!vertical) {
            final var row = position.getFirst() / colCount;
            IO.println("Row: " + row);
            final var p0 = firstPosition - 1;
            final var p1 = lastPosition + 1;

            surroundTemp.addAll(position);

            if (p0 >= 0) {
                surroundTemp.add(p0);
            }

            if (p1 % colCount != 0) {
                surroundTemp.add(p1);
            }

            for (int p : surroundTemp) {
                if (p / colCount == row) {
                    if ((p - colCount) >= 0) {
                        surround.add(p - colCount);
                    }
                    if ((p + colCount) < board.getBoard().length) {
                        surround.add(p + colCount);
                    }
                    surround.add(p);
                }
            }

        } else {

            final var col = position.getFirst() % colCount;
            IO.println("Column: " + col);
            final var p0 = firstPosition - colCount;
            final var p1 = lastPosition + colCount;

            surroundTemp.addAll(position);
            surroundTemp.addAll(List.of(p0, p1));

            for (int p : surroundTemp) {
                if (p % colCount == col) {
                    if ((p + 1) % colCount != 0) {
                        surround.add(p + 1);
                    }
                    if ((p - 1) % colCount != board.getColumnsCount() - 1) {
                        surround.add(p - 1);
                    }
                    surround.add(p);
                }

            }
        }
        return surround;
    }
}
