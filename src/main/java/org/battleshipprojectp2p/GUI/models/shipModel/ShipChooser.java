package org.battleshipprojectp2p.GUI.models.shipModel;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.battleshipprojectp2p.game.ship.ShipType;

import java.util.function.Consumer;

public class ShipChooser extends VBox {

    private static final String LABEL_TEXT = "%s: %s/%s";
    private final ShipType shipType;
    private int amountOfShipsChosen = 0;
    private Button chooseButton;
    private Label textLable;

    public ShipChooser(ShipType shipType, Consumer<ShipType> onClick) {
        this.shipType = shipType;

        chooseButton = new Button();
        ShipModel ship = new ShipModel(shipType);
        chooseButton.setGraphic(ship);
        chooseButton.setOnAction(e -> onClick.accept(shipType));
        chooseButton.setMinWidth(ship.getWidth());
        chooseButton.setMinHeight(ship.getHeight());
        chooseButton.setPadding(Insets.EMPTY);

        textLable = new Label(LABEL_TEXT.formatted(shipType.name(), amountOfShipsChosen, shipType.getTotalAmount()));
        textLable.setAlignment(Pos.CENTER);

        this.getChildren().addAll(chooseButton, textLable);
        this.setAlignment(Pos.BASELINE_RIGHT);
        this.setSpacing(5);
    }

    public void incrementShipsAmount() {

        this.amountOfShipsChosen++;
        textLable.setText(LABEL_TEXT.formatted(shipType.name(), amountOfShipsChosen, shipType.getTotalAmount()));
        if (amountOfShipsChosen == shipType.getTotalAmount()) {
            chooseButton.setDisable(true);
        }
    }

    public void decrementShipsAmount() {
        this.amountOfShipsChosen--;
        textLable.setText(LABEL_TEXT.formatted(shipType.name(), amountOfShipsChosen, shipType.getTotalAmount()));
        if (amountOfShipsChosen == shipType.getTotalAmount()) {
            chooseButton.setDisable(false);
        }
    }

}
