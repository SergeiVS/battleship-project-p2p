package org.battleshipprojectp2p.game.board;

import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.common.CellValue;
import org.battleshipprojectp2p.error.BrokenRuleException;
import org.battleshipprojectp2p.error.InvalidMoveException;
import org.battleshipprojectp2p.game.board.boardRules.BoardRule;
import org.battleshipprojectp2p.game.board.boardRules.ShipAllowedPositionRule;
import org.battleshipprojectp2p.game.gameDto.AttackResponseDto;
import org.battleshipprojectp2p.game.player.Player;
import org.battleshipprojectp2p.game.ship.Ship;

import java.util.ArrayList;
import java.util.List;

public class OpponentBoard extends Board {


    public OpponentBoard(int rows, int columns, Player owner, List<BoardRule> rules) {
        super(owner, rows, columns, rules);

        for (int i = 0; i < board.length; i++) {
            board[i] = new BoardCell(i);
        }
    }

    public void markAttack(int row, int column, AttackResponseDto attackResponse) throws BrokenRuleException {
        validateRow(row);
        validateColumn(column);

        var cellIndex = getCellIndexByCoordinates(row, column);

        validateIsAttacked(cellIndex);

        board[cellIndex].setAttacked();

        setCellValueAfterHit(attackResponse, cellIndex);
    }

    private void setCellValueAfterHit(AttackResponseDto attackResponse, int cellIndex) throws BrokenRuleException {

        if (AttackStatus.HIT.equals(attackResponse.attackStatus())) {
            board[cellIndex].setCellValue(CellValue.X);
            verifyHitSurround(cellIndex);
        }

        if (AttackStatus.SINK.equals(attackResponse.attackStatus())) {
            board[cellIndex].setCellValue(attackResponse.cellValue());
            findSunkShip(cellIndex, attackResponse.cellValue());
        }
    }

    private void findSunkShip(int cellIndex, CellValue cellValue) throws BrokenRuleException {

        final var shipLength = cellValue.getLength();
        final List<Integer> shipPosition = new ArrayList<>();

        shipPosition.add(cellIndex);

        var isVertical = isIsVertical(cellIndex, shipPosition);

        assert (shipPosition.size() == shipLength);

        shipPosition.forEach(position -> board[position].setCellValue(cellValue));

        int[] positionArray = shipPosition.stream().mapToInt(Integer::intValue).toArray();
        var ship = new Ship(cellValue, positionArray, isVertical, true);

        verifyRules(ship);

        fleet.add(ship);
    }

    private boolean isIsVertical(int cellIndex, List<Integer> shipPosition) {
        var isVertical = true;

        if (board[cellIndex + 1].getCellValue() == CellValue.X || board[cellIndex - 1].getCellValue() == CellValue.X) {
            fillShipPosition(cellIndex, 1, shipPosition);
            isVertical = false;
        } else if (board[cellIndex + columnsCount].getCellValue() == CellValue.X || board[cellIndex - columnsCount].getCellValue() == CellValue.X) {
            fillShipPosition(cellIndex, columnsCount, shipPosition);
        }
        return isVertical;
    }

    private void fillShipPosition(int cellIndex, int shiftIndex, List<Integer> shipPosition) {
        var index = cellIndex + shiftIndex;

        while (index < board.length && board[index].getCellValue() == CellValue.X) {
            shipPosition.add(index);
            index += shiftIndex;
        }
        index = cellIndex - shiftIndex;

        while (index >= 0 && board[index].getCellValue() == CellValue.X) {
            shipPosition.add(index);
            index -= shiftIndex;
        }

    }

    private void validateIsAttacked(int cellIndex) {
        if (board[cellIndex].isHit()) {
            throw new InvalidMoveException("Double tap is not alloyed");
        }
    }

    private void validateColumn(int col) {
        if (col < 0 || col >= columnsCount) {
            throw new IllegalArgumentException("Invalid column");
        }
    }

    private void validateRow(int row) {
        if (row < 0 || row >= rowsCount) {
            throw new IllegalArgumentException("Invalid row");
        }
    }

    private void verifyHitSurround(int cellIndex) throws BrokenRuleException {
        for (Ship ship : fleet) {
            verifyRules(ship);
        }
    }
}