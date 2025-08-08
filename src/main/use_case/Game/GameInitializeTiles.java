package main.use_case.Game;

import main.Constants.Constants;
import main.entity.Game;
import main.entity.tiles.GoTile;
import main.entity.tiles.PropertyTile;
import main.entity.tiles.StockMarketTile;
import main.entity.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Use case for initializing tiles in the game.
 * Creates a mix of Go, Property, and Stock Market tiles with flexible board sizes.
 */
public class GameInitializeTiles {
    private Game game;

    // Board size configurations (must be multiples of 4)
    private static final int SMALL_BOARD_SIZE = 20;
    private static final int MEDIUM_BOARD_SIZE = 24;
    private static final int LARGE_BOARD_SIZE = 28;

    // Default board size
    private static final int DEFAULT_BOARD_SIZE = MEDIUM_BOARD_SIZE;

    public GameInitializeTiles(Game game) {
        this.game = game;
    }

    public void execute() {
        execute(DEFAULT_BOARD_SIZE);
    }

    public void execute(int boardSize) {
        // Ensure board size is a multiple of 4
        if (boardSize % 4 != 0) {
            throw new IllegalArgumentException("Board size must be a multiple of 4, got: " + boardSize);
        }

        List<Tile> tiles = new ArrayList<>();

        // Always start with Go tile at position 0
        tiles.add(new GoTile());

        // Calculate how many tiles we need to fill
        int remainingTiles = boardSize - 1;

        // Distribute remaining tiles: 70% Properties, 30% Stock Market
        int stockMarketTiles = Math.max(1, remainingTiles * 3 / 10); // At least 1 stock market tile
        int propertyTiles = remainingTiles - stockMarketTiles;

        // Add property tiles
        for (int i = 0; i < propertyTiles; i++) {
            String propertyName = generatePropertyName(i + 1);
            int price = generatePropertyPrice(i + 1);
            tiles.add(new PropertyTile(propertyName, price, Constants.PLACEHOLDER_RENT));
        }

        // Add stock market tiles distributed throughout the board
        int stockMarketInterval = remainingTiles / stockMarketTiles;
        for (int i = 0; i < stockMarketTiles; i++) {
            int insertPosition = Math.min(tiles.size(), 1 + (i + 1) * stockMarketInterval);
            tiles.add(insertPosition, new StockMarketTile());
        }

        game.setTiles(tiles);
    }

    /**
     * Generate property names based on position
     */
    private String generatePropertyName(int index) {
        String[] propertyNames = {
            "Mediterranean Ave", "Baltic Ave", "Oriental Ave", "Vermont Ave",
            "Connecticut Ave", "St. James Place", "Tennessee Ave", "New York Ave",
            "Kentucky Ave", "Indiana Ave", "Illinois Ave", "Atlantic Ave",
            "Ventnor Ave", "Marvin Gardens", "Pacific Ave", "North Carolina Ave",
            "Pennsylvania Ave", "Boardwalk", "Park Place", "Luxury Tax",
            "Short Line", "B&O Railroad", "Reading Railroad", "Pennsylvania Railroad"
        };

        if (index <= propertyNames.length) {
            return propertyNames[index - 1];
        } else {
            return "Property " + index;
        }
    }

    /**
     * Generate property prices based on position (higher index = higher price)
     */
    private int generatePropertyPrice(int index) {
        // Base price increases with position
        int basePrice = 60 + (index - 1) * 20;

        // Add some variation for realism
        if (index % 5 == 0) {
            basePrice += 40; // Premium properties
        }

        return Math.min(basePrice, 400); // Cap at $400
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
