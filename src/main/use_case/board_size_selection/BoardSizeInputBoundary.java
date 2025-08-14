package main.use_case.board_size_selection;

import main.use_case.board_size_selection.BoardSizeSelection.BoardSize;

/**
 * Input boundary interface for board size selection use case.
 */
public interface BoardSizeInputBoundary {

    /**
     * Select the board size.
     *
     * @param size the selected board size
     */
    void selectBoardSize(BoardSize size);
}
