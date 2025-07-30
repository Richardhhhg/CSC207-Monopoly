package main.view;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;

import javax.swing.*;
import java.awt.*;

/**
 * Modal dialog prompting the user to buy an unowned PropertyTile.
 * Follows clean architecture by handling all purchase UI logic and validation.
 */
public class BuyPropertyPopup extends JDialog {

    /**
     * Callback interface for communicating purchase results back to the caller
     */
    public interface PurchaseResultCallback {
        void onPurchaseCompleted(boolean success, String message);
    }

    private JLabel infoLabel;
    private JButton yesButton;
    private JButton noButton;
    private final Player player;
    private final PropertyTile property;
    private final PurchaseResultCallback callback;

    /**
     * @param owner      parent frame for centering
     * @param player     the current player attempting purchase
     * @param property   the PropertyTile being landed on
     * @param callback   callback to notify about purchase results
     */
    public BuyPropertyPopup(
            Frame owner,
            Player player,
            PropertyTile property,
            PurchaseResultCallback callback
    ) {
        super(owner, "Buy Property", true);
        this.player = player;
        this.property = property;
        this.callback = callback;

        initializeUI();
        setupEventHandlers();

        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    private void initializeUI() {
        setLayout(new BorderLayout(15, 15));

        // Add padding around the entire dialog
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Main message with better spacing
        JLabel messageLabel = new JLabel(
                "<html><center>" +
                        "<div style='width: 280px; text-align: center;'>" +
                        "Would you like to buy<br><br>" +
                        "<b style='font-size: 16px;'>" + property.getName() + "</b><br><br>" +
                        "for <b style='font-size: 14px; color: #006600;'>$" + (int)property.getPrice() + "</b>?<br><br>" +
                        "<hr><br>" +
                        "Your current balance: <b>$" + (int)player.getMoney() + "</b>" +
                        "</div>" +
                        "</center></html>",
                SwingConstants.CENTER
        );
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setPreferredSize(new Dimension(320, 180));
        mainPanel.add(messageLabel, BorderLayout.NORTH);

        // Status label for feedback - make it very visible
        infoLabel = new JLabel(" ");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoLabel.setPreferredSize(new Dimension(320, 60));
        infoLabel.setOpaque(true);
        infoLabel.setBackground(Color.WHITE);
        infoLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        mainPanel.add(infoLabel, BorderLayout.CENTER);

        // Button panel with better spacing
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        yesButton = new JButton("Yes");
        noButton = new JButton("No");

        // Make buttons larger and more appealing
        yesButton.setPreferredSize(new Dimension(100, 40));
        noButton.setPreferredSize(new Dimension(100, 40));
        yesButton.setFont(new Font("Arial", Font.BOLD, 16));
        noButton.setFont(new Font("Arial", Font.BOLD, 16));

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // Set minimum size for the dialog
        setMinimumSize(new Dimension(400, 370));
        setPreferredSize(new Dimension(400, 370));
    }

    private void setupEventHandlers() {
        yesButton.addActionListener(e -> handlePurchaseAttempt());

        noButton.addActionListener(e -> {
            if (callback != null) {
                callback.onPurchaseCompleted(false, "Purchase declined by player");
            }
            dispose();
        });
    }

    private void handlePurchaseAttempt() {
        // Validate purchase conditions

        if (player.getMoney() < property.getPrice()) {
            float needed = property.getPrice() - player.getMoney();
            showMessage("<html><center>Insufficient funds!<br>You need $" + (int)needed + " more.</center></html>", Color.RED, true);
            return;
        }

        // Attempt the purchase
        boolean success = property.attemptPurchase(player);
        if (success) {
            showMessage("Purchase successful!", new Color(0, 120, 0), false);
            if (callback != null) {
                callback.onPurchaseCompleted(true,
                        player.getName() + " purchased " + property.getName() + " for $" + (int)property.getPrice());
            }
        } else {
            showMessage("Purchase failed!", Color.RED, true);
            if (callback != null) {
                callback.onPurchaseCompleted(false, "Purchase failed due to unknown error");
            }
        }
    }

    private void showMessage(String message, Color textColor, boolean isError) {
        // Set the message and color
        infoLabel.setText(message);
        infoLabel.setForeground(textColor);

        // Set background color based on message type
        if (isError) {
            infoLabel.setBackground(new Color(255, 230, 230)); // Light red
            infoLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.RED, 2),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
        } else {
            infoLabel.setBackground(new Color(230, 255, 230)); // Light green
            infoLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 120, 0), 2),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
        }

        // Disable buttons
        yesButton.setEnabled(false);
        noButton.setEnabled(false);

        // Force immediate update - use multiple approaches to ensure visibility
        SwingUtilities.invokeLater(() -> {
            infoLabel.revalidate();
            infoLabel.repaint();
            this.revalidate();
            this.repaint();
        });

        // Schedule dialog close
        int delay = isError ? 4000 : 2000; // Error messages stay longer
        Timer closeTimer = new Timer(delay, e -> dispose());
        closeTimer.setRepeats(false);
        closeTimer.start();
    }

    /**
     * Static factory method for easier usage
     */
    public static void showPurchaseDialog(Frame parent, Player player, PropertyTile property,
                                          PurchaseResultCallback callback) {
        SwingUtilities.invokeLater(() ->
                new BuyPropertyPopup(parent, player, property, callback));
    }
}
