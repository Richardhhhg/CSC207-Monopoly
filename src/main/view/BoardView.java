package main.view;

import main.entity.players.Player;
import main.entity.tiles.PropertyTile;
import main.entity.*;
import main.Constants.Constants;
import main.entity.tiles.StockMarketTile;
import main.entity.tiles.Tile;
import java.util.HashMap;
import java.util.List;
import main.interface_adapter.Property.PropertyPresenter;
import main.interface_adapter.Property.PropertyPurchaseController;
import main.interface_adapter.Property.PropertyViewModel;
import main.interface_adapter.Property.RentPaymentController;
import main.interface_adapter.Tile.GoTileViewModel;
import main.use_case.Tiles.OnLandingController;
import main.use_case.Tiles.OnLandingUseCase;
import main.use_case.Tiles.Property.PropertyPurchaseUseCase;
import main.use_case.Tiles.Property.RentPaymentUseCase;
import main.view.Tile.GoTileView;
import main.view.Tile.TileView;
import main.view.Tile.PropertyTileView;
import main.view.Tile.StockMarketTileView;
import main.interface_adapter.Tile.PropertyTileViewModel;
import main.interface_adapter.Tile.StockMarketTileViewModel;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

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
        PropertyPurchaseUseCase propertyPurchaseUseCase = new PropertyPurchaseUseCase(propertyPresenter);
        RentPaymentUseCase rentPaymentUseCase = new RentPaymentUseCase(propertyPresenter);

        // Create OnLanding components
        OnLandingUseCase onLandingUseCase = new OnLandingUseCase(propertyPurchaseUseCase, rentPaymentUseCase);
        this.onLandingController = new OnLandingController(onLandingUseCase);


        setPreferredSize(new java.awt.Dimension(Constants.BOARD_PANEL_WIDTH,
                Constants.BOARD_PANEL_HEIGHT));
        setupUI();
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

            tileViewMap.put(tiles.get(i).getName(), tileView);
        }

        add(boardPanel, BorderLayout.WEST);
    }

    @NotNull
    private static TileView drawTile(List<Tile> tiles, int index) {
        TileView tileView;
        Tile tile = tiles.get(index);
        if (tile instanceof PropertyTile property) {
            PropertyTileViewModel viewModel = new PropertyTileViewModel(
                    property.getName(),
                    property.getPrice(),
                    property.isOwned() ? property.getOwner().getName() : "",
                    property.getRent(),
                    property.isOwned() ? property.getOwner().getColor() : Color.WHITE
            );
            tileView = new PropertyTileView(viewModel);
        } else if (tile instanceof StockMarketTile) {
            StockMarketTileViewModel viewModel = new StockMarketTileViewModel();
            tileView = new StockMarketTileView(viewModel);
        } else {
            GoTileViewModel viewModel = new GoTileViewModel();
            tileView = new GoTileView(viewModel);
        }
        return tileView;
    }

    public void handleLandingOnTile() {
        Player currentPlayer = game.getCurrentPlayer();
        int position = currentPlayer.getPosition();
        Tile tile = game.getPropertyAt(position);

        if (tile != null) {
            // Use OnLandingController to handle all tile landing logic
            onLandingController.handleLanding(currentPlayer, tile);

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
        if (viewModel != null) {
            String propertyName = viewModel.propertyName;
            TileView oldTile = tileViewMap.get(propertyName);

            if (oldTile != null) {
                this.remove(oldTile); // remove old one
                int tilePosition = getTilePosition(propertyName);
                Tile tile = game.getPropertyAt(tilePosition);

                TileView newTile = drawTile(game.getTiles(), tilePosition); // regenerate based on new state
                Point pos = game.getTilePosition(tilePosition, 50, 8, Constants.BOARD_SIZE / ((game.getTiles().size() - 4) / 4 + 2));
                newTile.setBounds(pos.x, pos.y, oldTile.getWidth(), oldTile.getHeight());

                this.add(newTile);
                tileViewMap.put(propertyName, newTile);
                this.revalidate();
                this.repaint();
            } else {
                // fallback if viewModel is null (e.g. rent case)
                this.repaint();
            }
        }
    }

    private int getTilePosition(String propertyName) {
        Tile tile = findPropertyByName(propertyName);
        if (tile != null) {
            return game.getTiles().indexOf(tile);
        }
        return -1; // Not found
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
                .filter(tile -> tile.getName().equals(name) && tile instanceof PropertyTile)
                .map(tile -> (PropertyTile) tile)
                .findFirst()
                .orElse(null);
    }
}
