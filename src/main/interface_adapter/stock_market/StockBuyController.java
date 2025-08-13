package main.interface_adapter.stock_market;

import main.entity.players.Player;
import main.entity.stocks.Stock;
import main.use_case.stocks.BuyStockInputBoundary;
import main.use_case.stocks.BuyStockInputData;
import main.use_case.stocks.BuyStockInteractor;

public class StockBuyController {
    private final BuyStockInputBoundary buyStockInteractor;

    public StockBuyController(BuyStockInteractor buyStockInteractor) {
        this.buyStockInteractor = buyStockInteractor;
    }

    /**
     * Executes the buy stock use case.
     * @param player the player who wants to buy the stock
     * @param stock the stock to be bought
     * @param quantity the number of shares to buy
     */
    public void execute(Player player, Stock stock, int quantity) {
        final BuyStockInputData inputData = new BuyStockInputData(player, stock, quantity);
        buyStockInteractor.execute(inputData);
    }
}
