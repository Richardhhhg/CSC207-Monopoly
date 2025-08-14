package main.use_case.tiles.property;

import main.entity.players.AbstractPlayer;
import main.entity.tiles.PropertyTile;

public class PropertyPurchaseUseCase {
    private final PropertyPurchaseOutputBoundary outputBoundary;

    public PropertyPurchaseUseCase(PropertyPurchaseOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    /**
     * Executes the property purchase use case.
     *
     * @param player   the player attempting to purchase
     * @param property the property being purchased
     */
    public void execute(AbstractPlayer player, PropertyTile property) {
        if (!property.isOwned()) {
            final PropertyPurchaseData purchaseData = new PropertyPurchaseData(
                player.getName(),
                player.getMoney(),
                property.getName(),
                property.getPrice(),
                player.getMoney() >= property.getPrice()
            );

            outputBoundary.presentPurchaseDialog(purchaseData, success -> {
                handlePurchaseResult(success, player, property);
            });
        }
    }

    private void handlePurchaseResult(boolean success, AbstractPlayer player, PropertyTile property) {
        if (success) {
            final boolean purchaseSuccessful = property.attemptPurchase(player);
            if (purchaseSuccessful) {
                final PropertyOwnershipData ownershipData = new PropertyOwnershipData(
                    property.getName(),
                    player.getName(),
                    player.getMoney()
                );
                outputBoundary.presentPropertyPurchased(ownershipData);
            }
        }
    }

    // Data transfer objects
        public record PropertyPurchaseData(String playerName, float playerMoney, String propertyName, float propertyPrice,
                                           boolean canAfford) {
    }

    public record PropertyOwnershipData(String propertyName, String ownerName, float newOwnerMoney) {
    }

    public interface PurchaseResultCallback {
        /**
         * Called when the purchase result is determined.
         *
         * @param success true if purchase was successful, false otherwise
         */
        void onResult(boolean success);
    }
}
