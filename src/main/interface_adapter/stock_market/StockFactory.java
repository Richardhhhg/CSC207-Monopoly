package main.interface_adapter.stock_market;

import main.data_access.stock_market.StockInfoDataOutputObject;
import main.entity.stocks.Stock;

public class StockFactory {
    /**
     * Makes a Stock object from a StockInfoDataOutputObject.
     * @param stockInfoDataOutputObject The StockInfoDataOutputObject containing stock information.
     * @return A Stock object initialized with the data from the StockInfoDataOutputObject.
     */
    public Stock execute(StockInfoDataOutputObject stockInfoDataOutputObject) {
        return new Stock(stockInfoDataOutputObject.ticker(),
                stockInfoDataOutputObject.currentPrice(),
                stockInfoDataOutputObject.meanDailyReturnPct(),
                stockInfoDataOutputObject.standardDeviationPct());
    }
}
