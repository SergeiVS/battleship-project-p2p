package org.battleshipprojectp2p.GUI.gameView;

import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.battleshipprojectp2p.GUI.models.boardModel.CellColors;
import org.battleshipprojectp2p.GUI.models.boardModel.PaintableBoardCell;
import org.battleshipprojectp2p.GUI.models.boardModel.PlayerBoardBuilderPane;
import org.battleshipprojectp2p.GUI.models.shipModel.ShipChooserPane;
import org.battleshipprojectp2p.error.BrokenRuleException;
import org.battleshipprojectp2p.game.GameManager;
import org.battleshipprojectp2p.game.gameDto.GameData;
import org.battleshipprojectp2p.game.observer.GameObserver;
import org.battleshipprojectp2p.game.ship.Ship;

import java.util.Arrays;
import java.util.Optional;

import static org.battleshipprojectp2p.GUI.utils.AlertService.showAlert;

public class PlayerBoardSetupPane extends HBox implements GameObserver<GameData> {

    private final GameManager gameManager;

    private final PlayerBoardBuilderPane playersBoard;

    private final ShipChooserPane chooserPane;

    public PlayerBoardSetupPane(GameManager gameManager) {
        this.gameManager = gameManager;
        this.playersBoard = new PlayerBoardBuilderPane(gameManager.getPlayerBoard(), this::onHandleClick);
        this.chooserPane = new ShipChooserPane(playersBoard::setChosenShip);
    }

    public void initialize() {
        this.getChildren().addAll(chooserPane, playersBoard);
        this.setSpacing(10);
    }

    public void onHandleClick(PaintableBoardCell cell, MouseEvent event) {
        var color = cell.getFill();

        if (color == (CellColors.EMPTY.getColor())) {
            if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
                this.playersBoard.paintShipForChoice(cell.getPosition());
            }
        }

        if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
            update(gameManager.getGameData());
        }


        if (event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton() == MouseButton.PRIMARY) {
            addShip(cell);
        }

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton() == MouseButton.SECONDARY) {
            IO.println("Clicked on " + cell.getPosition() + ", Remove Ship");
            removeShip(cell);
            update(gameManager.getGameData());
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
            gameManager.addShip(ship);
            playersBoard.setChosenShip(null);
            update(gameManager.getGameData());
        } catch (BrokenRuleException e) {
            playersBoard.setChosenShip(null);

            assert chooser != null;
            chooser.decrementShipsAmount();
            showAlert(Alert.AlertType.ERROR, e.getRule(), e.getMessage());
        }
    }

    private void removeShip(PaintableBoardCell cell) {

        IO.println("Fleet: " + gameManager.getPlayerBoard().fleet());

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

        this.gameManager.removeShip(ship);
        IO.println("Removed Ship:" + gameManager.getPlayerBoard().fleet());
        final var chooser = this.chooserPane.getShipChooser(ship.type());

        if (chooser != null) {
            chooser.decrementShipsAmount();
        }
    }

    private int[] getShipPosition(PaintableBoardCell cell, Ship ship) {
        var firstPoint = cell.getPosition();
        var shipLength = ship.type().getLength();
        var columns = gameManager.getColumns();
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

        final var fleet = this.gameManager.getGameData().board().fleet();
        var columns = this.gameManager.getColumns();
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
