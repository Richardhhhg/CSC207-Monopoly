package main.interface_adapter.Property;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;
import main.use_case.Property.PropertyPurchaseUseCase;

public class PropertyPurchaseController {
    private final PropertyPurchaseUseCase propertyPurchaseUseCase;
    private final PropertyPresenter presenter;

    public PropertyPurchaseController(PropertyPresenter presenter) {
        this.presenter = presenter;
        this.propertyPurchaseUseCase = new PropertyPurchaseUseCase();
    }

    public void execute(Player player, PropertyTile property) {
        propertyPurchaseUseCase.execute(player, property, presenter);
    }

    public void handleUnownedProperty(Player player, PropertyTile property) {
        execute(player, property);
    }
}
