package main.view;

import main.entity.*;
import main.use_case.Game.GameNextTurn;
import main.use_case.Player;
import main.Constants.Constants;
import main.use_case.Tile;

import javax.swing.*;
import java.awt.*;

/**
 * BoardView is a JPanel that represents the main.view of the game board.
 * Note: THIS IS NOT THE ENTIRE WINDOW, just the board itself.
 */
public class BoardView extends JPanel {
    // Components responsible for specific functionality
    private final Game game;
    private final BoardRenderer boardRenderer;
    private final DiceController diceController;
    private final PlayerMovementAnimator playerMovementAnimator;
    private JFrame parentFrame; // Reference to parent frame for end screen

    // ——— Dice UI & state ———
    private final JButton rollDiceButton = new JButton("Roll Dice");
    private final JButton endTurnButton = new JButton("End Turn");
    private final JButton stockMarketButton = new JButton("Stock Market");
    private final JLabel roundLabel = new JLabel("Round: 1");
    private final JLabel turnLabel = new JLabel("Turns: 0");

    // player stats
    private final PlayerStatsView statsPanel;

    public BoardView() {
        this.game = new Game();
        this.diceController = new DiceController();
        this.boardRenderer = new BoardRenderer();
        this.playerMovementAnimator = new PlayerMovementAnimator();
        this.statsPanel = new PlayerStatsView(game.getPlayers());

        setPreferredSize(new java.awt.Dimension(Constants.BOARD_PANEL_WIDTH,
                Constants.BOARD_PANEL_HEIGHT));
        setupUI();
    }

    public void setParentFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT));

        // Create board panel
        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                boardRenderer.drawBoard(g, game, diceController);
            }
        };
        boardPanel.setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT));
        boardPanel.setBackground(Color.LIGHT_GRAY);

        add(boardPanel, BorderLayout.WEST);
        add(statsPanel, BorderLayout.CENTER);

        // Roll-Dice side-panel
        JPanel side = new JPanel();
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));

        // Game status labels
        roundLabel.setFont(new Font("Arial", Font.BOLD, 16));
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        side.add(roundLabel);
        side.add(turnLabel);
        side.add(Box.createVerticalStrut(20));

        side.add(rollDiceButton);
        side.add(endTurnButton);
        side.add(stockMarketButton);
        add(side, BorderLayout.EAST);

        // wire the button
        rollDiceButton.addActionListener(e -> handleRollDice());
        endTurnButton.addActionListener(e -> handleEndTurn());
        stockMarketButton.addActionListener(e -> displayStockMarket());

        updateStatusLabels();
    }

    private void displayStockMarket() {
        // Create a new StockMarketView with the current players' stocks
        StockMarketView stockMarketView = new StockMarketView(game.getCurrentPlayer());
        stockMarketView.setVisible(true);
    }

    private void updateStatusLabels() {
        roundLabel.setText("Round: " + game.getCurrentRound());
        turnLabel.setText("Turns: " + game.getTotalTurns());
    }

    private void handleRollDice() {
        rollDiceButton.setEnabled(false);

        // Use DiceController for dice animation and logic
        diceController.startDiceAnimation(
            this::repaint, // Animation frame callback
            this::onDiceRollComplete // Completion callback
        );
    }

    private void onDiceRollComplete() {
        Player currentPlayer = game.getCurrentPlayer();
        int diceSum = diceController.getLastDiceSum();

        // Handle crossing GO bonus using GameBoard logic
        game.moveCurrentPlayer(diceSum);

        // Use PlayerMovementAnimator for movement animation
        playerMovementAnimator.animatePlayerMovement(
            currentPlayer,
            diceSum,
            game.getTileCount(),
            this::repaint, // Movement step callback
            this::onPlayerMovementComplete // Completion callback
        );
    }

    private void onPlayerMovementComplete() {
        // Handle landing on property using PropertyTile's onLanding method
        handleLandingOnProperty();
    }

    //TODO: Generalize this to handle all tile types
    private void handleLandingOnProperty() {
        Player currentPlayer = game.getCurrentPlayer();
        int position = currentPlayer.getPosition();
        Tile property = game.getPropertyAt(position);

        if (property != null) {
            // Use PropertyTile's built-in landing logic
            property.onLanding(currentPlayer);
        }
    }

    private void handleEndTurn() {
        new GameNextTurn(game).execute();
        updateStatusLabels();
        statsPanel.updatePlayers(game.getPlayers());

        if (game.isGameOver()) {
            showEndScreen();
            return;
        }

        rollDiceButton.setEnabled(true);
        repaint();
    }

    private void showEndScreen() {
        rollDiceButton.setEnabled(false);
        endTurnButton.setEnabled(false);

        // Hide the parent frame if it exists
        if (parentFrame != null) {
            parentFrame.setVisible(false);
        }

        // Show the end screen
        SwingUtilities.invokeLater(() -> {
            new EndScreen(
                    game.getPlayers(),
                    game.getGameEndReason(),
                    game.getCurrentRound()
            );
        });
    }

    public int getLastDiceSum() {
        return diceController.getLastDiceSum();
    }

    /**
     * For Testing the Board View on its own
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Monopoly Board");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            BoardView boardView = new BoardView();
            boardView.setParentFrame(frame);
            frame.add(boardView);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}