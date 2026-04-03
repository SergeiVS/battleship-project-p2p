package org.battleshipprojectp2p.GUI.models.boardModel;

import javafx.scene.input.MouseEvent;
import org.battleshipprojectp2p.game.board.BoardData;

import java.util.function.BiConsumer;

public class PlayerBoardPane extends BoardPane {

    public PlayerBoardPane(BoardData board, BiConsumer<PaintableBoardCell, MouseEvent> onClick) {
        super(board, onClick);
    }
}
