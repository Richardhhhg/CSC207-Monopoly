package main.interface_adapter.StockMarket;

import main.Constants.Config;
import main.Constants.Constants;
import main.data_access.StockMarket.StockMarketInputDataObject;
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
    private StockMarketInputDataObject stockMarketInputDataObject;

    public StockMarketViewModel() {
        StockInformationRetriever stockInfoRetriever = new StockInformationRetriever(Config.getApiKey());
        try {
            this.stockMarketInputDataObject = stockInfoRetriever.createStocks(Constants.STOCK_NAME_FILE);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize StockMarketViewModel", e);
        }
    }

    public List<Stock> getStocks() {
        return stockMarketInputDataObject.getStocks();
    }

}
