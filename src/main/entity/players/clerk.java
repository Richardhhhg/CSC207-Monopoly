package main.entity.players;

import main.entity.Stock;

import java.awt.*;

public class clerk extends Player implements applyAfterEffects {
    private static final int CLERK_INIT_MONEY = 1200;
    public clerk(String name, Color color) {
        super(name, CLERK_INIT_MONEY, color);
        this.loadPortrait("main/Resources/clerk.jpg");
    }

    @Override
    public void buyStock(Stock stock, int quantity) {
        double totalCost = stock.getCurrentPrice() * quantity;

        if (this.money >= totalCost) {
            this.deductMoney((float) totalCost);
            stocks.put(stock, stocks.getOrDefault(stock, 0) + quantity);
        }
    }

    @Override
    public void sellStock(Stock stock, int quantity) {
        if (stocks.getOrDefault(stock, 0) >= quantity) {
            double totalSale = stock.getCurrentPrice() * quantity;
            this.addMoney((float) totalSale);
            stocks.put(stock, stocks.get(stock) - quantity);
        }
    }

    /**
     *
     * apply turn effect
     */
    @Override
    public void applyTurnEffects() {
        this.addMoney(50);
        System.out.println("Just another day");
    }
}
