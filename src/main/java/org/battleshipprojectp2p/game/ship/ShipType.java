package org.battleshipprojectp2p.game.ship;

import org.battleshipprojectp2p.common.CellValue;

import java.util.Arrays;
import java.util.List;

public enum ShipType {

    CARRIER(5, 1),
    BATTLESHIP(4, 2),
    FRIGATE(3, 3),
    DESTROYER(2, 4),
    SUBMARINE(1, 1),
    UNDEFINED(0, 0);

    private final int length;
    private final int totalAmount;

    ShipType(int length, int totalAmount) {
        this.length = length;
        this.totalAmount = totalAmount;
    }

    public int getLength() {
        return length;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public static List<ShipType> getAllShipClasses() {
        return Arrays.asList(ShipType.values());
    }

    public static int getTotalShipsAmount() {
        return Arrays.stream(ShipType.values()).mapToInt(ShipType::getTotalAmount).sum();
    }

    public static CellValue getCellValueFromShipClass(ShipType type) {
        return switch (type) {
            case CARRIER -> CellValue.C;
            case BATTLESHIP -> CellValue.B;
            case FRIGATE -> CellValue.F;
            case DESTROYER -> CellValue.D;
            case SUBMARINE -> CellValue.S;
            case UNDEFINED -> CellValue.E;
        };
    }

    public static ShipType getShipByLength(int length) {
        return switch (length) {
            case 5 -> ShipType.CARRIER;
            case 4 -> ShipType.BATTLESHIP;
            case 3 -> ShipType.FRIGATE;
            case 2 -> ShipType.DESTROYER;
            case 1 -> ShipType.SUBMARINE;
            default -> throw new IllegalArgumentException("Illegal length: " + length);
        };
    }
}
