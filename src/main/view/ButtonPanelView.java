package main.view;

import main.entity.Game;

import javax.swing.*;
import java.awt.*;

public class ButtonPanelView extends JPanel {
    private final JLabel roundLabel = new JLabel("Round: 1");
    private final JLabel turnLabel = new JLabel("Turn: Player 1");

    private final JButton rollDiceButton = new JButton("Roll Dice");
    private final JButton endTurnButton = new JButton("End Turn");
    private final JButton stockMarketButton = new JButton("Stock Market");

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

        // Wire actions
        rollDiceButton.addActionListener(e -> onRollDice.run());
        endTurnButton.addActionListener(e -> onEndTurn.run());
        stockMarketButton.addActionListener(e -> onStockMarket.run());
    }

    public void updateStatus(int round, String currentPlayerName) {
        roundLabel.setText("Round: " + round);
        turnLabel.setText("Turn: " + currentPlayerName);
    }

    public JButton getRollDiceButton() {
        return rollDiceButton;
    }

    public JButton getEndTurnButton() {
        return endTurnButton;
    }

    public JButton getStockMarketButton() {
        return stockMarketButton;
    }
}
