package main.view;

import main.entity.*;
import main.entity.tiles.PropertyTile;
import main.use_case.Player;
import main.Constants.Constants;
import main.interface_adapter.dice.DicePresenter;
import main.interface_adapter.dice.RollDiceController;
import main.interface_adapter.view_model.DiceViewModel;
import main.use_case.dice.RollDiceInputBoundary;
import main.use_case.dice.RollDiceInteractor;

import javax.swing.*;
import java.awt.*;

/**
 * BoardView is a JPanel that represents the view of the game board.
 * This version uses a Clean Architecture stack to handle the roll‑dice
 * feature.  The dice values are generated in a use‑case interactor,
 * passed through a presenter into a view model, and the view merely
 * animates them.
 */
public class BoardView extends JPanel {
    // Core components
    private final GameBoard gameBoard;
    private final BoardRenderer boardRenderer;
    private final PlayerMovementAnimator playerMovementAnimator;
    private final PlayerStatsView statsPanel;

    // Clean‑architecture components for dice
    private final DiceAnimator diceAnimator;
    private final RollDiceController rollDiceController;

    // UI widgets
    private final JButton rollDiceButton = new JButton("Roll Dice");
    private final JButton endTurnButton = new JButton("End Turn");
    private final JButton stockMarketButton = new JButton("Stock Market");
    private final JLabel roundLabel = new JLabel("Round: 1");
    private final JLabel turnLabel = new JLabel("Turns: 0");
    private JFrame parentFrame;

    public BoardView() {
        this.gameBoard = new GameBoard();
        this.boardRenderer = new BoardRenderer();
        this.playerMovementAnimator = new PlayerMovementAnimator();
        this.statsPanel = new PlayerStatsView(gameBoard.getPlayers());

        // Set up Clean Architecture stack for dice
        DiceViewModel diceViewModel = new DiceViewModel();
        DicePresenter dicePresenter = new DicePresenter(diceViewModel);
        RollDiceInputBoundary interactor = new RollDiceInteractor(dicePresenter);
        this.rollDiceController = new RollDiceController(interactor, diceViewModel);
        this.diceAnimator = new DiceAnimator();

        setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT));
        setupUI();
    }

    public void setParentFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        // Board drawing panel
        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the board and current dice faces
                boardRenderer.drawBoard(g, gameBoard, diceAnimator);
            }
        };
        boardPanel.setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT));
        boardPanel.setBackground(Color.LIGHT_GRAY);
        add(boardPanel, BorderLayout.WEST);
        add(statsPanel, BorderLayout.CENTER);

        // Side panel with controls and status labels
        JPanel side = new JPanel();
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        roundLabel.setFont(new Font("Arial", Font.BOLD, 16));
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        side.add(roundLabel);
        side.add(turnLabel);
        side.add(Box.createVerticalStrut(20));
        side.add(rollDiceButton);
        side.add(endTurnButton);
        side.add(stockMarketButton);
        add(side, BorderLayout.EAST);

        // Wire up button actions
        rollDiceButton.addActionListener(e -> handleRollDice());
        endTurnButton.addActionListener(e -> handleEndTurn());
        stockMarketButton.addActionListener(e -> displayStockMarket());

        updateStatusLabels();
    }

    private void displayStockMarket() {
        StockMarketView stockMarketView = new StockMarketView(gameBoard.getCurrentPlayer());
        stockMarketView.setVisible(true);
    }

    private void updateStatusLabels() {
        roundLabel.setText("Round: " + gameBoard.getCurrentRound());
        turnLabel.setText("Turns: " + gameBoard.getTotalTurns());
    }

    private void handleRollDice() {
        rollDiceButton.setEnabled(false);
        // Ask the use case to roll the dice; the presenter updates the view model
        rollDiceController.rollDice();
        int d1 = rollDiceController.getDie1();
        int d2 = rollDiceController.getDie2();
        // Animate the dice using the predetermined values
        diceAnimator.startDiceAnimation(d1, d2, this::repaint, this::onDiceRollComplete);
    }

    private void onDiceRollComplete() {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        int diceSum = rollDiceController.getSum();
        gameBoard.moveCurrentPlayer(diceSum);
        playerMovementAnimator.animatePlayerMovement(
                currentPlayer,
                diceSum,
                gameBoard.getTileCount(),
                this::repaint,
                this::onPlayerMovementComplete
        );
    }

    private void onPlayerMovementComplete() {
        handleLandingOnProperty();
    }

    private void handleLandingOnProperty() {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        int position = currentPlayer.getPosition();
        PropertyTile property = gameBoard.getPropertyAt(position);
        if (property != null) {
            property.onLanding(currentPlayer);
        }
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
        if (parentFrame != null) {
            parentFrame.setVisible(false);
        }
        SwingUtilities.invokeLater(() -> new EndScreen(
                gameBoard.getPlayers(),
                gameBoard.getGameEndReason(),
                gameBoard.getCurrentRound()
        ));
    }

    // Compatibility method if other code calls getLastDiceSum()
    public int getLastDiceSum() {
        return rollDiceController.getSum();
    }
}
