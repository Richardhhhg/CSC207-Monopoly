package main.use_case.boardSizeSelection;

import main.constants.Constants;

/**
 * Use case for handling board size selection.
 */
public class BoardSizeSelection implements BoardSizeInputBoundary {
    private final BoardSizeOutputBoundary outputBoundary;

    public BoardSizeSelection(BoardSizeOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    public enum BoardSize {
        SMALL, MEDIUM, LARGE;

        public int getTileCount() {
            return switch (this) {
                case SMALL -> Constants.SMALL_BOARD_SIZE;
                case MEDIUM -> Constants.MEDIUM_BOARD_SIZE;
                case LARGE -> Constants.LARGE_BOARD_SIZE;
            };
        }

    }

    @Override
    public void selectBoardSize(BoardSize size) {
        outputBoundary.presentBoardSizeSelection(size);
    }
}
