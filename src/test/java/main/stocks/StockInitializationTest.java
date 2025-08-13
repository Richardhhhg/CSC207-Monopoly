package main.stocks;

import main.entity.Game;
import main.entity.players.DefaultPlayer;
import main.entity.players.Player;
import main.entity.stocks.Stock;
import main.use_case.game.GameInitializeStocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import main.data_access.stock_market.ApiStockInfoRepository;
import main.data_access.stock_market.StockInfoDataOutputObject;
import main.interface_adapter.stock_market.StockFactory;
import main.constants.Config;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class StockInitializationTest {
    private Game game;
    private List<Player> players;
    private GameInitializeStocks gameInitializeStocks;

    @BeforeEach
    void setUp() {
        game = new Game();
        players = new ArrayList<>();
        players.add(new DefaultPlayer("Alice", Color.RED));
        players.add(new DefaultPlayer("Bob", Color.BLUE));
        game.setPlayers(players);
        gameInitializeStocks = new GameInitializeStocks(game);
    }

    @Test
    void testStocksInitializedInGame() {
        gameInitializeStocks.execute();
        List<Stock> stocks = game.getStocks();
        assertNotNull(stocks, "Stocks list should not be null after initialization");
        assertFalse(stocks.isEmpty(), "Stocks list should not be empty after initialization");
        assertEquals(5, stocks.size(), "Default stock count should be 5");
        for (Stock stock : stocks) {
            assertNotNull(stock.getTicker());
            assertTrue(stock.getCurrentPrice() > 0);
        }
    }

    @Test
    void testPlayersHaveStocksInitialized() {
        gameInitializeStocks.execute();
        for (Player player : game.getPlayers()) {
            assertNotNull(player.getStocks(), "Player's stocks should be initialized");
            assertEquals(5, player.getStocks().size(), "Player should have 5 stocks initialized");
        }
    }

    @Test
    void testStockDefaultValues() {
        gameInitializeStocks.execute();
        Stock stock = game.getStocks().get(0);
        assertEquals(100.00, stock.getCurrentPrice(), 0.01);
    }

    @Test
    void testInitializeStockUsingAPI() throws Exception {
        String apiKey = Config.getApiKey();
        boolean apiEnabled = !apiKey.isEmpty();
        assumeTrue(apiEnabled, "API key must be set to run this test");
        ApiStockInfoRepository repo = new ApiStockInfoRepository(apiKey);

        String jsonFilePath = "src/test/java/stocks/test_stock_name.json";
        java.util.List<String> tickers = repo.loadTickerSymbols(jsonFilePath);
        assertNotNull(tickers);
        assertFalse(tickers.isEmpty());
        for (String ticker : tickers) {
            StockInfoDataOutputObject info = repo.getStockInfo(ticker);
            assertNotNull(info);
            assertEquals(ticker, info.ticker());
            assertTrue(info.currentPrice() > 0);
            StockFactory factory = new StockFactory();
            Stock stock = factory.execute(info);
            assertNotNull(stock);
            assertEquals(ticker, stock.getTicker());
            assertTrue(stock.getCurrentPrice() > 0);
        }
    }
}
