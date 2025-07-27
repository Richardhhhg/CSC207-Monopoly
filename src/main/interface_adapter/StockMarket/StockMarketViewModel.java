package main.interface_adapter.StockMarket;

import main.entity.Stocks.Stock;

import java.util.Map;

import main.entity.Stocks.StockMarket;
import main.use_case.Player;

/**
 * Viewmodel for StockMarket
 * This should take in a player and create some output that is read into StockMarketView
 */
public class StockMarketViewModel {
    private StockMarket stockMarket;
    private Player player;

    public StockMarketViewModel(Player player) {
        this.stockMarket = stockMarket;
        this.player = player;
    }

    public Map<Stock, Integer> getStocks() {
        return player.getStocks();
    }

}
