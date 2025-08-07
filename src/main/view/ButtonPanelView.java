package main.view;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.constants.Constants;
import main.entity.Game;

public class ButtonPanelView extends JPanel {
    private static final JLabel ROUND_LABEL = new JLabel("Round: 1");
    private static final JLabel TURN_LABEL = new JLabel("Turn: Player 1");

    private static final JButton ROLL_DICE_BUTTON = new JButton("Roll Dice");
    private static final JButton END_TURN_BUTTON = new JButton("End Turn");
    private static final JButton STOCK_MARKET_BUTTON = new JButton("Stock Market");

    public ButtonPanelView(Game game, Runnable onRollDice, Runnable onEndTurn, Runnable onStockMarket) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(Constants.BUTTON_PANEL_PADDING, Constants.BUTTON_PANEL_PADDING,
                Constants.BUTTON_PANEL_PADDING, Constants.BUTTON_PANEL_PADDING));

        ROUND_LABEL.setFont(new Font("Arial", Font.BOLD, Constants.BUTTON_FONT_SIZE));
        TURN_LABEL.setFont(new Font("Arial", Font.BOLD, Constants.BUTTON_FONT_SIZE));

        add(ROUND_LABEL);
        add(TURN_LABEL);
        add(Box.createVerticalStrut(Constants.BUTTON_PANEL_VERTICAL_PADDING));

        add(ROLL_DICE_BUTTON);
        add(END_TURN_BUTTON);
        add(STOCK_MARKET_BUTTON);

        // Wire actions
        ROLL_DICE_BUTTON.addActionListener(event -> onRollDice.run());
        END_TURN_BUTTON.addActionListener(event -> onEndTurn.run());
        STOCK_MARKET_BUTTON.addActionListener(event -> onStockMarket.run());
    }

    /**
     * Updates the status labels for the current round and player turn.
     * @param round The current round number.
     * @param currentPlayerName The name of the player whose turn it is.
     */
    public static void updateStatus(int round, String currentPlayerName) {
        ROUND_LABEL.setText("Round: " + round);
        TURN_LABEL.setText("Turn: " + currentPlayerName);
    }

    public static JButton getRollDiceButton() {
        return ROLL_DICE_BUTTON;
    }
}
