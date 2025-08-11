package main.use_case.game;

import main.constants.Constants;
import main.entity.Game;
import main.entity.tiles.GoTile;
import main.entity.tiles.PropertyTile;
import main.entity.tiles.StockMarketTile;
import main.entity.tiles.Tile;
import main.infrastructure.FallbackPropertyDataSource;
import main.infrastructure.JsonPropertyDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Use case for initializing tiles in the game.
 * Follows Single Responsibility Principle - only handles tile creation logic.
 * Follows Dependency Inversion Principle - depends on PropertyDataSource abstraction.
 */
public class GameInitializeTiles {
    private final Game game;
    private final PropertyDataSource propertyDataSource;
    private final PropertyDataSource fallbackDataSource;

    // Board size configurations (must be multiples of 4)
    private static final int SMALL_BOARD_SIZE = 20;
    private static final int MEDIUM_BOARD_SIZE = 24;
    private static final int LARGE_BOARD_SIZE = 28;
    private static final int DEFAULT_BOARD_SIZE = MEDIUM_BOARD_SIZE;

    public GameInitializeTiles(Game game) {
        this(game, new JsonPropertyDataSource("/Board/properties.json"), new FallbackPropertyDataSource());
    }

    // Constructor for dependency injection (Open/Closed Principle)
    public GameInitializeTiles(Game game, PropertyDataSource propertyDataSource, PropertyDataSource fallbackDataSource) {
        this.game = game;
        this.propertyDataSource = propertyDataSource;
        this.fallbackDataSource = fallbackDataSource;
    }

    /**
     * Initialize the game tiles with the default board size.
     */
    public void execute() {
        execute(DEFAULT_BOARD_SIZE);
    }

    /**
     * Execute the tile initialization with the specified board size.
     */
    public void execute(int boardSize) {
        List<PropertyDataSource.PropertyInfo> propertyData = getPropertyData();
        List<Tile> tiles = createTiles(boardSize, propertyData);
        game.setTiles(tiles);
    }

    private List<PropertyDataSource.PropertyInfo> getPropertyData() {
        try {
            return propertyDataSource.getPropertyData();
        } catch (Exception e) {
            System.err.println("Primary data source failed, using fallback: " + e.getMessage());
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

    /**
     * Create a small board (16 tiles)
     */
    public void executeSmallBoard() {
        execute(SMALL_BOARD_SIZE);
    }

    /**
     * Create a medium board (20 tiles) - default
     */
    public void executeMediumBoard() {
        execute(MEDIUM_BOARD_SIZE);
    }

    /**
     * Create a large board (24 tiles)
     */
    public void executeLargeBoard() {
        execute(LARGE_BOARD_SIZE);
    }
}
