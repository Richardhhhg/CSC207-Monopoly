package main.use_case.tiles.property;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;

public class RentPaymentUseCase {
    private final RentPaymentOutputBoundary outputBoundary;

    public RentPaymentUseCase(RentPaymentOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    public void execute(Player payer, Player owner, PropertyTile property, float rentAmount) {
        // Business logic: Perform the money transfer
        payer.deductMoney(rentAmount);
        owner.addMoney(rentAmount);

        // Create data transfer object for presenter (with updated money amounts)
        RentPaymentData rentData = new RentPaymentData(
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
}
