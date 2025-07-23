package main.view;

import main.Constants.Constants;
import main.use_case.Player;

import javax.swing.*;
import java.awt.*;

/**
 * BoardView is a JPanel that represents the view of the game board.
 * This refactored version focuses only on UI coordination.
 */
public class BoardView extends JPanel {
    private final GameBoard gameBoard;
    private final DiceController diceController;
    private final BoardRenderer renderer;
    private final PlayerMovementAnimator animator;

    // UI Components
    private final JButton rollDiceButton = new JButton("Roll Dice");
    private final JButton endTurnButton = new JButton("End Turn");

    public BoardView() {
        this.gameBoard = new GameBoard();
        this.diceController = new DiceController();
        this.renderer = new BoardRenderer();
        this.animator = new PlayerMovementAnimator();

        setPreferredSize(new java.awt.Dimension(Constants.BOARD_PANEL_WIDTH,
                Constants.BOARD_PANEL_HEIGHT));
        setupUI();
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

        add(boardPanel, BorderLayout.CENTER);

        // Roll-Dice side-panel
        JPanel side = new JPanel();
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.add(rollDiceButton);
        side.add(endTurnButton);
        add(side, BorderLayout.EAST);

        // Wire the buttons
        rollDiceButton.addActionListener(e -> handleRollDice());
        endTurnButton.addActionListener(e -> handleEndTurn());
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

        if (gameBoard.isGameOver()) {
            JOptionPane.showMessageDialog(this, "Game Over: All players are bankrupt.");
            rollDiceButton.setEnabled(false);
            return;
        }

        rollDiceButton.setEnabled(true);
        repaint();
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
            frame.add(new BoardView());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}