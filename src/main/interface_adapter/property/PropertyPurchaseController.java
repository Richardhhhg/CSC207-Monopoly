package main.interface_adapter.property;

import java.awt.Component;
import java.awt.Frame;

import javax.swing.SwingUtilities;

import main.entity.players.AbstractPlayer;
import main.entity.tiles.PropertyTile;
import main.use_case.tiles.property.PropertyPurchaseUseCase;
import main.view.BuyPropertyPopup;

/**
 * Controller for handling property purchases.
 */
public class PropertyPurchaseController {
    private final PropertyPurchaseUseCase propertyPurchaseUseCase;

    /**
     * Constructs a PropertyPurchaseController.
     *
     * @param presenter the property presenter
     */
    public PropertyPurchaseController(PropertyPresenter presenter) {
        // Inject presenter as output boundary into use case
        this.propertyPurchaseUseCase = new PropertyPurchaseUseCase(presenter);
    }

    /**
     * Executes the property purchase use case.
     *
     * @param player   the player attempting to purchase
     * @param property the property being purchased
     */
    public void execute(AbstractPlayer player, PropertyTile property) {
        propertyPurchaseUseCase.execute(player, property);
    }

    /**
     * Handles when a player lands on an unowned property.
     *
     * @param player   the player who landed on the property
     * @param property the unowned property
     */
    public void handleUnownedProperty(AbstractPlayer player, PropertyTile property) {
        execute(player, property);
    }

    /**
     * Shows the purchase dialog for a property.
     *
     * @param viewModel       the view model containing display data
     * @param callback        callback for purchase results
     * @param player          the player attempting to purchase
     * @param property        the property being purchased
     * @param parentComponent the parent component for dialog positioning
     */
    public void showPurchaseDialog(PropertyViewModel.PurchaseDialogViewModel viewModel,
                                   PropertyPurchaseUseCase.PurchaseResultCallback callback,
                                   AbstractPlayer player, PropertyTile property, Component parentComponent) {
        final Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(parentComponent);

        if (player != null && property != null) {
            BuyPropertyPopup.showPurchaseDialog(parentFrame, player, property,
                    (success, message) -> callback.onResult(success));
        }
    }
}
