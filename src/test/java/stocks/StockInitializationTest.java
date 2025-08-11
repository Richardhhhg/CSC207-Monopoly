package stocks;

import main.entity.Game;
import main.entity.players.DefaultPlayer;
import main.entity.players.Player;
import main.entity.Stocks.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StockInitializationTest {
    private Game game;
    private List<Player> players;

    @BeforeEach
    void setUp() {
        game = new Game();
        players = new ArrayList<>();
        players.add(new DefaultPlayer("Alice", Color.RED));
        players.add(new DefaultPlayer("Bob", Color.BLUE));
        game.setPlayers(players);
    }

    @Test
    void testStocksInitializedInGame() {
        game.initializeGame();
        List<Stock> stocks = game.getStocks();
        assertNotNull(stocks, "Stocks list should not be null after initialization");
        assertFalse(stocks.isEmpty(), "Stocks list should not be empty after initialization");
        assertEquals(5, stocks.size(), "Default stock count should be 5");
        for (Stock stock : stocks) {
            assertNotNull(stock.getName());
            assertTrue(stock.getCurrentPrice() > 0);
        }
    }

    @Test
    void testPlayersHaveStocksInitialized() {
        game.initializeGame();
        for (Player player : game.getPlayers()) {
            assertNotNull(player.getStocks(), "Player's stocks should be initialized");
            assertEquals(5, player.getStocks().size(), "Player should have 5 stocks initialized");
        }
    }

    @Test
    void testStockDefaultValues() {
        game.initializeGame();
        Stock stock = game.getStocks().get(0);
        assertEquals(100.00, stock.getCurrentPrice(), 0.01);
        assertEquals(10, stock.getMeanDailyReturn(), 0.01);
        assertEquals(30, stock.getStandardDeviation(), 0.01);
    }
}
