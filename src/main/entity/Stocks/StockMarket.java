package main.entity.Stocks;

import java.util.List;

import main.Constants.Config;
import main.Constants.Constants;

public class StockMarket {
    private List<Stock> stocks;

    public StockMarket() {
        StockInformationRetriever stockInfoRetriever = new StockInformationRetriever(Config.getApiKey());
        try {
            // TODO: This method of creating stocks will be depreciated in future refactoring
            this.stocks = stockInfoRetriever.createStocks(Constants.STOCK_NAME_FILE);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize StockMarketViewModel", e);
        }
    }

    public List<Stock> getStocks() {
        return stocks;
    }
}
