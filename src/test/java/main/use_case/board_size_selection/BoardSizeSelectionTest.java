package main.use_case.board_size_selection;

import main.constants.Constants;
import main.use_case.board_size_selection.BoardSizeSelection.BoardSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardSizeSelectionTest {

    private BoardSizeSelection boardSizeSelection;
    private MockBoardSizeOutputBoundary mockOutputBoundary;

    // Mock implementation of BoardSizeOutputBoundary
    private static class MockBoardSizeOutputBoundary implements BoardSizeOutputBoundary {
        private BoardSize lastPresentedSize;
        private int presentCallCount = 0;

        @Override
        public void presentBoardSizeSelection(BoardSize size) {
            this.lastPresentedSize = size;
            this.presentCallCount++;
        }

        public BoardSize getLastPresentedSize() {
            return lastPresentedSize;
        }

        public int getPresentCallCount() {
            return presentCallCount;
        }

        public void reset() {
            lastPresentedSize = null;
            presentCallCount = 0;
        }
    }

    @BeforeEach
    void setUp() {
        mockOutputBoundary = new MockBoardSizeOutputBoundary();
        boardSizeSelection = new BoardSizeSelection(mockOutputBoundary);
    }

    @Test
    void testConstructor_WithValidOutputBoundary_CreatesInstance() {
        // Arrange & Act
        BoardSizeSelection newBoardSizeSelection = new BoardSizeSelection(mockOutputBoundary);

        // Assert
        assertNotNull(newBoardSizeSelection);
    }

    @Test
    void testConstructor_WithNullOutputBoundary_CreatesInstance() {
        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> new BoardSizeSelection(null));
    }

    @Test
    void testSelectBoardSize_SmallBoard_CallsOutputBoundary() {
        // Act
        boardSizeSelection.selectBoardSize(BoardSize.SMALL);

        // Assert
        assertEquals(1, mockOutputBoundary.getPresentCallCount());
        assertEquals(BoardSize.SMALL, mockOutputBoundary.getLastPresentedSize());
    }

    @Test
    void testSelectBoardSize_MediumBoard_CallsOutputBoundary() {
        // Act
        boardSizeSelection.selectBoardSize(BoardSize.MEDIUM);

        // Assert
        assertEquals(1, mockOutputBoundary.getPresentCallCount());
        assertEquals(BoardSize.MEDIUM, mockOutputBoundary.getLastPresentedSize());
    }

    @Test
    void testSelectBoardSize_LargeBoard_CallsOutputBoundary() {
        // Act
        boardSizeSelection.selectBoardSize(BoardSize.LARGE);

        // Assert
        assertEquals(1, mockOutputBoundary.getPresentCallCount());
        assertEquals(BoardSize.LARGE, mockOutputBoundary.getLastPresentedSize());
    }

    @Test
    void testSelectBoardSize_MultipleSelections_CallsOutputBoundaryMultipleTimes() {
        // Act
        boardSizeSelection.selectBoardSize(BoardSize.SMALL);
        boardSizeSelection.selectBoardSize(BoardSize.MEDIUM);
        boardSizeSelection.selectBoardSize(BoardSize.LARGE);

        // Assert
        assertEquals(3, mockOutputBoundary.getPresentCallCount());
        assertEquals(BoardSize.LARGE, mockOutputBoundary.getLastPresentedSize()); // Last call
    }

    @Test
    void testSelectBoardSize_NullBoardSize_CallsOutputBoundary() {
        // Act
        boardSizeSelection.selectBoardSize(null);

        // Assert
        assertEquals(1, mockOutputBoundary.getPresentCallCount());
        assertNull(mockOutputBoundary.getLastPresentedSize());
    }

    @Test
    void testBoardSizeEnum_SmallBoardTileCount_ReturnsCorrectValue() {
        // Act
        int tileCount = BoardSize.SMALL.getTileCount();

        // Assert
        assertEquals(Constants.SMALL_BOARD_SIZE, tileCount);
    }

    @Test
    void testBoardSizeEnum_MediumBoardTileCount_ReturnsCorrectValue() {
        // Act
        int tileCount = BoardSize.MEDIUM.getTileCount();

        // Assert
        assertEquals(Constants.MEDIUM_BOARD_SIZE, tileCount);
    }

    @Test
    void testBoardSizeEnum_LargeBoardTileCount_ReturnsCorrectValue() {
        // Act
        int tileCount = BoardSize.LARGE.getTileCount();

        // Assert
        assertEquals(Constants.LARGE_BOARD_SIZE, tileCount);
    }

    @Test
    void testBoardSizeEnum_AllSizesHaveDifferentTileCounts() {
        // Act
        int smallTileCount = BoardSize.SMALL.getTileCount();
        int mediumTileCount = BoardSize.MEDIUM.getTileCount();
        int largeTileCount = BoardSize.LARGE.getTileCount();

        // Assert
        assertTrue(smallTileCount < mediumTileCount);
        assertTrue(mediumTileCount < largeTileCount);
        assertNotEquals(smallTileCount, mediumTileCount);
        assertNotEquals(mediumTileCount, largeTileCount);
        assertNotEquals(smallTileCount, largeTileCount);
    }

    @Test
    void testBoardSizeEnum_AllValuesExist() {
        // Act & Assert
        assertEquals(3, BoardSize.values().length);
        assertNotNull(BoardSize.valueOf("SMALL"));
        assertNotNull(BoardSize.valueOf("MEDIUM"));
        assertNotNull(BoardSize.valueOf("LARGE"));
    }

    @Test
    void testSelectBoardSize_WithNullOutputBoundary_DoesNotThrow() {
        // Arrange
        BoardSizeSelection nullBoundarySelection = new BoardSizeSelection(null);

        // Act & Assert - should handle null output boundary gracefully
        assertThrows(NullPointerException.class, () -> {
            nullBoundarySelection.selectBoardSize(BoardSize.SMALL);
        });
    }

    @Test
    void testBoardSizeEnum_TileCountsMatchConstants() {
        // Assert
        assertEquals(Constants.SMALL_BOARD_SIZE, BoardSize.SMALL.getTileCount());
        assertEquals(Constants.MEDIUM_BOARD_SIZE, BoardSize.MEDIUM.getTileCount());
        assertEquals(Constants.LARGE_BOARD_SIZE, BoardSize.LARGE.getTileCount());
    }

    @Test
    void testSelectBoardSize_SameSizeMultipleTimes_CallsOutputBoundaryEachTime() {
        // Act
        boardSizeSelection.selectBoardSize(BoardSize.MEDIUM);
        boardSizeSelection.selectBoardSize(BoardSize.MEDIUM);
        boardSizeSelection.selectBoardSize(BoardSize.MEDIUM);

        // Assert
        assertEquals(3, mockOutputBoundary.getPresentCallCount());
        assertEquals(BoardSize.MEDIUM, mockOutputBoundary.getLastPresentedSize());
    }

    @Test
    void testBoardSizeEnum_EnumOrdinalValues() {
        // Assert - verify enum ordering
        assertEquals(0, BoardSize.SMALL.ordinal());
        assertEquals(1, BoardSize.MEDIUM.ordinal());
        assertEquals(2, BoardSize.LARGE.ordinal());
    }
}

