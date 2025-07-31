package main.interface_adapter.Property;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;
import main.view.BuyPropertyPopup;
import main.view.PlayerStatsView;
import main.view.TileView;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Presenter that handles property-related UI presentation logic.
 * Acts as an intermediary between the controller and view components.
 * Focuses only on property-specific concerns.
 */
public class PropertyPresenter {
    private final JPanel boardView;
    private final PlayerStatsView statsView;
    private final List<Player> players;

    public PropertyPresenter(JPanel boardView, PlayerStatsView statsView, List<Player> players) {
        this.boardView = boardView;
        this.statsView = statsView;
        this.players = players;
    }

    /**
     * Update ownership display for a property tile
     */
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
            String message = payer.getName() + " paid $" + (int)rentAmount +
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
