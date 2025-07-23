package main.view;

import main.use_case.Player;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * EndScreen displays the final game results including winner, player stats, and game summary.
 */
public class EndScreen extends JFrame {
    private final List<Player> players;
    private final Player winner;
    private final String winCondition;
    private final int totalTurns;
    private final int maxTurns;

    public EndScreen(List<Player> players, Player winner, String winCondition, int totalTurns, int maxTurns) {
        this.players = players;
        this.winner = winner;
        this.winCondition = winCondition;
        this.totalTurns = totalTurns;
        this.maxTurns = maxTurns;

        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {
        setTitle("Game Over - Results");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        // Title panel
        JPanel titlePanel = createTitlePanel();
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Stats panel
        JPanel statsPanel = createStatsPanel();
        mainPanel.add(statsPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(240, 248, 255));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        // Game Over title
        JLabel gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gameOverLabel.setForeground(Color.DARK_GRAY);
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Winner announcement
        String winnerText = winner != null ? winner.getName() + " WINS!" : "NO WINNER";
        JLabel winnerLabel = new JLabel(winnerText, SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        winnerLabel.setForeground(new Color(0, 128, 0)); // Green
        winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Win condition
        JLabel conditionLabel = new JLabel(winCondition, SwingConstants.CENTER);
        conditionLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        conditionLabel.setForeground(Color.GRAY);
        conditionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Game summary
        JLabel summaryLabel = new JLabel(
                String.format("Game lasted %d/%d turns (%d rounds)", totalTurns, maxTurns, getCurrentRound()),
                SwingConstants.CENTER
        );
        summaryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        summaryLabel.setForeground(Color.GRAY);
        summaryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(gameOverLabel);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(winnerLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(conditionLabel);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(summaryLabel);

        return titlePanel;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Final Player Statistics",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16)
        ));
        statsPanel.setBackground(new Color(240, 248, 255));

        // Sort players by money (descending)
        List<Player> sortedPlayers = players.stream()
                .sorted((p1, p2) -> Float.compare(p2.getMoney(), p1.getMoney()))
                .collect(Collectors.toList());

        // Create table
        String[] columnNames = {"Rank", "Player", "Cash", "Properties", "Status", "Portrait"};
        Object[][] data = new Object[sortedPlayers.size()][6];

        for (int i = 0; i < sortedPlayers.size(); i++) {
            Player player = sortedPlayers.get(i);
            data[i][0] = i + 1; // Rank
            data[i][1] = player.getName();
            data[i][2] = String.format("$%.2f", player.getMoney());
            data[i][3] = player.getProperties().size();
            data[i][4] = player.isBankrupt() ? "Bankrupt" : "Active";
            data[i][5] = ""; // Portrait placeholder
        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }

            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);

                // Highlight winner row
                if (sortedPlayers.get(row) == winner) {
                    comp.setBackground(new Color(144, 238, 144)); // Light green
                    comp.setForeground(Color.BLACK);
                } else if (sortedPlayers.get(row).isBankrupt()) {
                    comp.setBackground(new Color(255, 182, 193)); // Light red
                    comp.setForeground(Color.BLACK);
                } else {
                    comp.setBackground(Color.WHITE);
                    comp.setForeground(Color.BLACK);
                }

                return comp;
            }
        };

        // Style the table
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(176, 196, 222)); // Light steel blue
        table.setRowHeight(60);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Custom renderer for portraits
        table.getColumnModel().getColumn(5).setCellRenderer(new PortraitRenderer());
        table.getColumnModel().getColumn(5).setPreferredWidth(80);
        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(new Color(240, 248, 255));
        statsPanel.add(scrollPane, BorderLayout.CENTER);

        return statsPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton newGameButton = new JButton("New Game");
        newGameButton.setFont(new Font("Arial", Font.BOLD, 16));
        newGameButton.setPreferredSize(new Dimension(120, 40));
        newGameButton.addActionListener(e -> startNewGame());

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setPreferredSize(new Dimension(120, 40));
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(newGameButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(exitButton);

        return buttonPanel;
    }

    private void startNewGame() {
        dispose(); // Close end screen
        new StartScreen(); // Show start screen again
    }

    private int getCurrentRound() {
        return (totalTurns / players.size()) + (totalTurns % players.size() > 0 ? 1 : 0);
    }

    /**
     * Custom cell renderer for player portraits
     */
    private class PortraitRenderer extends JLabel implements javax.swing.table.TableCellRenderer {
        public PortraitRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Player player = players.stream()
                    .sorted((p1, p2) -> Float.compare(p2.getMoney(), p1.getMoney()))
                    .collect(Collectors.toList())
                    .get(row);

            if (player.getPortrait() != null) {
                ImageIcon icon = new ImageIcon(player.getPortrait().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                setIcon(icon);
            } else {
                setIcon(null);
                setText("No Image");
            }

            // Apply background color based on player status
            if (player == winner) {
                setBackground(new Color(144, 238, 144)); // Light green
            } else if (player.isBankrupt()) {
                setBackground(new Color(255, 182, 193)); // Light red
            } else {
                setBackground(Color.WHITE);
            }

            return this;
        }
    }

    /**
     * Testing method
     */
    public static void main(String[] args) {
        // Create mock players for testing
        List<Player> mockPlayers = java.util.Arrays.asList(
                new main.entity.DefaultPlayer("Alice", Color.RED) {{ addMoney(500); }},
                new main.entity.DefaultPlayer("Bob", Color.BLUE) {{ deductMoney(1200); }}, // Bankrupt
                new main.entity.DefaultPlayer("Charlie", Color.GREEN) {{ addMoney(200); }},
                new main.entity.DefaultPlayer("Diana", Color.YELLOW) {{ addMoney(800); }}
        );

        Player winner = mockPlayers.get(3); // Diana with most money
        new EndScreen(mockPlayers, winner, "20 Turns Completed - Highest Cash Wins", 80, 80);
    }
}