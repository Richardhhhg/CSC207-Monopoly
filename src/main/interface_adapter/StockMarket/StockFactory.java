package main.interface_adapter.StockMarket;

import main.data_access.StockMarket.StockInfoDataOutputObject;
import main.entity.Stocks.Stock;

public class StockFactory {
    public Stock execute(StockInfoDataOutputObject stockInfoDataOutputObject) {
        return new Stock(stockInfoDataOutputObject.ticker(),
                stockInfoDataOutputObject.currentPrice(),
                stockInfoDataOutputObject.meanDailyReturnPct(),
                stockInfoDataOutputObject.standardDeviationPct());
    }
}
