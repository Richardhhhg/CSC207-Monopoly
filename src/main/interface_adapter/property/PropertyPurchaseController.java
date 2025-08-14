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
     * @param abstractPlayer   the player attempting to purchase
     * @param property the property being purchased
     */
    public void execute(AbstractPlayer abstractPlayer, PropertyTile property) {
        propertyPurchaseUseCase.execute(abstractPlayer, property);
    }

    /**
     * Handles when a player lands on an unowned property.
     *
     * @param abstractPlayer   the player who landed on the property
     * @param property the unowned property
     */
    public void handleUnownedProperty(AbstractPlayer abstractPlayer, PropertyTile property) {
        execute(abstractPlayer, property);
    }

    /**
     * Shows the purchase dialog for a property.
     *
     * @param viewModel       the view model containing display data
     * @param callback        callback for purchase results
     * @param abstractPlayer          the player attempting to purchase
     * @param property        the property being purchased
     * @param parentComponent the parent component for dialog positioning
     */
    public void showPurchaseDialog(PropertyViewModel.PurchaseDialogViewModel viewModel,
                                   PropertyPurchaseUseCase.PurchaseResultCallback callback,
                                   AbstractPlayer abstractPlayer, PropertyTile property, Component parentComponent) {
        final Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(parentComponent);

        if (abstractPlayer != null && property != null) {
            BuyPropertyPopup.showPurchaseDialog(parentFrame, abstractPlayer, property,
                    (success, message) -> callback.onResult(success));
        }
    }
}
