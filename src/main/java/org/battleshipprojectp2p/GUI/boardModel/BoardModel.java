package org.battleshipprojectp2p.GUI.boardModel;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.battleshipprojectp2p.common.CellValue;
import org.battleshipprojectp2p.game.board.BoardData;
import org.jetbrains.annotations.NotNull;


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
                BoardCell boardCell = (BoardCell) row.getChildren().get(x);
                setCellColor(data.board()[x][y].getCellValue(), boardCell);
            }
        }
    }

    private void fillBoard(EventHandler<? super MouseEvent> eventHandler, BoardData data) {
        var colsNumbers = fillColsNumbers();
        rows.getChildren().add(colsNumbers);

        for (int x = 0; x < rowsCount; x++) {
            HBox row = new HBox();
            var lineNumber = getRowNumber(x);
            row.getChildren().add(lineNumber);

            for (int y = 0; y < colsCount; y++) {
                BoardCell boardCell = new BoardCell(new Point(x, y));
                boardCell.setOnMouseClicked(eventHandler);
                setCellColor(data.board()[x][y].getCellValue(), boardCell);
                row.getChildren().add(boardCell);
            }
            rows.getChildren().add(row);
        }
        this.getChildren().add(rows);
    }

    @NotNull
    private static Text getRowNumber(int x) {
        Text lineNumber = new Text(String.valueOf(x));
        lineNumber.setWrappingWidth(20);
        lineNumber.setStyle("-fx-font-weight: bold; -fx-padding: 3; -fx-cell-size: 20");
        return lineNumber;
    }

    @NotNull
    private HBox fillColsNumbers() {
        HBox colsNumbers = new HBox();
        Rectangle emptyCell = new Rectangle(20, 0);
        emptyCell.setFill(Color.TRANSPARENT);
        colsNumbers.getChildren().add(emptyCell);
        colsNumbers.setSpacing(2);

        for (int i = 0; i < colsCount; i++) {
            Text lineNumber = new Text(String.valueOf(i));
            lineNumber.setWrappingWidth(19);
            lineNumber.setStyle("-fx-font-weight: bold; -fx-alignment: BOTTOM-CENTER");
            colsNumbers.getChildren().add(lineNumber);
        }
        return colsNumbers;
    }

    private void setCellColor(CellValue value, BoardCell boardCell) {

        switch (value) {
            case CellValue.E: {
                boardCell.setFill(CellColors.EMPTY.getColor());
                break;
            }
            case CellValue.X: {
                boardCell.setFill(CellColors.HIT.getColor());
                break;
            }
            case CellValue.K: {
                boardCell.setFill(CellColors.KILLED.getColor());
                break;
            }
            case CellValue.M: {
                boardCell.setFill(CellColors.MISSED.getColor());
                break;
            }
            default:
                boardCell.setFill(CellColors.SHIP.getColor());

        }
    }
}
