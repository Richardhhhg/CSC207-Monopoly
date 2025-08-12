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
     * Executes the use case for buying stocks given a player.
     *
     * @param player Player that is buying the stock
     * @param stock Stock that is being bought
     * @param quantity How much of the stock is being bought
     */
    public void execute(Player player, Stock stock, int quantity) {
        final BuyStockInputData inputData = new BuyStockInputData(player, stock, quantity);
        buyStockInteractor.execute(inputData);
    }
}
