package main.entity.players;

import main.entity.Stock;
import main.entity.Stocks.Stock;
import main.use_case.Player;

import java.awt.*;

import static main.Constants.Constants.INHERITOR_INIT_MONEY;

/**
 * A special type of Player called "Inheritor".
 * This character pays more for stocks but may later get passive bonuses.
 * Starts with $1000.
 */
public class inheritor extends Player implements StockModifier{
    public inheritor(String name, Color color) {
        super(name, INHERITOR_INIT_MONEY, color);
        this.loadPortrait("main/Resources/inheritor.jpg");
    }

    @Override
    public void buyStock(Stock stock, int quantity) {
        double totalCost = stock.getCurrentPrice() * quantity;
        double finalCost = adjustStockBuyPrice((float) totalCost);
        if (this.money >= totalCost) {
            this.deductMoney((float) finalCost);
            stocks.put(stock, stocks.getOrDefault(stock, 0) + quantity);
        }
    }

    @Override
    public void sellStock(Stock stock, int quantity) {
        if (stocks.get(stock) >= quantity) {
            double totalSale = stock.getCurrentPrice() * quantity;
            double finalSale = adjustStockSellPrice((float) totalSale);
            this.addMoney((float) finalSale);
            stocks.put(stock, stocks.get(stock) - quantity);
        }
    }

    /**
     * Inheritors pay 10% more than the base price when buying stocks.
     *
     * @param basePrice The base stock price.
     * @return The adjusted stock buy price.
     */
    @Override
    public float adjustStockBuyPrice(float basePrice) {
        return (float) (basePrice * 1.1); // Pays 10% more when buying
    }

    /**
     * Inheritors receive no bonus when selling stocks (default 0 here; can be updated).
     *
     * @param basePrice The base stock price.
     * @return The basePrice.
     */
    @Override
    public float adjustStockSellPrice(float basePrice) {
        return (float) (basePrice * 0.7);
    }
}
