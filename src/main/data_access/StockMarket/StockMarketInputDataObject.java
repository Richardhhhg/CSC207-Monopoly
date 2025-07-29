package main.data_access.StockMarket;

import main.entity.Stocks.Stock;

import java.util.List;

public class StockMarketInputDataObject {
    List<Stock> stocks;

    public StockMarketInputDataObject(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public List<Stock> getStocks() {
        return stocks;
    }
}
