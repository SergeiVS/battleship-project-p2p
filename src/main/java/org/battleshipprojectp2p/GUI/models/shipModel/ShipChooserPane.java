package org.battleshipprojectp2p.GUI.models.shipModel;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.battleshipprojectp2p.game.ship.ShipType;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.function.Consumer;

public class ShipChooserPane extends VBox {

    private final EnumMap<ShipType, ShipChooser> shipsChoosers = new EnumMap<>(ShipType.class);

    public ShipChooserPane(Consumer<ShipType> onClick) {
        final var ships = Arrays.stream(ShipType.values())
                .filter(type -> type != ShipType.UNDEFINED).toList();

        for (ShipType ship : ships) {
            var chooser = new ShipChooser(ship, onClick);
            shipsChoosers.put(ship, chooser);
            this.getChildren().add(chooser);
        }
        this.setSpacing(10);
        this.setAlignment(Pos.TOP_CENTER);
    }

    public ShipChooser getShipChooser(ShipType ship) {
        return shipsChoosers.get(ship);
    }
}
