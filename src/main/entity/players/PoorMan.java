package main.entity.players;

import java.awt.Color;

import main.constants.Constants;
import main.entity.stocks.Stock;

public class PoorMan extends AbstractPlayer implements ApplyAfterEffects {
    private static final int POORMAN_INIT_MONEY = 200;

    public PoorMan(String name, Color color) {
        super(name, POORMAN_INIT_MONEY, color);
        this.loadPortrait(Constants.PP_POR);
    }

    @Override
    public void buyStock(Stock stock, int quantity) {
        final double totalCost = stock.getCurrentPrice() * quantity;

        if (this.getMoney() >= totalCost) {
            this.deductMoney((float) totalCost);
            this.getStocks().put(stock, this.getStocks().getOrDefault(stock, 0) + quantity);
        }
    }

    @Override
    public void sellStock(Stock stock, int quantity) {
        if (this.getStocks().getOrDefault(stock, 0) >= quantity) {
            final double totalSale = stock.getCurrentPrice() * quantity;
            this.addMoney((float) totalSale);
            this.getStocks().put(stock, this.getStocks().get(stock) - quantity);
        }
    }

    @Override
    public void applyTurnEffects() {
        System.out.println("spare a dollar for a poor fellah.?");
        this.deductMoney(Constants.POORMAN_TURN_EFFECTS_COST);

    }
}
