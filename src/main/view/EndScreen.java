package main.view;

import main.entity.players.Player;
import main.interface_adapter.EndScreen.EndScreenController;
import main.interface_adapter.EndScreen.EndScreenPresenter;
import main.interface_adapter.EndScreen.EndScreenViewModel;
import main.use_case.EndScreen.EndGame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EndScreen extends JFrame {
    private final EndScreenController controller;
    private EndScreenViewModel viewModel;

    public EndScreen(List<Player> players, String gameEndReason, int totalRounds) {
        this.controller = new EndScreenController();
        initializeEndScreen(players, gameEndReason, totalRounds);
    }

    private void initializeEndScreen(List<Player> players, String gameEndReason, int totalRounds) {
        // Get data through controller and presenter
        EndGame.EndGameResult result = controller.execute(players, gameEndReason, totalRounds);
        EndScreenPresenter presenter = new EndScreenPresenter();
        viewModel = presenter.execute(result);

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

        // Winner label (if exists)
        if (!viewModel.getWinnerText().isEmpty()) {
            JLabel winnerLabel = new JLabel(viewModel.getWinnerText(), SwingConstants.CENTER);
            winnerLabel.setFont(new Font("Arial", Font.BOLD, 24));
            winnerLabel.setForeground(new Color(0, 150, 0));
            winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            titlePanel.add(winnerLabel);
            titlePanel.add(Box.createVerticalStrut(10));
        }

        JLabel gameOverLabel = new JLabel(viewModel.getGameOverTitle(), SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gameOverLabel.setForeground(new Color(150, 0, 0));
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel reasonLabel = new JLabel(viewModel.getGameEndReason(), SwingConstants.CENTER);
        reasonLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        reasonLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel roundsLabel = new JLabel(viewModel.getTotalRoundsText(), SwingConstants.CENTER);
        roundsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        roundsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        for (EndScreenViewModel.PlayerDisplayData playerData : viewModel.getPlayerDisplayData()) {
            JPanel playerPanel = createPlayerStatsPanel(playerData);
            resultsPanel.add(playerPanel);
            resultsPanel.add(Box.createVerticalStrut(15));
        }

        return resultsPanel;
    }

    // Add this method to your EndScreen.java to update the createPlayerStatsPanel method

    private JPanel createPlayerStatsPanel(EndScreenViewModel.PlayerDisplayData playerData) {
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BorderLayout());
        playerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(playerData.getPlayer().getColor(), 3),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        playerPanel.setBackground(Color.WHITE);

        // Left side - Portrait and basic info
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Rank and name
        JLabel rankLabel = new JLabel(playerData.getRankText());
        rankLabel.setFont(new Font("Arial", Font.BOLD, 20));
        rankLabel.setForeground(playerData.getPlayer().getColor());
        rankLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Portrait
        if (playerData.getPlayer().getPortrait() != null) {
            ImageIcon portraitIcon = new ImageIcon(playerData.getPlayer().getPortrait().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
            JLabel portraitLabel = new JLabel(portraitIcon);
            portraitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            leftPanel.add(portraitLabel);
            leftPanel.add(Box.createVerticalStrut(5));
        }

        leftPanel.add(rankLabel);

        // Status
        JLabel statusLabel = new JLabel(playerData.getStatusText());
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setForeground(playerData.getPlayer().isBankrupt() ? Color.RED : Color.GREEN);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(statusLabel);

        playerPanel.add(leftPanel, BorderLayout.WEST);

        // Center - Stats
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(0, 2, 10, 5));

        // Cash (liquid money only, same as during game)
        statsPanel.add(new JLabel(" Cash:"));
        JLabel moneyLabel = new JLabel("$" + playerData.getMoneyText());
        moneyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        moneyLabel.setToolTipText("Liquid cash (same as shown during game)");
        statsPanel.add(moneyLabel);

        // Properties count
        statsPanel.add(new JLabel(" Properties Owned:"));
        statsPanel.add(new JLabel(playerData.getPropertiesCountText()));

        // Property value
        statsPanel.add(new JLabel(" Property Value:"));
        statsPanel.add(new JLabel("$" + playerData.getPropertyValueText()));

        // Stock value (current market value)
        statsPanel.add(new JLabel(" Stock Value:"));
        JLabel stockValueLabel = new JLabel("$" + playerData.getStockValueText());
        stockValueLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        stockValueLabel.setToolTipText("Current market value of stock portfolio");
        statsPanel.add(stockValueLabel);

        // Separator line
        statsPanel.add(new JSeparator());
        statsPanel.add(new JSeparator());

        // Net worth (cash + properties + stocks)
        statsPanel.add(new JLabel(" Total Net Worth:"));
        JLabel netWorthLabel = new JLabel("$" + playerData.getNetWorthText());
        netWorthLabel.setFont(new Font("Arial", Font.BOLD, 16));
        netWorthLabel.setForeground(new Color(0, 120, 0));
        netWorthLabel.setToolTipText("Cash + Property Value + Stock Value");
        statsPanel.add(netWorthLabel);

        playerPanel.add(statsPanel, BorderLayout.CENTER);

        // Right side - Properties list
        if (!playerData.getPlayer().getProperties().isEmpty()) {
            JPanel propertiesPanel = new JPanel();
            propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
            propertiesPanel.setBorder(BorderFactory.createTitledBorder("Properties Owned"));

            for (var property : playerData.getPlayer().getProperties()) {
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

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton newGameButton = new JButton(viewModel.getNewGameButtonText());
        newGameButton.setFont(new Font("Arial", Font.PLAIN, 16));
        newGameButton.addActionListener(e -> {
            dispose();
            new StartScreen();
        });

        JButton exitButton = new JButton(viewModel.getExitButtonText());
        exitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(newGameButton);
        buttonPanel.add(exitButton);

        return buttonPanel;
    }
}