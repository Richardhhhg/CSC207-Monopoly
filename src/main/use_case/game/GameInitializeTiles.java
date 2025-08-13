package main.use_case.game;

import java.util.ArrayList;
import java.util.List;

import main.constants.Constants;
import main.entity.tiles.AbstractTile;
import main.entity.tiles.GoTile;
import main.entity.tiles.PropertyTile;
import main.entity.tiles.StockMarketTile;

/**
 * Use case for initializing tiles in the game.
 * Simplified to focus only on tile creation with provided data.
 */
public class GameInitializeTiles {
    private static final int STOCK_MARKET_PERCENTAGE_NUMERATOR = 3;
    private static final int STOCK_MARKET_PERCENTAGE_DENOMINATOR = 10;

    private final PropertyDataSource dataSource;

    public GameInitializeTiles(PropertyDataSource dataSource) {
        if (dataSource == null) {
            throw new IllegalArgumentException("PropertyDataSource cannot be null");
        }
        this.dataSource = dataSource;
    }

    /**
     * Creates tiles for a small board.
     *
     * @return list of tiles for small board
     */
    public List<AbstractTile> executeSmallBoard() {
        return createTiles(Constants.SMALL_BOARD_SIZE);
    }

    /**
     * Creates tiles for a medium board.
     *
     * @return list of tiles for medium board
     */
    public List<AbstractTile> executeMediumBoard() {
        return createTiles(Constants.MEDIUM_BOARD_SIZE);
    }

    /**
     * Creates tiles for a large board.
     *
     * @return list of tiles for large board
     */
    public List<AbstractTile> executeLargeBoard() {
        return createTiles(Constants.LARGE_BOARD_SIZE);
    }

    private List<AbstractTile> createTiles(int boardSize) {
        // Use the injected data source - fallback should be handled at injection time
        final List<PropertyDataSource.PropertyInfo> propertyData = dataSource.getPropertyData();

        if (propertyData == null || propertyData.isEmpty()) {
            throw new IllegalStateException("Property data source returned no data");
        }

        return createTiles(boardSize, propertyData);
    }

    private List<AbstractTile> createTiles(int boardSize, List<PropertyDataSource.PropertyInfo> propertyData) {
        final List<AbstractTile> tiles = new ArrayList<>();

        // Always start with Go tile
        tiles.add(new GoTile());

        // Calculate tile distribution
        final int remainingTiles = boardSize - 1;
        final int stockMarketTiles = Math.max(1, remainingTiles * STOCK_MARKET_PERCENTAGE_NUMERATOR
                / STOCK_MARKET_PERCENTAGE_DENOMINATOR);
        final int propertyTiles = remainingTiles - stockMarketTiles;

        // Add property tiles using data source
        for (int i = 0; i < propertyTiles && i < propertyData.size(); i++) {
            final PropertyDataSource.PropertyInfo info = propertyData.get(i);
            tiles.add(new PropertyTile(info.getName(), info.getBasePrice(), Constants.PLACEHOLDER_RENT));
        }

        // Add stock market tiles distributed throughout
        for (int i = 0; i < stockMarketTiles; i++) {
            final int insertPosition = Math.min(tiles.size(), 1 + (i + 1) * (remainingTiles / stockMarketTiles));
            tiles.add(insertPosition, new StockMarketTile());
        }

        return tiles;
    }
}
