package main.interface_adapter.property;

import main.interface_adapter.property.PropertyViewModel.PropertyPurchasedViewModel;
import main.interface_adapter.property.PropertyViewModel.PurchaseDialogViewModel;
import main.interface_adapter.property.PropertyViewModel.RentPaymentViewModel;
import main.use_case.tiles.property.PropertyPurchaseOutputBoundary;
import main.use_case.tiles.property.PropertyPurchaseUseCase.PropertyOwnershipData;
import main.use_case.tiles.property.PropertyPurchaseUseCase.PropertyPurchaseData;
import main.use_case.tiles.property.PropertyPurchaseUseCase.PurchaseResultCallback;
import main.use_case.tiles.property.RentPaymentOutputBoundary;
import main.use_case.tiles.property.RentPaymentUseCase.RentPaymentData;

/**
 * Presenter that implements both output boundaries and creates view models for property-related UI.
 */
public class PropertyPresenter implements PropertyPurchaseOutputBoundary, RentPaymentOutputBoundary {
    private PurchaseDialogViewModel currentPurchaseDialog;
    private PropertyPurchasedViewModel currentPropertyPurchased;
    private RentPaymentViewModel currentRentPayment;
    private PurchaseResultCallback currentCallback;

    /**
     * Creates a new PropertyPresenter with no view dependency.
     */
    public PropertyPresenter() {
        // No view dependency
    }

    @Override
    public void presentPurchaseDialog(PropertyPurchaseData data, PurchaseResultCallback callback) {
        // Convert use case data to view model and store it
        this.currentPurchaseDialog = new PurchaseDialogViewModel(
                data.getPlayerName(),
                data.getPlayerMoney(),
                data.getPropertyName(),
                data.getPropertyPrice(),
                data.isCanAfford()
        );
        this.currentCallback = callback;
    }

    @Override
    public void presentPropertyPurchased(PropertyOwnershipData data) {
        // Convert use case data to view model and store it
        this.currentPropertyPurchased = new PropertyPurchasedViewModel(
                data.getPropertyName(),
                data.getOwnerName(),
                data.getNewOwnerMoney()
        );
    }

    @Override
    public void presentRentPayment(RentPaymentData data) {
        // Convert use case data to view model and store it
        this.currentRentPayment = new RentPaymentViewModel(
                data.getPayerName(),
                data.getOwnerName(),
                data.getPropertyName(),
                data.getRentAmount(),
                data.getPayerNewMoney(),
                data.getOwnerNewMoney()
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

    /**
     * Clears the current purchase dialog view model and callback.
     */
    public void clearPurchaseDialog() {
        this.currentPurchaseDialog = null;
        this.currentCallback = null;
    }

    /**
     * Clears the current property purchased view model.
     */
    public void clearPropertyPurchased() {
        this.currentPropertyPurchased = null;
    }

    /**
     * Clears the current rent payment view model.
     */
    public void clearRentPayment() {
        this.currentRentPayment = null;
    }
}
