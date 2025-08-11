package main.interface_adapter.characterSelectionScreen;

import main.use_case.BoardSizeSelection.BoardSizeOutputBoundary;
import main.use_case.BoardSizeSelection.BoardSizeSelection.BoardSizeSelectionResult;

/**
 * Presenter for board size selection that implements the output boundary interface.
 */
public class BoardSizePresenter implements BoardSizeOutputBoundary {
    private final BoardSizeViewModel viewModel;

    public BoardSizePresenter() {
        this.viewModel = new BoardSizeViewModel();
    }

    @Override
    public void presentBoardSizeSelection(BoardSizeSelectionResult result) {
        viewModel.setSelectedBoardSize(result.getSelectedSize());
    }

    public BoardSizeViewModel getViewModel() {
        return viewModel;
    }
}
