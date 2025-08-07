package main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.jetbrains.annotations.NotNull;

import main.constants.Constants;
import main.entity.Game;
import main.entity.players.Player;
import main.entity.tiles.PropertyTile;
import main.entity.tiles.StockMarketTile;
import main.entity.tiles.Tile;
import main.interface_adapter.Property.PropertyPresenter;
import main.interface_adapter.Property.PropertyPurchaseController;
import main.interface_adapter.Property.PropertyViewModel;
import main.interface_adapter.Property.RentPaymentController;
import main.interface_adapter.Tile.GoTileViewModel;
import main.interface_adapter.Tile.PropertyTileViewModel;
import main.interface_adapter.Tile.StockMarketTileViewModel;
import main.use_case.Tiles.OnLandingController;
import main.use_case.Tiles.OnLandingUseCase;
import main.use_case.Tiles.Property.PropertyPurchaseUseCase;
import main.use_case.Tiles.Property.RentPaymentUseCase;
import main.view.Tile.GoTileView;
import main.view.Tile.PropertyTileView;
import main.view.Tile.StockMarketTileView;
import main.view.Tile.TileView;

/**
 * BoardView is a JPanel that represents the main.view of the game board.
 * Note: THIS IS NOT THE ENTIRE WINDOW, just the board itself.
 */
public class BoardView extends JPanel {
    // Components responsible for specific functionality
    private final Game game;

    private final PropertyPresenter propertyPresenter;
    private final PropertyPurchaseController propertyPurchaseController;
    private final RentPaymentController rentPaymentController;
    private final OnLandingController onLandingController;

    private final Map<String, TileView> tileViewMap = new HashMap<>();

    public BoardView(Game game) {
        this.game = game;
        this.propertyPresenter = new PropertyPresenter();

        // Controllers act as interactors and depend on presenter
        this.propertyPurchaseController = new PropertyPurchaseController(propertyPresenter);
        this.rentPaymentController = new RentPaymentController(propertyPresenter);

        // Create use cases
        final PropertyPurchaseUseCase propertyPurchaseUseCase = new PropertyPurchaseUseCase(propertyPresenter);
        final RentPaymentUseCase rentPaymentUseCase = new RentPaymentUseCase(propertyPresenter);

        // Create OnLanding components
        final OnLandingUseCase onLandingUseCase = new OnLandingUseCase(propertyPurchaseUseCase, rentPaymentUseCase);
        this.onLandingController = new OnLandingController(onLandingUseCase);

        setPreferredSize(new java.awt.Dimension(Constants.BOARD_PANEL_WIDTH,
                Constants.BOARD_PANEL_HEIGHT));
        setupBoard();
    }

    private void setupBoard() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT));

        // Create board panel
        final JPanel boardPanel = new JPanel(null);
        boardPanel.setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT));
        boardPanel.setBackground(Color.LIGHT_GRAY);

        // Add TileView components
        final int startX = 50;
        final int startY = 8;
        final int tilesPerSide = (game.getTiles().size() - Constants.BOARD_SIDES) / 4 + 2;
        final int tileSize = Constants.BOARD_SIZE / tilesPerSide;
        final List<Tile> tiles = game.getTiles();

        for (int i = 0; i < game.getTileCount(); i++) {
            final Point pos = game.getTilePosition(i, startX, startY, tileSize);
            final TileView tileView = drawTile(tiles, i);

            tileView.setBounds(pos.x, pos.y, tileSize, tileSize);
            boardPanel.add(tileView);

            tileViewMap.put(tiles.get(i).getName(), tileView);
        }

        add(boardPanel, BorderLayout.WEST);
    }

    @NotNull
    private static TileView drawTile(List<Tile> tiles, int index) {
        final TileView tileView;
        final Tile tile = tiles.get(index);
        if (tile instanceof PropertyTile property) {
            if (property.getOwner() == null) {
                final PropertyTileViewModel viewModel = new PropertyTileViewModel(
                        property.getName(),
                        property.getPrice(),
                        "",
                        property.getRent(),
                        Color.WHITE
                );
                tileView = new PropertyTileView(viewModel);
            }
            else {
                final PropertyTileViewModel viewModel = new PropertyTileViewModel(
                        property.getName(),
                        property.getPrice(),
                        property.getOwner().getName(),
                        property.getRent(),
                        property.getOwner().getColor()
                );
                tileView = new PropertyTileView(viewModel);
            }
        }
        else if (tile instanceof StockMarketTile) {
            final StockMarketTileViewModel viewModel = new StockMarketTileViewModel();
            tileView = new StockMarketTileView(viewModel);
        }
        else {
            final GoTileViewModel viewModel = new GoTileViewModel();
            tileView = new GoTileView(viewModel);
        }
        return tileView;
    }

    /**
     * Handles the logic when a player lands on a tile.
     */
    public void handleLandingOnTile() {
        final Player currentPlayer = game.getCurrentPlayer();
        final int position = currentPlayer.getPosition();
        final Tile tile = game.getPropertyAt(position);

        if (tile != null) {
            // Use OnLandingController to handle all tile landing logic
            onLandingController.handleLanding(currentPlayer, tile);

            // Check if presenter has any view models to display
            checkForPresenterUpdates();
        }
    }

    private void checkForPresenterUpdates() {
        // Check for purchase dialog
        final PropertyViewModel.PurchaseDialogViewModel purchaseDialog = propertyPresenter.getPurchaseDialogViewModel();
        if (purchaseDialog != null) {
            final PropertyPurchaseUseCase.PurchaseResultCallback callback = propertyPresenter.getPurchaseCallback();

            // Find the actual player and property objects
            final Player player = findPlayerByName(purchaseDialog.playerName);
            final PropertyTile property = findPropertyByName(purchaseDialog.propertyName);

            propertyPurchaseController.showPurchaseDialog(purchaseDialog, callback, player, property, this);
            propertyPresenter.clearPurchaseDialog();
        }

        // Check for property purchased notification
        final PropertyViewModel.PropertyPurchasedViewModel propertyPurchased =
                propertyPresenter.getPropertyPurchasedViewModel();
        if (propertyPurchased != null) {
            updateAfterPropertyPurchased(propertyPurchased);
            propertyPresenter.clearPropertyPurchased();
        }

        // Check for rent payment notification
        final PropertyViewModel.RentPaymentViewModel rentPayment = propertyPresenter.getRentPaymentViewModel();
        if (rentPayment != null) {
            rentPaymentController.showRentPaymentNotification(rentPayment, this);
            updateAfterPropertyPurchased(null);
            propertyPresenter.clearRentPayment();
        }
    }

    /**
     * Updates the board after a property has been purchased.
     * @param viewModel the view model containing the property purchase details.
     */
    public void updateAfterPropertyPurchased(PropertyViewModel.PropertyPurchasedViewModel viewModel) {
        if (viewModel != null) {
            final String propertyName = viewModel.propertyName;
            final TileView oldTile = tileViewMap.get(propertyName);

            if (oldTile != null) {
                this.remove(oldTile);
                final int tilePosition = getTilePosition(propertyName);

                final TileView newTile = drawTile(game.getTiles(), tilePosition);
                final Point pos = game.getTilePosition(tilePosition, Constants.START_X, Constants.START_Y,
                        Constants.BOARD_SIZE / ((game.getTiles().size() - Constants.BOARD_SIDES)
                                / Constants.BOARD_SIDES + 2));
                newTile.setBounds(pos.x, pos.y, oldTile.getWidth(), oldTile.getHeight());

                this.add(newTile);
                tileViewMap.put(propertyName, newTile);
                this.revalidate();
                this.repaint();
            }
            else {
                // fallback if viewModel is null (e.g. rent case)
                this.repaint();
            }
        }
    }

    private int getTilePosition(String propertyName) {
        final Tile tile = findPropertyByName(propertyName);
        return game.getTiles().indexOf(tile);
    }

    // Helper methods for finding entities (needed for legacy popup interface)
    private Player findPlayerByName(String name) {
        return game.getPlayers().stream()
                .filter(player -> player.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private PropertyTile findPropertyByName(String name) {
        return game.getTiles().stream()
                .filter(tile -> tile.getName().equals(name) && tile instanceof PropertyTile)
                .map(tile -> (PropertyTile) tile)
                .findFirst()
                .orElse(null);
    }
}
