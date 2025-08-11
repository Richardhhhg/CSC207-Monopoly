package main.use_case.tiles.property;

import main.use_case.tiles.property.PropertyPurchaseUseCase.*;

/**
 * Output boundary interface for property purchase use case.
 * This interface defines what the use case expects from its presenter.
 */
public interface PropertyPurchaseOutputBoundary {
    void presentPurchaseDialog(PropertyPurchaseData data, PurchaseResultCallback callback);
    void presentPropertyPurchased(PropertyOwnershipData data);
}

