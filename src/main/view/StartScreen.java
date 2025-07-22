package main.view;

import main.app.Main;

import javax.swing.*;
import java.awt.*;

public class StartScreen extends JFrame {
    public StartScreen() {
        setTitle("Monopoly Game - Start");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel title = new JLabel("Welcome to our Monopoly!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        panel.add(title, BorderLayout.CENTER);

        JPanel buttonPanel = getJPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);
        setVisible(true);
    }

    private JPanel getJPanel() {
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.addActionListener(e -> {
            dispose(); // close start screen
            new Main().startGame(); // start actual game
        });

        JButton rulesButton = new JButton("Rules");
        rulesButton.setFont(new Font("Arial", Font.PLAIN, 16));
        rulesButton.addActionListener(e -> showRules());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(startButton);
        buttonPanel.add(rulesButton);
        return buttonPanel;
    }

    private void showRules() {
        String rules = """
        Objective
          Be the last solvent player—or, if 20 rounds pass, have the most cash.
    
        Setup
          4 players start with equal cash and zero properties or stocks; board and stock prices initialize from real‑world data.
    
        Turn Sequence
          1. Roll & Move: Roll two dice and advance.
          2. Actions on Landing:
           - Unowned Property: May buy; otherwise auction.
           - Owned Property: Pay rent to the owner.
           - Stock Market Tile: May buy up to 5 available stocks.
           - Other Tiles: Follow standard Monopoly rules (Chance, Community Chest, etc.).
          3. Stock Trades: At any point this turn, you may sell shares at current market price.
          4. Pass Go: Collect your salary when you complete a full lap.
    
        Bankruptcy
          If you can’t meet a payment, you’re out—give your assets to your creditor or to the bank.
    
        Stock Market
          - Prices update each round based on live data.
          - You can only buy when landing on the market tile; selling is always allowed.
    
        Endgame
          - Instant Win: Last player standing.
          - Round 20: Highest cash total (properties/stocks excluded) wins.
        """;

        JOptionPane.showMessageDialog(this, rules, "Game Rules", JOptionPane.INFORMATION_MESSAGE);
    }
}
