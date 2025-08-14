package main.interface_adapter.board_size_selection;

import main.use_case.board_size_selection.BoardSizeInputBoundary;
import main.use_case.board_size_selection.BoardSizeSelection.BoardSize;

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
