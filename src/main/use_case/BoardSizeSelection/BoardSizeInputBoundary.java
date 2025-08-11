package main.use_case.BoardSizeSelection;

import main.use_case.BoardSizeSelection.BoardSizeSelection.BoardSize;
import main.use_case.BoardSizeSelection.BoardSizeSelection.BoardSizeSelectionResult;

/**
 * Input boundary interface for board size selection use case.
 */
public interface BoardSizeInputBoundary {
    BoardSizeSelectionResult selectBoardSize(BoardSize size);
    BoardSizeSelectionResult getDefaultBoardSize();
}

