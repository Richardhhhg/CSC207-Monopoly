package main.entity.players;

import main.entity.stocks.Stock;

import java.awt.*;

import static main.constants.Constants.POORMAN_INIT_MONEY;
import static main.constants.Constants.PP_POR;

public class PoorMan extends AbstractPlayer implements ApplyAfterEffects {
    public PoorMan(String name, Color color) {
        super(name, POORMAN_INIT_MONEY, color);
        this.loadPortrait(PP_POR);
    }

    @Override
    public void buyStock(Stock stock, int quantity) {
        double totalCost = stock.getCurrentPrice() * quantity;

        if (this.getMoney() >= totalCost) {
            this.deductMoney((float) totalCost);
            this.getStocks().put(stock, this.getStocks().getOrDefault(stock, 0) + quantity);
        }
    }

    @Override
    public void sellStock(Stock stock, int quantity) {
        if (this.getStocks().getOrDefault(stock, 0) >= quantity) {
            double totalSale = stock.getCurrentPrice() * quantity;
            this.addMoney((float) totalSale);
            this.getStocks().put(stock, this.getStocks().get(stock) - quantity);
        }
    }

    @Override
    public void applyTurnEffects() {
        System.out.println("spare a dollar for a poor fellah.?");
        this.deductMoney(20);

    }
}
