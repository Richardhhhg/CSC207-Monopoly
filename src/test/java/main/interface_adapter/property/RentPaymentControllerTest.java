package main.interface_adapter.property;

import main.entity.players.AbstractPlayer;
import main.entity.players.DefaultPlayer;
import main.entity.tiles.PropertyTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JFrame;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class RentPaymentControllerTest {

    private RentPaymentController controller;
    private MockPropertyPresenter mockPresenter;
    private AbstractPlayer testPayer;
    private AbstractPlayer testOwner;
    private PropertyTile testProperty;
    private JFrame testFrame;

    private static class MockPropertyPresenter extends PropertyPresenter {
        private int presentRentPaymentCallCount = 0;

        @Override
        public void presentRentPayment(main.use_case.tiles.property.RentPaymentUseCase.RentPaymentData data) {
            super.presentRentPayment(data);
            this.presentRentPaymentCallCount++;
        }

        public int getPresentRentPaymentCallCount() {
            return presentRentPaymentCallCount;
        }
    }

    @BeforeEach
    void setUp() {
        mockPresenter = new MockPropertyPresenter();
        controller = new RentPaymentController(mockPresenter);
        testPayer = new DefaultPlayer("Payer", Color.BLUE);
        testOwner = new DefaultPlayer("Owner", Color.RED);
        testProperty = new PropertyTile("TestProperty", 200, 25.0f);
        testFrame = new JFrame("Test Frame");
    }

    @Test
    void testConstructor_ValidPresenter_CreatesController() {
        // Arrange & Act
        RentPaymentController newController = new RentPaymentController(mockPresenter);

        // Assert
        assertNotNull(newController);
    }

    @Test
    void testExecute_ValidParameters_CallsUseCase() {
        // Arrange
        float rentAmount = 50.0f;

        // Act
        controller.execute(testPayer, testOwner, testProperty, rentAmount);

        // Assert
        assertEquals(1, mockPresenter.getPresentRentPaymentCallCount());
        assertNotNull(mockPresenter.getRentPaymentViewModel());
        assertEquals("Payer", mockPresenter.getRentPaymentViewModel().getPayerName());
        assertEquals("Owner", mockPresenter.getRentPaymentViewModel().getOwnerName());
        assertEquals("TestProperty", mockPresenter.getRentPaymentViewModel().getPropertyName());
        assertEquals(50.0f, mockPresenter.getRentPaymentViewModel().getRentAmount());
    }

    @Test
    void testHandleRentPayment_ValidParameters_CallsExecute() {
        // Arrange
        float rentAmount = 75.0f;

        // Act
        controller.handleRentPayment(testPayer, testOwner, testProperty, rentAmount);

        // Assert
        assertEquals(1, mockPresenter.getPresentRentPaymentCallCount());
        assertNotNull(mockPresenter.getRentPaymentViewModel());
        assertEquals("Payer", mockPresenter.getRentPaymentViewModel().getPayerName());
        assertEquals("Owner", mockPresenter.getRentPaymentViewModel().getOwnerName());
        assertEquals(75.0f, mockPresenter.getRentPaymentViewModel().getRentAmount());
    }

    @Test
    void testExecute_ZeroRent_CallsUseCase() {
        // Arrange
        float rentAmount = 0.0f;

        // Act
        controller.execute(testPayer, testOwner, testProperty, rentAmount);

        // Assert
        assertEquals(1, mockPresenter.getPresentRentPaymentCallCount());
        assertNotNull(mockPresenter.getRentPaymentViewModel());
        assertEquals(0.0f, mockPresenter.getRentPaymentViewModel().getRentAmount());
    }

    @Test
    void testExecute_NegativeRent_CallsUseCase() {
        // Arrange
        float rentAmount = -10.0f;

        // Act
        controller.execute(testPayer, testOwner, testProperty, rentAmount);

        // Assert
        assertEquals(1, mockPresenter.getPresentRentPaymentCallCount());
        assertNotNull(mockPresenter.getRentPaymentViewModel());
        assertEquals(-10.0f, mockPresenter.getRentPaymentViewModel().getRentAmount());
    }

    @Test
    void testShowRentPaymentNotification_ValidViewModel_DoesNotThrowException() {
        // Arrange
        controller.execute(testPayer, testOwner, testProperty, 50.0f);
        PropertyViewModel.RentPaymentViewModel viewModel = mockPresenter.getRentPaymentViewModel();

        // Act & Assert
        assertDoesNotThrow(() -> {
            controller.showRentPaymentNotification(viewModel, testFrame);
        });
    }

    @Test
    void testExecute_MultipleInvocations_CallsUseCaseMultipleTimes() {
        // Arrange
        AbstractPlayer payer2 = new DefaultPlayer("Payer2", Color.GREEN);
        PropertyTile property2 = new PropertyTile("Property2", 300, 30.0f);

        // Act
        controller.execute(testPayer, testOwner, testProperty, 50.0f);
        controller.execute(payer2, testOwner, property2, 75.0f);

        // Assert
        assertEquals(2, mockPresenter.getPresentRentPaymentCallCount());
    }

    @Test
    void testExecute_HighRentAmount_CallsUseCase() {
        // Arrange
        float rentAmount = 500.0f;

        // Act
        controller.execute(testPayer, testOwner, testProperty, rentAmount);

        // Assert
        assertEquals(1, mockPresenter.getPresentRentPaymentCallCount());
        assertNotNull(mockPresenter.getRentPaymentViewModel());
        assertEquals(500.0f, mockPresenter.getRentPaymentViewModel().getRentAmount());
    }

    @Test
    void testExecute_SamePlayerAsOwnerAndPayer_CallsUseCase() {
        // Act
        controller.execute(testOwner, testOwner, testProperty, 25.0f);

        // Assert
        assertEquals(1, mockPresenter.getPresentRentPaymentCallCount());
        assertNotNull(mockPresenter.getRentPaymentViewModel());
        assertEquals("Owner", mockPresenter.getRentPaymentViewModel().getPayerName());
        assertEquals("Owner", mockPresenter.getRentPaymentViewModel().getOwnerName());
    }
}
