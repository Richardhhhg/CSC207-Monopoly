package main.interface_adapter.StockMarket;

import main.Constants.Config;
import main.Constants.Constants;
import main.data_access.StockMarket.StockMarketInputDataObject;
import main.entity.Stock;

import java.util.List;
import main.entity.StockInformationRetriever;
import main.entity.StockMarket;

/**
 * Viewmodel for StockMarket
 * This should take in list of stocks
 * and create some output that is read into
 * StockMarketView
 */
public class StockMarketViewModel {
    private StockMarket stockMarket;

    public StockMarketViewModel(StockMarket stockMarket) {
        this.stockMarket = stockMarket;
    }

    public List<Stock> getStocks() {
        return stockMarket.getStocks();
    }

}
