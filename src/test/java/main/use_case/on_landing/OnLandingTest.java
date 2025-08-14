package main.use_case.on_landing;

import main.entity.players.AbstractPlayer;
import main.entity.players.DefaultPlayer;
import main.entity.tiles.AbstractTile;
import main.entity.tiles.PropertyTile;
import main.entity.tiles.StockMarketTile;
import main.use_case.tiles.OnLandingUseCase;
import main.use_case.tiles.property.PropertyPurchaseOutputBoundary;
import main.use_case.tiles.property.PropertyPurchaseUseCase;
import main.use_case.tiles.property.RentPaymentOutputBoundary;
import main.use_case.tiles.property.RentPaymentUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class OnLandingTest {

    private OnLandingUseCase onLandingUseCase;
    private AbstractPlayer player;
    private AbstractPlayer owner;
    private MockPropertyPurchasePresenter purchasePresenter;
    private MockRentPaymentPresenter rentPresenter;

    static class MockPropertyPurchasePresenter implements PropertyPurchaseOutputBoundary {
        PropertyPurchaseUseCase.PropertyPurchaseData lastPurchaseData;
        PropertyPurchaseUseCase.PropertyOwnershipData lastOwnershipData;
        PropertyPurchaseUseCase.PurchaseResultCallback lastCallback;

        @Override
        public void presentPurchaseDialog(PropertyPurchaseUseCase.PropertyPurchaseData data,
                                        PropertyPurchaseUseCase.PurchaseResultCallback callback) {
            this.lastPurchaseData = data;
            this.lastCallback = callback;
        }

        @Override
        public void presentPropertyPurchased(PropertyPurchaseUseCase.PropertyOwnershipData data) {
            this.lastOwnershipData = data;
        }
    }

    static class MockRentPaymentPresenter implements RentPaymentOutputBoundary {
        RentPaymentUseCase.RentPaymentData lastRentData;

        @Override
        public void presentRentPayment(RentPaymentUseCase.RentPaymentData data) {
            this.lastRentData = data;
        }
    }

    static class MockPropertyTile extends PropertyTile {
        private boolean owned;
        private AbstractPlayer owner;
        private float rent;

        public MockPropertyTile(String name, int price, float rent) {
            super(name, price, price);
            this.owned = false;
            this.rent = rent;
        }

        @Override
        public boolean isOwned() {
            return owned;
        }

        @Override
        public AbstractPlayer getOwner() {
            return owner;
        }

        public void setOwner(AbstractPlayer owner) {
            this.owner = owner;
            this.owned = (owner != null);
        }

        @Override
        public float calculateRent() {
            return rent;
        }
    }

    static class MockOtherTile extends AbstractTile {
        public MockOtherTile(String name) {
            super(name);
        }
    }

    @BeforeEach
    void setUp() {
        player = new DefaultPlayer("Player1", Color.BLUE);
        owner = new DefaultPlayer("Owner", Color.RED);
        purchasePresenter = new MockPropertyPurchasePresenter();
        rentPresenter = new MockRentPaymentPresenter();

        PropertyPurchaseUseCase purchaseUseCase = new PropertyPurchaseUseCase(purchasePresenter);
        RentPaymentUseCase rentUseCase = new RentPaymentUseCase(rentPresenter);

        onLandingUseCase = new OnLandingUseCase(purchaseUseCase, rentUseCase);
    }

    @Test
    void testExecute_UnownedProperty_CallsPropertyPurchaseUseCase() {
        // Arrange
        MockPropertyTile property = new MockPropertyTile("TestProperty", 500, 50.0f);

        // Act
        onLandingUseCase.execute(player, property);

        // Assert
        assertNotNull(purchasePresenter.lastPurchaseData);
        assertEquals("Player1", purchasePresenter.lastPurchaseData.playerName());
        assertEquals("TestProperty", purchasePresenter.lastPurchaseData.propertyName());
        assertEquals(500.0f, purchasePresenter.lastPurchaseData.propertyPrice());
        assertNull(rentPresenter.lastRentData);
    }

    @Test
    void testExecute_OwnedPropertyByOtherPlayer_CallsRentPaymentUseCase() {
        // Arrange
        MockPropertyTile property = new MockPropertyTile("TestProperty", 500, 50.0f);
        property.setOwner(owner);

        // Act
        onLandingUseCase.execute(player, property);

        // Assert
        assertNotNull(rentPresenter.lastRentData);
        assertEquals("Player1", rentPresenter.lastRentData.payerName());
        assertEquals("Owner", rentPresenter.lastRentData.ownerName());
        assertEquals("TestProperty", rentPresenter.lastRentData.propertyName());
        assertEquals(50.0f, rentPresenter.lastRentData.rentAmount());
        assertNull(purchasePresenter.lastPurchaseData);
    }

    @Test
    void testExecute_OwnedPropertyBySamePlayer_NoAction() {
        // Arrange
        MockPropertyTile property = new MockPropertyTile("TestProperty", 500, 50.0f);
        property.setOwner(player);

        // Act
        onLandingUseCase.execute(player, property);

        // Assert
        assertNull(purchasePresenter.lastPurchaseData);
        assertNull(rentPresenter.lastRentData);
    }

    @Test
    void testExecute_StockMarketTile_NoExceptionThrown() {
        // Arrange
        StockMarketTile stockTile = new StockMarketTile();

        // Act & Assert - Should not throw exception
        assertDoesNotThrow(() -> onLandingUseCase.execute(player, stockTile));
        assertNull(purchasePresenter.lastPurchaseData);
        assertNull(rentPresenter.lastRentData);
    }

    @Test
    void testExecute_OtherTileType_NoAction() {
        // Arrange
        MockOtherTile otherTile = new MockOtherTile("Other Tile");

        // Act
        onLandingUseCase.execute(player, otherTile);

        // Assert
        assertNull(purchasePresenter.lastPurchaseData);
        assertNull(rentPresenter.lastRentData);
    }
}
