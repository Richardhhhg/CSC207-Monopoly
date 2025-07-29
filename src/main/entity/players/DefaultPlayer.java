package main.entity.players;

import main.entity.Stock;

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
}
