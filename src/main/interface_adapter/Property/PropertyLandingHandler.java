package main.interface_adapter.Property;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;

/**
 * Interface for handling property landing events.
 * This follows the dependency inversion principle by allowing PropertyTile
 * to depend on an abstraction rather than concrete implementations.
 */
public interface PropertyLandingHandler {
    void handleUnownedProperty(Player player, PropertyTile property);
    void handleRentPayment(Player payer, Player owner, PropertyTile property, float rentAmount);
}

