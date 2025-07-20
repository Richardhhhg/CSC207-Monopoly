package main.interface_adapter.StockMarket;

import main.entity.Stock;

import java.util.List;
import main.entity.StockInformationRetriever;

/**
 * Viewmodel for StockMarket
 * This should take in list of stocks
 * and create some output that is read into
 * StockMarketView
 */
public class StockMarketViewModel {
    private final List<Stock> stocks;

    public StockMarketViewModel() {
        StockInformationRetriever stockInfoRetriever = new StockInformationRetriever("5ETSDNB7Z6CD1T3M");
        try {
            // Load ticker symbols from JSON file
            this.stocks = stockInfoRetriever.createStocks("src/main/Resources/StockData/stock_names.json");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize StockMarketViewModel", e);
        }
    }

    public List<Stock> getStocks() {
        return stocks;
    }

}
