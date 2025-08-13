package main.view;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ButtonPanelView extends JPanel {

    private static final int BORDER_SPACING = 10;
    private static final int VERTICAL_STRUT_HEIGHT = 20;
    private static final int FONT_SIZE = 16;

    private final JLabel roundLabel = new JLabel("Round: 1");
    private final JLabel turnLabel = new JLabel("Turn: Player 1");

    private final JButton rollDiceButton = new JButton("Roll Dice");
    private final JButton endTurnButton = new JButton("End Turn");
    private final JButton stockMarketButton = new JButton("Stock Market");

    public ButtonPanelView(Runnable onRollDice, Runnable onEndTurn, Runnable onStockMarket) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(BORDER_SPACING, BORDER_SPACING, BORDER_SPACING, BORDER_SPACING));

        roundLabel.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        turnLabel.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));

        add(roundLabel);
        add(turnLabel);
        add(Box.createVerticalStrut(VERTICAL_STRUT_HEIGHT));

        add(rollDiceButton);
        add(endTurnButton);
        add(stockMarketButton);

        // Wire actions
        rollDiceButton.addActionListener(actionEvent -> onRollDice.run());
        endTurnButton.addActionListener(actionEvent -> onEndTurn.run());
        stockMarketButton.addActionListener(actionEvent -> onStockMarket.run());
    }

    /**
     * Updates the status labels for the current round and player turn.
     *
     * @param round The current round number.
     * @param currentPlayerName The name of the player whose turn it is.
     */
    public void updateStatus(int round, String currentPlayerName) {
        roundLabel.setText("Round: " + round);
        turnLabel.setText("Turn: " + currentPlayerName);
    }

    public JButton getRollDiceButton() {
        return rollDiceButton;
    }

}
