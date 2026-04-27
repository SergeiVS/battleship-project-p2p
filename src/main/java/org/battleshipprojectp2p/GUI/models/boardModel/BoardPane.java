package org.battleshipprojectp2p.GUI.models.boardModel;

import javafx.event.EventHandler;
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

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;


public class BoardPane extends Pane {
    private final VBox rows = new VBox();
    protected final Map<CellPoint, PaintableBoardCell> cells = new HashMap<>();

    protected final BoardData currentBoard;
    private final BiConsumer<PaintableBoardCell, MouseEvent> onClick;

    public BoardPane(BoardData board, BiConsumer<PaintableBoardCell, MouseEvent> onClick) {
        this.currentBoard = board;
        this.onClick = onClick;
        fillBoard();
    }

    protected void fillBoard() {
        var colsNumbers = fillColsNumbers(this.currentBoard.colsCount());
        rows.getChildren().add(colsNumbers);

        for (int x = 0; x < this.currentBoard.rowsCount(); x++) {
            HBox row = new HBox();
            var lineNumber = getRowNumber(x);
            row.getChildren().add(lineNumber);

            for (int y = 0; y < this.currentBoard.colsCount(); y++) {
                final var point = new CellPoint(x, y);
                PaintableBoardCell cell = new PaintableBoardCell(point);
                setCellColor(this.currentBoard.board()[x][y].getCellValue(), cell);
                cell.addEventHandler(MouseEvent.ANY, getMouseEvent(cell));
                cells.put(point, cell);
                row.getChildren().add(cell);
            }
            rows.getChildren().add(row);
        }
        this.getChildren().add(rows);
    }

    @NotNull
    private static Text getRowNumber(int x) {
        Text lineNumber = new Text(String.valueOf(x));
        lineNumber.setWrappingWidth(24);
        lineNumber.setStyle("-fx-font-weight: bold; -fx-padding: 3; -fx-cell-size: 25");
        return lineNumber;
    }

    @NotNull
    private HBox fillColsNumbers(int colsNumber) {
        HBox colsNumbers = new HBox();
        Rectangle emptyCell = new Rectangle(25, 25);
        emptyCell.setFill(Color.TRANSPARENT);
        colsNumbers.getChildren().add(emptyCell);
        colsNumbers.setSpacing(2);

        for (int y = 0; y < colsNumber; y++) {
            Text lineNumber = new Text(String.valueOf(y));
            lineNumber.setWrappingWidth(24);
            lineNumber.setStyle("-fx-font-weight: bold; -fx-alignment: BOTTOM-CENTER");
            colsNumbers.getChildren().add(lineNumber);
        }
        return colsNumbers;
    }

    public EventHandler<MouseEvent> getMouseEvent(PaintableBoardCell cell) {
        return event -> {
            invokeClick(cell, event);
        };
    }

    public void update(BoardData board) {
        cells.keySet().forEach(point -> {
            final var cell = cells.get(point);
            setCellColor(board.board()[point.x()][point.y()].getCellValue(), cell);
        });
    }


    private void invokeClick(PaintableBoardCell cell, MouseEvent event) {
        if (onClick != null) {
            onClick.accept(cell, event);
        }
    }

    public PaintableBoardCell getCell(CellPoint point) {
        return cells.get(point);
    }

    void setCellColor(CellValue value, PaintableBoardCell pBoardCell) {

        switch (value) {
            case CellValue.E -> pBoardCell.setFill(CellColors.EMPTY.getColor());

            case CellValue.X -> pBoardCell.setFill(CellColors.HIT.getColor());
            case CellValue.K -> pBoardCell.setFill(CellColors.KILLED.getColor());
            case CellValue.M -> pBoardCell.setFill(CellColors.MISSED.getColor());
            default -> pBoardCell.setFill(CellColors.SHIP.getColor());

        }
    }
}
