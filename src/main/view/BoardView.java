package main.view;

import main.interface_adapter.roll_dice.*;
import main.use_case.roll_dice.*;
import main.data_access.RandomNumberGenerator;
import main.entity.tiles.PropertyTile;
import main.use_case.Player;
import main.Constants.Constants;
import main.view.dice.DiceAnimationController;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JPanel {
    // Components responsible for specific functionality
    private final GameBoard gameBoard;
    private final BoardRenderer boardRenderer;
    private final PlayerMovementAnimator playerMovementAnimator;
    private JFrame parentFrame;

    // Clean Architecture Dice Components (REPLACE OLD DICECONTROLLER)
    private RollDiceController rollDiceController;
    private DiceViewModel diceViewModel;
    private DiceAnimationController animationController;
    private RollDicePresenter presenter;

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
        this.boardRenderer = new BoardRenderer();
        this.playerMovementAnimator = new PlayerMovementAnimator();
        this.statsPanel = new PlayerStatsView(gameBoard.getPlayers());

        // Initialize Clean Architecture Dice Components
        setupDiceComponents();

        setPreferredSize(new java.awt.Dimension(Constants.BOARD_PANEL_WIDTH,
                Constants.BOARD_PANEL_HEIGHT));
        setupUI();
    }

    private void setupDiceComponents() {
        // Create the clean architecture components
        this.diceViewModel = new DiceViewModel();

        // Animation callback that will be called when dice roll is complete
        RollDicePresenter.DiceAnimationCallback animationCallback =
                this::onDiceRollComplete;

        // Create presenter
        this.presenter = new RollDicePresenter(diceViewModel, animationCallback);

        // Create data access
        DiceRandomDataAccessInterface randomDataAccess = new RandomNumberGenerator();

        // Create interactor
        RollDiceInteractor interactor = new RollDiceInteractor(presenter, randomDataAccess);

        // Create controller
        this.rollDiceController = new RollDiceController(interactor);

        // Create animation controller
        this.animationController = new DiceAnimationController(diceViewModel, this::repaint);
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
                // Updated to use new dice view model
                boardRenderer.drawBoard(g, gameBoard, diceViewModel);
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

        // wire the button - UPDATED METHOD
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

    // UPDATED: New clean architecture dice rolling
    private void handleRollDice() {
        rollDiceButton.setEnabled(false);
        presenter.presentDiceRolling(); // Set rolling state

        // Start animation first
        animationController.startAnimation(() -> {
            // When animation completes, actually roll the dice
            rollDiceController.rollDice();
        });
    }

    // NEW: Called by presenter when dice roll is complete
    public void onDiceRollComplete(DiceViewModel viewModel) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        int diceSum = viewModel.getSum();

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

    private void handleLandingOnProperty() {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        int position = currentPlayer.getPosition();
        PropertyTile property = gameBoard.getPropertyAt(position);

        if (property != null) {
            property.onLanding(currentPlayer);
        }

        // Re-enable roll dice button after everything is complete
        rollDiceButton.setEnabled(true);
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

        SwingUtilities.invokeLater(() -> {
            new EndScreen(
                    gameBoard.getPlayers(),
                    gameBoard.getGameEndReason(),
                    gameBoard.getCurrentRound()
            );
        });
    }

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