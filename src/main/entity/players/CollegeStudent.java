package main.entity.players;

import java.awt.Color;

import main.constants.Constants;
import main.entity.stocks.Stock;

public class CollegeStudent extends AbstractPlayer implements ApplyAfterEffects, StockModifier {
    private static final int STUDENT_INIT_MONEY = 800;

    public CollegeStudent(String name, Color color) {
        super(name, STUDENT_INIT_MONEY, color);
        this.loadPortrait(Constants.CS_POR);
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
     * College Student has to pay his school tuition 100$ every turn.
     */
    @Override
    public void applyTurnEffects() {
        this.deductMoney(Constants.COLLEGE_STUDENT_TUITION);
        System.out.println("ah man, the tuition goes up again!");
    }

    @Override
    public float adjustStockBuyPrice(float basePrice) {
        return basePrice * Constants.COLLEGE_STUDENT_STOCK_PRICE;
    }

    @Override
    public float adjustStockSellPrice(float basePrice) {
        return basePrice * Constants.COLLEGE_STUDENT_STOCK_SELL_PRICE;
    }
}
