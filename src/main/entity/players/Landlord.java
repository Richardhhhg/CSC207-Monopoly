package main.entity.players;

import main.entity.stocks.Stock;

import java.awt.*;

import static main.constants.Constants.LANDLORD_INIT_MONEY;
import static main.constants.Constants.LL_POR;

/**
 * A Player subclass representing a Landlord.
 * Landlords gain extra rent and can sell properties for more money.
 */
public class Landlord extends Player implements RentModifier, StockModifier {
    public Landlord(String name, Color color) {
        super(name, LANDLORD_INIT_MONEY, color);
        this.loadPortrait(LL_POR);
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
     * @param basePrice
     * @return
     */
    @Override
    public float adjustStockBuyPrice(float basePrice) {
        return (float) (basePrice * 1.8);
    }

    /**
     * @param basePrice
     * @return
     */
    @Override
    public float adjustStockSellPrice(float basePrice) {
        return (float) (basePrice * 0.8);
    }
    /**
     * @param baseRent
     * @return
     */
    @Override
    public float adjustRent(float baseRent) {
        return (float) (baseRent * 1.8);
    }
}
