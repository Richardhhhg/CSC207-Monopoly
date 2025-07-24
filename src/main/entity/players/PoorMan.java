package main.entity.players;

import main.entity.Stock;
import main.use_case.Player;

import java.awt.*;

public class PoorMan extends Player {
    private static final int POORMAN_INIT_MONEY = 200;
    public PoorMan(String name, Color color) {
        super(name, POORMAN_INIT_MONEY, color);
        this.loadPortrait("poormana.png");
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

    @Override
    public float adjustStockBuyPrice(float basePrice) {
        return 0;
    }

    @Override
    public float adjustStockSellPrice(float basePrice) {
        return 0;
    }

    @Override
    public float adjustRent(float baseRent) {
        return 0;
    }

    @Override
    public void applyTurnEffects() {
        System.out.println("spare a dollar for a poor fellah.?");
        this.deductMoney(20);

    }
}
