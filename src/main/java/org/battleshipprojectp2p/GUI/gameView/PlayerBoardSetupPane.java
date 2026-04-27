package org.battleshipprojectp2p.GUI.gameView;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.battleshipprojectp2p.GUI.models.boardModel.CellColors;
import org.battleshipprojectp2p.GUI.models.boardModel.PaintableBoardCell;
import org.battleshipprojectp2p.GUI.models.boardModel.PlayerBoardBuilderPane;
import org.battleshipprojectp2p.GUI.models.shipModel.ShipChooserPane;
import org.battleshipprojectp2p.common.GameState;
import org.battleshipprojectp2p.error.BrokenRuleException;
import org.battleshipprojectp2p.game.gameDto.GameData;
import org.battleshipprojectp2p.game.observer.GameObserver;
import org.battleshipprojectp2p.game.ship.Ship;
import org.battleshipprojectp2p.service.AbstractService;

import java.util.Arrays;
import java.util.Optional;

import static org.battleshipprojectp2p.GUI.utils.AlertService.showAlert;

public class PlayerBoardSetupPane extends HBox implements GameObserver<GameData> {

    private final AbstractService service;
    private PlayerBoardBuilderPane playersBoard;
    private ShipChooserPane chooserPane;

    public PlayerBoardSetupPane(AbstractService service) {
        this.service = service;
        service.registerObserver(this);
    }

    public void initialize() {
        this.playersBoard = new PlayerBoardBuilderPane(service.getPlayerBoard(), this::onHandleClick);
        this.chooserPane = new ShipChooserPane(playersBoard::setChosenShip);
        this.getChildren().addAll(chooserPane, playersBoard);
        this.setSpacing(20);
        this.setPadding(new Insets(40));
    }

    public void onHandleClick(PaintableBoardCell cell, MouseEvent event) {
        if (service.getGameState() == GameState.GAME_OVER) {
            return;
        }
        var color = cell.getFill();

        if (color == (CellColors.EMPTY.getColor())) {
            if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
                this.playersBoard.paintShipForChoice(cell.getPosition());
            }
        }

        if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
            update(service.getGameData());
        }

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton() == MouseButton.PRIMARY) {
            addShip(cell);
        }

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton() == MouseButton.SECONDARY) {
            removeShip(cell);
        }
    }

    @Override
    public void update(GameData data) {
        playersBoard.update(data.board());
    }

    private void addShip(PaintableBoardCell cell) {
        final var optShip = playersBoard.getChosenShip();

        if (optShip.isEmpty()) {
            return;
        }

        final var cShip = optShip.get();
        final var chooser = this.chooserPane.getShipChooser(cShip.type());

        if (chooser != null) {
            chooser.incrementShipsAmount();
        }

        final var position = getShipPosition(cell, cShip);

        try {
            final var ship = new Ship(cShip.type(), position, cShip.isVertical(), false);
            service.addShip(ship);
            playersBoard.setChosenShip(null);
            update(service.getGameData());
        } catch (BrokenRuleException e) {
            playersBoard.setChosenShip(null);
            assert chooser != null;
            chooser.decrementShipsAmount();
            showAlert(Alert.AlertType.ERROR, e.getRule(), e.getMessage());
        }
    }

    private void removeShip(PaintableBoardCell cell) {

        if (this.playersBoard.getChosenShip().isPresent()) {
            return;
        }
        if (cell.getFill() != CellColors.SHIP.getColor()) {
            return;
        }

        final var optShip = getShipToRemove(cell);

        if (optShip.isEmpty()) {
            return;
        }

        final var ship = optShip.get();

        service.removeShip(ship);

        final var chooser = this.chooserPane.getShipChooser(ship.type());

        if (chooser != null) {
            chooser.decrementShipsAmount();
        }
    }

    private int[] getShipPosition(PaintableBoardCell cell, Ship ship) {

        var firstPoint = cell.getPosition();
        var shipLength = ship.type().getLength();
        var columns = service.getGameData().columns();
        var isVertical = ship.isVertical();
        var position = new int[shipLength];

        if (!isVertical) {

            for (int i = 0; i < shipLength; i++) {
                position[i] = firstPoint.x() * columns + firstPoint.y() + i;
            }
        } else {

            for (int i = 0; i < shipLength; i++) {
                position[i] = (firstPoint.x() + i) * columns + firstPoint.y();
            }
        }
        return position;
    }

    private Optional<Ship> getShipToRemove(PaintableBoardCell cell) {

        final var fleet = service.getGameData().board().fleet();
        var columns = service.getGameData().columns();
        final var point = cell.getPosition();
        final var pos1D = point.x() * columns + point.y();
        return fleet.stream()
                .filter(s -> isWrightPosition(s.position(), pos1D))
                .findFirst();
    }

    private boolean isWrightPosition(int[] shipPosition, int pointPosition) {
        return Arrays.stream(shipPosition).anyMatch(p -> p == pointPosition);
    }
}
