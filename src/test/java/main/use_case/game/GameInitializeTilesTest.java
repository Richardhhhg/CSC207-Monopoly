package main.use_case.game;

import main.constants.Constants;
import main.entity.tiles.AbstractTile;
import main.entity.tiles.GoTile;
import main.entity.tiles.PropertyTile;
import main.entity.tiles.StockMarketTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameInitializeTilesTest {

    private GameInitializeTiles gameInitializeTiles;
    private MockPropertyDataSource mockDataSource;

    // Mock implementation of PropertyDataSource
    private static class MockPropertyDataSource implements PropertyDataSource {
        private List<PropertyInfo> propertyData;

        public MockPropertyDataSource(List<PropertyInfo> propertyData) {
            this.propertyData = propertyData;
        }

        @Override
        public List<PropertyInfo> getPropertyData() {
            return propertyData;
        }

        public void setPropertyData(List<PropertyInfo> propertyData) {
            this.propertyData = propertyData;
        }
    }

    // Helper class for creating PropertyInfo
    private static class TestPropertyInfo extends PropertyDataSource.PropertyInfo {
        private final String name;
        private final int basePrice;

        public TestPropertyInfo(String name, int basePrice) {
            super(name, basePrice);
            this.name = name;
            this.basePrice = basePrice;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getBasePrice() {
            return basePrice;
        }
    }

    @BeforeEach
    void setUp() {
        // Create enough property data to fill the largest board
        List<PropertyDataSource.PropertyInfo> defaultPropertyData = createSufficientPropertyData();
        mockDataSource = new MockPropertyDataSource(defaultPropertyData);
        gameInitializeTiles = new GameInitializeTiles(mockDataSource);
    }

    private List<PropertyDataSource.PropertyInfo> createSufficientPropertyData() {
        List<PropertyDataSource.PropertyInfo> propertyData = new ArrayList<>();
        // Create enough properties to fill even the largest board
        for (int i = 1; i <= 30; i++) {
            propertyData.add(new TestPropertyInfo("Property" + i, 100 + (i * 10)));
        }
        return propertyData;
    }

    private List<PropertyDataSource.PropertyInfo> createDefaultPropertyData() {
        List<PropertyDataSource.PropertyInfo> propertyData = new ArrayList<>();
        propertyData.add(new TestPropertyInfo("Boardwalk", 400));
        propertyData.add(new TestPropertyInfo("Park Place", 350));
        propertyData.add(new TestPropertyInfo("Pennsylvania Avenue", 320));
        propertyData.add(new TestPropertyInfo("North Carolina Avenue", 300));
        propertyData.add(new TestPropertyInfo("Pacific Avenue", 300));
        propertyData.add(new TestPropertyInfo("Marvin Gardens", 280));
        propertyData.add(new TestPropertyInfo("Atlantic Avenue", 260));
        propertyData.add(new TestPropertyInfo("Ventnor Avenue", 260));
        propertyData.add(new TestPropertyInfo("Illinois Avenue", 240));
        propertyData.add(new TestPropertyInfo("Indiana Avenue", 220));
        return propertyData;
    }

    @Test
    void testConstructor_WithValidDataSource_CreatesGameInitializeTiles() {
        // Arrange & Act
        GameInitializeTiles newGameInitializeTiles = new GameInitializeTiles(mockDataSource);

        // Assert
        assertNotNull(newGameInitializeTiles);
    }

    @Test
    void testConstructor_WithNullDataSource_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new GameInitializeTiles(null);
        });
        assertEquals("PropertyDataSource cannot be null", exception.getMessage());
    }

    @Test
    void testExecuteSmallBoard_ValidData_ReturnsCorrectNumberOfTiles() {
        // Act
        List<AbstractTile> tiles = gameInitializeTiles.executeSmallBoard();

        // Assert
        assertEquals(Constants.SMALL_BOARD_SIZE, tiles.size());
        assertInstanceOf(GoTile.class, tiles.get(0));
    }

    @Test
    void testExecuteMediumBoard_ValidData_ReturnsCorrectNumberOfTiles() {
        // Act
        List<AbstractTile> tiles = gameInitializeTiles.executeMediumBoard();

        // Assert
        assertEquals(Constants.MEDIUM_BOARD_SIZE, tiles.size());
        assertInstanceOf(GoTile.class, tiles.get(0));
    }

    @Test
    void testExecuteLargeBoard_ValidData_ReturnsCorrectNumberOfTiles() {
        // Act
        List<AbstractTile> tiles = gameInitializeTiles.executeLargeBoard();

        // Assert
        assertEquals(Constants.LARGE_BOARD_SIZE, tiles.size());
        assertInstanceOf(GoTile.class, tiles.get(0));
    }

    @Test
    void testExecuteSmallBoard_FirstTileIsGoTile() {
        // Act
        List<AbstractTile> tiles = gameInitializeTiles.executeSmallBoard();

        // Assert
        assertInstanceOf(GoTile.class, tiles.get(0));
    }

    @Test
    void testExecuteSmallBoard_ContainsPropertyTiles() {
        // Act
        List<AbstractTile> tiles = gameInitializeTiles.executeSmallBoard();

        // Assert
        long propertyCount = tiles.stream().filter(tile -> tile instanceof PropertyTile).count();
        assertTrue(propertyCount > 0, "Should contain at least one property tile");
    }

    @Test
    void testExecuteSmallBoard_ContainsStockMarketTiles() {
        // Act
        List<AbstractTile> tiles = gameInitializeTiles.executeSmallBoard();

        // Assert
        long stockMarketCount = tiles.stream().filter(tile -> tile instanceof StockMarketTile).count();
        assertTrue(stockMarketCount > 0, "Should contain at least one stock market tile");
    }

    @Test
    void testExecuteSmallBoard_CorrectTileDistribution() {
        // Act
        List<AbstractTile> tiles = gameInitializeTiles.executeSmallBoard();

        // Assert
        long goCount = tiles.stream().filter(tile -> tile instanceof GoTile).count();
        long propertyCount = tiles.stream().filter(tile -> tile instanceof PropertyTile).count();
        long stockMarketCount = tiles.stream().filter(tile -> tile instanceof StockMarketTile).count();

        assertEquals(1, goCount, "Should have exactly one Go tile");
        assertTrue(propertyCount > 0, "Should have property tiles");
        assertTrue(stockMarketCount > 0, "Should have stock market tiles");
        assertEquals(Constants.SMALL_BOARD_SIZE, goCount + propertyCount + stockMarketCount);
    }

    @Test
    void testExecuteMediumBoard_CorrectTileDistribution() {
        // Act
        List<AbstractTile> tiles = gameInitializeTiles.executeMediumBoard();

        // Assert
        long goCount = tiles.stream().filter(tile -> tile instanceof GoTile).count();
        long propertyCount = tiles.stream().filter(tile -> tile instanceof PropertyTile).count();
        long stockMarketCount = tiles.stream().filter(tile -> tile instanceof StockMarketTile).count();

        assertEquals(1, goCount, "Should have exactly one Go tile");
        assertTrue(propertyCount > 0, "Should have property tiles");
        assertTrue(stockMarketCount > 0, "Should have stock market tiles");
        assertEquals(Constants.MEDIUM_BOARD_SIZE, goCount + propertyCount + stockMarketCount);
    }

    @Test
    void testExecuteLargeBoard_CorrectTileDistribution() {
        // Act
        List<AbstractTile> tiles = gameInitializeTiles.executeLargeBoard();

        // Assert
        long goCount = tiles.stream().filter(tile -> tile instanceof GoTile).count();
        long propertyCount = tiles.stream().filter(tile -> tile instanceof PropertyTile).count();
        long stockMarketCount = tiles.stream().filter(tile -> tile instanceof StockMarketTile).count();

        assertEquals(1, goCount, "Should have exactly one Go tile");
        assertTrue(propertyCount > 0, "Should have property tiles");
        assertTrue(stockMarketCount > 0, "Should have stock market tiles");
        assertEquals(Constants.LARGE_BOARD_SIZE, goCount + propertyCount + stockMarketCount);
    }

    @Test
    void testPropertyTilesUseDataFromSource() {
        // Act
        List<AbstractTile> tiles = gameInitializeTiles.executeSmallBoard();

        // Assert
        List<PropertyTile> propertyTiles = tiles.stream()
                .filter(tile -> tile instanceof PropertyTile)
                .map(tile -> (PropertyTile) tile)
                .toList();

        assertFalse(propertyTiles.isEmpty(), "Should have property tiles");

        // Check that property names match data source
        PropertyTile firstProperty = propertyTiles.get(0);
        assertEquals("Property1", firstProperty.getName());
    }

    @Test
    void testPropertyTilesUseCalculatedRent() {
        // Act
        List<AbstractTile> tiles = gameInitializeTiles.executeSmallBoard();

        // Assert
        List<PropertyTile> propertyTiles = tiles.stream()
                .filter(tile -> tile instanceof PropertyTile)
                .map(tile -> (PropertyTile) tile)
                .toList();

        assertFalse(propertyTiles.isEmpty(), "Should have property tiles");

        // Check that rent is calculated as 25% of property price
        PropertyTile firstProperty = propertyTiles.get(0);
        float expectedRent = 110 * Constants.RENT_MULTIPLIER; // Property1 has basePrice 110
        assertEquals(expectedRent, firstProperty.getRent(), 0.01f);
    }

    @Test
    void testPropertyTilesRentCalculation_MultipleProperties() {
        // Act
        List<AbstractTile> tiles = gameInitializeTiles.executeSmallBoard();

        // Assert
        List<PropertyTile> propertyTiles = tiles.stream()
                .filter(tile -> tile instanceof PropertyTile)
                .map(tile -> (PropertyTile) tile)
                .toList();

        assertFalse(propertyTiles.isEmpty(), "Should have property tiles");

        // Check rent calculation for multiple properties
        for (int i = 0; i < Math.min(3, propertyTiles.size()); i++) {
            PropertyTile property = propertyTiles.get(i);
            // Property basePrice is 100 + ((i+1) * 10) = 110, 120, 130...
            int expectedBasePrice = 100 + ((i + 1) * 10);
            float expectedRent = expectedBasePrice * Constants.RENT_MULTIPLIER;

            assertEquals(expectedRent, property.getRent(), 0.01f,
                    "Property " + property.getName() + " should have rent as 25% of base price");
        }
    }

    @Test
    void testWithNullPropertyData_ThrowsException() {
        // Arrange
        mockDataSource.setPropertyData(null);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            gameInitializeTiles.executeSmallBoard();
        });
        assertEquals("Property data source returned no data", exception.getMessage());
    }

    @Test
    void testWithEmptyPropertyData_ThrowsException() {
        // Arrange
        mockDataSource.setPropertyData(Collections.emptyList());

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            gameInitializeTiles.executeSmallBoard();
        });
        assertEquals("Property data source returned no data", exception.getMessage());
    }

    @Test
    void testStockMarketTilesDistribution_AtLeastOne() {
        // Act
        List<AbstractTile> tiles = gameInitializeTiles.executeSmallBoard();

        // Assert
        long stockMarketCount = tiles.stream().filter(tile -> tile instanceof StockMarketTile).count();
        assertTrue(stockMarketCount >= 1, "Should have at least one stock market tile");
    }

    @Test
    void testDifferentBoardSizes_ProducesDifferentTileCounts() {
        // Act
        List<AbstractTile> smallTiles = gameInitializeTiles.executeSmallBoard();
        List<AbstractTile> mediumTiles = gameInitializeTiles.executeMediumBoard();
        List<AbstractTile> largeTiles = gameInitializeTiles.executeLargeBoard();

        // Assert
        assertTrue(smallTiles.size() < mediumTiles.size());
        assertTrue(mediumTiles.size() < largeTiles.size());
        assertEquals(Constants.SMALL_BOARD_SIZE, smallTiles.size());
        assertEquals(Constants.MEDIUM_BOARD_SIZE, mediumTiles.size());
        assertEquals(Constants.LARGE_BOARD_SIZE, largeTiles.size());
    }

    @Test
    void testWithOriginalPropertyData_LimitedByAvailableProperties() {
        // Arrange - Use the original limited property data (10 properties)
        mockDataSource.setPropertyData(createDefaultPropertyData());

        // Act
        List<AbstractTile> smallTiles = gameInitializeTiles.executeSmallBoard();

        // Assert - Check actual tile distribution with limited property data
        long goCount = smallTiles.stream().filter(tile -> tile instanceof GoTile).count();
        long propertyCount = smallTiles.stream().filter(tile -> tile instanceof PropertyTile).count();
        long stockMarketCount = smallTiles.stream().filter(tile -> tile instanceof StockMarketTile).count();

        assertEquals(1, goCount, "Should have exactly one Go tile");
        assertTrue(propertyCount <= 10, "Should not have more property tiles than available property data");
        assertTrue(stockMarketCount > 0, "Should have stock market tiles");

        // The actual board size might be smaller if there's insufficient property data
        int actualBoardSize = (int) (goCount + propertyCount + stockMarketCount);
        assertTrue(actualBoardSize <= Constants.SMALL_BOARD_SIZE,
                "Actual board size should not exceed expected size");
    }
}
