package org.battleshipprojectp2p.GUI.boardModel;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.battleshipprojectp2p.common.CellValue;
import org.battleshipprojectp2p.game.board.BoardData;


public class BoardModel extends Parent {
    private final VBox rows = new VBox();
    private final int colsCount;
    private final int rowsCount;

    public BoardModel(EventHandler<? super MouseEvent> eventHandler, BoardData data) {
        this.colsCount = data.columnsCount();
        this.rowsCount = data.rowsCount();
        fillBoard(eventHandler, data);
    }

    public void refreshBoard(BoardData data) {
        ObservableList<Node> rowsToIterate = rows.getChildren();
        if (rowsToIterate.isEmpty()) {
            throw new RuntimeException("board is empty");
        }
        for (int y = 0; y < rowsCount; y++) {
            Pane pane = (Pane) rowsToIterate.get(y);
            HBox row;

            if (pane instanceof HBox) {
                row = (HBox) pane;
            } else {
                throw new RuntimeException("pane is not a HBox");
            }

            for (int x = 0; x < colsCount; x++) {
                Cell cell = (Cell) row.getChildren().get(x);
                setCellColor(data.board()[x][y].getCellValue(), cell);
            }
        }
    }

    private void fillBoard(EventHandler<? super MouseEvent> eventHandler, BoardData data) {
        for (int x = 0; x < rowsCount; x++) {
            HBox row = new HBox();
            for (int y = 0; y < colsCount; y++) {
                Cell cell = new Cell(new Point(x, y));
                cell.setOnMouseClicked(eventHandler);
                setCellColor(data.board()[x][y].getCellValue(), cell);
                row.getChildren().add(cell);
            }
            rows.getChildren().add(row);
        }
        this.getChildren().add(rows);
    }

    private void setCellColor(CellValue value, Cell cell) {

        switch (value) {
            case CellValue.E: {
                cell.setFill(CellColors.EMPTY.getColor());
                break;
            }
            case CellValue.X: {
                cell.setFill(CellColors.HIT.getColor());
                break;
            }
            case CellValue.K: {
                cell.setFill(CellColors.KILLED.getColor());
                break;
            }
            case CellValue.M: {
                cell.setFill(CellColors.MISSED.getColor());
                break;
            }
            default:
                cell.setFill(CellColors.SHIP.getColor());

        }
    }
}
