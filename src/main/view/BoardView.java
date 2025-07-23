package main.view;

import main.Constants.Constants;
import main.interface_adapter.GameController;
import main.view.PlayerMovementAnimator;
import main.interface_adapter.GameViewModel;
import main.view.BoardRenderer;
import main.interface_adapter.GameViewObserver;
import main.use_case.game.GameUseCase;

import javax.swing.*;
import java.awt.*;

/**
 * Main board view - now focused purely on UI concerns
 */
public class BoardView extends JPanel implements GameViewObserver {
    private final GameController gameController;
    private final GameViewModel gameViewModel;
    private final BoardRenderer renderer;
    private final PlayerMovementAnimator animator;

    // UI Components
    private final JButton rollDiceButton = new JButton("Roll Dice");
    private final JButton endTurnButton = new JButton("End Turn");
    private final JPanel boardPanel;

    public BoardView(GameController gameController) {
        this.gameController = gameController;
        this.gameViewModel = gameController.getGameViewModel();
        this.renderer = new BoardRenderer();
        this.animator = new PlayerMovementAnimator();

        // Register as observer
        gameViewModel.addObserver(this);

        setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT));
        setupUI();
        updateUI(); // Initial UI state
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        // Create board panel
        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                renderer.drawBoard(g, gameViewModel, gameController.getDiceController());
            }
        };
        boardPanel.setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT));
        boardPanel.setBackground(Color.LIGHT_GRAY);

        add(boardPanel, BorderLayout.CENTER);

        // Control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(rollDiceButton);
        controlPanel.add(endTurnButton);
        add(controlPanel, BorderLayout.EAST);

        // Wire the buttons
        rollDiceButton.addActionListener(e -> gameController.handleRollDice());
        endTurnButton.addActionListener(e -> gameController.handleEndTurn());
    }

    // Observer methods
    @Override
    public void onGameStateChanged() {
        SwingUtilities.invokeLater(this::updateUI);
    }

    @Override
    public void onPlayerMovementStart(int playerIndex, int steps) {
        SwingUtilities.invokeLater(() -> {
            animator.animatePlayerMovement(
                    gameViewModel.getPlayers().get(playerIndex),
                    steps,
                    gameViewModel.getTileCount(),
                    this::repaint,  // On each move step
                    () -> {}        // On movement complete
            );
        });
    }

    private void updateUI() {
        // Update button states
        rollDiceButton.setEnabled(gameViewModel.isRollButtonEnabled());

        // Handle error messages
        if (!gameViewModel.getErrorMessage().isEmpty()) {
            JOptionPane.showMessageDialog(this, gameViewModel.getErrorMessage());
        }

        // Handle game over
        if (gameViewModel.isGameOver()) {
            JOptionPane.showMessageDialog(this, "Game Over: All players are bankrupt.");
            rollDiceButton.setEnabled(false);
        }

        repaint();
    }

    /**
     * For Testing the Board View on its own
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create dependencies
            main.use_case.GameState gameState = new main.use_case.GameState();
            GameUseCase gameUseCase = new main.use_case.game.GameUseCase(gameState);
            main.interface_adapter.BoardPositionCalculator calculator = new main.interface_adapter.BoardPositionCalculator();
            GameViewModel gameViewModel = new GameViewModel(calculator);
            main.interface_adapter.DiceController diceController = new main.interface_adapter.DiceController();
            GameController gameController = new GameController(gameUseCase, gameViewModel, diceController);

            JFrame frame = new JFrame("Monopoly Board");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new BoardView(gameController));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}