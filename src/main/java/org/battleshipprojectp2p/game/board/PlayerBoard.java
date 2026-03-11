package org.battleshipprojectp2p.game.board;

import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.common.CellValue;
import org.battleshipprojectp2p.error.BrokenRuleException;
import org.battleshipprojectp2p.error.InvalidMoveException;
import org.battleshipprojectp2p.game.board.boardRules.BoardRule;
import org.battleshipprojectp2p.game.gameDto.AttackDto;
import org.battleshipprojectp2p.game.gameDto.AttackResponseDto;
import org.battleshipprojectp2p.game.ship.Ship;
import org.battleshipprojectp2p.game.player.Player;

import java.util.*;

import static org.battleshipprojectp2p.game.ship.ShipType.getCellValueFromShipClass;

public class PlayerBoard extends Board {


    public PlayerBoard(int rows, int columns, Player owner, List<BoardRule> rules) {
        super(owner, rows, columns, rules);

    }


    public void addShip(Ship ship) throws BrokenRuleException {

        verifyRules(ship);
        final var shipPosition = ship.position();

        Arrays.stream(shipPosition).forEach(
                position -> board[position]
                        .setCellValue(getCellValueFromShipClass(ship.type()))
        );
        fleet.add(ship);
    }

    public void removeShip(Ship ship) {
        var shipPosition = ship.position();

        Arrays.stream(shipPosition).forEach(position -> {
            if (
                    board[position].getCellValue()
                            .equals(getCellValueFromShipClass(ship.type()))
            ) {
                board[position].setCellValue(CellValue.E);
            } else {
                throw new IllegalArgumentException("Invalid ship position");
            }
        });
        fleet.remove(ship);
    }


    public AttackResponseDto markAttack(AttackDto attackDto) {
        Player player = attackDto.player();
        int row = attackDto.row();
        int col = attackDto.column();

        validatePlayer(player);
        validateRow(row);
        validateColumn(col);

        var cellIndex = getCellIndexByCoordinates(attackDto.row(), attackDto.column());
        validateIsAttacked(cellIndex);
        board[cellIndex].setAttacked();

        if (board[cellIndex].getCellValue() == CellValue.E) {
            return new AttackResponseDto(AttackStatus.MISS, CellValue.E);
        }

        var ship = getShipByCell(cellIndex);
        var notAttacked = Arrays.stream(ship.position()).filter(position -> !board[position].isHit()).toArray();

        if (notAttacked.length == 0) {
            setShipSunk(ship);
            return new AttackResponseDto(AttackStatus.SINK, getCellValueFromShipClass(ship.type()));
        }

        return new AttackResponseDto(AttackStatus.HIT, CellValue.X);
    }

    private Ship getShipByCell(int cellIndex) {
        return fleet.stream()
                .filter(s -> Arrays.stream(s.position()).anyMatch((c) -> c == cellIndex)).findFirst()
                .orElseThrow(() -> new RuntimeException("Ship not Found"));
    }

    private void setShipSunk(Ship ship) {
        final var shipIndex = fleet.indexOf(ship);
        fleet.set(shipIndex, ship.sunk());
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

    private void validatePlayer(Player player) {
        if (!player.name().equals(owner.name())) {
            throw new IllegalArgumentException("Player is not equal to this player");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PlayerBoard playerBoard1 = (PlayerBoard) o;
        return rowsCount == playerBoard1.rowsCount && columnsCount == playerBoard1.columnsCount && Objects.equals(owner, playerBoard1.owner) && Objects.deepEquals(board, playerBoard1.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, rowsCount, columnsCount, Arrays.hashCode(board));
    }

}