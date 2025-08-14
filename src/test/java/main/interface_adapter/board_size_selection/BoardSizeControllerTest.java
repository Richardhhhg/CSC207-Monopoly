package main.interface_adapter.board_size_selection;

import main.use_case.board_size_selection.BoardSizeInputBoundary;
import main.use_case.board_size_selection.BoardSizeSelection.BoardSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardSizeControllerTest {

    private BoardSizeController controller;
    private MockBoardSizeInputBoundary mockInputBoundary;

    // Mock implementation of BoardSizeInputBoundary
    private static class MockBoardSizeInputBoundary implements BoardSizeInputBoundary {
        private BoardSize lastSelectedSize;
        private int selectCallCount = 0;

        @Override
        public void selectBoardSize(BoardSize size) {
            this.lastSelectedSize = size;
            this.selectCallCount++;
        }

        public BoardSize getLastSelectedSize() {
            return lastSelectedSize;
        }

        public int getSelectCallCount() {
            return selectCallCount;
        }

        public void reset() {
            lastSelectedSize = null;
            selectCallCount = 0;
        }
    }

    @BeforeEach
    void setUp() {
        mockInputBoundary = new MockBoardSizeInputBoundary();
        controller = new BoardSizeController(mockInputBoundary);
    }

    @Test
    void testConstructor_ValidInputBoundary_CreatesController() {
        // Arrange & Act
        BoardSizeController newController = new BoardSizeController(mockInputBoundary);

        // Assert
        assertNotNull(newController);
    }

    @Test
    void testConstructor_NullInputBoundary_CreatesController() {
        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> new BoardSizeController(null));
    }

    @Test
    void testSelectBoardSize_SmallBoard_CallsInputBoundary() {
        // Act
        controller.selectBoardSize(BoardSize.SMALL);

        // Assert
        assertEquals(1, mockInputBoundary.getSelectCallCount());
        assertEquals(BoardSize.SMALL, mockInputBoundary.getLastSelectedSize());
    }

    @Test
    void testSelectBoardSize_MediumBoard_CallsInputBoundary() {
        // Act
        controller.selectBoardSize(BoardSize.MEDIUM);

        // Assert
        assertEquals(1, mockInputBoundary.getSelectCallCount());
        assertEquals(BoardSize.MEDIUM, mockInputBoundary.getLastSelectedSize());
    }

    @Test
    void testSelectBoardSize_LargeBoard_CallsInputBoundary() {
        // Act
        controller.selectBoardSize(BoardSize.LARGE);

        // Assert
        assertEquals(1, mockInputBoundary.getSelectCallCount());
        assertEquals(BoardSize.LARGE, mockInputBoundary.getLastSelectedSize());
    }

    @Test
    void testSelectBoardSize_NullSize_CallsInputBoundary() {
        // Act
        controller.selectBoardSize(null);

        // Assert
        assertEquals(1, mockInputBoundary.getSelectCallCount());
        assertNull(mockInputBoundary.getLastSelectedSize());
    }

    @Test
    void testSelectBoardSize_MultipleSelections_CallsInputBoundaryMultipleTimes() {
        // Act
        controller.selectBoardSize(BoardSize.SMALL);
        controller.selectBoardSize(BoardSize.MEDIUM);
        controller.selectBoardSize(BoardSize.LARGE);

        // Assert
        assertEquals(3, mockInputBoundary.getSelectCallCount());
        assertEquals(BoardSize.LARGE, mockInputBoundary.getLastSelectedSize()); // Last call
    }

    @Test
    void testSelectBoardSize_SameSizeMultipleTimes_CallsInputBoundaryEachTime() {
        // Act
        controller.selectBoardSize(BoardSize.MEDIUM);
        controller.selectBoardSize(BoardSize.MEDIUM);
        controller.selectBoardSize(BoardSize.MEDIUM);

        // Assert
        assertEquals(3, mockInputBoundary.getSelectCallCount());
        assertEquals(BoardSize.MEDIUM, mockInputBoundary.getLastSelectedSize());
    }

    @Test
    void testSelectBoardSize_WithNullInputBoundary_ThrowsNullPointerException() {
        // Arrange
        BoardSizeController nullBoundaryController = new BoardSizeController(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            nullBoundaryController.selectBoardSize(BoardSize.SMALL);
        });
    }

    @Test
    void testSelectBoardSize_AllBoardSizes_CallsInputBoundaryCorrectly() {
        // Act & Assert
        for (BoardSize boardSize : BoardSize.values()) {
            mockInputBoundary.reset();
            controller.selectBoardSize(boardSize);

            assertEquals(1, mockInputBoundary.getSelectCallCount(),
                        "Should call input boundary once for: " + boardSize);
            assertEquals(boardSize, mockInputBoundary.getLastSelectedSize(),
                        "Should pass correct board size for: " + boardSize);
        }
    }
}

