package main.interface_adapter.Property;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;

/**
 * Controller that handles property-related business logic and coordinates
 * between the model (PropertyTile) and presenter (PropertyPresenter)
 */
public class PropertyController implements PropertyTile.PropertyLandingHandler {
    private final PropertyPresenter presenter;

    public PropertyController(PropertyPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void handleUnownedProperty(Player player, PropertyTile property) {
        // Business logic: Check if property can be purchased
        if (property.isOwned()) {
            return; // Should not happen, but safety check
        }

        // Delegate to presenter for UI handling
        presenter.showPurchaseDialog(player, property, (success, message) -> {
            if (success) {
                // Notify presenter to update UI
                presenter.onPropertyPurchased(player, property);
            }
        });
    }

    @Override
    public void handleRentPayment(Player payer, Player owner, PropertyTile property, float rentAmount) {
        // Business logic is already handled in PropertyTile.onLanding()
        // Just notify presenter to show the result
        presenter.showRentPayment(payer, owner, property, rentAmount);
        presenter.onRentPaid(payer, owner, rentAmount);
    }
}
