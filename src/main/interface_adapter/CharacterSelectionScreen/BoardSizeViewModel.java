package main.interface_adapter.CharacterSelectionScreen;

import main.use_case.BoardSizeSelection.BoardSizeSelection.BoardSize;

/**
 * View model for board size selection.
 * Contains data needed by the view layer.
 */
public class BoardSizeViewModel {
    private BoardSize selectedBoardSize;
    private String message;
    private boolean isValid;

    public BoardSizeViewModel() {
        this.selectedBoardSize = BoardSize.MEDIUM; // Default
        this.isValid = true;
        this.message = "";
    }

    public BoardSize getSelectedBoardSize() {
        return selectedBoardSize;
    }

    public void setSelectedBoardSize(BoardSize selectedBoardSize) {
        this.selectedBoardSize = selectedBoardSize;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getSmallButtonText() {
        return "Small (" + BoardSize.SMALL.getTileCount() + " tiles)";
    }

    public String getMediumButtonText() {
        return "Medium (" + BoardSize.MEDIUM.getTileCount() + " tiles)";
    }

    public String getLargeButtonText() {
        return "Large (" + BoardSize.LARGE.getTileCount() + " tiles)";
    }
}



