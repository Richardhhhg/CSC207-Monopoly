package main.stocks;

import main.use_case.stocks.*;
import main.entity.stocks.Stock;
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
        AbstractStockOutputData lastOutput;
        @Override
        public void execute(AbstractStockOutputData outputData) {
            this.lastOutput = outputData;
        }
    }

    @BeforeEach
    void setUp() {
        player = new DefaultPlayer("TestPlayer", Color.BLUE);
        stock = new Stock("TEST", 100.0, 0.1, 1.0);
        presenter = new TestStockPresenter();
        interactor = new SellStockInteractor(presenter);
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
        assertEquals(stock, presenter.lastOutput.getStock());
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

    @Test
    void testSellStockControllerAndPresenterUpdateViewModel() {
        player.getStocks().put(stock, 5);

        main.interface_adapter.stock_market.StockState stockState = new main.interface_adapter.stock_market.StockState();
        stockState.setTicker(stock.getTicker());
        stockState.setPrice(stock.getCurrentPrice());
        stockState.setChange(stock.getChange());
        stockState.setAllowBuy(true);
        main.interface_adapter.stock_market.StockPlayerViewModel viewModel = new main.interface_adapter.stock_market.StockPlayerViewModel(stockState);
        viewModel.getState().setPlayer(player);
        viewModel.getState().setStock(stock);
        viewModel.getState().setQuantity(5);

        main.interface_adapter.stock_market.StockPresenter presenter = new main.interface_adapter.stock_market.StockPresenter(viewModel);
        SellStockInteractor interactor = new SellStockInteractor(presenter);
        main.interface_adapter.stock_market.StockSellController controller = new main.interface_adapter.stock_market.StockSellController(interactor);

        controller.execute(player, stock, 2);

        assertEquals(3, player.getStockQuantity(stock));
        assertEquals(3, viewModel.getState().getQuantity());
        assertEquals(player, viewModel.getState().getPlayer());
        assertEquals(stock.getTicker(), viewModel.getState().getStock().getTicker());
        assertEquals(stock, viewModel.getState().getStock());
        assertTrue(viewModel.getState().getStockState().isAllowBuy());
    }
}
