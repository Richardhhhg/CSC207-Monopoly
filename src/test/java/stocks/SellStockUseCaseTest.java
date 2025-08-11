package stocks;

import main.use_case.Stocks.*;
import main.entity.Stocks.Stock;
import main.entity.players.Player;
import main.entity.players.DefaultPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class SellStockUseCaseTest {
    private Player player;
    private Stock stock;
    private SellStockInteractor interactor;
    private TestStockPresenter presenter;

    static class TestStockPresenter implements StockOutputBoundary {
        StockOutputData lastOutput;
        @Override
        public void execute(StockOutputData outputData) {
            this.lastOutput = outputData;
        }
    }

    @BeforeEach
    void setUp() {
        player = new DefaultPlayer("TestPlayer", Color.BLUE);
        stock = new Stock("TEST", 100.0, 0.1, 1.0);
        presenter = new TestStockPresenter();
        interactor = new SellStockInteractor(presenter);
        // Give player some stocks to sell
        player.buyStock(stock, 5);
    }

    @Test
    void testSellStockSuccess() {
        int quantity = 3;
        float initialMoney = player.getMoney();
        SellStockInputData inputData = new SellStockInputData(player, stock, quantity);
        interactor.execute(inputData);
        assertEquals(2, player.getStockQuantity(stock));
        assertEquals(initialMoney + stock.getCurrentPrice() * quantity, player.getMoney(), 0.01);
        assertNotNull(presenter.lastOutput);
        assertTrue(presenter.lastOutput.isAllowBuy());
    }

    @Test
    void testSellStockNotEnoughOwned() {
        int quantity = 10; // More than owned
        SellStockInputData inputData = new SellStockInputData(player, stock, quantity);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> interactor.execute(inputData));
        assertEquals("Not enough stock to sell", exception.getMessage());
        assertEquals(5, player.getStockQuantity(stock));
    }

    @Test
    void testSellStockInvalidQuantity() {
        int quantity = 0;
        SellStockInputData inputData = new SellStockInputData(player, stock, quantity);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> interactor.execute(inputData));
        assertEquals("Quantity must be greater than 0", exception.getMessage());
        assertEquals(5, player.getStockQuantity(stock));
    }
}
