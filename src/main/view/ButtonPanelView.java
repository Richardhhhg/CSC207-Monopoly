package main.view;

import main.entity.Game;

import javax.swing.*;
import java.awt.*;

public class ButtonPanelView extends JPanel {
    private static final JLabel roundLabel = new JLabel("Round: 1");
    private static final JLabel turnLabel = new JLabel("Turn: Player 1");

    private static final JButton rollDiceButton = new JButton("Roll Dice");
    private static final JButton endTurnButton = new JButton("End Turn");
    private static final JButton stockMarketButton = new JButton("Stock Market");

    public ButtonPanelView(Game game, Runnable onRollDice, Runnable onEndTurn, Runnable onStockMarket) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        roundLabel.setFont(new Font("Arial", Font.BOLD, 16));
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));

        add(roundLabel);
        add(turnLabel);
        add(Box.createVerticalStrut(20));

        add(rollDiceButton);
        add(endTurnButton);
        add(stockMarketButton);

        rollDiceButton.addActionListener(e -> onRollDice.run());
        endTurnButton.addActionListener(e -> onEndTurn.run());
        stockMarketButton.addActionListener(e -> onStockMarket.run());
    }

    public static void updateStatus(int round, String currentPlayerName) {
        roundLabel.setText("Round: " + round);
        turnLabel.setText("Turn: " + currentPlayerName);
    }

    public static JButton getRollDiceButton() {
        return rollDiceButton;
    }
}
