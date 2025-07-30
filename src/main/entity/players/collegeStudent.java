package main.entity.players;

import main.entity.Stocks.Stock;
import main.use_case.Player;
import main.entity.Stock;
import main.entity.tiles.PropertyTile;

import java.awt.*;

import static main.Constants.Constants.STUDENT_INIT_MONEY;

public class collegeStudent extends Player implements applyAfterEffects, StockModifier{
    public collegeStudent(String name, Color color) {
        super(name, STUDENT_INIT_MONEY,color);
        this.loadPortrait("main/Resources/Computer-nerd.jpg");
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
     * College Student has to pay his school tuition every turn.
     */
    @Override
    public void applyTurnEffects() {
        this.deductMoney(100);
        System.out.println("ah man, the tuition goes up again!");
    }

    @Override
    public float adjustStockBuyPrice(float basePrice) {
        return basePrice * 0.90f;
    }

    @Override
    public float adjustStockSellPrice(float basePrice) {
        return basePrice * 1.3f;
    }
}
