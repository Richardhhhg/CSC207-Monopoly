package main.entity.players;

import main.entity.Stocks.Stock;
import main.use_case.Player;

import java.awt.*;

public class clerk extends Player {
    private static final int CLERK_INIT_MONEY = 1200;
    public clerk(String name, Color color) {
        super(name, CLERK_INIT_MONEY, color);
        this.loadPortrait("main/Resources/clerk.jpg");
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
     * Hello.
     * @param basePrice
     * @return
     */
    @Override
    public float adjustStockBuyPrice(float basePrice) {
        return basePrice;
    }

    /**
     * @param basePrice
     * @return
     */
    @Override
    public float adjustStockSellPrice(float basePrice) {
        return basePrice;
    }

    /**
     * @param baseRent
     * @return
     */
    @Override
    public float adjustRent(float baseRent) {
        return baseRent;
    }

    /**
     * Clerk recieves his salary every turn
     */
    @Override
    public void applyTurnEffects() {
        this.addMoney(50);
        System.out.println("Just another day");
    }
}
