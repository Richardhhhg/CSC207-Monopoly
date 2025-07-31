package main.interface_adapter.Property;

/**
 * View models for property-related UI data.
 * These are data containers that the presenter creates and the view consumes.
 */
public class PropertyViewModel {

    public static class PurchaseDialogViewModel {
        public final String playerName;
        public final float playerMoney;
        public final String propertyName;
        public final float propertyPrice;
        public final boolean canAfford;

        public PurchaseDialogViewModel(String playerName, float playerMoney, String propertyName,
                                     float propertyPrice, boolean canAfford) {
            this.playerName = playerName;
            this.playerMoney = playerMoney;
            this.propertyName = propertyName;
            this.propertyPrice = propertyPrice;
            this.canAfford = canAfford;
        }
    }

    public static class PropertyPurchasedViewModel {
        public final String propertyName;
        public final String ownerName;
        public final float newOwnerMoney;

        public PropertyPurchasedViewModel(String propertyName, String ownerName, float newOwnerMoney) {
            this.propertyName = propertyName;
            this.ownerName = ownerName;
            this.newOwnerMoney = newOwnerMoney;
        }
    }

    public static class RentPaymentViewModel {
        public final String payerName;
        public final String ownerName;
        public final String propertyName;
        public final float rentAmount;
        public final float payerNewMoney;
        public final float ownerNewMoney;

        public RentPaymentViewModel(String payerName, String ownerName, String propertyName,
                                  float rentAmount, float payerNewMoney, float ownerNewMoney) {
            this.payerName = payerName;
            this.ownerName = ownerName;
            this.propertyName = propertyName;
            this.rentAmount = rentAmount;
            this.payerNewMoney = payerNewMoney;
            this.ownerNewMoney = ownerNewMoney;
        }
    }
}

