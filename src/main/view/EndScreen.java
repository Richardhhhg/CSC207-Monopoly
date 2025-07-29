package main.view;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class EndScreen extends JFrame {
    private final List<Player> players;
    private final String gameEndReason;
    private final int totalRounds;

    public EndScreen(List<Player> players, String gameEndReason, int totalRounds) {
        this.players = new ArrayList<>(players);
        this.gameEndReason = gameEndReason;
        this.totalRounds = totalRounds;

        initializeEndScreen();
    }

    private void initializeEndScreen() {
        setTitle("Game Over - Final Results");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        // Title Panel
        JPanel titlePanel = createTitlePanel();
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Results Panel
        JPanel resultsPanel = createResultsPanel();
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setPreferredSize(new Dimension(750, 400));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(220, 220, 220));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gameOverLabel.setForeground(new Color(150, 0, 0));
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel reasonLabel = new JLabel(gameEndReason, SwingConstants.CENTER);
        reasonLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        reasonLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel roundsLabel = new JLabel("Total Rounds Played: " + totalRounds, SwingConstants.CENTER);
        roundsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        roundsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Determine winner
        Player winner = determineWinner();
        if (winner != null) {
            JLabel winnerLabel = new JLabel("🏆 WINNER: " + winner.getName() + " 🏆", SwingConstants.CENTER);
            winnerLabel.setFont(new Font("Arial", Font.BOLD, 24));
            winnerLabel.setForeground(new Color(0, 150, 0));
            winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            titlePanel.add(winnerLabel);
            titlePanel.add(Box.createVerticalStrut(10));
        }

        titlePanel.add(gameOverLabel);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(reasonLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(roundsLabel);

        return titlePanel;
    }

    private JPanel createResultsPanel() {
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Sort players by money (descending) for ranking
        List<Player> sortedPlayers = new ArrayList<>(players);
        sortedPlayers.sort(Comparator.comparingDouble(Player::getMoney).reversed());

        for (int i = 0; i < sortedPlayers.size(); i++) {
            Player player = sortedPlayers.get(i);
            JPanel playerPanel = createPlayerStatsPanel(player, i + 1);
            resultsPanel.add(playerPanel);
            resultsPanel.add(Box.createVerticalStrut(15));
        }

        return resultsPanel;
    }

    private JPanel createPlayerStatsPanel(Player player, int rank) {
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BorderLayout());
        playerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(player.getColor(), 3),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        playerPanel.setBackground(Color.WHITE);

        // Left side - Portrait and basic info
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Rank and name
        JLabel rankLabel = new JLabel("#" + rank + " - " + player.getName());
        rankLabel.setFont(new Font("Arial", Font.BOLD, 20));
        rankLabel.setForeground(player.getColor());
        rankLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Portrait
        if (player.getPortrait() != null) {
            ImageIcon portraitIcon = new ImageIcon(player.getPortrait().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
            JLabel portraitLabel = new JLabel(portraitIcon);
            portraitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            leftPanel.add(portraitLabel);
            leftPanel.add(Box.createVerticalStrut(5));
        }

        leftPanel.add(rankLabel);

        // Status
        JLabel statusLabel = new JLabel(player.isBankrupt() ? "BANKRUPT" : "SOLVENT");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setForeground(player.isBankrupt() ? Color.RED : Color.GREEN);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(statusLabel);

        playerPanel.add(leftPanel, BorderLayout.WEST);

        // Center - Stats
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(0, 2, 10, 5));

        // Money
        statsPanel.add(new JLabel("💰 Cash:"));
        JLabel moneyLabel = new JLabel("$" + String.format("%.2f", player.getMoney()));
        moneyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statsPanel.add(moneyLabel);

        // Properties
        statsPanel.add(new JLabel("🏠 Properties Owned:"));
        statsPanel.add(new JLabel(String.valueOf(player.getProperties().size())));

        // Property value
        float totalPropertyValue = calculateTotalPropertyValue(player);
        statsPanel.add(new JLabel("🏘️ Property Value:"));
        statsPanel.add(new JLabel("$" + String.format("%.2f", totalPropertyValue)));

        // Net worth (cash + properties)
        float netWorth = player.getMoney() + totalPropertyValue;
        statsPanel.add(new JLabel("💎 Net Worth:"));
        JLabel netWorthLabel = new JLabel("$" + String.format("%.2f", netWorth));
        netWorthLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statsPanel.add(netWorthLabel);

        // Current position
        statsPanel.add(new JLabel("📍 Final Position:"));
        statsPanel.add(new JLabel("Tile " + player.getPosition()));

        playerPanel.add(statsPanel, BorderLayout.CENTER);

        // Right side - Properties list
        if (!player.getProperties().isEmpty()) {
            JPanel propertiesPanel = new JPanel();
            propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
            propertiesPanel.setBorder(BorderFactory.createTitledBorder("Properties Owned"));

            for (PropertyTile property : player.getProperties()) {
                JLabel propLabel = new JLabel("• " + property.getName());
                propLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                propertiesPanel.add(propLabel);
            }

            JScrollPane propScrollPane = new JScrollPane(propertiesPanel);
            propScrollPane.setPreferredSize(new Dimension(200, 120));
            playerPanel.add(propScrollPane, BorderLayout.EAST);
        }

        return playerPanel;
    }

    private float calculateTotalPropertyValue(Player player) {
        float total = 0;
        for (PropertyTile property : player.getProperties()) {
            total += property.getPrice();
        }
        return total;
    }

    private Player determineWinner() {
        // Filter out bankrupt players
        List<Player> solventPlayers = players.stream()
                .filter(p -> !p.isBankrupt())
                .toList();

        if (solventPlayers.size() == 1) {
            return solventPlayers.get(0);
        } else if (solventPlayers.size() > 1) {
            // Find player with most money
            return solventPlayers.stream()
                    .max(Comparator.comparingDouble(Player::getMoney))
                    .orElse(null);
        }

        return null; // No winner if all bankrupt
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton newGameButton = new JButton("New Game");
        newGameButton.setFont(new Font("Arial", Font.PLAIN, 16));
        newGameButton.addActionListener(e -> {
            dispose();
            new StartScreen();
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(newGameButton);
        buttonPanel.add(exitButton);

        return buttonPanel;
    }

    /**
     * Test method to demonstrate the EndScreen
     */
    public static void main(String[] args) {
        // Create mock players for testing
        List<Player> testPlayers = new ArrayList<>();

        // You would need to import your concrete player classes for this test
        // For now, this is just to show the structure
        SwingUtilities.invokeLater(() -> {
            new EndScreen(testPlayers, "Maximum 20 rounds reached", 20);
        });
    }
}