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
import main.entity.players.AbstractPlayer;
import main.entity.tiles.AbstractTile;
import main.entity.tiles.PropertyTile;
import main.entity.tiles.StockMarketTile;
import main.interface_adapter.property.PropertyPresenter;
import main.interface_adapter.property.PropertyPurchaseController;
import main.interface_adapter.property.PropertyViewModel;
import main.interface_adapter.property.RentPaymentController;
import main.interface_adapter.tile.GoTileViewModel;
import main.interface_adapter.tile.PropertyTileViewModel;
import main.interface_adapter.tile.StockMarketTileViewModel;
import main.use_case.tiles.OnLandingController;
import main.use_case.tiles.OnLandingUseCase;
import main.use_case.tiles.property.PropertyPurchaseUseCase;
import main.use_case.tiles.property.RentPaymentUseCase;
import main.view.tile.AbstractTileView;
import main.view.tile.GoTileView;
import main.view.tile.PropertyTileView;
import main.view.tile.StockMarketTileView;

/**
 * BoardView is a JPanel that represents the main view of the game board.
 * Note: THIS IS NOT THE ENTIRE WINDOW, just the board itself.
 */
public class BoardView extends JPanel {
    private final Game game;

    private final PropertyPresenter propertyPresenter;
    private final PropertyPurchaseController propertyPurchaseController;
    private final RentPaymentController rentPaymentController;
    private final OnLandingController onLandingController;

    private final Map<String, AbstractTileView> tileViewMap = new HashMap<>();

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
        setupUi();
    }

    /**
     * Sets up the UI components for the board view.
     */
    private void setupUi() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT));

        // Create board panel
        final JPanel boardPanel = new JPanel(null);
        boardPanel.setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT));
        boardPanel.setBackground(Color.LIGHT_GRAY);

        final int tilesPerSide = (game.getTiles().size() - 4) / 4 + 2;
        final int tileSize = Constants.BOARD_SIZE / tilesPerSide;
        final List<AbstractTile> tiles = game.getTiles();

        for (int i = 0; i < game.getTileCount(); i++) {
            final Point pos = game.getTilePosition(i, Constants.START_X, Constants.START_Y, tileSize);
            final AbstractTileView abstractTileView = drawTile(tiles, i);

            abstractTileView.setBounds(pos.x, pos.y, tileSize, tileSize);
            boardPanel.add(abstractTileView);

            tileViewMap.put(tiles.get(i).getName(), abstractTileView);
        }

        add(boardPanel, BorderLayout.WEST);
    }

    @NotNull
    private static AbstractTileView drawTile(List<AbstractTile> tiles, int index) {
        final AbstractTileView abstractTileView;
        final AbstractTile tile = tiles.get(index);

        String ownerName = "";
        Color ownerColor = Color.WHITE;
        if (tile instanceof PropertyTile property) {
            if (property.isOwned()) {
                ownerName = property.getOwner().getName();
                ownerColor = property.getOwner().getColor();
            }

            final PropertyTileViewModel viewModel = new PropertyTileViewModel(
                    property.getName(),
                    property.getPrice(),
                    ownerName,
                    property.getRent(),
                    ownerColor
            );
            abstractTileView = new PropertyTileView(viewModel);
        }
        else if (tile instanceof StockMarketTile) {
            final StockMarketTileViewModel viewModel = new StockMarketTileViewModel();
            abstractTileView = new StockMarketTileView(viewModel);
        }
        else {
            final GoTileViewModel viewModel = new GoTileViewModel();
            abstractTileView = new GoTileView(viewModel);
        }
        return abstractTileView;
    }

    /**
     * Handles the logic when a player lands on a tile.
     * This method is called when the player moves and lands on a tile.
     */
    public void handleLandingOnTile() {
        final AbstractPlayer currentPlayer = game.getCurrentPlayer();
        final int position = currentPlayer.getPosition();
        final AbstractTile tile = game.getPropertyAt(position);

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
            final AbstractPlayer player = findPlayerByName(purchaseDialog.getPlayerName());
            final PropertyTile property = findPropertyByName(purchaseDialog.getPropertyName());

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
        final PropertyViewModel.RentPaymentViewModel rentPayment =
                propertyPresenter.getRentPaymentViewModel();
        if (rentPayment != null) {
            rentPaymentController.showRentPaymentNotification(rentPayment, this);
            updateAfterPropertyPurchased(null);
            propertyPresenter.clearRentPayment();
        }
    }

    /**
     * Updates the board view after a property has been purchased.
     * This method regenerates the tile view based on the new state of the game.
     *
     * @param viewModel The view model containing information about the purchased property.
     */
    public void updateAfterPropertyPurchased(PropertyViewModel.PropertyPurchasedViewModel viewModel) {
        if (viewModel != null) {
            final String propertyName = viewModel.getPropertyName();
            final AbstractTileView oldTile = tileViewMap.get(propertyName);

            if (oldTile != null) {
                this.remove(oldTile);
                final int tilePosition = getTilePosition(propertyName);

                final AbstractTileView newTile = drawTile(game.getTiles(), tilePosition);
                final Point pos = game.getTilePosition(tilePosition, Constants.START_X, Constants.START_Y,
                        Constants.BOARD_SIZE / ((game.getTiles().size() - 4) / 4 + 2));
                newTile.setBounds(pos.x, pos.y, oldTile.getWidth(), oldTile.getHeight());

                this.add(newTile);
                tileViewMap.put(propertyName, newTile);
                this.revalidate();
                this.repaint();
            }
            else {
                this.repaint();
            }
        }
    }

    /**
     * Gets the position of a tile by its property name.
     * This is used to find the index of the tile in the game board.
     *
     * @param propertyName The name of the property tile.
     * @return The index of the tile in the game board, or -1 if not found.
     * @throws IllegalArgumentException if the property name does not correspond to a valid tile.
     */
    private int getTilePosition(String propertyName) throws IllegalArgumentException {
        final AbstractTile tile = findPropertyByName(propertyName);
        if (tile != null) {
            return game.getTiles().indexOf(tile);
        }
        else {
            throw new IllegalArgumentException("Property not found: " + propertyName);
        }
    }

    private AbstractPlayer findPlayerByName(String name) {
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
