package org.battleshipprojectp2p.common;

import java.util.Arrays;
import java.util.List;

public enum ShipClass {

    CARRIER(5, 1),
    BATTLESHIP(4, 2),
    DESTROYER(2, 4),
    FRIGATE(3, 3),
    SUBMARINE(1, 1);

    private final int length;
    private final int totalAmount;

    ShipClass(int length, int totalAmount) {
        this.length = length;
        this.totalAmount = totalAmount;
    }

    public int getLength() {
        return length;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public static List<ShipClass> getAllShipClasses() {
        return Arrays.asList(ShipClass.values());
    }

}
