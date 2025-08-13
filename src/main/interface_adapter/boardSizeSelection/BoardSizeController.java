package main.interface_adapter.boardSizeSelection;

import main.use_case.boardSizeSelection.BoardSizeInputBoundary;
import main.use_case.boardSizeSelection.BoardSizeSelection.BoardSize;

/**
 * Controller for board size selection.
 */
public class BoardSizeController {
    private final BoardSizeInputBoundary boardSizeInputBoundary;

    public BoardSizeController(BoardSizeInputBoundary boardSizeInputBoundary) {
        this.boardSizeInputBoundary = boardSizeInputBoundary;
    }

    /**
     * Select the board size.
     *
     * @param size the selected board size
     */
    public void selectBoardSize(BoardSize size) {
        boardSizeInputBoundary.selectBoardSize(size);
    }
}
