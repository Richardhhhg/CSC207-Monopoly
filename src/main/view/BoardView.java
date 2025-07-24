package main.view;

import main.Constants.Constants;
import main.use_case.Player;

import javax.swing.*;
import java.awt.*;

/**
 * BoardView is a JPanel that represents the main.view of the game board.
 * Note: THIS IS NOT THE ENTIRE WINDOW, just the board itself.
 */
public class BoardView extends JPanel {
    private final GameBoard gameBoard;
    private final DiceController diceController;
    private final BoardRenderer renderer;
    private final PlayerMovementAnimator animator;
    private JFrame parentFrame; // Reference to parent frame for end screen

    // UI Components
    private final JButton rollDiceButton = new JButton("Roll Dice");
    private final JButton endTurnButton = new JButton("End Turn");
    private final JButton stockMarketButton = new JButton("Stock Market");
    private final JLabel roundLabel = new JLabel("Round: 1");
    private final JLabel turnLabel = new JLabel("Turns: 0");

    // player stats
    private final PlayerStatsView statsPanel;

    public BoardView() {
        this.gameBoard = new GameBoard();
        this.diceController = new DiceController();
        this.renderer = new BoardRenderer();
        this.animator = new PlayerMovementAnimator();
        this.statsPanel = new PlayerStatsView(gameBoard.getPlayers());

        setPreferredSize(new java.awt.Dimension(Constants.BOARD_PANEL_WIDTH,
                Constants.BOARD_PANEL_HEIGHT));
        setupUI();
    }

    public void setParentFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        // Create board panel
        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                renderer.drawBoard(g, gameBoard, diceController);
            }
        };
        boardPanel.setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH,
                Constants.BOARD_PANEL_HEIGHT));
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

        // Wire the buttons
        rollDiceButton.addActionListener(e -> handleRollDice());
        endTurnButton.addActionListener(e -> handleEndTurn());
        stockMarketButton.addActionListener(e -> displayStockMarket());

        updateStatusLabels();
    }

    // TODO: This implementation is absolutely horrible, it should be replaced by the tile logic
    private void displayStockMarket() {
        // Create a new StockMarketView with the current players' stocks
        StockMarketView stockMarketView = new StockMarketView(gameBoard.getCurrentPlayer());
        stockMarketView.setVisible(true);
    }

    private void updateStatusLabels() {
        roundLabel.setText("Round: " + gameBoard.getCurrentRound());
        turnLabel.setText("Turns: " + gameBoard.getTotalTurns());
    }

    private void handleRollDice() {
        rollDiceButton.setEnabled(false);

        diceController.startDiceAnimation(
                // On animation frame
                this::repaint,
                // On animation complete
                () -> {
                    Player currentPlayer = gameBoard.getCurrentPlayer();
                    int diceSum = diceController.getLastDiceSum();

                    // Handle finish line bonus
                    gameBoard.moveCurrentPlayer(diceSum);

                    // Animate player movement
                    animator.animatePlayerMovement(
                            currentPlayer,
                            diceSum,
                            gameBoard.getTileCount(),
                            this::repaint,  // On each move step
                            () -> {}        // On movement complete
                    );
                }
        );
    }

    private void handleEndTurn() {
        gameBoard.nextPlayer();
        updateStatusLabels();
        statsPanel.updatePlayers(gameBoard.getPlayers());

        if (gameBoard.isGameOver()) {
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
                    gameBoard.getPlayers(),
                    gameBoard.getGameEndReason(),
                    gameBoard.getCurrentRound()
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