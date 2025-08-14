package main.interface_adapter.board_size_selection;

import main.use_case.board_size_selection.BoardSizeOutputBoundary;
import main.use_case.board_size_selection.BoardSizeSelection.BoardSize;

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
