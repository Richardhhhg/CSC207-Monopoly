package main.view;

import main.Constants.Constants;
import main.use_case.Player;

import javax.swing.*;
import java.awt.*;

/**
 * BoardView is a JPanel that represents the view of the game board.
 * Updated to handle end game conditions.
 */
public class BoardView extends JPanel {
    private final GameBoard gameBoard;
    private final DiceController diceController;
    private final BoardRenderer renderer;
    private final PlayerMovementAnimator animator;

    // UI Components
    private final JButton rollDiceButton = new JButton("Roll Dice");
    private final JButton endTurnButton = new JButton("End Turn");
    private final JLabel gameStatusLabel = new JLabel();

    public BoardView() {
        this.gameBoard = new GameBoard();
        this.diceController = new DiceController();
        this.renderer = new BoardRenderer();
        this.animator = new PlayerMovementAnimator();

        setPreferredSize(new java.awt.Dimension(Constants.BOARD_PANEL_WIDTH,
                Constants.BOARD_PANEL_HEIGHT));
        setupUI();
        updateGameStatusDisplay();
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

        // Create side panel with controls and status
        JPanel sidePanel = createSidePanel();
        add(sidePanel, BorderLayout.EAST);

        // Wire the buttons
        rollDiceButton.addActionListener(e -> handleRollDice());
        endTurnButton.addActionListener(e -> handleEndTurn());
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidePanel.setPreferredSize(new Dimension(200, Constants.BOARD_PANEL_HEIGHT));

        // Game status section
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        statusPanel.setBorder(BorderFactory.createTitledBorder("Game Status"));

        gameStatusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        gameStatusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusPanel.add(gameStatusLabel);

        statusPanel.add(Box.createVerticalStrut(10));

        // Current player info
        JLabel currentPlayerLabel = new JLabel() {
            @Override
            public void setText(String text) {
                Player currentPlayer = gameBoard.getCurrentPlayer();
                if (currentPlayer != null) {
                    super.setText("<html><center>Current Player:<br>" +
                            currentPlayer.getName() + "<br>$" +
                            String.format("%.2f", currentPlayer.getMoney()) + "</center></html>");
                } else {
                    super.setText("Game Over");
                }
            }
        };
        currentPlayerLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        currentPlayerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusPanel.add(currentPlayerLabel);

        sidePanel.add(statusPanel);
        sidePanel.add(Box.createVerticalStrut(20));

        // Button section
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        rollDiceButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rollDiceButton.setMaximumSize(new Dimension(150, 30));
        endTurnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        endTurnButton.setMaximumSize(new Dimension(150, 30));

        buttonPanel.add(rollDiceButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(endTurnButton);

        sidePanel.add(buttonPanel);
        sidePanel.add(Box.createVerticalGlue());

        // Player list section
        JPanel playersPanel = createPlayersPanel();
        sidePanel.add(playersPanel);

        return sidePanel;
    }

    private JPanel createPlayersPanel() {
        JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        playersPanel.setBorder(BorderFactory.createTitledBorder("Players"));

        for (Player player : gameBoard.getPlayers()) {
            JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            // Color indicator
            JPanel colorBox = new JPanel();
            colorBox.setBackground(player.getColor());
            colorBox.setPreferredSize(new Dimension(15, 15));
            colorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // Player info
            JLabel playerInfo = new JLabel() {
                @Override
                public void setText(String text) {
                    String status = player.isBankrupt() ? " (Bankrupt)" : "";
                    super.setText("<html>" + player.getName() + "<br>$" +
                            String.format("%.2f", player.getMoney()) + status + "</html>");
                    setForeground(player.isBankrupt() ? Color.RED : Color.BLACK);
                }
            };
            playerInfo.setFont(new Font("Arial", Font.PLAIN, 10));

            playerPanel.add(colorBox);
            playerPanel.add(playerInfo);
            playersPanel.add(playerPanel);
        }

        return playersPanel;
    }

    private void handleRollDice() {
        if (gameBoard.isGameOver()) {
            return;
        }

        rollDiceButton.setEnabled(false);

        diceController.startDiceAnimation(
                // On animation frame
                this::repaint,
                // On animation complete
                () -> {
                    Player currentPlayer = gameBoard.getCurrentPlayer();
                    if (currentPlayer == null) {
                        return;
                    }

                    int diceSum = diceController.getLastDiceSum();

                    // Handle finish line bonus
                    gameBoard.moveCurrentPlayer(diceSum);

                    // Animate player movement
                    animator.animatePlayerMovement(
                            currentPlayer,
                            diceSum,
                            gameBoard.getTileCount(),
                            this::repaint,  // On each move step
                            () -> {
                                // Handle tile landing after movement
                                handleTileLanding(currentPlayer);
                                updateGameStatusDisplay();
                                repaint();
                            }
                    );
                }
        );
    }

    private void handleTileLanding(Player player) {
        int position = player.getPosition();
        if (position >= 0 && position < gameBoard.getProperties().size()) {
            gameBoard.getProperties().get(position).onLanding(player);
        }
    }

    private void handleEndTurn() {
        if (gameBoard.isGameOver()) {
            return;
        }

        // Check if game ends after this turn
        boolean gameEnded = gameBoard.nextPlayer();

        if (gameEnded) {
            // Game has ended, disable buttons
            rollDiceButton.setEnabled(false);
            endTurnButton.setEnabled(false);
            updateGameStatusDisplay();
            repaint();
            return;
        }

        // Game continues
        rollDiceButton.setEnabled(true);
        updateGameStatusDisplay();
        repaint();
    }

    private void updateGameStatusDisplay() {
        if (gameBoard.isGameOver()) {
            gameStatusLabel.setText("Game Over!");
        } else {
            gameStatusLabel.setText("<html><center>" + gameBoard.getGameStatus() + "</center></html>");
        }
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