package main.interface_adapter.tile;

import main.entity.players.AbstractPlayer;
import main.entity.players.DefaultPlayer;
import main.entity.tiles.AbstractTile;
import main.entity.tiles.PropertyTile;
import main.use_case.tiles.OnLandingUseCase;
import main.use_case.tiles.property.PropertyPurchaseUseCase;
import main.use_case.tiles.property.RentPaymentUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class OnLandingControllerTest {

    private OnLandingController controller;
    private MockOnLandingUseCase mockUseCase;
    private AbstractPlayer testPlayer;
    private AbstractTile testTile;

    // Mock implementation of OnLandingUseCase for testing
    private static class MockOnLandingUseCase extends OnLandingUseCase {
        private AbstractPlayer lastPlayer;
        private AbstractTile lastTile;
        private int executeCallCount = 0;

        /**
         * Constructor for OnLandingUseCase.
         *
         * @param propertyPurchaseUseCase the use case for property purchase
         * @param rentPaymentUseCase      the use case for rent payment
         */
        public MockOnLandingUseCase(PropertyPurchaseUseCase propertyPurchaseUseCase, RentPaymentUseCase rentPaymentUseCase) {
            super(propertyPurchaseUseCase, rentPaymentUseCase);
        }

        @Override
        public void execute(AbstractPlayer player, AbstractTile tile) {
            this.lastPlayer = player;
            this.lastTile = tile;
            this.executeCallCount++;
        }

        public AbstractPlayer getLastPlayer() {
            return lastPlayer;
        }

        public AbstractTile getLastTile() {
            return lastTile;
        }

        public int getExecuteCallCount() {
            return executeCallCount;
        }

        public void reset() {
            lastPlayer = null;
            lastTile = null;
            executeCallCount = 0;
        }
    }

    @BeforeEach
    void setUp() {
        // Create mock use cases for the OnLandingUseCase dependencies
        mockUseCase = new MockOnLandingUseCase(null, null);
        controller = new OnLandingController(mockUseCase);
        testPlayer = new DefaultPlayer("TestPlayer", Color.BLUE);
        testTile = new PropertyTile("TestProperty", 200, 25.0f);
    }

    @Test
    void testConstructor_ValidUseCase_CreatesController() {
        // Arrange & Act
        OnLandingController newController = new OnLandingController(mockUseCase);

        // Assert
        assertNotNull(newController);
    }

    @Test
    void testHandleLanding_ValidPlayerAndTile_CallsUseCaseExecute() {
        // Act
        controller.handleLanding(testPlayer, testTile);

        // Assert
        assertEquals(1, mockUseCase.getExecuteCallCount());
        assertEquals(testPlayer, mockUseCase.getLastPlayer());
        assertEquals(testTile, mockUseCase.getLastTile());
    }

    @Test
    void testHandleLanding_MultipleInvocations_CallsUseCaseMultipleTimes() {
        // Arrange
        AbstractPlayer player2 = new DefaultPlayer("Player2", Color.RED);
        AbstractTile tile2 = new PropertyTile("Property2", 300, 30.0f);

        // Act
        controller.handleLanding(testPlayer, testTile);
        controller.handleLanding(player2, tile2);

        // Assert
        assertEquals(2, mockUseCase.getExecuteCallCount());
        assertEquals(player2, mockUseCase.getLastPlayer()); // Last call should be recorded
        assertEquals(tile2, mockUseCase.getLastTile());
    }

    @Test
    void testHandleLanding_NullPlayer_PassesToUseCase() {
        // Act
        controller.handleLanding(null, testTile);

        // Assert
        assertEquals(1, mockUseCase.getExecuteCallCount());
        assertNull(mockUseCase.getLastPlayer());
        assertEquals(testTile, mockUseCase.getLastTile());
    }

    @Test
    void testHandleLanding_NullTile_PassesToUseCase() {
        // Act
        controller.handleLanding(testPlayer, null);

        // Assert
        assertEquals(1, mockUseCase.getExecuteCallCount());
        assertEquals(testPlayer, mockUseCase.getLastPlayer());
        assertNull(mockUseCase.getLastTile());
    }

    @Test
    void testHandleLanding_BothNull_PassesToUseCase() {
        // Act
        controller.handleLanding(null, null);

        // Assert
        assertEquals(1, mockUseCase.getExecuteCallCount());
        assertNull(mockUseCase.getLastPlayer());
        assertNull(mockUseCase.getLastTile());
    }

    @Test
    void testHandleLanding_SamePlayerDifferentTiles_HandlesCorrectly() {
        // Arrange
        AbstractTile tile2 = new PropertyTile("Property2", 400, 40.0f);

        // Act
        controller.handleLanding(testPlayer, testTile);
        controller.handleLanding(testPlayer, tile2);

        // Assert
        assertEquals(2, mockUseCase.getExecuteCallCount());
        assertEquals(testPlayer, mockUseCase.getLastPlayer());
        assertEquals(tile2, mockUseCase.getLastTile());
    }
}
