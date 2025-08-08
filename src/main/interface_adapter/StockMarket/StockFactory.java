package main.interface_adapter.StockMarket;

import main.data_access.StockMarket.StockInfoDataOutputObject;
import main.entity.Stocks.Stock;

/**
 * Factory for creating stock DTOs and entities.
 * Updated to create DTOs while maintaining backward compatibility for entities.
 */
public class StockFactory {
    // New method creating DTOs
    public StockData createStockData(StockInfoDataOutputObject stockInfoDataOutputObject) {
        return new StockData(
            stockInfoDataOutputObject.ticker(),
            stockInfoDataOutputObject.currentPrice(),
            0.0 // Change will be updated separately
        );
    }

    public StockData createStockData(String ticker, double currentPrice, double change) {
        return new StockData(ticker, currentPrice, change);
    }

    // Backward compatibility method for creating entities
    public Stock execute(StockInfoDataOutputObject stockInfoDataOutputObject) {
        return new Stock(stockInfoDataOutputObject.ticker(),
                stockInfoDataOutputObject.currentPrice(),
                stockInfoDataOutputObject.meanDailyReturnPct(),
                stockInfoDataOutputObject.standardDeviationPct());
    }
}
