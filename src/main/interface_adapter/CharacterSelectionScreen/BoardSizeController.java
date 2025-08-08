package main.interface_adapter.CharacterSelectionScreen;

import main.use_case.BoardSizeSelection.BoardSizeSelection;
import main.use_case.BoardSizeSelection.BoardSizeSelection.BoardSize;
import main.use_case.BoardSizeSelection.BoardSizeSelection.BoardSizeSelectionResult;

/**
 * Controller for board size selection only.
 * Follows Single Responsibility Principle - only handles board size selection logic.
 */
public class BoardSizeController {
    private final BoardSizeSelection boardSizeSelection;
    private final BoardSizePresenter presenter;

    public BoardSizeController(BoardSizeSelection boardSizeSelection,
                              BoardSizePresenter presenter) {
        this.boardSizeSelection = boardSizeSelection;
        this.presenter = presenter;
    }

    public void selectBoardSize(BoardSize size) {
        BoardSizeSelectionResult result = boardSizeSelection.selectBoardSize(size);
        presenter.presentBoardSizeSelection(result);
    }

    public void initializeDefaultBoardSize() {
        BoardSizeSelectionResult result = boardSizeSelection.getDefaultBoardSize();
        presenter.presentBoardSizeSelection(result);
    }
}
