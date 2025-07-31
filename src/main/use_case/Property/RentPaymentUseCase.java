package main.use_case.Property;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;
import main.interface_adapter.Property.PropertyPresenter;

public class RentPaymentUseCase {

    public void execute(Player payer, Player owner, PropertyTile property, float rentAmount, PropertyPresenter presenter) {
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

        // Send to presenter directly
        presenter.presentRentPayment(rentData);
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
