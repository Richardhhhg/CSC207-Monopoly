package main.interface_adapter.Property;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;
import main.use_case.Property.RentPaymentUseCase;

public class RentPaymentController {
    private final RentPaymentUseCase rentPaymentUseCase;
    private final PropertyPresenter presenter;

    public RentPaymentController(PropertyPresenter presenter) {
        this.presenter = presenter;
        this.rentPaymentUseCase = new RentPaymentUseCase();
    }

    public void execute(Player payer, Player owner, PropertyTile property, float rentAmount) {
        rentPaymentUseCase.execute(payer, owner, property, rentAmount, presenter);
    }

    public void handleRentPayment(Player payer, Player owner, PropertyTile property, float rentAmount) {
        execute(payer, owner, property, rentAmount);
    }
}
