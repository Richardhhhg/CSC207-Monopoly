package main.use_case.board_size_selection;

import main.use_case.board_size_selection.BoardSizeSelection.BoardSize;

/**
 * Output boundary interface for board size selection presenter.
 */
public interface BoardSizeOutputBoundary {

    /**
     * Present the selected board size.
     *
     * @param boardSize the selected board size
     */
    void presentBoardSizeSelection(BoardSize boardSize);
}
