package main.use_case.Property;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;

public class PropertyPurchaseUseCase {
    private final PropertyPurchaseOutputBoundary outputBoundary;

    public PropertyPurchaseUseCase(PropertyPurchaseOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    public void execute(Player player, PropertyTile property) {
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
        outputBoundary.presentPurchaseDialog(purchaseData, (success) -> {
            if (success) {
                // Execute the purchase
                boolean purchaseSuccessful = property.attemptPurchase(player);
                if (purchaseSuccessful) {
                    PropertyOwnershipData ownershipData = new PropertyOwnershipData(
                        property.getName(),
                        player.getName(),
                        player.getMoney()
                    );
                    outputBoundary.presentPropertyPurchased(ownershipData);
                }
            }
        });
    }

    // Data transfer objects
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

    public interface PurchaseResultCallback {
        void onResult(boolean success);
    }
}
