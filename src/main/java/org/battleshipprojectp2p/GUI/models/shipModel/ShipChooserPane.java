package org.battleshipprojectp2p.GUI.models.shipModel;

import javafx.scene.layout.VBox;
import org.battleshipprojectp2p.game.ship.ShipType;

import java.util.Arrays;
import java.util.EnumMap;

public class ShipChooserPane extends VBox {

    private final EnumMap<ShipType, ShipChooser> shipsChoosers = new EnumMap<>(ShipType.class);

    public ShipChooserPane() {
        final var ships = Arrays.stream(ShipType.values())
                .filter(type -> type != ShipType.UNDEFINED).toList();

        for (ShipType ship : ships) {
            var chooser = new ShipChooser(ship);
            chooser.initialize();
            shipsChoosers.put(ship, chooser);
            this.getChildren().add(chooser);
        }
        this.setSpacing(10);
    }
}
