package main.use_case.boardSizeSelection;

import main.use_case.boardSizeSelection.BoardSizeSelection.BoardSize;

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
