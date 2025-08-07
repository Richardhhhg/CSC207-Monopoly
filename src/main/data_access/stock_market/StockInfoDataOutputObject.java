package main.data_access.stock_market;

public record StockInfoDataOutputObject(String ticker, double currentPrice, double meanDailyReturnPct,
                                        double standardDeviationPct) {
}
