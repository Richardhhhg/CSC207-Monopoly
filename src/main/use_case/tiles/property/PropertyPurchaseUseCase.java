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
     * @param abstractPlayer   the player attempting to purchase
     * @param property the property being purchased
     */
    public void execute(AbstractPlayer abstractPlayer, PropertyTile property) {
        if (!property.isOwned()) {
            final PropertyPurchaseData purchaseData = new PropertyPurchaseData(
                abstractPlayer.getName(),
                abstractPlayer.getMoney(),
                property.getName(),
                property.getPrice(),
                abstractPlayer.getMoney() >= property.getPrice()
            );

            outputBoundary.presentPurchaseDialog(purchaseData, success -> {
                handlePurchaseResult(success, abstractPlayer, property);
            });
        }
    }

    private void handlePurchaseResult(boolean success, AbstractPlayer abstractPlayer, PropertyTile property) {
        if (success) {
            final boolean purchaseSuccessful = property.attemptPurchase(abstractPlayer);
            if (purchaseSuccessful) {
                final PropertyOwnershipData ownershipData = new PropertyOwnershipData(
                    property.getName(),
                    abstractPlayer.getName(),
                    abstractPlayer.getMoney()
                );
                outputBoundary.presentPropertyPurchased(ownershipData);
            }
        }
    }

    // Data transfer objects
    public static class PropertyPurchaseData {
        private final String playerName;
        private final float playerMoney;
        private final String propertyName;
        private final float propertyPrice;
        private final boolean canAfford;

        public PropertyPurchaseData(String playerName, float playerMoney, String propertyName,
                                  float propertyPrice, boolean canAfford) {
            this.playerName = playerName;
            this.playerMoney = playerMoney;
            this.propertyName = propertyName;
            this.propertyPrice = propertyPrice;
            this.canAfford = canAfford;
        }

        public String getPlayerName() {
            return playerName;
        }

        public float getPlayerMoney() {
            return playerMoney;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public float getPropertyPrice() {
            return propertyPrice;
        }

        public boolean isCanAfford() {
            return canAfford;
        }
    }

    public static class PropertyOwnershipData {
        private final String propertyName;
        private final String ownerName;
        private final float newOwnerMoney;

        public PropertyOwnershipData(String propertyName, String ownerName, float newOwnerMoney) {
            this.propertyName = propertyName;
            this.ownerName = ownerName;
            this.newOwnerMoney = newOwnerMoney;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public float getNewOwnerMoney() {
            return newOwnerMoney;
        }
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
