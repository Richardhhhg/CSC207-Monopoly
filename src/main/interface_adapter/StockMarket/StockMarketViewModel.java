package main.interface_adapter.StockMarket;

import main.entity.Stock;

import java.util.Map;

import main.entity.StockMarket;
import main.entity.players.Player;

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
