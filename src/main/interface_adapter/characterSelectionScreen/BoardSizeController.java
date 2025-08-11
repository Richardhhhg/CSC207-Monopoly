package main.interface_adapter.characterSelectionScreen;

import main.use_case.BoardSizeSelection.BoardSizeInputBoundary;
import main.use_case.BoardSizeSelection.BoardSizeOutputBoundary;
import main.use_case.BoardSizeSelection.BoardSizeSelection.BoardSize;
import main.use_case.BoardSizeSelection.BoardSizeSelection.BoardSizeSelectionResult;

/**
 * Controller for board size selection that depends on interfaces for better testability
 * and adherence to the Dependency Inversion Principle.
 */
public class BoardSizeController {
    private final BoardSizeInputBoundary boardSizeInputBoundary;
    private final BoardSizeOutputBoundary boardSizeOutputBoundary;

    public BoardSizeController(BoardSizeInputBoundary boardSizeInputBoundary,
                              BoardSizeOutputBoundary boardSizeOutputBoundary) {
        this.boardSizeInputBoundary = boardSizeInputBoundary;
        this.boardSizeOutputBoundary = boardSizeOutputBoundary;
    }

    public void selectBoardSize(BoardSize size) {
        BoardSizeSelectionResult result = boardSizeInputBoundary.selectBoardSize(size);
        boardSizeOutputBoundary.presentBoardSizeSelection(result);
    }
}
