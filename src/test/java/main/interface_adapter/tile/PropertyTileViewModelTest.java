package main.interface_adapter.tile;

import org.junit.jupiter.api.Test;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.*;

class PropertyTileViewModelTest {

    @Test
    void testConstructor_ValidParameters_SetsAllFieldsCorrectly() {
        // Arrange & Act
        PropertyTileViewModel viewModel = new PropertyTileViewModel(
            "Boardwalk", 400, "Player1", 50.0f, Color.BLUE
        );

        // Assert
        assertEquals("Boardwalk", viewModel.getName());
        assertEquals(400, viewModel.getPrice());
        assertEquals("Player1", viewModel.getOwner());
        assertEquals(50.0f, viewModel.getRent());
        assertTrue(viewModel.isOwned());
        assertEquals(Color.BLUE, viewModel.getOwnerColor());
    }

    @Test
    void testIsOwned_WithValidOwner_ReturnsTrue() {
        // Arrange
        PropertyTileViewModel viewModel = new PropertyTileViewModel(
            "Park Place", 350, "Player2", 35.0f, Color.RED
        );

        // Act & Assert
        assertTrue(viewModel.isOwned());
    }

    @Test
    void testIsOwned_WithNullOwner_ReturnsFalse() {
        // Arrange
        PropertyTileViewModel viewModel = new PropertyTileViewModel(
            "Park Place", 350, null, 35.0f, Color.RED
        );

        // Act & Assert
        assertFalse(viewModel.isOwned());
    }

    @Test
    void testIsOwned_WithEmptyOwner_ReturnsFalse() {
        // Arrange
        PropertyTileViewModel viewModel = new PropertyTileViewModel(
            "Park Place", 350, "", 35.0f, Color.RED
        );

        // Act & Assert
        assertFalse(viewModel.isOwned());
    }

    @Test
    void testGetOwnerColor_WhenOwned_ReturnsOwnerColor() {
        // Arrange
        PropertyTileViewModel viewModel = new PropertyTileViewModel(
            "Boardwalk", 400, "Player1", 50.0f, Color.GREEN
        );

        // Act
        Color ownerColor = viewModel.getOwnerColor();

        // Assert
        assertEquals(Color.GREEN, ownerColor);
    }

    @Test
    void testGetOwnerColor_WhenNotOwned_ReturnsWhite() {
        // Arrange
        PropertyTileViewModel viewModel = new PropertyTileViewModel(
            "Park Place", 350, null, 35.0f, Color.RED
        );

        // Act
        Color ownerColor = viewModel.getOwnerColor();

        // Assert
        assertEquals(Color.WHITE, ownerColor);
    }

    @Test
    void testGetOwnerColor_WhenEmptyOwner_ReturnsWhite() {
        // Arrange
        PropertyTileViewModel viewModel = new PropertyTileViewModel(
            "Park Place", 350, "", 35.0f, Color.RED
        );

        // Act
        Color ownerColor = viewModel.getOwnerColor();

        // Assert
        assertEquals(Color.WHITE, ownerColor);
    }

    @Test
    void testGetPrice_ReturnsCorrectPrice() {
        // Arrange
        PropertyTileViewModel viewModel = new PropertyTileViewModel(
            "Mayfair", 500, "Player3", 75.0f, Color.YELLOW
        );

        // Act & Assert
        assertEquals(500, viewModel.getPrice());
    }

    @Test
    void testGetRent_ReturnsCorrectRent() {
        // Arrange
        PropertyTileViewModel viewModel = new PropertyTileViewModel(
            "Mayfair", 500, "Player3", 75.5f, Color.YELLOW
        );

        // Act & Assert
        assertEquals(75.5f, viewModel.getRent());
    }

    @Test
    void testGetOwner_ReturnsCorrectOwner() {
        // Arrange
        PropertyTileViewModel viewModel = new PropertyTileViewModel(
            "Mayfair", 500, "TestPlayer", 75.0f, Color.YELLOW
        );

        // Act & Assert
        assertEquals("TestPlayer", viewModel.getOwner());
    }

    @Test
    void testInstanceOfAbstractTileViewModel() {
        // Arrange & Act
        PropertyTileViewModel viewModel = new PropertyTileViewModel(
            "Test", 100, "Owner", 10.0f, Color.BLACK
        );

        // Assert
        assertInstanceOf(AbstractTileViewModel.class, viewModel);
    }

    @Test
    void testZeroPrice_HandledCorrectly() {
        // Arrange & Act
        PropertyTileViewModel viewModel = new PropertyTileViewModel(
            "Free Property", 0, "Player1", 0.0f, Color.PINK
        );

        // Assert
        assertEquals(0, viewModel.getPrice());
        assertEquals(0.0f, viewModel.getRent());
        assertTrue(viewModel.isOwned());
    }

    @Test
    void testNegativeValues_HandledCorrectly() {
        // Arrange & Act
        PropertyTileViewModel viewModel = new PropertyTileViewModel(
            "Weird Property", -100, "Player1", -10.0f, Color.CYAN
        );

        // Assert
        assertEquals(-100, viewModel.getPrice());
        assertEquals(-10.0f, viewModel.getRent());
        assertTrue(viewModel.isOwned());
    }
}
