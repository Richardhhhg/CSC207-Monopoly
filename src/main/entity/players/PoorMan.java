package main.entity.players;

import main.entity.Stocks.Stock;

import java.awt.*;

import static main.Constants.Constants.POORMAN_INIT_MONEY;

public class PoorMan extends Player implements  applyAfterEffects {
    public PoorMan(String name, Color color) {
        super(name, POORMAN_INIT_MONEY, color);
        this.loadPortrait("main/Resources/poorman.png");
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

    @Override
    public void applyTurnEffects() {
        System.out.println("spare a dollar for a poor fellah.?");
        this.deductMoney(20);

    }
}
