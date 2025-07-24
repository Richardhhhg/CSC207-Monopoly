package main.view;

import main.entity.tiles.PropertyTile;
import main.use_case.Player;
import main.Constants.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * BoardView is a JPanel that represents the main view of the game board.
 * Follows clean architecture by delegating responsibilities to appropriate classes.
 */
public class BoardView extends JPanel {
    // Components responsible for specific functionality
    private final GameBoard gameBoard;
    private final BoardRenderer boardRenderer;
    private final DiceController diceController;
    private final PlayerMovementAnimator playerMovementAnimator;

    // UI Components
    private final JButton rollDiceButton = new JButton("Roll Dice");
    private final JButton endTurnButton = new JButton("End Turn");

    public BoardView() {
        // Initialize controllers and renderers
        this.gameBoard = new GameBoard();
        this.boardRenderer = new BoardRenderer();
        this.diceController = new DiceController();
        this.playerMovementAnimator = new PlayerMovementAnimator();

        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT));

        // Create board panel that delegates rendering to BoardRenderer
        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                boardRenderer.drawBoard(g, gameBoard, diceController);
            }
        };
        boardPanel.setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT));
        boardPanel.setBackground(Color.LIGHT_GRAY);

        add(boardPanel, BorderLayout.CENTER);

        // Create control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(rollDiceButton);
        controlPanel.add(endTurnButton);
        add(controlPanel, BorderLayout.EAST);

        // Setup event handlers
        rollDiceButton.addActionListener(e -> handleRollDice());
        endTurnButton.addActionListener(e -> handleEndTurn());
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
        Player currentPlayer = gameBoard.getCurrentPlayer();
        int diceSum = diceController.getLastDiceSum();

        // Handle crossing GO bonus using GameBoard logic
        gameBoard.moveCurrentPlayer(diceSum);

        // Use PlayerMovementAnimator for movement animation
        playerMovementAnimator.animatePlayerMovement(
            currentPlayer,
            diceSum,
            gameBoard.getTileCount(),
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
        Player currentPlayer = gameBoard.getCurrentPlayer();
        int position = currentPlayer.getPosition();
        PropertyTile property = gameBoard.getPropertyAt(position);

        if (property != null) {
            // Use PropertyTile's built-in landing logic
            property.onLanding(currentPlayer);
        }
    }

    private void handleEndTurn() {
        if (gameBoard.isGameOver()) {
            showGameOverDialog();
            return;
        }

        // Use GameBoard to handle turn transitions
        gameBoard.nextPlayer();

        if (gameBoard.isGameOver()) {
            showGameOverDialog();
            return;
        }

        // Re-enable dice rolling for next player
        rollDiceButton.setEnabled(true);
        repaint(); // Update current player display
    }

    private void showGameOverDialog() {
        String message = "Game Over!\n" + gameBoard.getGameEndReason() +
                        "\nTotal rounds played: " + gameBoard.getCurrentRound();

        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        rollDiceButton.setEnabled(false);
        endTurnButton.setEnabled(false);
    }

    /**
     * Set reference to parent frame if needed for dialogs
     */
    public void setParentFrame(GameView gameView) {
        // Can be used for parent frame reference if needed
    }

    /**
     * For testing the BoardView on its own
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Monopoly Board");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new BoardView());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
