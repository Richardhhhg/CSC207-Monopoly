package view;

import app.Main;
import util.MusicPlayer;

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

        MusicPlayer.playMusic("background_music.wav");
    }

    private JPanel getJPanel() {
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.addActionListener(e -> {
            dispose(); // close start screen
            Main.startGame(); // start actual game
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
         Monopoly

         Roll two dice to move your avatar around the board.
         Buy properties if they're unowned — earn rent when others land on them.
         Pay rent if you land on someone else's property.

         Every time you make a full round trip, receive $200.
         If your money reaches $0, you go bankrupt and are eliminated.
         The game ends after 20 rounds or when only 1 player remains.
        
          Stock Market:
        - Buy stocks ONLY when on the Stock Tile.
        - Sell stocks anytime during your turn.
        - Prices change based on real market distributions.

        Have fun and become the richest player!
        """;

        JOptionPane.showMessageDialog(this, rules, "Game Rules", JOptionPane.INFORMATION_MESSAGE);
    }
}
