package main.interface_adapter.boardSizeSelection;

import main.use_case.boardSizeSelection.BoardSizeOutputBoundary;
import main.use_case.boardSizeSelection.BoardSizeSelection.BoardSize;

/**
 * Presenter for board size selection.
 */
public class BoardSizePresenter implements BoardSizeOutputBoundary {
    private final BoardSizeViewModel viewModel;

    public BoardSizePresenter() {
        this.viewModel = new BoardSizeViewModel();
    }

    @Override
    public void presentBoardSizeSelection(BoardSize boardSize) {
        viewModel.setSelectedBoardSize(boardSize);
    }

    public BoardSizeViewModel getViewModel() {
        return viewModel;
    }
}
