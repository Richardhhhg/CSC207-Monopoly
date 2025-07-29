package main.entity.players;

public interface StockModifier {
    float adjustStockBuyPrice(float basePrice);
    float adjustStockSellPrice(float basePrice);
}
