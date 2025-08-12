package main.data_access.stock_market;

import main.constants.Constants;
import main.use_case.stocks.StockRepository;

public class DefaultStockInfoRepository implements StockRepository {
    /**
     * Generates sample data for a stock ticker.
     *
     * @param ticker The stock ticker symbol.
     * @return A StockInfoDataOutputObject containing sample data.
     */
    public StockInfoDataOutputObject getStockInfo(String ticker) {
        return new StockInfoDataOutputObject(ticker, Constants.DEFAULT_STOCK_PRICE,
                Constants.DEFAULT_MEAN_RETURN, Constants.DEFAULT_STD_DEV);
    }
}
