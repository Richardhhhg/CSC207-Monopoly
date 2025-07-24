package main.interface_adapter.StockMarket;

import main.Constants.Config;
import main.Constants.Constants;
import main.data_access.StockMarket.StockMarketInputDataObject;
import main.entity.Stock;

import java.util.List;
import java.util.Map;

import main.entity.StockInformationRetriever;
import main.entity.StockMarket;
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
