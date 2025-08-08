package main.interface_adapter.StockMarket;

import main.entity.Stocks.Stock;
import main.entity.players.Player;

/**
 * Presenter for stock data.
 * Updated to work with DTOs while maintaining backward compatibility.
 */
public class StockPresenter {
    // New method using DTOs
    public StockViewModel execute(StockData stock, PlayerStockData playerStock, boolean allowBuy) {
        return new StockViewModel(stock, playerStock, allowBuy);
    }

    // Backward compatibility method
    public StockViewModel execute(Stock stock, Player player, boolean allowBuy) {
        return new StockViewModel(stock, player, allowBuy);
    }
}
