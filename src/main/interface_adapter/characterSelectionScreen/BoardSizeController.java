package main.interface_adapter.characterSelectionScreen;

import main.use_case.BoardSizeSelection.BoardSizeInputBoundary;
import main.use_case.BoardSizeSelection.BoardSizeSelection.BoardSize;

/**
 * Controller for board size selection.
 */
public class BoardSizeController {
    private final BoardSizeInputBoundary boardSizeInputBoundary;

    public BoardSizeController(BoardSizeInputBoundary boardSizeInputBoundary) {
        this.boardSizeInputBoundary = boardSizeInputBoundary;
    }

    public void selectBoardSize(BoardSize size) {
        boardSizeInputBoundary.selectBoardSize(size);
    }
}
