package main.interface_adapter.stock_market;

import main.entity.players.Player;
import main.entity.stocks.Stock;
import main.use_case.stocks.SellStockInputBoundary;
import main.use_case.stocks.SellStockInputData;

public class StockSellController {
    private final SellStockInputBoundary sellStockInteractor;

    public StockSellController(SellStockInputBoundary sellStockInteractor) {
        this.sellStockInteractor = sellStockInteractor;
    }

    /**
     * Execute the sell stock use case.
     *
     * @param player the player who is selling the stock
     * @param stock the stock to be sold
     * @param quantity the quantity of stock to be sold
     */
    public void execute(Player player, Stock stock, int quantity) {
        final SellStockInputData inputData = new SellStockInputData(player, stock, quantity);
        sellStockInteractor.execute(inputData);
    }
}
