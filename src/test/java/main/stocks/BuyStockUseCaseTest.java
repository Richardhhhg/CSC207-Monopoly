package main.stocks;

import main.use_case.stocks.*;
import main.entity.stocks.Stock;
import main.entity.players.Player;
import main.entity.players.DefaultPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import main.interface_adapter.stock_market.StockBuyController;
import main.use_case.stocks.BuyStockInteractor;
import main.interface_adapter.stock_market.StockPlayerViewModel;
import main.interface_adapter.stock_market.StockState;

import static org.junit.jupiter.api.Assertions.*;

class BuyStockUseCaseTest {
    private Player player;
    private Stock stock;
    private BuyStockInteractor interactor;
    private MockStockPresenter presenter;

    static class MockStockPresenter implements StockOutputBoundary {
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
        assertEquals(stock, presenter.lastOutput.getStock());
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

    @Test
    void testBuyStockControllerAndPresenterUpdateViewModel() {
        StockState stockState = new StockState();
        stockState.setTicker(stock.getTicker());
        stockState.setPrice(stock.getCurrentPrice());
        stockState.setChange(stock.getChange());
        stockState.setAllowBuy(true);
        StockPlayerViewModel viewModel = new StockPlayerViewModel(stockState);
        viewModel.getState().setPlayer(player);
        viewModel.getState().setStock(stock);
        viewModel.getState().setQuantity(0);

        main.interface_adapter.stock_market.StockPresenter presenter = new main.interface_adapter.stock_market.StockPresenter(viewModel);
        BuyStockInteractor interactor = new BuyStockInteractor(presenter);
        StockBuyController controller = new StockBuyController(interactor);

        controller.execute(player, stock, 1);

        assertEquals(1, player.getStockQuantity(stock));
        assertEquals(1, viewModel.getState().getQuantity());
        assertEquals(player, viewModel.getState().getPlayer());
        assertEquals(stock.getTicker(), viewModel.getState().getStock().getTicker());
        assertEquals(stock, viewModel.getState().getStock());
        assertTrue(viewModel.getState().getStockState().isAllowBuy());
    }
}
