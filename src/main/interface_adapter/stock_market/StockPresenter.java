package main.interface_adapter.stock_market;

import main.entity.players.Player;
import main.entity.stocks.Stock;

public class StockPresenter {
    /**
     * Converts a Stock and Player into a StockViewModel.
     *
     * @param stock  the stock to be presented
     * @param player the player associated with the stock
     * @return a StockViewModel containing the stock and player information
     */
    public StockViewModel execute(Stock stock, Player player) {
        return new StockViewModel(stock, player);
    }
}
