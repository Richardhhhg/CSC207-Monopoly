package main.interface_adapter.stock_market;

import main.entity.players.AbstractPlayer;
import main.entity.stocks.Stock;
import main.use_case.stocks.SellStockInputBoundary;
import main.use_case.stocks.SellStockInputData;

public class StockSellController {
    private final SellStockInputBoundary sellStockInteractor;

    public StockSellController(SellStockInputBoundary sellStockInteractor) {
        this.sellStockInteractor = sellStockInteractor;
    }

    /**
     * Executes the sell stock use case.
     *
     * @param player the player who wants to sell the stock
     * @param stock the stock to be sold
     * @param quantity the number of shares to sell
     */
    public void execute(AbstractPlayer player, Stock stock, int quantity) {
        final SellStockInputData inputData = new SellStockInputData(player, stock, quantity);
        sellStockInteractor.execute(inputData);
    }
}
