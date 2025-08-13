package main.interface_adapter.stock_market;

import main.data_access.stock_market.StockInfoDataOutputObject;
import main.entity.stocks.Stock;

public class StockFactory {
    /**
     * Creates a Stock object from the StockInfoDataOutputObject.
     *
     * @param stockInfoDataOutputObject the data output object containing stock information
     * @return a Stock object initialized with the data from the output object
     */
    public Stock execute(StockInfoDataOutputObject stockInfoDataOutputObject) {
        return new Stock(stockInfoDataOutputObject.ticker(),
                stockInfoDataOutputObject.currentPrice(),
                stockInfoDataOutputObject.meanDailyReturnPct(),
                stockInfoDataOutputObject.standardDeviationPct());
    }
}
