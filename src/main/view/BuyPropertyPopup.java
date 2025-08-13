package main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import main.entity.players.Player;
import main.entity.tiles.PropertyTile;

/**
 * Modal dialog prompting the user to buy an unowned PropertyTile.
 * Follows clean architecture by handling only UI concerns.
 */
public class BuyPropertyPopup extends JDialog {

    private static final String FONT_NAME = "Arial";
    private static final int SMALL_PADDING = 10;
    private static final int MEDIUM_PADDING = 15;
    private static final int LARGE_PADDING = 20;
    private static final int MESSAGE_WIDTH = 280;
    private static final int STANDARD_FONT_SIZE = 14;
    private static final int LARGE_FONT_SIZE = 16;
    private static final int DIALOG_WIDTH = 320;
    private static final int MESSAGE_HEIGHT = 180;
    private static final int INFO_HEIGHT = 80;
    private static final int BUTTON_SIZE = 100;
    private static final int BUTTON_HEIGHT = 40;
    private static final int MIN_DIALOG_SIZE = 400;
    private static final int MIN_DIALOG_HEIGHT = 390;
    private static final int SUCCESS_COLOR_GREEN = 120;
    private static final int RED = 255;
    private static final int LIGHT_COLOR_VALUE = 230;
    private static final int BORDER_WIDTH = 2;
    private static final int ERROR_DELAY = 4000;
    private static final int SUCCESS_DELAY = 2000;

    private JLabel infoLabel;
    private JButton yesButton;
    private JButton noButton;
    private final Player player;
    private final PropertyTile property;
    private final PurchaseResultCallback callback;

    /**
     * Constructor for creating a BuyPropertyPopup dialog.
     *
     * @param owner      parent frame for centering
     * @param player     the current player attempting purchase
     * @param property   the PropertyTile being purchased
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

        initializeUserInterface();
        setupEventHandlers();

        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    /**
     * Initializes the user interface components.
     */
    private void initializeUserInterface() {
        setLayout(new BorderLayout(MEDIUM_PADDING, MEDIUM_PADDING));

        final JPanel mainPanel = new JPanel(new BorderLayout(SMALL_PADDING, SMALL_PADDING));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
                LARGE_PADDING, LARGE_PADDING, LARGE_PADDING, LARGE_PADDING));

        final JLabel messageLabel = new JLabel(
                "<html><center>"
                        + "<div style='width: " + MESSAGE_WIDTH + "px; text-align: center;'>"
                        + "Would you like to buy<br><br>"
                        + "<b style='font-size: " + LARGE_FONT_SIZE + "px;'>" + property.getName() + "</b><br><br>"
                        + "for <b style='font-size: " + STANDARD_FONT_SIZE + "px; color: #006600;'>$"
                        + property.getPrice() + "</b>?<br><br>"
                        + "<hr><br>"
                        + "Your current balance: <b>$" + (int) player.getMoney() + "</b>"
                        + "</div>"
                        + "</center></html>",
                SwingConstants.CENTER
        );
        messageLabel.setFont(new Font(FONT_NAME, Font.PLAIN, STANDARD_FONT_SIZE));
        messageLabel.setPreferredSize(new Dimension(DIALOG_WIDTH, MESSAGE_HEIGHT));
        mainPanel.add(messageLabel, BorderLayout.NORTH);

        infoLabel = new JLabel(" ");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(new Font(FONT_NAME, Font.BOLD, LARGE_FONT_SIZE));
        infoLabel.setPreferredSize(new Dimension(DIALOG_WIDTH, INFO_HEIGHT));
        infoLabel.setOpaque(true);
        infoLabel.setBackground(Color.WHITE);
        infoLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, BORDER_WIDTH),
                BorderFactory.createEmptyBorder(SMALL_PADDING, SMALL_PADDING, SMALL_PADDING, SMALL_PADDING)
        ));
        mainPanel.add(infoLabel, BorderLayout.CENTER);

        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, LARGE_PADDING, MEDIUM_PADDING));
        yesButton = new JButton("Yes");
        noButton = new JButton("No");

        yesButton.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_HEIGHT));
        noButton.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_HEIGHT));
        yesButton.setFont(new Font(FONT_NAME, Font.BOLD, LARGE_FONT_SIZE));
        noButton.setFont(new Font(FONT_NAME, Font.BOLD, LARGE_FONT_SIZE));

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        setMinimumSize(new Dimension(MIN_DIALOG_SIZE, MIN_DIALOG_HEIGHT));
        setPreferredSize(new Dimension(MIN_DIALOG_SIZE, MIN_DIALOG_HEIGHT));
    }

    private void setupEventHandlers() {
        yesButton.addActionListener(actionEvent -> handlePurchaseAttempt());

        noButton.addActionListener(actionEvent -> {
            if (callback != null) {
                callback.onPurchaseCompleted(false, "Purchase declined by player");
            }
            dispose();
        });
    }

    private void handlePurchaseAttempt() {
        if (player.getMoney() < property.getPrice()) {
            final float needed = property.getPrice() - player.getMoney();
            showMessage("<html><center>Insufficient funds!<br>You need $"
                    + (int) needed + " more.</center></html>", Color.RED, true);
        }
        else {
            final boolean success = property.attemptPurchase(player);
            if (success) {
                showMessage("Purchase successful!", new Color(0, SUCCESS_COLOR_GREEN, 0), false);
                if (callback != null) {
                    callback.onPurchaseCompleted(true,
                            player.getName() + " purchased " + property.getName() + " for $"
                                    + property.getPrice());
                }
            }
            else {
                showMessage("Purchase failed!", Color.RED, true);
                if (callback != null) {
                    callback.onPurchaseCompleted(false, "Purchase failed due to unknown error");
                }
            }
        }
    }

    private void showMessage(String message, Color textColor, boolean isError) {
        infoLabel.setText(message);
        infoLabel.setForeground(textColor);

        if (isError) {
            infoLabel.setBackground(new Color(RED, LIGHT_COLOR_VALUE, LIGHT_COLOR_VALUE));
            infoLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.RED, BORDER_WIDTH),
                    BorderFactory.createEmptyBorder(SMALL_PADDING, SMALL_PADDING, SMALL_PADDING, SMALL_PADDING)
            ));
        }
        else {
            infoLabel.setBackground(new Color(LIGHT_COLOR_VALUE, RED, LIGHT_COLOR_VALUE));
            infoLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, SUCCESS_COLOR_GREEN, 0), BORDER_WIDTH),
                    BorderFactory.createEmptyBorder(SMALL_PADDING, SMALL_PADDING, SMALL_PADDING, SMALL_PADDING)
            ));
        }

        yesButton.setEnabled(false);
        noButton.setEnabled(false);

        SwingUtilities.invokeLater(() -> {
            infoLabel.revalidate();
            infoLabel.repaint();
            this.revalidate();
            this.repaint();
        });

        final int delay;
        if (isError) {
            delay = ERROR_DELAY;
        }
        else {
            delay = SUCCESS_DELAY;
        }
        final Timer closeTimer = new Timer(delay, timerEvent -> dispose());
        closeTimer.setRepeats(false);
        closeTimer.start();
    }

    /**
     * Static factory method for creating purchase dialogs.
     *
     * @param parent   the parent frame for centering
     * @param player   the current player attempting purchase
     * @param property the PropertyTile being purchased
     * @param callback callback to notify about purchase results
     */
    public static void showPurchaseDialog(Frame parent, Player player, PropertyTile property,
                                          PurchaseResultCallback callback) {
        SwingUtilities.invokeLater(() -> {
            new BuyPropertyPopup(parent, player, property, callback);
        });
    }

    /**
     * Callback interface for communicating purchase results back to the caller.
     */
    public interface PurchaseResultCallback {

        /**
         * Called when the purchase attempt is completed.
         *
         * @param success true if the purchase was successful, false otherwise
         * @param message a message providing additional context about the result
         */
        void onPurchaseCompleted(boolean success, String message);
    }
}
