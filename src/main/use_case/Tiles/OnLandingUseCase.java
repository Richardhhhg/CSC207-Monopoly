package main.use_case.Tiles;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;
import main.use_case.Tile;
import main.use_case.Tiles.Property.PropertyPurchaseUseCase;
import main.use_case.Tiles.Property.RentPaymentUseCase;

public class OnLandingUseCase {
    private final PropertyPurchaseUseCase propertyPurchaseUseCase;
    private final RentPaymentUseCase rentPaymentUseCase;

    public OnLandingUseCase(PropertyPurchaseUseCase propertyPurchaseUseCase,
                           RentPaymentUseCase rentPaymentUseCase) {
        this.propertyPurchaseUseCase = propertyPurchaseUseCase;
        this.rentPaymentUseCase = rentPaymentUseCase;
    }

    public void execute(Player player, Tile tile) {
        if (tile instanceof PropertyTile) {
            PropertyTile property = (PropertyTile) tile;
            handlePropertyLanding(player, property);
        }
        // Future: Add other tile types (RailroadTile, UtilityTile, etc.)
    }

    private void handlePropertyLanding(Player player, PropertyTile property) {
        if (!property.isOwned()) {
            // Property is unowned - trigger purchase flow
            propertyPurchaseUseCase.execute(player, property);
        } else if (player != property.getOwner()) {
            // Property is owned by someone else - calculate and pay rent
            float rent = property.calculateRent();

            // Perform the transaction (business logic)
            player.deductMoney(rent);
            property.getOwner().addMoney(rent);

            // Trigger rent payment notification
            rentPaymentUseCase.execute(player, property.getOwner(), property, rent);
        }
        // If player owns the property, nothing happens
    }
}
