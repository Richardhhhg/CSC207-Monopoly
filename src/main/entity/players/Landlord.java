package main.entity.players;

import java.awt.Color;

import main.constants.Constants;
import main.entity.stocks.Stock;

/**
 * A Player subclass representing a Landlord.
 * Landlords gain extra rent and can sell properties for more money.
 */
public class Landlord extends AbstractPlayer implements RentModifier, StockModifier {
    public Landlord(String name, Color color) {
        super(name, Constants.LANDLORD_INIT_MONEY, color);
        this.loadPortrait(Constants.LL_POR);
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
     * Landlords pay 80% more than the base price when buying stocks.
     *
     * @param basePrice The base stock price.
     * @return The adjusted stock buy price.
     */
    @Override
    public float adjustStockBuyPrice(float basePrice) {
        return (float) (basePrice * Constants.LANDLORD_STOCK_BUY_PRICE);
    }

    /**
     * Sell price reduced for Landlords.
     *
     * @param basePrice The base stock price.
     * @return The adjusted stock sell price.
     */
    @Override
    public float adjustStockSellPrice(float basePrice) {
        return (float) (basePrice * Constants.LANDLORD_STOCK_SELL_PRICE);
    }

    /**
     * Rent adjustment for Landlords.
     *
     * @param baseRent The base rent amount.
     * @return The adjusted rent amount.
     */
    @Override
    public float adjustRent(float baseRent) {
        return (float) (baseRent * Constants.LANDLORD_RENT_MULTIPLIER);
    }
}
