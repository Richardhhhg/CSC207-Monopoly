package Stock;

import main.entity.Stocks.Stock;
import main.entity.players.DefaultPlayer;
import main.entity.players.Player;
import main.use_case.Stocks.BuyStock;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class StockBuyTest {
    private Player player;
    private Stock stock;
    private BuyStock buyStock;

    @Before
    public void setUp() {
        player = new DefaultPlayer("TestPlayer", Color.BLUE);
        stock = new Stock("TST", 100.0, 0.1, 1.0);
        buyStock = new BuyStock();
    }

    @Test
    public void testBuyStockSuccess() {
        float initialMoney = player.getMoney();
        buyStock.execute(player, stock, 2);
        assertEquals(2, player.getStockQuantity(stock));
        assertEquals(initialMoney - 200.0f, player.getMoney(), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuyStockInsufficientFunds() {
        buyStock.execute(player, stock, 100000); // Should throw
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuyStockInvalidQuantity() {
        buyStock.execute(player, stock, 0); // Should throw
    }
}
