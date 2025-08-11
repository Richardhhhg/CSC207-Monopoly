package stocks;

import entity.Stocks.Stock;
import entity.players.DefaultPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerBuyStockTest {
    private DefaultPlayer player;
    private Stock stock;

    @BeforeEach
    public void setUp() {
        player = new DefaultPlayer("TestPlayer", Color.BLUE);
        stock = new Stock("TEST", 100.0, 0.01, 0.02);
    }

    @Test
    public void testPlayerBuyStockSuccess() {
        int quantity = 5;
        player.buyStock(stock, quantity);

        assertEquals(700, player.getMoney());
        assertEquals(quantity, player.getStockQuantity(stock));
    }

    @Test
    public void testPlayerBuyStockInsufficientFunds() {
        int quantity = 30;
        player.buyStock(stock, quantity);

        assertEquals(1200, player.getMoney(), 0.01);
        assertEquals(0, player.getStockQuantity(stock));
    }

    @Test
    public void testPlayerBuyStockUpdateQuantity() {
        player.buyStock(stock, 5);
        player.buyStock(stock, 3);

        assertEquals(8, player.getStockQuantity(stock));
    }
}
