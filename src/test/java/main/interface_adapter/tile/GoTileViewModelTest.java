package main.interface_adapter.tile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoTileViewModelTest {

    @Test
    void testConstructor_SetsNameToGo() {
        // Arrange & Act
        GoTileViewModel viewModel = new GoTileViewModel();

        // Assert
        assertEquals("Go", viewModel.getName());
    }

    @Test
    void testInstanceOfAbstractTileViewModel() {
        // Arrange & Act
        GoTileViewModel viewModel = new GoTileViewModel();

        // Assert
        assertInstanceOf(AbstractTileViewModel.class, viewModel);
    }

    @Test
    void testMultipleInstances_AllHaveSameName() {
        // Arrange & Act
        GoTileViewModel viewModel1 = new GoTileViewModel();
        GoTileViewModel viewModel2 = new GoTileViewModel();

        // Assert
        assertEquals(viewModel1.getName(), viewModel2.getName());
        assertEquals("Go", viewModel1.getName());
        assertEquals("Go", viewModel2.getName());
    }
}
