package main.interface_adapter.Property;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;
import main.use_case.Property.RentPaymentUseCase;

public class RentPaymentController {
    private final RentPaymentUseCase rentPaymentUseCase;

    public RentPaymentController(PropertyPresenter presenter) {
        // Inject presenter as output boundary into use case
        this.rentPaymentUseCase = new RentPaymentUseCase(presenter);
    }

    public void execute(Player payer, Player owner, PropertyTile property, float rentAmount) {
        rentPaymentUseCase.execute(payer, owner, property, rentAmount);
    }

    public void handleRentPayment(Player payer, Player owner, PropertyTile property, float rentAmount) {
        execute(payer, owner, property, rentAmount);
    }
}
