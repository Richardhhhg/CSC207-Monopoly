package main.entity.tiles;

import main.use_case.Tile;
import main.use_case.Player;
import main.view.BuyPropertyPopup;

import javax.swing.*;
import java.awt.*;

/*
 * A purchasable board tile that can collect rent.
 * */
public class PropertyTile extends Tile {
    private final float price;
    private final float rent;
    private Player owner; //null if not owned

    /**
     * @param name  tile name
     * @param price purchase price
     * @param rent  base rent amount
     */
    public PropertyTile(String name, float price, float rent) {
        super(name);
        this.price = price;
        this.rent = rent;
    }

    /**
     * @return purchase price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @return base rent (before adjustment)
     */
    public float getRent() {
        return rent;
    }

    /**
     * @return true if someone owns this property
     */
    public boolean isOwned() {
        return owner != null;
    }

    /**
     * @return the owning player, or null if unowned
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * When a player lands here:
     * 1) If unowned, show buy property dialog
     * 2) If owned by someone else, charge rent and show notification
     */
    @Override
    public void onLanding(Player p) {
        if (!isOwned()) {
            // Show buy property popup
            showBuyPropertyDialog(p);
            return;
        }

        if (p != owner) {
            // Charge rent
            float finalRent = owner.adjustRent(rent);
            p.deductMoney(finalRent);
            owner.addMoney(finalRent);

            // Show rent payment notification
            showRentNotification(p, finalRent);
        }
    }

    private void showBuyPropertyDialog(Player player) {
        SwingUtilities.invokeLater(() -> {
            Frame parentFrame = getActiveFrame();
            BuyPropertyPopup.showPurchaseDialog(parentFrame, player, this,
                new BuyPropertyPopup.PurchaseResultCallback() {
                    @Override
                    public void onPurchaseCompleted(boolean success, String message) {
                        if (success) {
                            // Trigger board repaint to show ownership change
                            repaintBoard();
                        }
                    }
                });
        });
    }

    private void showRentNotification(Player payer, float rentAmount) {
        SwingUtilities.invokeLater(() -> {
            String message = payer.getName() + " paid $" + (int)rentAmount +
                           " rent to " + owner.getName() + " for landing on " + this.getName();

            JOptionPane.showMessageDialog(
                getActiveFrame(),
                message,
                "Rent Payment",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
    }

    private Frame getActiveFrame() {
        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            if (frame.isVisible() && frame.isActive()) {
                return frame;
            }
        }
        // Fallback to first visible frame
        for (Frame frame : frames) {
            if (frame.isVisible()) {
                return frame;
            }
        }
        return null;
    }

    private void repaintBoard() {
        // Find and repaint the board component
        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            if (frame.isVisible()) {
                frame.repaint();
            }
        }
    }

    /**
     * Sets or clears ownership.
     *
     * @param owned ignored; ownership is determined by non-null owner
     * @param owner the new owner, or null to clear
     */
    public void setOwned(boolean owned, Player owner) {
        this.owner = owner;
    }

    public boolean attemptPurchase(Player player) {
        if (player.getMoney() <= price) {
            return false; // Cannot purchase if already owned or insufficient funds
        }
        player.buyProperty(this);
        return true; // Purchase successful
    }
}
