package main.view;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;
import main.entity.*;
import main.use_case.Game.GameNextTurn;
import main.Constants.Constants;
import main.use_case.Tiles.Property.PropertyPurchaseUseCase;
import main.use_case.Tile;
import main.use_case.Game.GameMoveCurrentPlayer;
import main.interface_adapter.Property.PropertyPresenter;
import main.interface_adapter.Property.PropertyViewModel.*;
import main.interface_adapter.Property.PropertyPurchaseController;
import main.interface_adapter.Property.RentPaymentController;
import main.use_case.Tiles.OnLandingUseCase;
import main.use_case.Tiles.OnLandingController;
import main.use_case.Tiles.Property.RentPaymentUseCase;

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
    private final DiceAnimator diceAnimator;
    private final PlayerMovementAnimator playerMovementAnimator;
    private JFrame parentFrame; // Reference to parent frame for end screen
    private final GameMoveCurrentPlayer gameMoveCurrentPlayer;

    // Controllers and Presenters following Clean Architecture
    private final PropertyPresenter propertyPresenter;
    private final PropertyPurchaseController propertyPurchaseController;
    private final RentPaymentController rentPaymentController;
    private final OnLandingController onLandingController;

    // ——— Dice UI & state ———
    private final JButton rollDiceButton = new JButton("Roll Dice");
    private final JButton endTurnButton = new JButton("End Turn");
    private final JButton stockMarketButton = new JButton("Stock Market");
    private final JLabel roundLabel = new JLabel("Round: 1");
    private final JLabel turnLabel = new JLabel("Turns: 0");

    // player stats panel
    private final main.interface_adapter.PlayerStats.PlayerStatsViewModel playerStatsViewModel;
    private final main.interface_adapter.PlayerStats.PlayerStatsPresenter playerStatsPresenter;
    private final main.use_case.PlayerStats.PlayerStatsInputBoundary playerStatsInputBoundary;
    private final main.interface_adapter.PlayerStats.PlayerStatsController playerStatsController;
    private final main.view.PlayerStatsView statsPanel;

    public BoardView() {
        this.game = new Game();
        this.diceAnimator = new DiceAnimator();
        this.boardRenderer = new BoardRenderer();
        this.playerMovementAnimator = new PlayerMovementAnimator();
        this.gameMoveCurrentPlayer = new GameMoveCurrentPlayer(game);

        // Initialize Clean Architecture components in proper order
        this.propertyPresenter = new PropertyPresenter();

        // Controllers act as interactors and depend on presenter
        this.propertyPurchaseController = new PropertyPurchaseController(propertyPresenter);
        this.rentPaymentController = new RentPaymentController(propertyPresenter);

        // Create use cases
        PropertyPurchaseUseCase propertyPurchaseUseCase = new PropertyPurchaseUseCase(propertyPresenter);
        RentPaymentUseCase rentPaymentUseCase = new RentPaymentUseCase(propertyPresenter);

        // Create OnLanding components
        OnLandingUseCase onLandingUseCase = new OnLandingUseCase(propertyPurchaseUseCase, rentPaymentUseCase);
        this.onLandingController = new OnLandingController(onLandingUseCase);

        // StatsViewPanel
        this.playerStatsViewModel = new main.interface_adapter.PlayerStats.PlayerStatsViewModel();
        this.playerStatsPresenter =
                new main.interface_adapter.PlayerStats.PlayerStatsPresenter(playerStatsViewModel);
        this.playerStatsInputBoundary =
                new main.use_case.PlayerStats.PlayerStatsInteractor(playerStatsPresenter);
        this.playerStatsController =
                new main.interface_adapter.PlayerStats.PlayerStatsController(playerStatsInputBoundary);
        this.statsPanel = new main.view.PlayerStatsView(playerStatsViewModel, playerStatsController);
        this.statsPanel.refreshFrom(this.game);


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
                boardRenderer.drawBoard(g, game, diceAnimator);
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

    /**
     * Delegate stock market display to appropriate component
     */
    private void displayStockMarket() {
        StockMarketView stockMarketView = new StockMarketView(game.getCurrentPlayer());
        stockMarketView.setVisible(true);
    }

    /**
     * Update game status display
     */
    private void updateStatusLabels() {
        roundLabel.setText("Round: " + game.getCurrentRound());
        turnLabel.setText("Turns: " + game.getTotalTurns());
    }

    /**
     * Handle dice roll - delegate to DiceController
     */
    private void handleRollDice() {
        rollDiceButton.setEnabled(false);

        // Use DiceController for dice animation and logic
        diceAnimator.startDiceAnimation(
            this::repaint, // Animation frame callback
            this::onDiceRollComplete // Completion callback
        );
    }

    /**
     * Handle dice roll completion - delegate movement to game logic
     */
    private void onDiceRollComplete() {
        Player currentPlayer = game.getCurrentPlayer();
        int diceSum = diceAnimator.getLastDiceSum();

        // Handle crossing GO bonus using GameBoard logic
        gameMoveCurrentPlayer.execute(diceSum);

        // Update player stats finish lap bonus
        refreshStats();

        // Use PlayerMovementAnimator for movement animation
        playerMovementAnimator.animatePlayerMovement(
            currentPlayer,
            diceSum,
            game.getTileCount(),
            this::repaint, // Movement step callback
            this::onPlayerMovementComplete // Completion callback
        );
    }

    /**
     * Handle player movement completion - delegate to tile logic
     */
    private void onPlayerMovementComplete() {
        handleLandingOnTile();
    }

    /**
     * Handle landing on tile - delegate to OnLandingController
     */
    private void handleLandingOnTile() {
        Player currentPlayer = game.getCurrentPlayer();
        int position = currentPlayer.getPosition();
        Tile tile = game.getPropertyAt(position);

        if (tile != null) {
            // Use OnLandingController to handle all tile landing logic
            onLandingController.handleLanding(currentPlayer, tile);

            // update stat to show addition and deduction amount of rent
            refreshStats();

            // Check if presenter has any view models to display
            checkForPresenterUpdates();
        }
    }

    /**
     * Check if the presenter has any view models ready and display them
     */
    private void checkForPresenterUpdates() {
        // Check for purchase dialog
        PurchaseDialogViewModel purchaseDialog = propertyPresenter.getPurchaseDialogViewModel();
        if (purchaseDialog != null) {
            PropertyPurchaseUseCase.PurchaseResultCallback callback = propertyPresenter.getPurchaseCallback();

            // Find the actual player and property objects
            Player player = findPlayerByName(purchaseDialog.playerName);
            PropertyTile property = findPropertyByName(purchaseDialog.propertyName);

            propertyPurchaseController.showPurchaseDialog(purchaseDialog, callback, player, property, this);
            propertyPresenter.clearPurchaseDialog();
        }

        // Check for property purchased notification
        PropertyPurchasedViewModel propertyPurchased = propertyPresenter.getPropertyPurchasedViewModel();
        if (propertyPurchased != null) {
            updateAfterPropertyPurchased(propertyPurchased);
            propertyPresenter.clearPropertyPurchased();
        }

        // Check for rent payment notification
        RentPaymentViewModel rentPayment = propertyPresenter.getRentPaymentViewModel();
        if (rentPayment != null) {
            rentPaymentController.showRentPaymentNotification(rentPayment, this);
            updateAfterPropertyPurchased(null); // Just update the UI
            propertyPresenter.clearRentPayment();
        }
    }

    /**
     * Handle end turn - delegate to game logic
     */
    private void handleEndTurn() {
        new GameNextTurn(game).execute();
        updateStatusLabels();
        refreshStats();

        if (game.isGameOver()) {
            showEndScreen();
            return;
        }

        rollDiceButton.setEnabled(true);
        repaint();
    }

    /**
     * Show end screen when game is over
     */
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
        return diceAnimator.getLastDiceSum();
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

    public void updateAfterPropertyPurchased(PropertyPurchasedViewModel viewModel) {
        // Update UI after property purchase or rent payment
        repaint(); // Trigger board repaint to show ownership change
    }

    // Helper methods for finding entities (needed for legacy popup interface)
    private Player findPlayerByName(String name) {
        return game.getPlayers().stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private PropertyTile findPropertyByName(String name) {
        return game.getTiles().stream()
                .filter(tile -> tile.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private void refreshStats() { //everytime theres a change in money
        if (this.statsPanel != null) {
            this.statsPanel.refreshFrom(this.game);
        }
    }
}
