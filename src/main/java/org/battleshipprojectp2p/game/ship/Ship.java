package org.battleshipprojectp2p.game.ship;

import java.util.Arrays;
import java.util.Objects;

public record Ship(
        ShipType type,
        int[] position,
        boolean isVertical,
        boolean isSunk

) {
    public Ship(ShipType type) {

        this(type, null, false, false);
        validateShipType();
    }

    public Ship(ShipType type, int[] position, boolean isVertical, boolean isSunk) {
        this.type = type;
        this.position = position;
        this.isVertical = isVertical;
        this.isSunk = isSunk;
        validateShipType();
    }

    public Ship rotate(boolean isVert, int cols) {

        int[] pos = new int[type.getLength()];
        if (isVert) {
            for (int i = 0; i < type.getLength(); i++) {
                pos[i] = position[i] + cols;
            }
        } else {
            for (int i = 0; i < type.getLength(); i++) {
                pos[i] = position[i] + 1;
            }
        }
        return new Ship(this.type, pos, isVert, false);
    }

    public Ship sunk() {
        return new Ship(this.type, this.position, this.isVertical, true);
    }

    public Ship setVertical(boolean isVertical) {
        return new Ship(this.type, this.position, isVertical, this.isSunk);

    }

    private void validateShipType() {

        if (ShipType.UNDEFINED == type) {
            throw new IllegalArgumentException("Invalid cell type. Ship could not be of Type undefined");
        }
    }

    private void validateShipLength() {

        if (position.length != type.getLength()) {
            IO.println(position.length + " " + type.getLength());
            throw new IllegalArgumentException("Invalid ship position length. Ship could not be of Type empy or attacked");
        }

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return isVertical == ship.isVertical && type == ship.type && Objects.deepEquals(Arrays.stream(position).toArray(), Arrays.stream(ship.position).toArray());
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, Arrays.hashCode(position), isVertical);
    }
}
