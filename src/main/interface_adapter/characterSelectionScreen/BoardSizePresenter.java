package main.interface_adapter.characterSelectionScreen;

import main.use_case.BoardSizeSelection.BoardSizeSelection;

/**
 * Presenter for board size selection.
 * Converts use case output to view model data.
 */
public class BoardSizePresenter {
    private final BoardSizeViewModel viewModel;

    public BoardSizePresenter() {
        this.viewModel = new BoardSizeViewModel();
    }

    public void presentBoardSizeSelection(BoardSizeSelection.BoardSizeSelectionResult result) {
        viewModel.setSelectedBoardSize(result.getSelectedSize());
        viewModel.setMessage(result.getMessage());
        viewModel.setValid(result.isValid());
    }

    public BoardSizeViewModel getViewModel() {
        return viewModel;
    }
}
