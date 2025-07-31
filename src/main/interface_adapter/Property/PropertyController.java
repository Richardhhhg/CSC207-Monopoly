package main.interface_adapter.Property;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;
import main.use_case.Property.PropertyLandingUseCase;

/**
 * Controller that handles property-related input and delegates to use cases.
 * Controllers should only coordinate between input and use cases, not presenters.
 */
public class PropertyController implements PropertyLandingHandler {
    private final PropertyLandingUseCase propertyLandingUseCase;

    public PropertyController(PropertyLandingUseCase propertyLandingUseCase) {
        this.propertyLandingUseCase = propertyLandingUseCase;
    }

    @Override
    public void handleUnownedProperty(Player player, PropertyTile property) {
        // Delegate to use case - controller doesn't handle business logic
        propertyLandingUseCase.handleUnownedProperty(player, property);
    }

    @Override
    public void handleRentPayment(Player payer, Player owner, PropertyTile property, float rentAmount) {
        // Delegate to use case - controller doesn't handle business logic
        propertyLandingUseCase.handleRentPayment(payer, owner, property, rentAmount);
    }
}
