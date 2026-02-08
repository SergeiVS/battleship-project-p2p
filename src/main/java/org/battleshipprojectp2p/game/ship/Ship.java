package org.battleshipprojectp2p.game.ship;

import org.battleshipprojectp2p.common.CellValue;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public record Ship(
        CellValue type,
        int[] position,
        boolean isVertical,
        boolean isSunk

) {
    public Ship {
        if (CellValue.E.equals(type) || CellValue.X.equals(type)) {
            throw new IllegalArgumentException("Invalid cell type. Ship could not be of Type empy or attacked");
        }
        if (position.length != type.getLength()) {
            throw new IllegalArgumentException("Invalid ship position length. Ship could not be of Type empy or attacked");
        }
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
