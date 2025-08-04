package main.view;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;
import main.entity.*;
import main.interface_adapter.PlayerStats.PlayerStatsController;
import main.interface_adapter.PlayerStats.PlayerStatsPresenter;
import main.interface_adapter.PlayerStats.PlayerStatsViewModel;
import main.use_case.Game.GameNextTurn;
import main.Constants.Constants;
import main.use_case.PlayerStats.PlayerStatsInputBoundary;
import main.use_case.PlayerStats.PlayerStatsInteractor;
import main.use_case.Tiles.Property.PropertyPurchaseUseCase;
import main.entity.tiles.Tile;
import main.use_case.Game.GameMoveCurrentPlayer;
import main.interface_adapter.Property.PropertyPresenter;
import main.interface_adapter.Property.PropertyViewModel.*;
import main.interface_adapter.Property.PropertyPurchaseController;
import main.interface_adapter.Property.RentPaymentController;
import main.use_case.Tiles.OnLandingUseCase;
import main.use_case.Tiles.OnLandingController;
import main.use_case.Tiles.Property.RentPaymentUseCase;
import java.util.List;
import main.view.Tile.TileView;
import main.view.Tile.PropertyTileView;
import main.view.Tile.StockMarketTileView;
import main.interface_adapter.Tile.PropertyTileViewModel;
import main.interface_adapter.Tile.StockMarketTileViewModel;
import org.jetbrains.annotations.NotNull;


import javax.swing.*;
import java.awt.*;

/**
 * BoardView is a JPanel that represents the main.view of the game board.
 * Note: THIS IS NOT THE ENTIRE WINDOW, just the board itself.
 */
public class BoardView extends JPanel {
    // Components responsible for specific functionality
    private final Game game;
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
    private final PlayerStatsViewModel playerStatsViewModel;
    private final PlayerStatsPresenter playerStatsPresenter;
    private final PlayerStatsInputBoundary playerStatsInputBoundary;
    private final PlayerStatsController playerStatsController;
    private final PlayerStatsView statsPanel;

    public BoardView(Game game) {
        this.game = game;
        this.diceAnimator = new DiceAnimator();
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
        this.playerStatsViewModel = new PlayerStatsViewModel();
        this.playerStatsPresenter = new PlayerStatsPresenter(playerStatsViewModel);
        this.playerStatsInputBoundary = new PlayerStatsInteractor(playerStatsPresenter);
        this.playerStatsController = new PlayerStatsController(playerStatsInputBoundary);
        this.statsPanel = new PlayerStatsView(playerStatsViewModel, playerStatsController);
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
        JPanel boardPanel = new JPanel(null); // Use null layout for manual positioning
        boardPanel.setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT));
        boardPanel.setBackground(Color.LIGHT_GRAY);

        // Add TileView components
        int startX = 50;
        int startY = 8;
        int tilesPerSide = (game.getTiles().size()-4) / 4 + 2;
        int tileSize = Constants.BOARD_SIZE / tilesPerSide;
        List<Tile> tiles = game.getTiles();
        for (int i = 0; i < game.getTileCount(); i++) {
            Point pos = game.getTilePosition(i, startX, startY, tileSize);
            TileView tileView = drawTile(tiles, i);

            tileView.setBounds(pos.x, pos.y, tileSize, tileSize);
            boardPanel.add(tileView);
        }

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

    @NotNull
    private static TileView drawTile(List<Tile> tiles, int i) {
        Tile tile = tiles.get(i);

        TileView tileView;
        if (tile instanceof PropertyTile property) {
            PropertyTileViewModel viewModel = new PropertyTileViewModel(
                    property.getName(),
                    property.getPrice(),
                    property.isOwned() ? property.getOwner().getName() : "",
                    property.getRent(),
                    property.isOwned() ? property.getOwner().getColor() : Color.WHITE
            );
            tileView = new PropertyTileView(viewModel);
        } else {
            StockMarketTileViewModel viewModel = new StockMarketTileViewModel();
            tileView = new StockMarketTileView(viewModel);
        }
        return tileView;
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
     * Handle dice roll - delegate to DiceAnimator
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
        return (PropertyTile) game.getTiles().stream()
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
