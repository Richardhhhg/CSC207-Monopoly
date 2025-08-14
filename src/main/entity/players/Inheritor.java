package main.entity.players;

import java.awt.Color;

import main.constants.Constants;
import main.entity.stocks.Stock;

/**
 * A special type of Player called "Inheritor".
 * This character pays more for stocks but may later get passive bonuses.
 * Starts with $1000.
 */
public class Inheritor extends AbstractPlayer implements StockModifier {z
    public Inheritor(String name, Color color) {
        super(name, Constants.INHERITOR_INIT_MONEY, color);
        this.loadPortrait(Constants.INH_POR);
    }

    @Override
    public void buyStock(Stock stock, int quantity) {
        final double totalCost = stock.getCurrentPrice() * quantity;
        final double finalCost = adjustStockBuyPrice((float) totalCost);
        if (this.getMoney() >= totalCost) {
            this.deductMoney((float) finalCost);
            this.getStocks().put(stock, this.getStocks().getOrDefault(stock, 0) + quantity);
        }
    }

    @Override
    public void sellStock(Stock stock, int quantity) {
        if (this.getStocks().get(stock) >= quantity) {
            final double totalSale = stock.getCurrentPrice() * quantity;
            final double finalSale = adjustStockSellPrice((float) totalSale);
            this.addMoney((float) finalSale);
            this.getStocks().put(stock, this.getStocks().get(stock) - quantity);
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
        return (float) (basePrice * Constants.INHERITOR_STOCK_BUY_PRICE);
    }

    /**
     * Inheritors receive no bonus when selling stocks (default 0 here; can be updated).
     *
     * @param basePrice The base stock price.
     * @return The basePrice.
     */
    @Override
    public float adjustStockSellPrice(float basePrice) {
        return (float) (basePrice * Constants.INHERITOR_STOCK_SELL_PRICE);
    }
}
