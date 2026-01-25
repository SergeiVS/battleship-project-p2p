package org.battleshipprojectp2p.game.board;

import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.common.CellValue;
import org.battleshipprojectp2p.error.InvalidMoveException;
import org.battleshipprojectp2p.game.Player;
import org.battleshipprojectp2p.game.gameDto.AttackDto;
import org.battleshipprojectp2p.game.gameDto.AttackResponseDto;
import org.battleshipprojectp2p.game.gameDto.PlayerDto;
import org.battleshipprojectp2p.game.gameDto.Ship;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.IO.println;
//TODO Add fleet for enemy board

public class Board implements BoardInterface {

    private final Player boardOwner;
    private final int rowsCount;
    private final int columnsCount;

    private final BoardCell[] board;
    /*
     *    True if all ships are placed and accepted by player ships should not be moved boardOwner- HOST
     *    False if board is not yet ready, or boardOwner-ENEMY
     */
    private boolean isFixed;

    private final List<Ship> fleet;


    public Board(int rows, int columns, Player boardOwner) {
        this.boardOwner = boardOwner;
        this.rowsCount = rows;
        this.columnsCount = columns;
        this.board = new BoardCell[rows * columns];
        this.isFixed = false;

        for (int i = 0; i < board.length; i++) {
            board[i] = new BoardCell(i);
        }
        this.fleet = new ArrayList<>();
    }

    public PlayerDto getBoardOwner() {
        return new PlayerDto(boardOwner);
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public int getColumnsCount() {
        return columnsCount;
    }

    public BoardCell[] getBoard() {
        return board;
    }

    public boolean isFixed() {
        return isFixed;
    }
    @Override
    public void placeShip(Ship ship) {
        validateShipPosition(ship);

        var shipPosition = ship.position();

        Arrays.stream(shipPosition).forEach(position ->board[position].setCellValue(ship.type()));
        fleet.add(ship);
    }

    @Override
    public void removeShip(Ship ship) {
        var shipPosition = ship.position();

        Arrays.stream(shipPosition).forEach(position -> {
            if (board[position].getCellValue().equals(ship.type())) {
                board[position].setCellValue(CellValue.E);
            }else {
                throw new IllegalArgumentException("Invalid ship position");
            }
        });
        fleet.remove(ship);
    }

    @Override
    public void fixBoard() {
        if (!isFixed) {
            this.isFixed = true;
        }
    }

    @Override
    public AttackResponseDto attackFromEnemy(AttackDto attackDto) {
        PlayerDto player = attackDto.player();
        int row = attackDto.row();
        int col = attackDto.column();

        validatePlayer(player);
        validateRow(row);
        validateColumn(col);

        var cellIndex = getCellIndex(attackDto.row(), attackDto.column());
        validateIsAttacked(cellIndex);
        board[cellIndex].setIsAttacked(true);

        if (board[cellIndex].getCellValue() == CellValue.E) {
            return new AttackResponseDto(AttackStatus.MISS, CellValue.E);
        }

        var ship = getShipByCell(cellIndex);
        var notAttacked = Arrays.stream(ship.position()).filter(position -> !board[position].isAttacked()).toArray();

        return (notAttacked.length == 0)? new AttackResponseDto(AttackStatus.SINK, ship.type()):
                new AttackResponseDto(AttackStatus.HIT, null);
    }

    @Override
    public void attackSelf(int row, int column, AttackResponseDto attackResponse) {
        var cellIndex = getCellIndex(row, column);
        var cellValue = attackResponse.getCellValue();

        validateIsLocal();
        validateIsAttacked(cellIndex);
        validateRow(row);
        validateColumn(column);

        board[cellIndex].setAttacked();
        setCellValueAfterHit(attackResponse, cellIndex, cellValue);
    }

    private void setCellValueAfterHit(AttackResponseDto attackResponse, int cellIndex, Optional<CellValue> cellValue) {
        if(AttackStatus.HIT.equals(attackResponse.attackStatus())) {
            board[cellIndex].setCellValue(CellValue.X);
        }
        if(AttackStatus.SINK.equals(attackResponse.attackStatus())) {
            if(cellValue.isEmpty()){
                throw new IllegalArgumentException("Invalid cell value, by AttackStatus.Sink cell value could not be empty");
            }
            board[cellIndex].setCellValue(attackResponse.cellValue());
        }
    }

    private Ship getShipByCell(int cellIndex) {
        return fleet.stream().filter(s -> Arrays.stream(s.position())
                        .anyMatch((c)-> c== cellIndex))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private int getCellIndex(int row, int column) {
        return (row *columnsCount)+ column;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    private void validateIsLocal() {
        if(boardOwner.isLocal() ) {
            throw new IllegalArgumentException("Method alloyed for remote player only");
        }
    }

    private void validateIsAttacked(int cellIndex) {
        if(board[cellIndex].isAttacked()) {
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

    private void validatePlayer(PlayerDto player) {
        if (!player.name().equals(
                boardOwner.getName())
                && player.isLocal() != boardOwner.isLocal()
        ) {
            throw new IllegalArgumentException("Player is not equal to this player");
        }
    }

    private void validateShipPosition(Ship ship){
        var position = IntStream.of(ship.position()).sorted().boxed().toList();
        var shipLength = ship.type().getLength();
        if(position.size() != shipLength){
            throw new IllegalArgumentException("Invalid ship position");
        }
        validateShipSurround(position, shipLength, ship.isVertical());
    }

    private void validateShipSurround(List<Integer> position, int shipLength, boolean vertical) {
        List<Integer> surround = new ArrayList<>(position);
        List<Integer> surroundTemp = new ArrayList<>();

        if (!vertical) {
            surround.addAll(List.of(position.getFirst() - 1, position.getLast() + 1));
            for (int i = 1; i < surround.size(); ++i) {
                var p = surround.get(i);
                surroundTemp.add(p-columnsCount);
                surroundTemp.add(p+columnsCount);
            }
            surround.addAll(surroundTemp);
            throwIfPositionNotFree(surround);
        }else {
            surround.addAll(List.of(position.getFirst() - columnsCount, position.getLast() + columnsCount));

            for (int i = 1; i < surround.size(); ++i) {
                var p = surround.get(i);
                surroundTemp.add(p-1);
                surroundTemp.add(p+1);
            }
            surround.addAll(surroundTemp);
            throwIfPositionNotFree(surround);
        }
    }

    private void throwIfPositionNotFree(List<Integer> surround) {
        for (int i : surround) {
            if (i >=0 && i<board.length) {
                if(board[i].getCellValue() != CellValue.E){
                    throw new RuntimeException("This ship is not alloyed");
                }
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Board board1 = (Board) o;
        return rowsCount == board1.rowsCount && columnsCount == board1.columnsCount && isFixed == board1.isFixed && Objects.equals(boardOwner, board1.boardOwner) && Objects.deepEquals(board, board1.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardOwner, rowsCount, columnsCount, Arrays.hashCode(board), isFixed);
    }

}