package main.interface_adapter.property;

import java.awt.Component;
import java.awt.Frame;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import main.entity.players.AbstractPlayer;
import main.entity.tiles.PropertyTile;
import main.use_case.tiles.property.RentPaymentUseCase;

/**
 * Controller for handling rent payments between players and property owners.
 */
public class RentPaymentController {
    private final RentPaymentUseCase rentPaymentUseCase;

    /**
     * Constructs a RentPaymentController with the given presenter.
     *
     * @param presenter the presenter to be injected into the use case
     */
    public RentPaymentController(PropertyPresenter presenter) {
        // Inject presenter as output boundary into use case
        this.rentPaymentUseCase = new RentPaymentUseCase(presenter);
    }

    /**
     * Executes the rent payment use case.
     *
     * @param payer      the player paying rent
     * @param owner      the property owner receiving rent
     * @param property   the property for which rent is being paid
     * @param rentAmount the amount of rent to pay
     */
    public void execute(AbstractPlayer payer, AbstractPlayer owner, PropertyTile property, float rentAmount) {
        rentPaymentUseCase.execute(payer, owner, property, rentAmount);
    }

    /**
     * Handles rent payment when a player lands on an owned property.
     *
     * @param payer      the player paying rent
     * @param owner      the property owner receiving rent
     * @param property   the property for which rent is being paid
     * @param rentAmount the amount of rent to pay
     */
    public void handleRentPayment(AbstractPlayer payer, AbstractPlayer owner, PropertyTile property, float rentAmount) {
        execute(payer, owner, property, rentAmount);
    }

    /**
     * Shows a rent payment notification dialog.
     *
     * @param viewModel       the view model containing rent payment data
     * @param parentComponent the parent component for dialog positioning
     */
    public void showRentPaymentNotification(PropertyViewModel.RentPaymentViewModel viewModel,
                                            Component parentComponent) {
        final Frame parent = (Frame) SwingUtilities.getWindowAncestor(parentComponent);
        showRentNotification(parent, viewModel);
    }

    private void showRentNotification(Frame parent, PropertyViewModel.RentPaymentViewModel viewModel) {
        SwingUtilities.invokeLater(() -> {
            displayRentMessage(parent, viewModel);
        });
    }

    private void displayRentMessage(Frame parent, PropertyViewModel.RentPaymentViewModel viewModel) {
        final String message = viewModel.getPayerName() + " paid $" + (int) viewModel.getRentAmount()
                + " rent to " + viewModel.getOwnerName() + " for landing on "
                + viewModel.getPropertyName();

        JOptionPane.showMessageDialog(
                parent,
                message,
                "Rent Payment",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
