package main.interface_adapter.StockMarket;

import main.entity.Stocks.Stock;
import main.entity.players.Player;
import main.use_case.Stocks.SellStockInputBoundary;
import main.use_case.Stocks.SellStockInputData;
import main.use_case.Stocks.SellStockInteractor;

public class StockSellController {
    private final SellStockInputBoundary sellStockInteractor;

    public StockSellController(SellStockInputBoundary sellStockInteractor) {
        this.sellStockInteractor = sellStockInteractor;
    }

    public void execute(Player player, Stock stock, int quantity) {
        final SellStockInputData inputData = new SellStockInputData(player, stock, quantity);
        sellStockInteractor.execute(inputData);
    }
}
