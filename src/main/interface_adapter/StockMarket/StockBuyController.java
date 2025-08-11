package main.interface_adapter.StockMarket;

import main.entity.Stocks.Stock;
import main.entity.players.Player;
import main.use_case.Stocks.BuyStockInputBoundary;
import main.use_case.Stocks.BuyStockInputData;
import main.use_case.Stocks.BuyStockInteractor;

public class StockBuyController {
    private final BuyStockInputBoundary buyStockInteractor;

    public StockBuyController(BuyStockInteractor buyStockInteractor) {
        this.buyStockInteractor = buyStockInteractor;
    }

    public void execute(Player player, Stock stock, int quantity) {
        final BuyStockInputData inputData = new BuyStockInputData(player, stock, quantity);
        buyStockInteractor.execute(inputData);
    }
}
