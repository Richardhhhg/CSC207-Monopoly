package main.use_case.property;

import main.entity.players.AbstractPlayer;
import main.entity.players.DefaultPlayer;
import main.entity.tiles.PropertyTile;
import main.use_case.tiles.property.PropertyPurchaseOutputBoundary;
import main.use_case.tiles.property.PropertyPurchaseUseCase;
import main.use_case.tiles.property.PropertyPurchaseUseCase.PropertyOwnershipData;
import main.use_case.tiles.property.PropertyPurchaseUseCase.PropertyPurchaseData;
import main.use_case.tiles.property.PropertyPurchaseUseCase.PurchaseResultCallback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class PropertyPurchaseTest {

    private PropertyPurchaseUseCase propertyPurchaseUseCase;
    private AbstractPlayer player;
    private MockPropertyPurchasePresenter presenter;

    static class MockPropertyPurchasePresenter implements PropertyPurchaseOutputBoundary {
        PropertyPurchaseData lastPurchaseData;
        PropertyOwnershipData lastOwnershipData;
        PurchaseResultCallback lastCallback;

        @Override
        public void presentPurchaseDialog(PropertyPurchaseData data, PurchaseResultCallback callback) {
            this.lastPurchaseData = data;
            this.lastCallback = callback;
        }

        @Override
        public void presentPropertyPurchased(PropertyOwnershipData data) {
            this.lastOwnershipData = data;
        }
    }

    static class MockPropertyTile extends PropertyTile {
        private boolean owned;
        private AbstractPlayer owner;
        private boolean purchaseResult;

        public MockPropertyTile(String name, int price) {
            super(name, price, price);
            this.owned = false;
            this.purchaseResult = true;
        }

        @Override
        public boolean isOwned() {
            return owned;
        }

        @Override
        public AbstractPlayer getOwner() {
            return owner;
        }

        public void setOwned(boolean owned) {
            this.owned = owned;
        }

        public void setPurchaseResult(boolean result) {
            this.purchaseResult = result;
        }

        @Override
        public boolean attemptPurchase(AbstractPlayer player) {
            if (purchaseResult && player.getMoney() >= getPrice()) {
                player.deductMoney(getPrice());
                this.owner = player;
                this.owned = true;
                return true;
            }
            return false;
        }
    }

    @BeforeEach
    void setUp() {
        player = new DefaultPlayer("TestPlayer", Color.BLUE);
        // DefaultPlayer starts with 1200, so after adding 1000, total is 2200
        player.addMoney(1000.0f);
        presenter = new MockPropertyPurchasePresenter();
        propertyPurchaseUseCase = new PropertyPurchaseUseCase(presenter);
    }

    @Test
    void testExecute_UnownedProperty_CallsPresentPurchaseDialog() {
        // Arrange
        MockPropertyTile property = new MockPropertyTile("TestProperty", 500);

        // Act
        propertyPurchaseUseCase.execute(player, property);

        // Assert
        assertNotNull(presenter.lastPurchaseData);
        assertEquals("TestPlayer", presenter.lastPurchaseData.playerName());
        assertEquals(2200.0f, presenter.lastPurchaseData.playerMoney()); // 1200 + 1000
        assertEquals("TestProperty", presenter.lastPurchaseData.propertyName());
        assertEquals(500.0f, presenter.lastPurchaseData.propertyPrice());
        assertTrue(presenter.lastPurchaseData.canAfford());
        assertNotNull(presenter.lastCallback);
    }

    @Test
    void testExecute_UnownedPropertyCannotAfford_CallsPresentPurchaseDialogWithCanAffordFalse() {
        // Arrange
        AbstractPlayer poorPlayer = new DefaultPlayer("PoorPlayer", Color.RED);
        // DefaultPlayer starts with 1200, deduct 800 to have 400 (less than 500 property price)
        poorPlayer.deductMoney(800.0f);
        MockPropertyTile property = new MockPropertyTile("TestProperty", 500);

        // Act
        propertyPurchaseUseCase.execute(poorPlayer, property);

        // Assert
        assertNotNull(presenter.lastPurchaseData);
        assertFalse(presenter.lastPurchaseData.canAfford());
    }

    @Test
    void testExecute_OwnedProperty_DoesNotCallPresenter() {
        // Arrange
        MockPropertyTile property = new MockPropertyTile("TestProperty", 500);
        property.setOwned(true);

        // Act
        propertyPurchaseUseCase.execute(player, property);

        // Assert
        assertNull(presenter.lastPurchaseData);
        assertNull(presenter.lastOwnershipData);
    }

    @Test
    void testHandlePurchaseResult_SuccessfulPurchase_CallsPresentPropertyPurchased() {
        // Arrange
        MockPropertyTile property = new MockPropertyTile("TestProperty", 500);

        // Act
        propertyPurchaseUseCase.execute(player, property);
        presenter.lastCallback.onResult(true);

        // Assert
        assertNotNull(presenter.lastOwnershipData);
        assertEquals("TestProperty", presenter.lastOwnershipData.propertyName());
        assertEquals("TestPlayer", presenter.lastOwnershipData.ownerName());
        assertEquals(1700.0f, presenter.lastOwnershipData.newOwnerMoney()); // 2200 - 500
        assertEquals(1700.0f, player.getMoney()); // 2200 - 500
    }

    @Test
    void testHandlePurchaseResult_FailedPurchase_DoesNotCallPresentPropertyPurchased() {
        // Arrange
        MockPropertyTile property = new MockPropertyTile("TestProperty", 500);

        // Act
        propertyPurchaseUseCase.execute(player, property);
        presenter.lastCallback.onResult(false);

        // Assert
        assertNull(presenter.lastOwnershipData);
        assertEquals(2200.0f, player.getMoney()); // Money unchanged (1200 + 1000)
    }

    @Test
    void testHandlePurchaseResult_PurchaseAttemptFails_DoesNotCallPresentPropertyPurchased() {
        // Arrange
        MockPropertyTile property = new MockPropertyTile("TestProperty", 500);
        property.setPurchaseResult(false);

        // Act
        propertyPurchaseUseCase.execute(player, property);
        presenter.lastCallback.onResult(true);

        // Assert
        assertNull(presenter.lastOwnershipData);
    }

    // Test data objects
    @Test
    void testPropertyPurchaseData_AllGetters() {
        // Arrange & Act
        PropertyPurchaseData data = new PropertyPurchaseData(
            "Player1", 1000.0f, "Property1", 500.0f, true
        );

        // Assert
        assertEquals("Player1", data.playerName());
        assertEquals(1000.0f, data.playerMoney());
        assertEquals("Property1", data.propertyName());
        assertEquals(500.0f, data.propertyPrice());
        assertTrue(data.canAfford());
    }

    @Test
    void testPropertyOwnershipData_AllGetters() {
        // Arrange & Act
        PropertyOwnershipData data = new PropertyOwnershipData(
            "Property1", "Owner1", 500.0f
        );

        // Assert
        assertEquals("Property1", data.propertyName());
        assertEquals("Owner1", data.ownerName());
        assertEquals(500.0f, data.newOwnerMoney());
    }
}
