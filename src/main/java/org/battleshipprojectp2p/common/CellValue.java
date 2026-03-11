package org.battleshipprojectp2p.common;

import org.battleshipprojectp2p.game.ship.ShipType;

public enum CellValue {
    B("battleship"),
    C("carrier"),
    D("destroyer"),
    E("empty"),
    F("frigate"),
    K("killed"),
    M("miss"),
    S("submarine"),
    X("hit");

    private final String name;

    CellValue(String name) {
        this.name = name;
    }


    public String getName() {
        return this.name;
    }

    public static ShipType getShipTypeFromCellValue(CellValue cellValue) {

        return switch (cellValue) {
            case B -> ShipType.BATTLESHIP;
            case C -> ShipType.CARRIER;
            case D -> ShipType.DESTROYER;
            case F -> ShipType.FRIGATE;
            case S -> ShipType.SUBMARINE;
            default -> ShipType.UNDEFINED;
        };
    }
}
