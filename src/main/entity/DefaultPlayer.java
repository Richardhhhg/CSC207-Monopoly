package main.entity;

import main.use_case.Player;

import java.awt.*;

public class DefaultPlayer extends Player {
    private static final int DEFAULT_INIT_MONEY = 1200;
    private String name;

    public DefaultPlayer(String name, Color color) {
        super(name, DEFAULT_INIT_MONEY, color);
        this.loadPortrait("main/Resources/default portrait.png");
    }

    public String getName() {
        return this.name;
    }

    public float getMoney() {
        return this.money;
    }

    public int getPosition() {
        return this.position;
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

    @Override
    public float adjustStockSellPrice(float basePrice) {
        return basePrice;
    }

    @Override
    public float adjustRent(float baseRent) {
        return baseRent;
    }

    @Override
    public void applyTurnEffects() {
        System.out.println("nope, normie.");
    }
}
