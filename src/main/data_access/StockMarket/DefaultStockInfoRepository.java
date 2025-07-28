package main.data_access.StockMarket;

import main.use_case.Stocks.StockRepository;

public class DefaultStockInfoRepository implements StockRepository {
    /**
     * Generates sample data for a stock ticker.
     */
    public StockInfoDataOutputObject getStockInfo(String ticker) {
        return new StockInfoDataOutputObject(ticker, 100.00, 10, 30);
    }
}
