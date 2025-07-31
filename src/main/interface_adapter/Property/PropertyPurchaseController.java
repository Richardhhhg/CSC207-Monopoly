package main.interface_adapter.Property;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;
import main.use_case.Property.PropertyPurchaseUseCase;

public class PropertyPurchaseController {
    private final PropertyPurchaseUseCase propertyPurchaseUseCase;

    public PropertyPurchaseController(PropertyPresenter presenter) {
        // Inject presenter as output boundary into use case
        this.propertyPurchaseUseCase = new PropertyPurchaseUseCase(presenter);
    }

    public void execute(Player player, PropertyTile property) {
        propertyPurchaseUseCase.execute(player, property);
    }

    public void handleUnownedProperty(Player player, PropertyTile property) {
        execute(player, property);
    }
}
