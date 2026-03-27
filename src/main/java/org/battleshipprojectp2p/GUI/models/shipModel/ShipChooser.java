package org.battleshipprojectp2p.GUI.models.shipModel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.battleshipprojectp2p.game.ship.ShipType;

import java.util.function.Consumer;

public class ShipChooser extends VBox {

    private static final String LABEL_TEXT = "%s: %s/%s";
    private final ShipType shipType;
    private int amountOfShipsChosen = 0;
    private final Button chooseButton;
    private final Label textLabel;

    public ShipChooser(ShipType shipType, Consumer<ShipType> onClick) {
        this.shipType = shipType;

        this.chooseButton = new Button();
        ShipModel ship = new ShipModel(shipType);
        this.chooseButton.setGraphic(ship);
        this.chooseButton.setOnAction(e -> onClick.accept(shipType));
        this.chooseButton.setPadding(Insets.EMPTY);

        this.textLabel = new Label(LABEL_TEXT.formatted(shipType.name(), amountOfShipsChosen, shipType.getTotalAmount()));
        this.textLabel.setAlignment(Pos.CENTER);

        this.getChildren().addAll(chooseButton, textLabel);

        this.setAlignment(Pos.BASELINE_RIGHT);
        this.setSpacing(5);
    }

    public void incrementShipsAmount() {

        this.amountOfShipsChosen++;
        this.textLabel.setText(LABEL_TEXT.formatted(this.shipType.name(), this.amountOfShipsChosen, this.shipType.getTotalAmount()));

        if (this.amountOfShipsChosen == this.shipType.getTotalAmount()) {
            this.chooseButton.setDisable(true);
        }
    }

    public void decrementShipsAmount() {

        this.amountOfShipsChosen--;
        this.textLabel.setText(LABEL_TEXT.formatted(this.shipType.name(), this.amountOfShipsChosen, this.shipType.getTotalAmount()));

        if (this.amountOfShipsChosen < shipType.getTotalAmount()) {
            this.chooseButton.setDisable(false);
        }
    }
}
