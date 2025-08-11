package main.interface_adapter.stockMarket;

import main.data_access.StockMarket.StockInfoDataOutputObject;
import main.entity.stocks.Stock;

public class StockFactory {
    public Stock execute(StockInfoDataOutputObject stockInfoDataOutputObject) {
        return new Stock(stockInfoDataOutputObject.ticker(),
                stockInfoDataOutputObject.currentPrice(),
                stockInfoDataOutputObject.meanDailyReturnPct(),
                stockInfoDataOutputObject.standardDeviationPct());
    }
}
