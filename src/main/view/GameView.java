package main.view;

import main.Constants.Constants;
import main.entity.Game;
import main.entity.players.Player;
import main.entity.tiles.PropertyTile;
import main.entity.tiles.Tile;
import main.interface_adapter.PlayerStats.PlayerStatsController;
import main.interface_adapter.PlayerStats.PlayerStatsPresenter;
import main.interface_adapter.PlayerStats.PlayerStatsViewModel;
import main.interface_adapter.Property.PropertyPresenter;
import main.interface_adapter.Property.PropertyPurchaseController;
import main.interface_adapter.Property.PropertyViewModel;
import main.interface_adapter.Property.RentPaymentController;
import main.use_case.Game.GameMoveCurrentPlayer;
import main.use_case.Game.GameNextTurn;
import main.use_case.PlayerStats.PlayerStatsInteractor;
import main.use_case.Tiles.OnLandingController;
import main.use_case.Tiles.OnLandingUseCase;
import main.use_case.Tiles.Property.PropertyPurchaseUseCase;
import main.use_case.Tiles.Property.RentPaymentUseCase;

import java.util.List;

import javax.swing.*;
import java.awt.*;

/**
 * This Class displays the entire game view.
 */
public class GameView extends JFrame{
    private Game game;
    private JLayeredPane layeredPane;
    private DiceAnimator diceAnimator;
    private PlayerMovementAnimator playerMovementAnimator;
    private GameMoveCurrentPlayer gameMoveCurrentPlayer;

    private PlayerStatsViewModel playerStatsViewModel;
    private PlayerStatsPresenter playerStatsPresenter;
    private PlayerStatsInteractor playerStatsInputBoundary;
    private PlayerStatsController playerStatsController;
    private PlayerStatsView statsPanel;

    private final PropertyPresenter propertyPresenter;
    private final PropertyPurchaseController propertyPurchaseController;
    private final RentPaymentController rentPaymentController;
    private final OnLandingController onLandingController;


    // TODO: There is a ton of coupling here, fix it
    public GameView() {
        super(Constants.GAME_TITLE);
        this.game = new Game();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBackground(Color.lightGray);
        setLayout(null);

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

        this.diceAnimator = new DiceAnimator();
        this.playerMovementAnimator = new PlayerMovementAnimator();
        this.gameMoveCurrentPlayer = new GameMoveCurrentPlayer(game);

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        setContentPane(layeredPane);

        drawBoard();
        drawDice();
        drawPlayerPortrait();
        drawPlayers();
        drawButtonPanel();
        drawStatsPanel();

        setVisible(true);
    }

    // TODO: This should probably be like separate class - Richard
    private void drawBoard() {
        BoardView boardView = new BoardView(game, this);
        boardView.setBounds(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        layeredPane.add(boardView, Integer.valueOf(0));
    }

    // TODO: There is a lot of repeated code, fix it - Richard
    private void drawDice() {
        int startX = 50;
        int startY = 8;
        int tilesPerSide = (game.getTiles().size() - 4) / 4 + 2;
        int tileSize = Constants.BOARD_SIZE / tilesPerSide;

        int centerX = startX + Constants.BOARD_SIZE / 2;
        int centerY = startY + Constants.BOARD_SIZE / 2;

        DiceView diceView = new DiceView(diceAnimator, tileSize);

        // Center the DiceView
        int viewWidth = diceView.getWidth();
        int viewHeight = diceView.getHeight();
        diceView.setBounds(centerX - viewWidth / 2, centerY - viewHeight / 2, viewWidth, viewHeight);

        layeredPane.add(diceView, Integer.valueOf(2)); // Layer 2 = above board and players
        layeredPane.repaint();
    }

    private void drawButtonPanel() {
        ButtonPanelView buttonPanelView = new ButtonPanelView(game,
                () -> {
                    handleRollDice();
                    repaint();
                },
                () -> {
                    handleEndTurn();
                    repaint();
                },
                () -> {
                    displayStockMarket();
                    repaint();
                }
                );
        buttonPanelView.setBounds(Constants.GAME_WIDTH-200, 0, 200, Constants.GAME_HEIGHT);
        layeredPane.add(buttonPanelView, Integer.valueOf(4));
        layeredPane.repaint();
    }

    // TODO: There is a lot of repeated code, fix it - Richard
    private void drawPlayerPortrait() {
        int startX = 50;
        int startY = 8;

        Player currentPlayer = game.getCurrentPlayer();
        if (currentPlayer != null && currentPlayer.getPortrait() != null) {
            int tilesPerSide = (game.getTiles().size() - 4) / 4 + 2;
            int tileSize = Constants.BOARD_SIZE / tilesPerSide;

            int centerX = startX + Constants.BOARD_SIZE / 2;
            int centerY = startY + Constants.BOARD_SIZE / 2;

            int portraitSize = tileSize; // same as dice size
            PlayerPortraitView portraitView = new PlayerPortraitView(currentPlayer.getPortrait(), "Current Player:", portraitSize);

            // Offset to top-right of center area (as in original)
            int x = centerX - tileSize - 10 + 80;
            int y = centerY - tileSize / 2 - 150;

            portraitView.setBounds(x, y, portraitSize, portraitSize + 30); // height includes label
            layeredPane.add(portraitView, Integer.valueOf(2));
        }
    }

    private void drawStatsPanel() {
        this.playerStatsViewModel = new PlayerStatsViewModel();
        this.playerStatsPresenter = new PlayerStatsPresenter(playerStatsViewModel);
        this.playerStatsInputBoundary = new PlayerStatsInteractor(playerStatsPresenter);
        this.playerStatsController = new PlayerStatsController(playerStatsInputBoundary);
        this.statsPanel = new PlayerStatsView(playerStatsViewModel, playerStatsController);
        this.statsPanel.refreshFrom(this.game);
        this.statsPanel.setBounds(Constants.GAME_WIDTH/2, 0, 600, Constants.GAME_HEIGHT);
        layeredPane.add(statsPanel, Integer.valueOf(3));
        layeredPane.repaint();
    }

    // TODO: This should probably be like separate class - Richard
    // TODO: This is really messy, fix later
    private void drawPlayers() {
        // draws players
        int startX = 50;
        int startY = 8;
        int tilesPerSide = (game.getTiles().size()-4) / 4 + 2;
        int tileSize = Constants.BOARD_SIZE / tilesPerSide;

        List<Player> players = game.getPlayers();

        for (Player player : game.getPlayers()) {
            PlayerView playerView = new PlayerView(player.getColor());
            Point pos = game.getTilePosition(player.getPosition(), startX, startY, tileSize);
            int offsetX = (players.indexOf(player) % 2) * 20;
            int offsetY = (players.indexOf(player) / 2) * 20;
            playerView.setBounds(pos.x + offsetX, pos.y + offsetY, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
            layeredPane.add(playerView, Integer.valueOf(1));
        }
    }

    private void displayStockMarket() {
        StockMarketView stockMarketView = new StockMarketView(game.getCurrentPlayer());
        stockMarketView.setVisible(true);
    }

    private void handleRollDice() {
        ButtonPanelView.getRollDiceButton().setEnabled(false);

        // Use DiceController for dice animation and logic
        diceAnimator.startDiceAnimation(
                this::repaint, // Animation frame callback
                this::onDiceRollComplete // Completion callback
        );
    }

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

    private void onPlayerMovementComplete() {
        handleLandingOnTile();
    }

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

    private void checkForPresenterUpdates() {
        // Check for purchase dialog
        PropertyViewModel.PurchaseDialogViewModel purchaseDialog = propertyPresenter.getPurchaseDialogViewModel();
        if (purchaseDialog != null) {
            PropertyPurchaseUseCase.PurchaseResultCallback callback = propertyPresenter.getPurchaseCallback();

            // Find the actual player and property objects
            Player player = findPlayerByName(purchaseDialog.playerName);
            PropertyTile property = findPropertyByName(purchaseDialog.propertyName);

            propertyPurchaseController.showPurchaseDialog(purchaseDialog, callback, player, property, this);
            propertyPresenter.clearPurchaseDialog();
        }

        // Check for property purchased notification
        PropertyViewModel.PropertyPurchasedViewModel propertyPurchased = propertyPresenter.getPropertyPurchasedViewModel();
        if (propertyPurchased != null) {
            updateAfterPropertyPurchased(propertyPurchased);
            propertyPresenter.clearPropertyPurchased();
        }

        // Check for rent payment notification
        PropertyViewModel.RentPaymentViewModel rentPayment = propertyPresenter.getRentPaymentViewModel();
        if (rentPayment != null) {
            rentPaymentController.showRentPaymentNotification(rentPayment, this);
            updateAfterPropertyPurchased(null); // Just update the UI
            propertyPresenter.clearRentPayment();
        }
    }

    public void updateAfterPropertyPurchased(PropertyViewModel.PropertyPurchasedViewModel viewModel) {
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

    private void handleEndTurn() {
        new GameNextTurn(game).execute();
        ButtonPanelView.updateStatus(
                game.getCurrentRound(),
                game.getCurrentPlayer().getName()
        );
        refreshStats();

        if (game.isGameOver()) {
            showEndScreen();
            return;
        }

        ButtonPanelView.getRollDiceButton().setEnabled(true);
        repaint();
    }

    private void showEndScreen() {
        // TODO: Idk if any of these buttons will even need to be manually disabled
//        ButtonPanelView.getRollDiceButton().setEnabled(false);
//        ButtonPanelView.getEndTurnButton().setEnabled(false);
//        ButtonPanelView.getStockMarketButton().setEnabled(false);

        this.setVisible(false);

        // Show the end screen
        SwingUtilities.invokeLater(() -> {
            new EndScreen(
                    game.getPlayers(),
                    game.getGameEndReason(),
                    game.getCurrentRound()
            );
        });
    }

    private void refreshStats() { //everytime theres a change in money
        if (this.statsPanel != null) {
            this.statsPanel.refreshFrom(this.game);
        }
    }
}
