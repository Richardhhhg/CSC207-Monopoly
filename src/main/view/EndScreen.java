package main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import main.constants.Constants;
import main.entity.players.Player;
import main.interface_adapter.endScreen.EndScreenController;
import main.interface_adapter.endScreen.EndScreenPresenter;
import main.interface_adapter.endScreen.EndScreenViewModel;
import main.use_case.endScreen.EndGame;

public class EndScreen extends JFrame {
    private final EndScreenController controller;
    private EndScreenViewModel viewModel;

    public EndScreen(List<Player> players, String gameEndReason, int totalRounds) {
        this.controller = new EndScreenController();
        initializeEndScreen(players, gameEndReason, totalRounds);
    }

    private void initializeEndScreen(List<Player> players, String gameEndReason, int totalRounds) {
        // Get data through controller and presenter
        final EndGame.EndGameResult result =
                controller.execute(players, gameEndReason, totalRounds);
        final EndScreenPresenter presenter = new EndScreenPresenter();
        viewModel = presenter.execute(result);

        setTitle("Game Over - Final Results");
        setSize(Constants.END_SCREEN_WIDTH, Constants.END_SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Constants.BG_LIGHT);

        // Title Panel
        final JPanel titlePanel = createTitlePanel();
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Results Panel
        final JPanel resultsPanel = createResultsPanel();
        final JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setPreferredSize(
                new Dimension(Constants.END_SCROLL_WIDTH, Constants.END_SCROLL_HEIGHT));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        final JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createTitlePanel() {
        final JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(Constants.TITLE_BG);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(
                Constants.PAD_LARGE, Constants.PAD_LARGE,
                Constants.PAD_LARGE, Constants.PAD_LARGE));

        // Winner label (if exists)
        if (!viewModel.getWinnerText().isEmpty()) {
            final JLabel winnerLabel =
                    new JLabel(viewModel.getWinnerText(), SwingConstants.CENTER);
            winnerLabel.setFont(
                    new Font(Constants.UI_FONT_FAMILY, Font.BOLD, Constants.WINNER_FONT_SIZE));
            winnerLabel.setForeground(Constants.WINNER_GREEN);
            winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            titlePanel.add(winnerLabel);
            titlePanel.add(Box.createVerticalStrut(Constants.GAP_MEDIUM));
        }

        final JLabel gameOverLabel =
                new JLabel(viewModel.getGameOverTitle(), SwingConstants.CENTER);
        gameOverLabel.setFont(
                new Font(Constants.UI_FONT_FAMILY, Font.BOLD, Constants.GAME_OVER_FONT_SIZE));
        gameOverLabel.setForeground(new Color(150, 0, 0));
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel reasonLabel =
                new JLabel(viewModel.getGameEndReason(), SwingConstants.CENTER);
        reasonLabel.setFont(
                new Font(Constants.UI_FONT_FAMILY, Font.PLAIN, Constants.REASON_FONT_SIZE));
        reasonLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel roundsLabel =
                new JLabel(viewModel.getTotalRoundsText(), SwingConstants.CENTER);
        roundsLabel.setFont(
                new Font(Constants.UI_FONT_FAMILY, Font.PLAIN, Constants.ROUNDS_FONT_SIZE));
        roundsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(gameOverLabel);
        titlePanel.add(Box.createVerticalStrut(Constants.GAP_MEDIUM));
        titlePanel.add(reasonLabel);
        titlePanel.add(Box.createVerticalStrut(Constants.GAP_SMALL));
        titlePanel.add(roundsLabel);

        return titlePanel;
    }

    private JPanel createResultsPanel() {
        final JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(
                Constants.PAD_LARGE, Constants.PAD_LARGE,
                Constants.PAD_LARGE, Constants.PAD_LARGE));

        for (EndScreenViewModel.PlayerDisplayData playerData : viewModel.getPlayerDisplayData()) {
            final JPanel playerPanel = createPlayerStatsPanel(playerData);
            resultsPanel.add(playerPanel);
            resultsPanel.add(Box.createVerticalStrut(Constants.GAP_LARGE));
        }

        return resultsPanel;
    }

    private JPanel createPlayerStatsPanel(EndScreenViewModel.PlayerDisplayData playerData) {
        final JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BorderLayout());
        playerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        playerData.getPlayer().getColor(), Constants.BORDER_THICKNESS),
                BorderFactory.createEmptyBorder(
                        Constants.PAD_MEDIUM, Constants.PAD_MEDIUM,
                        Constants.PAD_MEDIUM, Constants.PAD_MEDIUM)));
        playerPanel.setBackground(Color.WHITE);

        // Left side - Portrait and basic info
        final JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Rank and name
        final JLabel rankLabel = new JLabel(playerData.getRankText());
        rankLabel.setFont(
                new Font(Constants.UI_FONT_FAMILY, Font.BOLD, Constants.MONEY_LABEL_FONT_SIZE + 6));
        rankLabel.setForeground(playerData.getPlayer().getColor());
        rankLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Portrait
        if (playerData.getPlayer().getPortrait() != null) {
            final Image scaled =
                    playerData.getPlayer().getPortrait()
                            .getScaledInstance(Constants.PORTRAIT_SIZE_PX,
                                    Constants.PORTRAIT_SIZE_PX,
                                    Image.SCALE_SMOOTH);
            final ImageIcon portraitIcon = new ImageIcon(scaled);
            final JLabel portraitLabel = new JLabel(portraitIcon);
            portraitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            leftPanel.add(portraitLabel);
            leftPanel.add(Box.createVerticalStrut(Constants.GAP_SMALL));
        }

        leftPanel.add(rankLabel);

        // Status
        final JLabel statusLabel = new JLabel(playerData.getStatusText());
        statusLabel.setFont(
                new Font(Constants.UI_FONT_FAMILY, Font.BOLD, Constants.STATUS_FONT_SIZE));
        if (playerData.getPlayer().isBankrupt()) {
            statusLabel.setForeground(Color.RED);
        }
        else {
            statusLabel.setForeground(Color.GREEN);
        }
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(statusLabel);

        playerPanel.add(leftPanel, BorderLayout.WEST);

        // Center - Stats
        final JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(0, 2, Constants.GAP_MEDIUM, Constants.GAP_SMALL));

        // Cash (liquid money only, same as during game)
        statsPanel.add(new JLabel(" Cash:"));
        final JLabel moneyLabel = new JLabel(Constants.CURRENCY_PREFIX + playerData.getMoneyText());
        moneyLabel.setFont(
                new Font(Constants.UI_FONT_FAMILY, Font.BOLD, Constants.MONEY_LABEL_FONT_SIZE));
        moneyLabel.setToolTipText("Liquid cash (same as shown during game)");
        statsPanel.add(moneyLabel);

        // Property value
        statsPanel.add(new JLabel(" Property Value:"));
        statsPanel.add(new JLabel(
                Constants.CURRENCY_PREFIX + playerData.getPropertyValueText()));

        // Stock value (current market value)
        statsPanel.add(new JLabel(" Stock Value:"));
        final JLabel stockValueLabel =
                new JLabel(Constants.CURRENCY_PREFIX + playerData.getStockValueText());
        stockValueLabel.setFont(
                new Font(Constants.UI_FONT_FAMILY, Font.PLAIN, Constants.MONEY_LABEL_FONT_SIZE));
        stockValueLabel.setToolTipText("Current market value of stock portfolio");
        statsPanel.add(stockValueLabel);

        // Spacing instead of JSeparator to reduce DAC and magic numbers
        statsPanel.add(Box.createHorizontalStrut(Constants.GAP_MEDIUM));
        statsPanel.add(Box.createHorizontalStrut(Constants.GAP_MEDIUM));

        // Net worth (cash + properties + stocks)
        statsPanel.add(new JLabel(" Total Net Worth:"));
        final JLabel netWorthLabel =
                new JLabel(Constants.CURRENCY_PREFIX + playerData.getNetWorthText());
        netWorthLabel.setFont(
                new Font(Constants.UI_FONT_FAMILY, Font.BOLD, Constants.NET_WORTH_FONT_SIZE));
        netWorthLabel.setForeground(Constants.NET_GREEN);
        netWorthLabel.setToolTipText("Cash + Property Value + Stock Value");
        statsPanel.add(netWorthLabel);

        playerPanel.add(statsPanel, BorderLayout.CENTER);

        // Right side - Properties list
        if (!playerData.getPlayer().getProperties().isEmpty()) {
            final JPanel propertiesPanel = new JPanel();
            propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
            propertiesPanel.setBorder(BorderFactory.createTitledBorder("Properties Owned"));

            playerData.getPlayer().getProperties().forEach(property -> {
                final JLabel propLabel = new JLabel("- " + property.getName());
                propLabel.setFont(new Font(Constants.UI_FONT_FAMILY, Font.PLAIN,
                        Constants.PROPERTY_LABEL_FONT_SIZE));
                propertiesPanel.add(propLabel);
            });

            final JScrollPane propScrollPane = new JScrollPane(propertiesPanel);
            propScrollPane.setPreferredSize(new Dimension(
                    Constants.PROPERTIES_PANEL_WIDTH,
                    Constants.PROPERTIES_PANEL_HEIGHT));
            playerPanel.add(propScrollPane, BorderLayout.EAST);
        }

        return playerPanel;
    }

    private JPanel createButtonPanel() {
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(
                Constants.PAD_LARGE, Constants.PAD_LARGE,
                Constants.PAD_LARGE, Constants.PAD_LARGE));

        final JButton newGameButton = new JButton(viewModel.getNewGameButtonText());
        newGameButton.setFont(new Font(Constants.UI_FONT_FAMILY, Font.PLAIN,
                Constants.ROUNDS_FONT_SIZE));
        newGameButton.addActionListener(event -> {
            dispose();
            new StartScreen();
        });

        final JButton exitButton = new JButton(viewModel.getExitButtonText());
        exitButton.setFont(new Font(Constants.UI_FONT_FAMILY, Font.PLAIN,
                Constants.ROUNDS_FONT_SIZE));
        exitButton.addActionListener(event -> System.exit(0));

        buttonPanel.add(newGameButton);
        buttonPanel.add(exitButton);

        return buttonPanel;
    }
}
