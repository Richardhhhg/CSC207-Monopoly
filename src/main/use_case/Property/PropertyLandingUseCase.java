package main.use_case.Property;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;

/**
 * Use case for handling property landing events.
 * This contains the business logic and coordinates with the presenter through an interface.
 */
public class PropertyLandingUseCase {
    private final PropertyLandingOutputBoundary presenter;

    public PropertyLandingUseCase(PropertyLandingOutputBoundary presenter) {
        this.presenter = presenter;
    }

    public void handleUnownedProperty(Player player, PropertyTile property) {
        // Business logic: Validate property can be purchased
        if (property.isOwned()) {
            return; // Should not happen, but safety check
        }

        // Create data transfer object for presenter
        PropertyPurchaseData purchaseData = new PropertyPurchaseData(
            player.getName(),
            player.getMoney(),
            property.getName(),
            property.getPrice(),
            player.getMoney() >= property.getPrice()
        );

        // Send to presenter through output boundary
        presenter.presentPurchaseDialog(purchaseData, (success) -> {
            if (success) {
                // Execute the purchase
                boolean purchaseSuccessful = property.attemptPurchase(player);
                if (purchaseSuccessful) {
                    PropertyOwnershipData ownershipData = new PropertyOwnershipData(
                        property.getName(),
                        player.getName(),
                        player.getMoney()
                    );
                    presenter.presentPropertyPurchased(ownershipData);
                }
            }
        });
    }

    public void handleRentPayment(Player payer, Player owner, PropertyTile property, float rentAmount) {
        // Business logic is already handled in PropertyTile.onLanding()
        // Create data transfer object for presenter
        RentPaymentData rentData = new RentPaymentData(
            payer.getName(),
            owner.getName(),
            property.getName(),
            rentAmount,
            payer.getMoney(),
            owner.getMoney()
        );

        // Send to presenter through output boundary
        presenter.presentRentPayment(rentData);
    }

    // Data transfer objects for presenter communication
    public static class PropertyPurchaseData {
        public final String playerName;
        public final float playerMoney;
        public final String propertyName;
        public final float propertyPrice;
        public final boolean canAfford;

        public PropertyPurchaseData(String playerName, float playerMoney, String propertyName,
                                  float propertyPrice, boolean canAfford) {
            this.playerName = playerName;
            this.playerMoney = playerMoney;
            this.propertyName = propertyName;
            this.propertyPrice = propertyPrice;
            this.canAfford = canAfford;
        }
    }

    public static class PropertyOwnershipData {
        public final String propertyName;
        public final String ownerName;
        public final float newOwnerMoney;

        public PropertyOwnershipData(String propertyName, String ownerName, float newOwnerMoney) {
            this.propertyName = propertyName;
            this.ownerName = ownerName;
            this.newOwnerMoney = newOwnerMoney;
        }
    }

    public static class RentPaymentData {
        public final String payerName;
        public final String ownerName;
        public final String propertyName;
        public final float rentAmount;
        public final float payerNewMoney;
        public final float ownerNewMoney;

        public RentPaymentData(String payerName, String ownerName, String propertyName,
                             float rentAmount, float payerNewMoney, float ownerNewMoney) {
            this.payerName = payerName;
            this.ownerName = ownerName;
            this.propertyName = propertyName;
            this.rentAmount = rentAmount;
            this.payerNewMoney = payerNewMoney;
            this.ownerNewMoney = ownerNewMoney;
        }
    }

    public interface PurchaseResultCallback {
        void onResult(boolean success);
    }
}

