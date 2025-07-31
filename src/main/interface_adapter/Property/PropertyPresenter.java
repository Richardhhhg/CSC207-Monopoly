package main.interface_adapter.Property;

import main.use_case.Property.PropertyLandingOutputBoundary;
import main.use_case.Property.PropertyLandingUseCase.*;
import main.interface_adapter.Property.PropertyViewModel.*;
import main.view.BoardView;

/**
 * Presenter that implements the output boundary and creates view models for property-related UI.
 * Directly communicates with BoardView without unnecessary interface abstraction.
 */
public class PropertyPresenter implements PropertyLandingOutputBoundary {
    private final BoardView boardView;

    public PropertyPresenter(BoardView boardView) {
        this.boardView = boardView;
    }

    @Override
    public void presentPurchaseDialog(PropertyPurchaseData data, PurchaseResultCallback callback) {
        // Convert use case data to view model
        PurchaseDialogViewModel viewModel = new PurchaseDialogViewModel(
            data.playerName,
            data.playerMoney,
            data.propertyName,
            data.propertyPrice,
            data.canAfford
        );

        // Send view model to board view
        boardView.showPurchaseDialog(viewModel, callback);
    }

    @Override
    public void presentPropertyPurchased(PropertyOwnershipData data) {
        // Convert use case data to view model
        PropertyPurchasedViewModel viewModel = new PropertyPurchasedViewModel(
            data.propertyName,
            data.ownerName,
            data.newOwnerMoney
        );

        // Send view model to board view
        boardView.updateAfterPropertyPurchased(viewModel);
    }

    @Override
    public void presentRentPayment(RentPaymentData data) {
        // Convert use case data to view model
        RentPaymentViewModel viewModel = new RentPaymentViewModel(
            data.payerName,
            data.ownerName,
            data.propertyName,
            data.rentAmount,
            data.payerNewMoney,
            data.ownerNewMoney
        );

        // Send view model to board view
        boardView.showRentPaymentNotification(viewModel);
    }
}
