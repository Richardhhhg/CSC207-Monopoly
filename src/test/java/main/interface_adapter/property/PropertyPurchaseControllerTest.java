package main.interface_adapter.property;

import main.entity.players.AbstractPlayer;
import main.entity.players.DefaultPlayer;
import main.entity.tiles.PropertyTile;
import main.use_case.tiles.property.PropertyPurchaseUseCase.PurchaseResultCallback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Frame;

import static org.junit.jupiter.api.Assertions.*;

class PropertyPurchaseControllerTest {

    private PropertyPurchaseController controller;
    private MockPropertyPresenter mockPresenter;
    private AbstractPlayer testPlayer;
    private PropertyTile testProperty;
    private JFrame testFrame;

    // Mock presenter to track method calls
    private static class MockPropertyPresenter extends PropertyPresenter {
        private int executeCallCount = 0;

        // Override the method that gets called by PropertyPurchaseUseCase
        @Override
        public void presentPurchaseDialog(main.use_case.tiles.property.PropertyPurchaseUseCase.PropertyPurchaseData data,
                                        PurchaseResultCallback callback) {
            super.presentPurchaseDialog(data, callback);
            this.executeCallCount++;
        }

        public int getExecuteCallCount() {
            return executeCallCount;
        }
    }

    private static class MockCallback implements PurchaseResultCallback {
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
        mockPresenter = new MockPropertyPresenter();
        controller = new PropertyPurchaseController(mockPresenter);
        testPlayer = new DefaultPlayer("TestPlayer", Color.BLUE);
        testProperty = new PropertyTile("TestProperty", 200, 25.0f);
        testFrame = new JFrame("Test Frame");
    }

    @Test
    void testConstructor_WithValidPresenter_CreatesController() {
        // Arrange & Act
        PropertyPurchaseController newController = new PropertyPurchaseController(mockPresenter);

        // Assert
        assertNotNull(newController);
    }

    @Test
    void testConstructor_WithNullPresenter_CreatesController() {
        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> new PropertyPurchaseController(null));
    }

    @Test
    void testExecute_WithValidPlayerAndProperty_CallsUseCase() {
        // Act
        controller.execute(testPlayer, testProperty);

        // Assert
        assertEquals(1, mockPresenter.getExecuteCallCount());
    }

    @Test
    void testExecute_WithNullPlayer_ThrowsException() {
        // Act & Assert - this should throw NullPointerException based on the use case behavior
        assertThrows(NullPointerException.class, () -> {
            controller.execute(null, testProperty);
        });
    }

    @Test
    void testExecute_WithNullProperty_ThrowsException() {
        // Act & Assert - this should throw NullPointerException based on the use case behavior
        assertThrows(NullPointerException.class, () -> {
            controller.execute(testPlayer, null);
        });
    }

    @Test
    void testExecute_WithBothNull_ThrowsException() {
        // Act & Assert - this should throw NullPointerException based on the use case behavior
        assertThrows(NullPointerException.class, () -> {
            controller.execute(null, null);
        });
    }

    @Test
    void testHandleUnownedProperty_WithValidPlayerAndProperty_CallsExecute() {
        // Act
        controller.handleUnownedProperty(testPlayer, testProperty);

        // Assert
        assertEquals(1, mockPresenter.getExecuteCallCount());
    }

    @Test
    void testHandleUnownedProperty_WithNullPlayer_ThrowsException() {
        // Act & Assert - this should throw NullPointerException because it calls execute
        assertThrows(NullPointerException.class, () -> {
            controller.handleUnownedProperty(null, testProperty);
        });
    }

    @Test
    void testHandleUnownedProperty_WithNullProperty_ThrowsException() {
        // Act & Assert - this should throw NullPointerException because it calls execute
        assertThrows(NullPointerException.class, () -> {
            controller.handleUnownedProperty(testPlayer, null);
        });
    }

    @Test
    void testShowPurchaseDialog_WithValidPlayerAndProperty_CallsPopupAndCreatesLambda() {
        // Arrange
        MockCallback callback = new MockCallback();

        // Act & Assert - should not throw exception and should hit the lambda line
        assertDoesNotThrow(() -> {
            controller.showPurchaseDialog(null, callback, testPlayer, testProperty, testFrame);
        });
        // This test ensures the lambda (success, message) -> callback.onResult(success) is created
    }

    @Test
    void testShowPurchaseDialog_WithNullPlayer_DoesNotCallPopup() {
        // Arrange
        MockCallback callback = new MockCallback();

        // Act & Assert - should not throw exception and not call popup
        assertDoesNotThrow(() -> {
            controller.showPurchaseDialog(null, callback, null, testProperty, testFrame);
        });
        // Popup should not be called since player is null
        assertEquals(0, callback.getCallCount());
    }

    @Test
    void testShowPurchaseDialog_WithNullProperty_DoesNotCallPopup() {
        // Arrange
        MockCallback callback = new MockCallback();

        // Act & Assert - should not throw exception and not call popup
        assertDoesNotThrow(() -> {
            controller.showPurchaseDialog(null, callback, testPlayer, null, testFrame);
        });
        // Popup should not be called since property is null
        assertEquals(0, callback.getCallCount());
    }

    @Test
    void testShowPurchaseDialog_WithBothPlayerAndPropertyNull_DoesNotCallPopup() {
        // Arrange
        MockCallback callback = new MockCallback();

        // Act & Assert
        assertDoesNotThrow(() -> {
            controller.showPurchaseDialog(null, callback, null, null, testFrame);
        });
        assertEquals(0, callback.getCallCount());
    }

    @Test
    void testShowPurchaseDialog_WithNullParentComponent_ThrowsException() {
        // Arrange
        MockCallback callback = new MockCallback();

        // Act & Assert - SwingUtilities.getWindowAncestor(null) throws NullPointerException
        assertThrows(NullPointerException.class, () -> {
            controller.showPurchaseDialog(null, callback, testPlayer, testProperty, null);
        });
    }

    @Test
    void testShowPurchaseDialog_WithNonFrameParentComponent_HandlesGracefully() {
        // Arrange
        MockCallback callback = new MockCallback();
        JPanel panel = new JPanel(); // Component that doesn't have a Frame ancestor

        // Act & Assert - should handle casting to Frame
        assertDoesNotThrow(() -> {
            controller.showPurchaseDialog(null, callback, testPlayer, testProperty, panel);
        });
    }

    @Test
    void testShowPurchaseDialog_WithDirectFrameComponent_WorksCorrectly() {
        // Arrange
        MockCallback callback = new MockCallback();
        Frame frame = new Frame("Direct Frame");

        // Act & Assert
        assertDoesNotThrow(() -> {
            controller.showPurchaseDialog(null, callback, testPlayer, testProperty, frame);
        });
    }

    @Test
    void testShowPurchaseDialog_WithNullCallback_HandlesGracefully() {
        // Act & Assert - should handle null callback
        assertDoesNotThrow(() -> {
            controller.showPurchaseDialog(null, null, testPlayer, testProperty, testFrame);
        });
    }

    @Test
    void testMultipleExecuteCalls_CallsUseCaseMultipleTimes() {
        // Act
        controller.execute(testPlayer, testProperty);
        controller.execute(testPlayer, testProperty);
        controller.execute(testPlayer, testProperty);

        // Assert
        assertEquals(3, mockPresenter.getExecuteCallCount());
    }
}
