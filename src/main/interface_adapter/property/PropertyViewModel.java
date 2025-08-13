package main.interface_adapter.property;

/**
 * View models for property-related UI data.
 * These are data containers that the presenter creates and the view consumes.
 */
public class PropertyViewModel {

    public static class PurchaseDialogViewModel {
        private final String playerName;
        private final float playerMoney;
        private final String propertyName;
        private final float propertyPrice;
        private final boolean canAfford;

        public PurchaseDialogViewModel(String playerName, float playerMoney, String propertyName,
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

    public static class PropertyPurchasedViewModel {
        private final String propertyName;
        private final String ownerName;
        private final float newOwnerMoney;

        public PropertyPurchasedViewModel(String propertyName, String ownerName, float newOwnerMoney) {
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

    public static class RentPaymentViewModel {
        private final String payerName;
        private final String ownerName;
        private final String propertyName;
        private final float rentAmount;
        private final float payerNewMoney;
        private final float ownerNewMoney;

        public RentPaymentViewModel(String payerName, String ownerName, String propertyName,
                                  float rentAmount, float payerNewMoney, float ownerNewMoney) {
            this.payerName = payerName;
            this.ownerName = ownerName;
            this.propertyName = propertyName;
            this.rentAmount = rentAmount;
            this.payerNewMoney = payerNewMoney;
            this.ownerNewMoney = ownerNewMoney;
        }

        public String getPayerName() {
            return payerName;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public float getRentAmount() {
            return rentAmount;
        }

        public float getPayerNewMoney() {
            return payerNewMoney;
        }

        public float getOwnerNewMoney() {
            return ownerNewMoney;
        }
    }
}
