package main.entity.players;

public interface StockModifier {
    /**
     * Adjusts the stock buy/sell price based on the base price.
     *
     * @param basePrice The base stock price.
     * @return The adjusted stock price.
     */
    float adjustStockBuyPrice(float basePrice);

    /**
     * Adjusts the stock sell price based on the base price.
     *
     * @param basePrice The base stock price.
     * @return The adjusted stock price.
     */
    float adjustStockSellPrice(float basePrice);
}
