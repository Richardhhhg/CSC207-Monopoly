package main.interface_adapter.property;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;
import main.use_case.tiles.property.PropertyPurchaseUseCase;
import main.view.BuyPropertyPopup;

import javax.swing.*;
import java.awt.*;

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

    public void showPurchaseDialog(PropertyViewModel.PurchaseDialogViewModel viewModel,
                                   PropertyPurchaseUseCase.PurchaseResultCallback callback,
                                   Player player, PropertyTile property, Component parentComponent) {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(parentComponent);

        if (player != null && property != null) {
            BuyPropertyPopup.showPurchaseDialog(parentFrame, player, property,
                    (success, message) -> callback.onResult(success));
        }
    }
}
