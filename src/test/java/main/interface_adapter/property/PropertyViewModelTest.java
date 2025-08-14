package main.interface_adapter.property;

import main.interface_adapter.property.PropertyViewModel.PropertyPurchasedViewModel;
import main.interface_adapter.property.PropertyViewModel.PurchaseDialogViewModel;
import main.interface_adapter.property.PropertyViewModel.RentPaymentViewModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyViewModelTest {

    @Test
    void testPurchaseDialogViewModel_ValidParameters_CreatesCorrectly() {
        // Arrange & Act
        PurchaseDialogViewModel viewModel = new PurchaseDialogViewModel(
            "Player1", 1500.0f, "Boardwalk", 400.0f, true
        );

        // Assert
        assertEquals("Player1", viewModel.getPlayerName());
        assertEquals(1500.0f, viewModel.getPlayerMoney());
        assertEquals("Boardwalk", viewModel.getPropertyName());
        assertEquals(400.0f, viewModel.getPropertyPrice());
        assertTrue(viewModel.isCanAfford());
    }

    @Test
    void testPurchaseDialogViewModel_CannotAfford_CreatesCorrectly() {
        // Arrange & Act
        PurchaseDialogViewModel viewModel = new PurchaseDialogViewModel(
            "Player2", 100.0f, "Park Place", 350.0f, false
        );

        // Assert
        assertEquals("Player2", viewModel.getPlayerName());
        assertEquals(100.0f, viewModel.getPlayerMoney());
        assertEquals("Park Place", viewModel.getPropertyName());
        assertEquals(350.0f, viewModel.getPropertyPrice());
        assertFalse(viewModel.isCanAfford());
    }

    @Test
    void testPurchaseDialogViewModel_NullValues_HandlesCorrectly() {
        // Arrange & Act
        PurchaseDialogViewModel viewModel = new PurchaseDialogViewModel(
            null, 0.0f, null, 0.0f, false
        );

        // Assert
        assertNull(viewModel.getPlayerName());
        assertEquals(0.0f, viewModel.getPlayerMoney());
        assertNull(viewModel.getPropertyName());
        assertEquals(0.0f, viewModel.getPropertyPrice());
        assertFalse(viewModel.isCanAfford());
    }

    @Test
    void testPropertyPurchasedViewModel_ValidParameters_CreatesCorrectly() {
        // Arrange & Act
        PropertyPurchasedViewModel viewModel = new PropertyPurchasedViewModel(
            "Mayfair", "Player1", 1100.0f
        );

        // Assert
        assertEquals("Mayfair", viewModel.getPropertyName());
        assertEquals("Player1", viewModel.getOwnerName());
        assertEquals(1100.0f, viewModel.getNewOwnerMoney());
    }

    @Test
    void testPropertyPurchasedViewModel_NullValues_HandlesCorrectly() {
        // Arrange & Act
        PropertyPurchasedViewModel viewModel = new PropertyPurchasedViewModel(
            null, null, 0.0f
        );

        // Assert
        assertNull(viewModel.getPropertyName());
        assertNull(viewModel.getOwnerName());
        assertEquals(0.0f, viewModel.getNewOwnerMoney());
    }

    @Test
    void testRentPaymentViewModel_ValidParameters_CreatesCorrectly() {
        // Arrange & Act
        RentPaymentViewModel viewModel = new RentPaymentViewModel(
            "Player2", "Player1", "Boardwalk", 50.0f, 950.0f, 1550.0f
        );

        // Assert
        assertEquals("Player2", viewModel.getPayerName());
        assertEquals("Player1", viewModel.getOwnerName());
        assertEquals("Boardwalk", viewModel.getPropertyName());
        assertEquals(50.0f, viewModel.getRentAmount());
        assertEquals(950.0f, viewModel.getPayerNewMoney());
        assertEquals(1550.0f, viewModel.getOwnerNewMoney());
    }

    @Test
    void testRentPaymentViewModel_ZeroRent_HandlesCorrectly() {
        // Arrange & Act
        RentPaymentViewModel viewModel = new RentPaymentViewModel(
            "Player2", "Player1", "Free Property", 0.0f, 1000.0f, 1000.0f
        );

        // Assert
        assertEquals("Player2", viewModel.getPayerName());
        assertEquals("Player1", viewModel.getOwnerName());
        assertEquals("Free Property", viewModel.getPropertyName());
        assertEquals(0.0f, viewModel.getRentAmount());
        assertEquals(1000.0f, viewModel.getPayerNewMoney());
        assertEquals(1000.0f, viewModel.getOwnerNewMoney());
    }

    @Test
    void testRentPaymentViewModel_NullValues_HandlesCorrectly() {
        // Arrange & Act
        RentPaymentViewModel viewModel = new RentPaymentViewModel(
            null, null, null, 0.0f, 0.0f, 0.0f
        );

        // Assert
        assertNull(viewModel.getPayerName());
        assertNull(viewModel.getOwnerName());
        assertNull(viewModel.getPropertyName());
        assertEquals(0.0f, viewModel.getRentAmount());
        assertEquals(0.0f, viewModel.getPayerNewMoney());
        assertEquals(0.0f, viewModel.getOwnerNewMoney());
    }

    @Test
    void testRentPaymentViewModel_NegativeRent_HandlesCorrectly() {
        // Arrange & Act
        RentPaymentViewModel viewModel = new RentPaymentViewModel(
            "Player2", "Player1", "Strange Property", -10.0f, 1010.0f, 990.0f
        );

        // Assert
        assertEquals("Player2", viewModel.getPayerName());
        assertEquals("Player1", viewModel.getOwnerName());
        assertEquals("Strange Property", viewModel.getPropertyName());
        assertEquals(-10.0f, viewModel.getRentAmount());
        assertEquals(1010.0f, viewModel.getPayerNewMoney());
        assertEquals(990.0f, viewModel.getOwnerNewMoney());
    }

    @Test
    void testPurchaseDialogViewModel_ZeroPrice_HandlesCorrectly() {
        // Arrange & Act
        PurchaseDialogViewModel viewModel = new PurchaseDialogViewModel(
            "Player1", 1000.0f, "Free Property", 0.0f, true
        );

        // Assert
        assertEquals("Player1", viewModel.getPlayerName());
        assertEquals(1000.0f, viewModel.getPlayerMoney());
        assertEquals("Free Property", viewModel.getPropertyName());
        assertEquals(0.0f, viewModel.getPropertyPrice());
        assertTrue(viewModel.isCanAfford());
    }

    @Test
    void testPropertyPurchasedViewModel_NegativeMoney_HandlesCorrectly() {
        // Arrange & Act
        PropertyPurchasedViewModel viewModel = new PropertyPurchasedViewModel(
            "Expensive Property", "Player1", -100.0f
        );

        // Assert
        assertEquals("Expensive Property", viewModel.getPropertyName());
        assertEquals("Player1", viewModel.getOwnerName());
        assertEquals(-100.0f, viewModel.getNewOwnerMoney());
    }
}

