package main.use_case.tiles.property;

import main.entity.players.Player;
import main.entity.tiles.PropertyTile;

public class RentPaymentUseCase {
    private final RentPaymentOutputBoundary outputBoundary;

    public RentPaymentUseCase(RentPaymentOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    /**
     * Executes the rent payment process.
     *
     * @param payer      the player paying rent
     * @param owner      the property owner receiving rent
     * @param property   the property for which rent is being paid
     * @param rentAmount the amount of rent to pay
     */
    public void execute(Player payer, Player owner, PropertyTile property, float rentAmount) {
        // Business logic: Perform the money transfer
        payer.deductMoney(rentAmount);
        owner.addMoney(rentAmount);

        // Create data transfer object for presenter (with updated money amounts)
        final RentPaymentData rentData = new RentPaymentData(
            payer.getName(),
            owner.getName(),
            property.getName(),
            rentAmount,
            payer.getMoney(),
            owner.getMoney()
        );

        // Send to presenter through output boundary
        outputBoundary.presentRentPayment(rentData);
    }

    // Data transfer object
    public static class RentPaymentData {
        private final String payerName;
        private final String ownerName;
        private final String propertyName;
        private final float rentAmount;
        private final float payerNewMoney;
        private final float ownerNewMoney;

        public RentPaymentData(String payerName, String ownerName, String propertyName,
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
