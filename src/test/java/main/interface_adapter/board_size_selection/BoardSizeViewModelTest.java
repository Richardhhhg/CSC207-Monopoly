package main.interface_adapter.board_size_selection;

import main.constants.Constants;
import main.use_case.board_size_selection.BoardSizeSelection.BoardSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardSizeViewModelTest {

    private BoardSizeViewModel viewModel;

    @BeforeEach
    void setUp() {
        viewModel = new BoardSizeViewModel();
    }

    @Test
    void testConstructor_DefaultsToMediumBoardSize() {
        // Arrange & Act
        BoardSizeViewModel newViewModel = new BoardSizeViewModel();

        // Assert
        assertEquals(BoardSize.MEDIUM, newViewModel.getSelectedBoardSize());
    }

    @Test
    void testGetSelectedBoardSize_InitialState_ReturnsMedium() {
        // Act
        BoardSize selectedSize = viewModel.getSelectedBoardSize();

        // Assert
        assertEquals(BoardSize.MEDIUM, selectedSize);
    }

    @Test
    void testSetSelectedBoardSize_SmallBoard_UpdatesCorrectly() {
        // Act
        viewModel.setSelectedBoardSize(BoardSize.SMALL);

        // Assert
        assertEquals(BoardSize.SMALL, viewModel.getSelectedBoardSize());
    }

    @Test
    void testSetSelectedBoardSize_MediumBoard_UpdatesCorrectly() {
        // Act
        viewModel.setSelectedBoardSize(BoardSize.MEDIUM);

        // Assert
        assertEquals(BoardSize.MEDIUM, viewModel.getSelectedBoardSize());
    }

    @Test
    void testSetSelectedBoardSize_LargeBoard_UpdatesCorrectly() {
        // Act
        viewModel.setSelectedBoardSize(BoardSize.LARGE);

        // Assert
        assertEquals(BoardSize.LARGE, viewModel.getSelectedBoardSize());
    }

    @Test
    void testSetSelectedBoardSize_NullValue_UpdatesCorrectly() {
        // Act
        viewModel.setSelectedBoardSize(null);

        // Assert
        assertNull(viewModel.getSelectedBoardSize());
    }

    @Test
    void testGetSmallButtonText_ReturnsCorrectFormat() {
        // Act
        String buttonText = viewModel.getSmallButtonText();

        // Assert
        String expected = "Small (" + Constants.SMALL_BOARD_SIZE + " tiles)";
        assertEquals(expected, buttonText);
    }

    @Test
    void testGetMediumButtonText_ReturnsCorrectFormat() {
        // Act
        String buttonText = viewModel.getMediumButtonText();

        // Assert
        String expected = "Medium (" + Constants.MEDIUM_BOARD_SIZE + " tiles)";
        assertEquals(expected, buttonText);
    }

    @Test
    void testGetLargeButtonText_ReturnsCorrectFormat() {
        // Act
        String buttonText = viewModel.getLargeButtonText();

        // Assert
        String expected = "Large (" + Constants.LARGE_BOARD_SIZE + " tiles)";
        assertEquals(expected, buttonText);
    }

    @Test
    void testButtonTexts_ContainTileCountsFromBoardSizeEnum() {
        // Act & Assert
        assertTrue(viewModel.getSmallButtonText().contains(String.valueOf(BoardSize.SMALL.getTileCount())));
        assertTrue(viewModel.getMediumButtonText().contains(String.valueOf(BoardSize.MEDIUM.getTileCount())));
        assertTrue(viewModel.getLargeButtonText().contains(String.valueOf(BoardSize.LARGE.getTileCount())));
    }

    @Test
    void testButtonTexts_AllContainTilesWord() {
        // Act & Assert
        assertTrue(viewModel.getSmallButtonText().contains("tiles)"));
        assertTrue(viewModel.getMediumButtonText().contains("tiles)"));
        assertTrue(viewModel.getLargeButtonText().contains("tiles)"));
    }

    @Test
    void testButtonTexts_AllHaveCorrectPrefix() {
        // Act & Assert
        assertTrue(viewModel.getSmallButtonText().startsWith("Small ("));
        assertTrue(viewModel.getMediumButtonText().startsWith("Medium ("));
        assertTrue(viewModel.getLargeButtonText().startsWith("Large ("));
    }

    @Test
    void testSetSelectedBoardSize_MultipleUpdates_UpdatesCorrectly() {
        // Act
        viewModel.setSelectedBoardSize(BoardSize.SMALL);
        assertEquals(BoardSize.SMALL, viewModel.getSelectedBoardSize());

        viewModel.setSelectedBoardSize(BoardSize.LARGE);
        assertEquals(BoardSize.LARGE, viewModel.getSelectedBoardSize());

        viewModel.setSelectedBoardSize(BoardSize.MEDIUM);
        assertEquals(BoardSize.MEDIUM, viewModel.getSelectedBoardSize());
    }

    @Test
    void testSetSelectedBoardSize_OverwritesPreviousValue() {
        // Arrange
        viewModel.setSelectedBoardSize(BoardSize.SMALL);

        // Act
        viewModel.setSelectedBoardSize(BoardSize.LARGE);

        // Assert
        assertEquals(BoardSize.LARGE, viewModel.getSelectedBoardSize());
        assertNotEquals(BoardSize.SMALL, viewModel.getSelectedBoardSize());
    }

    @Test
    void testButtonTexts_DifferentValues() {
        // Act
        String smallText = viewModel.getSmallButtonText();
        String mediumText = viewModel.getMediumButtonText();
        String largeText = viewModel.getLargeButtonText();

        // Assert
        assertNotEquals(smallText, mediumText);
        assertNotEquals(mediumText, largeText);
        assertNotEquals(smallText, largeText);
    }

    @Test
    void testButtonTexts_ConsistentAcrossMultipleCalls() {
        // Act
        String firstCall = viewModel.getSmallButtonText();
        String secondCall = viewModel.getSmallButtonText();

        // Assert
        assertEquals(firstCall, secondCall);
    }

    @Test
    void testSetSelectedBoardSize_AllBoardSizes_UpdatesCorrectly() {
        // Act & Assert
        for (BoardSize boardSize : BoardSize.values()) {
            viewModel.setSelectedBoardSize(boardSize);
            assertEquals(boardSize, viewModel.getSelectedBoardSize(),
                        "Should update correctly for: " + boardSize);
        }
    }
}
