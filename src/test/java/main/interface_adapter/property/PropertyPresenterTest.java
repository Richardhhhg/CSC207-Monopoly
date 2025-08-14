package main.interface_adapter.property;

import main.interface_adapter.property.PropertyViewModel.PropertyPurchasedViewModel;
import main.interface_adapter.property.PropertyViewModel.PurchaseDialogViewModel;
import main.interface_adapter.property.PropertyViewModel.RentPaymentViewModel;
import main.use_case.tiles.property.PropertyPurchaseUseCase.PropertyOwnershipData;
import main.use_case.tiles.property.PropertyPurchaseUseCase.PropertyPurchaseData;
import main.use_case.tiles.property.PropertyPurchaseUseCase.PurchaseResultCallback;
import main.use_case.tiles.property.RentPaymentUseCase.RentPaymentData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyPresenterTest {

    private PropertyPresenter presenter;
    private MockPurchaseResultCallback mockCallback;

    private static class MockPurchaseResultCallback implements PurchaseResultCallback {
        private boolean lastResult;
        private int callCount = 0;

        @Override
        public void onResult(boolean success) {
            this.lastResult = success;
            this.callCount++;
        }

        public boolean getLastResult() {
            return lastResult;
        }

        public int getCallCount() {
            return callCount;
        }
    }

    @BeforeEach
    void setUp() {
        presenter = new PropertyPresenter();
        mockCallback = new MockPurchaseResultCallback();
    }

    @Test
    void testConstructor_CreatesPresenterWithNullViewModels() {
        // Arrange & Act
        PropertyPresenter newPresenter = new PropertyPresenter();

        // Assert
        assertNotNull(newPresenter);
        assertNull(newPresenter.getPurchaseDialogViewModel());
        assertNull(newPresenter.getPropertyPurchasedViewModel());
        assertNull(newPresenter.getRentPaymentViewModel());
        assertNull(newPresenter.getPurchaseCallback());
    }

    @Test
    void testPresentPurchaseDialog_ValidData_CreatesViewModel() {
        // Arrange
        PropertyPurchaseData data = new PropertyPurchaseData(
            "Player1", 1500.0f, "Boardwalk", 400.0f, true
        );

        // Act
        presenter.presentPurchaseDialog(data, mockCallback);

        // Assert
        PurchaseDialogViewModel viewModel = presenter.getPurchaseDialogViewModel();
        assertNotNull(viewModel);
        assertEquals("Player1", viewModel.getPlayerName());
        assertEquals(1500.0f, viewModel.getPlayerMoney());
        assertEquals("Boardwalk", viewModel.getPropertyName());
        assertEquals(400.0f, viewModel.getPropertyPrice());
        assertTrue(viewModel.isCanAfford());
        assertEquals(mockCallback, presenter.getPurchaseCallback());
    }

    @Test
    void testPresentPurchaseDialog_CannotAfford_CreatesCorrectViewModel() {
        // Arrange
        PropertyPurchaseData data = new PropertyPurchaseData(
            "Player2", 100.0f, "Park Place", 350.0f, false
        );

        // Act
        presenter.presentPurchaseDialog(data, mockCallback);

        // Assert
        PurchaseDialogViewModel viewModel = presenter.getPurchaseDialogViewModel();
        assertNotNull(viewModel);
        assertEquals("Player2", viewModel.getPlayerName());
        assertEquals(100.0f, viewModel.getPlayerMoney());
        assertEquals("Park Place", viewModel.getPropertyName());
        assertEquals(350.0f, viewModel.getPropertyPrice());
        assertFalse(viewModel.isCanAfford());
    }

    @Test
    void testPresentPropertyPurchased_ValidData_CreatesViewModel() {
        // Arrange
        PropertyOwnershipData data = new PropertyOwnershipData(
            "Mayfair", "Player1", 1100.0f
        );

        // Act
        presenter.presentPropertyPurchased(data);

        // Assert
        PropertyPurchasedViewModel viewModel = presenter.getPropertyPurchasedViewModel();
        assertNotNull(viewModel);
        assertEquals("Mayfair", viewModel.getPropertyName());
        assertEquals("Player1", viewModel.getOwnerName());
        assertEquals(1100.0f, viewModel.getNewOwnerMoney());
    }

    @Test
    void testPresentRentPayment_ValidData_CreatesViewModel() {
        // Arrange
        RentPaymentData data = new RentPaymentData(
            "Player2", "Player1", "Boardwalk", 50.0f, 950.0f, 1550.0f
        );

        // Act
        presenter.presentRentPayment(data);

        // Assert
        RentPaymentViewModel viewModel = presenter.getRentPaymentViewModel();
        assertNotNull(viewModel);
        assertEquals("Player2", viewModel.getPayerName());
        assertEquals("Player1", viewModel.getOwnerName());
        assertEquals("Boardwalk", viewModel.getPropertyName());
        assertEquals(50.0f, viewModel.getRentAmount());
        assertEquals(950.0f, viewModel.getPayerNewMoney());
        assertEquals(1550.0f, viewModel.getOwnerNewMoney());
    }

    @Test
    void testClearPurchaseDialog_ClearsViewModelAndCallback() {
        // Arrange
        PropertyPurchaseData data = new PropertyPurchaseData(
            "Player1", 1500.0f, "Boardwalk", 400.0f, true
        );
        presenter.presentPurchaseDialog(data, mockCallback);

        // Act
        presenter.clearPurchaseDialog();

        // Assert
        assertNull(presenter.getPurchaseDialogViewModel());
        assertNull(presenter.getPurchaseCallback());
    }

    @Test
    void testClearPropertyPurchased_ClearsViewModel() {
        // Arrange
        PropertyOwnershipData data = new PropertyOwnershipData(
            "Mayfair", "Player1", 1100.0f
        );
        presenter.presentPropertyPurchased(data);

        // Act
        presenter.clearPropertyPurchased();

        // Assert
        assertNull(presenter.getPropertyPurchasedViewModel());
    }

    @Test
    void testClearRentPayment_ClearsViewModel() {
        // Arrange
        RentPaymentData data = new RentPaymentData(
            "Player2", "Player1", "Boardwalk", 50.0f, 950.0f, 1550.0f
        );
        presenter.presentRentPayment(data);

        // Act
        presenter.clearRentPayment();

        // Assert
        assertNull(presenter.getRentPaymentViewModel());
    }

    @Test
    void testMultiplePresentCalls_OverwritesPreviousViewModels() {
        // Arrange
        PropertyPurchaseData data1 = new PropertyPurchaseData(
            "Player1", 1500.0f, "Boardwalk", 400.0f, true
        );
        PropertyPurchaseData data2 = new PropertyPurchaseData(
            "Player2", 800.0f, "Park Place", 350.0f, true
        );

        // Act
        presenter.presentPurchaseDialog(data1, mockCallback);
        presenter.presentPurchaseDialog(data2, mockCallback);

        // Assert
        PurchaseDialogViewModel viewModel = presenter.getPurchaseDialogViewModel();
        assertEquals("Player2", viewModel.getPlayerName());
        assertEquals("Park Place", viewModel.getPropertyName());
    }
}

