package stocks;

import use_case.Stocks.*;
import entity.Stocks.Stock;
import entity.players.Player;
import entity.players.DefaultPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class BuyStockUseCaseTest {
    private Player player;
    private Stock stock;
    private BuyStockInteractor interactor;
    private MockStockPresenter presenter;

    static class MockStockPresenter implements StockOutputBoundary {
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
        presenter = new MockStockPresenter();
        interactor = new BuyStockInteractor(presenter);
    }

    @Test
    void testBuyStockSuccess() {
        int quantity = 2;
        float initialMoney = player.getMoney();
        BuyStockInputData inputData = new BuyStockInputData(player, stock, quantity);
        interactor.execute(inputData);
        assertEquals(quantity, player.getStockQuantity(stock));
        assertEquals(initialMoney - stock.getCurrentPrice() * quantity, player.getMoney(), 0.01);
        assertNotNull(presenter.lastOutput);
        assertTrue(presenter.lastOutput.isAllowBuy());
    }

    @Test
    void testBuyStockInsufficientFunds() {
        int quantity = 10000;
        BuyStockInputData inputData = new BuyStockInputData(player, stock, quantity);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> interactor.execute(inputData));
        assertEquals("Insufficient funds to buy stocks", exception.getMessage());
        assertEquals(0, player.getStockQuantity(stock));
    }

    @Test
    void testBuyStockInvalidQuantity() {
        int quantity = 0;
        BuyStockInputData inputData = new BuyStockInputData(player, stock, quantity);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> interactor.execute(inputData));
        assertEquals("Quantity must be greater than 0", exception.getMessage());
        assertEquals(0, player.getStockQuantity(stock));
    }
}
