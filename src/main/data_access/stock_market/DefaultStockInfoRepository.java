package main.data_access.stock_market;

import main.constants.Constants;
import main.use_case.stocks.StockRepository;

public class DefaultStockInfoRepository implements StockRepository {
    /**
     * Generates sample data for a stock ticker.
     *
     * @param ticker the stock ticker symbol
     * @return a StockInfoDataOutputObject containing sample stock data
     */
    public StockInfoDataOutputObject getStockInfo(String ticker) {
        return new StockInfoDataOutputObject(ticker, Constants.DEFAULT_STOCK_PRICE, Constants.DEFAULT_STOCK_RETURN, Constants.DEFAULT_STOCK_STD_DEV);
    }
}
