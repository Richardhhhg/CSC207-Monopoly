package main.interface_adapter.boardSizeSelection;

import main.use_case.boardSizeSelection.BoardSizeSelection.BoardSize;

/**
 * View model for board size selection.
 * Contains data needed by the view layer.
 */
public class BoardSizeViewModel {

    private static final String BUTTON_TEXT = " tiles)";
    private BoardSize selectedBoardSize;

    public BoardSizeViewModel() {
        // Default board size is MEDIUM
        this.selectedBoardSize = BoardSize.MEDIUM;
    }

    public BoardSize getSelectedBoardSize() {
        return selectedBoardSize;
    }

    public void setSelectedBoardSize(BoardSize selectedBoardSize) {
        this.selectedBoardSize = selectedBoardSize;
    }

    public String getSmallButtonText() {
        return "Small (" + BoardSize.SMALL.getTileCount() + BUTTON_TEXT;
    }

    public String getMediumButtonText() {
        return "Medium (" + BoardSize.MEDIUM.getTileCount() + BUTTON_TEXT;
    }

    public String getLargeButtonText() {
        return "Large (" + BoardSize.LARGE.getTileCount() + BUTTON_TEXT;
    }
}
