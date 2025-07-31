package main.use_case.Property;

import main.use_case.Property.PropertyLandingUseCase.*;

/**
 * Output boundary interface for property landing use case.
 * Presenters implement this to receive data from the use case.
 */
public interface PropertyLandingOutputBoundary {
    void presentPurchaseDialog(PropertyPurchaseData data, PurchaseResultCallback callback);
    void presentPropertyPurchased(PropertyOwnershipData data);
    void presentRentPayment(RentPaymentData data);
}

