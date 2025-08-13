package main.use_case.tiles.property;

import main.use_case.tiles.property.PropertyPurchaseUseCase.PropertyOwnershipData;
import main.use_case.tiles.property.PropertyPurchaseUseCase.PropertyPurchaseData;
import main.use_case.tiles.property.PropertyPurchaseUseCase.PurchaseResultCallback;

/**
 * Output boundary interface for property purchase use case.
 * This interface defines what the use case expects from its presenter.
 */
public interface PropertyPurchaseOutputBoundary {
    /**
     * Presents the purchase dialog to the user.
     *
     * @param data     the property purchase data to display
     * @param callback callback for handling the user's purchase decision
     */
    void presentPurchaseDialog(PropertyPurchaseData data, PurchaseResultCallback callback);

    /**
     * Presents the result of a successful property purchase.
     *
     * @param data the property ownership data after purchase
     */
    void presentPropertyPurchased(PropertyOwnershipData data);
}
