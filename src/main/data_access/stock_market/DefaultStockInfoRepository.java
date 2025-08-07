package main.data_access.stock_market;

import main.use_case.stocks.StockRepository;

public class DefaultStockInfoRepository implements StockRepository {
    private static final float DEFAULT_PRICE = 100.00f;
    private static final int DEFAULT_RETURN = 10;
    private static final int DEFAULT_STD = 30;

    /**
     * Generates sample data for a stock ticker.
     * @param ticker The stock ticker symbol.
     * @return A StockInfoDataOutputObject containing sample stock data.
     */
    public StockInfoDataOutputObject getStockInfo(String ticker) {
        return new StockInfoDataOutputObject(ticker, DEFAULT_PRICE, DEFAULT_RETURN, DEFAULT_STD);
    }
}
