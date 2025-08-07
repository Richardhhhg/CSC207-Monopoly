package Stock;

import main.entity.Stocks.Stock;
import main.entity.players.DefaultPlayer;
import main.entity.players.Player;
import main.use_case.Stocks.BuyStock;
import main.use_case.Stocks.SellStock;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class StockSellTest {
    private Player player;
    private Stock stock;
    private BuyStock buyStock;
    private SellStock sellStock;

    @Before
    public void setUp() {
        player = new DefaultPlayer("TestPlayer", Color.BLUE);
        stock = new Stock("TST", 100.0, 0.1, 1.0);
        buyStock = new BuyStock();
        sellStock = new SellStock();
    }

    @Test
    public void testSellStockSuccess() {
        buyStock.execute(player, stock, 5);
        float moneyAfterBuy = player.getMoney();
        sellStock.execute(player, stock, 3);
        assertEquals(2, player.getStockQuantity(stock));
        assertEquals(moneyAfterBuy + 300.0f, player.getMoney(), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSellStockMoreThanOwned() {
        buyStock.execute(player, stock, 1);
        sellStock.execute(player, stock, 2); // Should throw
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSellStockInvalidQuantity() {
        sellStock.execute(player, stock, 0); // Should throw
    }
}
