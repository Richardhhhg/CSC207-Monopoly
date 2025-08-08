package main.use_case.Game;

import main.Constants.Constants;
import main.entity.tiles.GoTile;
import main.entity.tiles.PropertyTile;
import main.entity.tiles.StockMarketTile;
import main.entity.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Use case for initializing tiles in the game.
 * Follows Single Responsibility Principle - only handles tile creation logic.
 * Fallback logic delegated to dedicated fallback data source.
 */
public class GameInitializeTiles {
    private final PropertyDataSource primaryDataSource;
    private final PropertyDataSource fallbackDataSource;

    // Board size configurations (must be multiples of 4)
    private static final int SMALL_BOARD_SIZE = 20;
    private static final int MEDIUM_BOARD_SIZE = 24;
    private static final int LARGE_BOARD_SIZE = 28;

    public GameInitializeTiles(PropertyDataSource primaryDataSource, PropertyDataSource fallbackDataSource) {
        this.primaryDataSource = primaryDataSource;
        this.fallbackDataSource = fallbackDataSource;
    }

    /**
     * Create a small board (20 tiles)
     */
    public List<Tile> executeSmallBoard() {
        return execute(SMALL_BOARD_SIZE);
    }

    /**
     * Create a medium board (24 tiles) - default
     */
    public List<Tile> executeMediumBoard() {
        return execute(MEDIUM_BOARD_SIZE);
    }

    /**
     * Create a large board (28 tiles)
     */
    public List<Tile> executeLargeBoard() {
        return execute(LARGE_BOARD_SIZE);
    }

    private List<Tile> execute(int boardSize) {
        List<PropertyDataSource.PropertyInfo> propertyData = getPropertyData();
        return createTiles(boardSize, propertyData);
    }

    /**
     * Fallback logic now properly delegates to fallback data source
     */
    private List<PropertyDataSource.PropertyInfo> getPropertyData() {
        try {
            List<PropertyDataSource.PropertyInfo> data = primaryDataSource.getPropertyData();
            return data != null && !data.isEmpty() ? data : fallbackDataSource.getPropertyData();
        } catch (Exception e) {
            // Any exception from primary data source triggers fallback
            return fallbackDataSource.getPropertyData();
        }
    }

    private List<Tile> createTiles(int boardSize, List<PropertyDataSource.PropertyInfo> propertyData) {
        List<Tile> tiles = new ArrayList<>();

        // Always start with Go tile at position 0
        tiles.add(new GoTile());

        // Calculate how many tiles we need to fill
        int remainingTiles = boardSize - 1;
        int stockMarketTiles = Math.max(1, remainingTiles * 3 / 10);
        int propertyTiles = remainingTiles - stockMarketTiles;

        // Add property tiles
        for (int i = 0; i < propertyTiles; i++) {
            String propertyName = generatePropertyName(i + 1, propertyData);
            int price = generatePropertyPrice(i + 1, propertyData);
            tiles.add(new PropertyTile(propertyName, price, Constants.PLACEHOLDER_RENT));
        }

        // Add stock market tiles distributed throughout the board
        int stockMarketInterval = remainingTiles / stockMarketTiles;
        for (int i = 0; i < stockMarketTiles; i++) {
            int insertPosition = Math.min(tiles.size(), 1 + (i + 1) * stockMarketInterval);
            tiles.add(insertPosition, new StockMarketTile());
        }

        return tiles;
    }

    private String generatePropertyName(int index, List<PropertyDataSource.PropertyInfo> propertyData) {
        if (index <= propertyData.size()) {
            return propertyData.get(index - 1).getName();
        } else {
            return "Property " + index;
        }
    }

    private int generatePropertyPrice(int index, List<PropertyDataSource.PropertyInfo> propertyData) {
        if (index <= propertyData.size()) {
            int basePrice = propertyData.get(index - 1).getBasePrice();

            // Add some variation for realism
            if (index % 5 == 0) {
                basePrice += 40; // Premium properties
            }

            return basePrice;
        } else {
            // Fallback for properties beyond data
            int basePrice = 60 + (index - 1) * 20;
            return Math.min(basePrice, 400);
        }
    }
}
