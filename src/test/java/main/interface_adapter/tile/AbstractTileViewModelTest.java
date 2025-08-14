package main.interface_adapter.tile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractTileViewModelTest {

    // Concrete implementation for testing the abstract class
    private static class TestTileViewModel extends AbstractTileViewModel {
        public TestTileViewModel(String name) {
            super(name);
        }
    }

    @Test
    void testConstructor_ValidName_SetsNameCorrectly() {
        // Arrange & Act
        TestTileViewModel viewModel = new TestTileViewModel("Test Tile");

        // Assert
        assertEquals("Test Tile", viewModel.getName());
    }

    @Test
    void testConstructor_EmptyName_SetsEmptyName() {
        // Arrange & Act
        TestTileViewModel viewModel = new TestTileViewModel("");

        // Assert
        assertEquals("", viewModel.getName());
    }

    @Test
    void testConstructor_NullName_SetsNullName() {
        // Arrange & Act
        TestTileViewModel viewModel = new TestTileViewModel(null);

        // Assert
        assertNull(viewModel.getName());
    }

    @Test
    void testGetName_ReturnsCorrectName() {
        // Arrange
        String expectedName = "Property Tile";
        TestTileViewModel viewModel = new TestTileViewModel(expectedName);

        // Act
        String actualName = viewModel.getName();

        // Assert
        assertEquals(expectedName, actualName);
    }
}
