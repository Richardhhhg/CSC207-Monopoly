package main.interface_adapter.board_size_selection;

import main.use_case.board_size_selection.BoardSizeSelection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardSizePresenterTest {

    private BoardSizePresenter presenter;

    @BeforeEach
    void setUp() {
        presenter = new BoardSizePresenter();
    }

    @Test
    void testConstructor_CreatesPresenterWithViewModel() {
        // Arrange & Act
        BoardSizePresenter newPresenter = new BoardSizePresenter();

        // Assert
        assertNotNull(newPresenter);
        assertNotNull(newPresenter.getViewModel());
    }

    @Test
    void testGetViewModel_ReturnsNonNullViewModel() {
        // Act
        BoardSizeViewModel viewModel = presenter.getViewModel();

        // Assert
        assertNotNull(viewModel);
        assertInstanceOf(BoardSizeViewModel.class, viewModel);
    }

    @Test
    void testPresentBoardSizeSelection_SmallBoard_UpdatesViewModel() {
        // Act
        presenter.presentBoardSizeSelection(BoardSizeSelection.BoardSize.SMALL);

        // Assert
        assertEquals(BoardSizeSelection.BoardSize.SMALL, presenter.getViewModel().getSelectedBoardSize());
    }

    @Test
    void testPresentBoardSizeSelection_MediumBoard_UpdatesViewModel() {
        // Act
        presenter.presentBoardSizeSelection(BoardSizeSelection.BoardSize.MEDIUM);

        // Assert
        assertEquals(BoardSizeSelection.BoardSize.MEDIUM, presenter.getViewModel().getSelectedBoardSize());
    }

    @Test
    void testPresentBoardSizeSelection_LargeBoard_UpdatesViewModel() {
        // Act
        presenter.presentBoardSizeSelection(BoardSizeSelection.BoardSize.LARGE);

        // Assert
        assertEquals(BoardSizeSelection.BoardSize.LARGE, presenter.getViewModel().getSelectedBoardSize());
    }

    @Test
    void testPresentBoardSizeSelection_NullBoardSize_UpdatesViewModel() {
        // Act
        presenter.presentBoardSizeSelection(null);

        // Assert
        assertNull(presenter.getViewModel().getSelectedBoardSize());
    }

    @Test
    void testPresentBoardSizeSelection_MultipleUpdates_UpdatesViewModelCorrectly() {
        // Act
        presenter.presentBoardSizeSelection(BoardSizeSelection.BoardSize.SMALL);
        assertEquals(BoardSizeSelection.BoardSize.SMALL, presenter.getViewModel().getSelectedBoardSize());

        presenter.presentBoardSizeSelection(BoardSizeSelection.BoardSize.MEDIUM);
        assertEquals(BoardSizeSelection.BoardSize.MEDIUM, presenter.getViewModel().getSelectedBoardSize());

        presenter.presentBoardSizeSelection(BoardSizeSelection.BoardSize.LARGE);
        assertEquals(BoardSizeSelection.BoardSize.LARGE, presenter.getViewModel().getSelectedBoardSize());
    }

    @Test
    void testPresentBoardSizeSelection_OverwritesPreviousSelection() {
        // Arrange
        presenter.presentBoardSizeSelection(BoardSizeSelection.BoardSize.SMALL);

        // Act
        presenter.presentBoardSizeSelection(BoardSizeSelection.BoardSize.LARGE);

        // Assert
        assertEquals(BoardSizeSelection.BoardSize.LARGE, presenter.getViewModel().getSelectedBoardSize());
    }

    @Test
    void testPresentBoardSizeSelection_AllBoardSizes_UpdatesCorrectly() {
        // Act & Assert
        for (BoardSizeSelection.BoardSize boardSize : BoardSizeSelection.BoardSize.values()) {
            presenter.presentBoardSizeSelection(boardSize);
            assertEquals(boardSize, presenter.getViewModel().getSelectedBoardSize(),
                    "Should update view model correctly for: " + boardSize);
        }
    }

    @Test
    void testViewModelPersistence_SameInstanceReturned() {
        // Act
        BoardSizeViewModel viewModel1 = presenter.getViewModel();
        BoardSizeViewModel viewModel2 = presenter.getViewModel();

        // Assert
        assertSame(viewModel1, viewModel2, "Should return the same view model instance");
    }

    @Test
    void testPresentBoardSizeSelection_ViewModelStateChanges() {
        // Arrange
        BoardSizeViewModel viewModel = presenter.getViewModel();
        BoardSizeSelection.BoardSize initialSelection = viewModel.getSelectedBoardSize();

        // Act
        presenter.presentBoardSizeSelection(BoardSizeSelection.BoardSize.LARGE);

        // Assert
        assertNotEquals(initialSelection, viewModel.getSelectedBoardSize());
        assertEquals(BoardSizeSelection.BoardSize.LARGE, viewModel.getSelectedBoardSize());
    }
}
