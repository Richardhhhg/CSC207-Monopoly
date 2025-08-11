package main.use_case.game;

import main.constants.Constants;
import main.entity.Game;
import main.entity.tiles.GoTile;
import main.entity.tiles.PropertyTile;
import main.entity.tiles.StockMarketTile;
import main.entity.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Use case for initializing tiles in the game.
 * Simplified to focus only on tile creation with provided data.
 */
public class GameInitializeTiles {
    private final PropertyDataSource dataSource;

    public GameInitializeTiles(PropertyDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Tile> executeSmallBoard() {
        return createTiles(Constants.SMALL_BOARD_SIZE);
    }

    public List<Tile> executeMediumBoard() {
        return createTiles(Constants.MEDIUM_BOARD_SIZE);
    }

    public List<Tile> executeLargeBoard() {
        return createTiles(Constants.LARGE_BOARD_SIZE);
    }

    private List<Tile> createTiles(int boardSize) {
        List<PropertyDataSource.PropertyInfo> propertyData;

        try {
            propertyData = dataSource.getPropertyData();
        } catch (RuntimeException e) {
            // If JSON fails, fall back to using FallbackPropertyDataSource
            PropertyDataSource fallback = new main.infrastructure.FallbackPropertyDataSource();
            propertyData = fallback.getPropertyData();
        }
    }

    private List<Tile> createTiles(int boardSize, List<PropertyDataSource.PropertyInfo> propertyData) {
        List<Tile> tiles = new ArrayList<>();

        // Always start with Go tile
        tiles.add(new GoTile());

        // Calculate tile distribution
        int remainingTiles = boardSize - 1;
        int stockMarketTiles = Math.max(1, remainingTiles * 3 / 10);
        int propertyTiles = remainingTiles - stockMarketTiles;

        // Add property tiles using data source
        for (int i = 0; i < propertyTiles && i < propertyData.size(); i++) {
            PropertyDataSource.PropertyInfo info = propertyData.get(i);
            tiles.add(new PropertyTile(info.getName(), info.getBasePrice(), Constants.PLACEHOLDER_RENT));
        }

        // Add stock market tiles distributed throughout
        for (int i = 0; i < stockMarketTiles; i++) {
            int insertPosition = Math.min(tiles.size(), 1 + (i + 1) * (remainingTiles / stockMarketTiles));
            tiles.add(insertPosition, new StockMarketTile());
        }

        return tiles;
    }
}
