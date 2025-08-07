package main.interface_adapter.stock_market;

import main.entity.players.Player;
import main.entity.stocks.Stock;

public class StockPresenter {
    /**
     * Creates a StockViewModel for the given stock and player.
     * @param stock the stock to be presented
     * @param player the player associated with the stock
     * @param allowBuy indicates if the player is allowed to buy the stock
     * @return StockViewModel containing stock and player information
     */
    public StockViewModel execute(Stock stock, Player player, boolean allowBuy) {
        return new StockViewModel(stock, player, allowBuy);
    }
}
