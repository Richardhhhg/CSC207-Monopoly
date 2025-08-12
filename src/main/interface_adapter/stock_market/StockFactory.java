package main.interface_adapter.stock_market;

import main.data_access.stock_market.StockInfoDataOutputObject;
import main.entity.stocks.Stock;

public class StockFactory {
    /**
     * Creates a Stock entity from StockInfoDataOutputObject.
     *
     * @param stockInfoDataOutputObject the data object containing stock information
     * @return a Stock entity
     */
    public Stock execute(StockInfoDataOutputObject stockInfoDataOutputObject) {
        return new Stock(stockInfoDataOutputObject.ticker(),
                stockInfoDataOutputObject.currentPrice(),
                stockInfoDataOutputObject.meanDailyReturnPct(),
                stockInfoDataOutputObject.standardDeviationPct());
    }
}
