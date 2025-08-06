package main.view;

import main.entity.tiles.PropertyTile;
import main.entity.*;
import main.Constants.Constants;
import main.entity.tiles.Tile;
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

    public BoardView(Game game, JFrame parentFrame) {
        this.game = game;

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
        }

        add(boardPanel, BorderLayout.WEST);
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
}
