package main.interface_adapter.Property;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;
import main.view.BuyPropertyPopup;
import main.view.PlayerStatsView;
import main.view.TileView;
import main.view.BoardView;
import main.use_case.Property.PropertyLandingOutputBoundary;
import main.use_case.Property.PropertyLandingUseCase.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Presenter that implements the output boundary and handles property-related UI presentation.
 * Receives data from use cases and formats it for the view.
 */
public class PropertyPresenter implements PropertyLandingOutputBoundary {
    private final JPanel boardView;
    private final PlayerStatsView statsView;
    private final List<Player> players;

    public PropertyPresenter(JPanel boardView, PlayerStatsView statsView, List<Player> players) {
        this.boardView = boardView;
        this.statsView = statsView;
        this.players = players;
    }

    @Override
    public void presentPurchaseDialog(PropertyPurchaseData data, PurchaseResultCallback callback) {
        // Convert use case data to view model
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(boardView);

        // Find the actual player and property objects for the popup
        Player player = findPlayerByName(data.playerName);
        PropertyTile property = findPropertyByName(data.propertyName);

        if (player != null && property != null) {
            BuyPropertyPopup.showPurchaseDialog(parentFrame, player, property,
                    (success, message) -> callback.onResult(success));
        }
    }

    @Override
    public void presentPropertyPurchased(PropertyOwnershipData data) {
        // Update UI after property purchase
        statsView.updatePlayers(players);
        boardView.repaint(); // Trigger board repaint to show ownership change
    }

    @Override
    public void presentRentPayment(RentPaymentData data) {
        // Show rent payment notification
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(boardView);
        showRentNotification(parentFrame, data);

        // Update UI after rent payment
        statsView.updatePlayers(players);
        boardView.repaint();
    }

    // Helper methods for finding entities (needed for legacy popup interface)
    private Player findPlayerByName(String name) {
        return players.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private PropertyTile findPropertyByName(String name) {
        // Get the game instance from BoardView to access tiles
        if (boardView instanceof BoardView) {
            BoardView bv = (BoardView) boardView;
            List<PropertyTile> tiles = bv.getGame().getTiles();
            return tiles.stream()
                    .filter(tile -> tile.getName().equals(name))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    /**
     * Shows rent payment notification dialog using view model data
     */
    private void showRentNotification(Frame parent, RentPaymentData data) {
        SwingUtilities.invokeLater(() -> {
            String message = data.payerName + " paid $" + (int) data.rentAmount +
                    " rent to " + data.ownerName + " for landing on " + data.propertyName;

            JOptionPane.showMessageDialog(
                    parent,
                    message,
                    "Rent Payment",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
    }

    // Legacy methods for backward compatibility with existing code
    public void updatePropertyOwnership(TileView tileView, PropertyTile property) {
        if (property.isOwned()) {
            tileView.setOwnerText(property.getOwner().getName());
        } else {
            tileView.setOwnerText("");
        }
    }

    /**
     * Shows the property purchase dialog
     */
    public void showPurchaseDialog(Player player, PropertyTile property,
                                   BuyPropertyPopup.PurchaseResultCallback callback) {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(boardView);
        BuyPropertyPopup.showPurchaseDialog(parentFrame, player, property, callback);
    }

    /**
     * Shows rent payment notification
     */
    public void showRentPayment(Player payer, Player owner, PropertyTile property, float rentAmount) {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(boardView);
        showRentNotification(parentFrame, payer, owner, property, rentAmount);
    }

    /**
     * Updates UI after a property purchase
     */
    public void onPropertyPurchased(Player player, PropertyTile property) {
        statsView.updatePlayers(players);
        boardView.repaint(); // Trigger board repaint to show ownership change
    }

    /**
     * Updates UI after rent payment
     */
    public void onRentPaid(Player payer, Player owner, float rentAmount) {
        statsView.updatePlayers(players);
        boardView.repaint();
    }

    /**
     * Shows rent payment notification dialog
     */
    private void showRentNotification(Frame parent, Player payer, Player owner,
                                      PropertyTile property, float rentAmount) {
        SwingUtilities.invokeLater(() -> {
            String message = payer.getName() + " paid $" + (int) rentAmount +
                    " rent to " + owner.getName() + " for landing on " + property.getName();

            JOptionPane.showMessageDialog(
                    parent,
                    message,
                    "Rent Payment",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
    }
}
