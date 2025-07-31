package main.interface_adapter.Property;

import main.use_case.Property.PropertyLandingOutputBoundary;
import main.use_case.Property.PropertyLandingUseCase.*;
import main.interface_adapter.Property.PropertyViewModel.*;

/**
 * Presenter that implements the output boundary and creates view models for property-related UI.
 * No longer depends on view - follows Clean Architecture dependency rule.
 */
public class PropertyPresenter implements PropertyLandingOutputBoundary {
    private PurchaseDialogViewModel currentPurchaseDialog;
    private PropertyPurchasedViewModel currentPropertyPurchased;
    private RentPaymentViewModel currentRentPayment;
    private PurchaseResultCallback currentCallback;

    public PropertyPresenter() {
        // No view dependency
    }

    @Override
    public void presentPurchaseDialog(PropertyPurchaseData data, PurchaseResultCallback callback) {
        // Convert use case data to view model and store it
        this.currentPurchaseDialog = new PurchaseDialogViewModel(
            data.playerName,
            data.playerMoney,
            data.propertyName,
            data.propertyPrice,
            data.canAfford
        );
        this.currentCallback = callback;
    }

    @Override
    public void presentPropertyPurchased(PropertyOwnershipData data) {
        // Convert use case data to view model and store it
        this.currentPropertyPurchased = new PropertyPurchasedViewModel(
            data.propertyName,
            data.ownerName,
            data.newOwnerMoney
        );
    }

    @Override
    public void presentRentPayment(RentPaymentData data) {
        // Convert use case data to view model and store it
        this.currentRentPayment = new RentPaymentViewModel(
            data.payerName,
            data.ownerName,
            data.propertyName,
            data.rentAmount,
            data.payerNewMoney,
            data.ownerNewMoney
        );
    }

    // View can call these methods to get the view models
    public PurchaseDialogViewModel getPurchaseDialogViewModel() {
        return currentPurchaseDialog;
    }

    public PropertyPurchasedViewModel getPropertyPurchasedViewModel() {
        return currentPropertyPurchased;
    }

    public RentPaymentViewModel getRentPaymentViewModel() {
        return currentRentPayment;
    }

    public PurchaseResultCallback getPurchaseCallback() {
        return currentCallback;
    }

    // Clear methods for cleanup
    public void clearPurchaseDialog() {
        this.currentPurchaseDialog = null;
        this.currentCallback = null;
    }

    public void clearPropertyPurchased() {
        this.currentPropertyPurchased = null;
    }

    public void clearRentPayment() {
        this.currentRentPayment = null;
    }
}
