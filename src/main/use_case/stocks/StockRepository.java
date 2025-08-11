package main.use_case.stocks;

import main.data_access.StockMarket.StockInfoDataOutputObject;

import java.io.IOException;

public interface StockRepository {
    /**
     * Retrieves stock information for a single ticker.
     *
     * @param ticker The stock ticker symbol.
     * @return StockInfoDataOutputObject containing stock information.
     * @throws IOException If an I/O error occurs while fetching stock data.
     * @throws InterruptedException If the thread is interrupted while waiting for a response.
     */
    StockInfoDataOutputObject getStockInfo(String ticker) throws IOException, InterruptedException;
}
