package main.interface_adapter.property;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;
import main.use_case.tiles.property.RentPaymentUseCase;

import javax.swing.*;
import java.awt.*;

public class RentPaymentController {
    private final RentPaymentUseCase rentPaymentUseCase;

    public RentPaymentController(PropertyPresenter presenter) {
        // Inject presenter as output boundary into use case
        this.rentPaymentUseCase = new RentPaymentUseCase(presenter);
    }

    public void execute(Player payer, Player owner, PropertyTile property, float rentAmount) {
        rentPaymentUseCase.execute(payer, owner, property, rentAmount);
    }

    public void handleRentPayment(Player payer, Player owner, PropertyTile property, float rentAmount) {
        execute(payer, owner, property, rentAmount);
    }

    public void showRentPaymentNotification(PropertyViewModel.RentPaymentViewModel viewModel, Component parentComponent) {
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(parentComponent);
        showRentNotification(parent, viewModel);
    }

    private void showRentNotification(Frame parent, PropertyViewModel.RentPaymentViewModel viewModel) {
        SwingUtilities.invokeLater(() -> {
            String message = viewModel.payerName + " paid $" + (int) viewModel.rentAmount +
                    " rent to " + viewModel.ownerName + " for landing on " + viewModel.propertyName;

            JOptionPane.showMessageDialog(
                    parent,
                    message,
                    "Rent Payment",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
    }
}
