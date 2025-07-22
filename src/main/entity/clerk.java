package main.entity;

import main.use_case.Player;

import java.awt.*;

public class clerk extends Player {
    private static final int CLERK_INIT_MONEY = 1200;
    public clerk(String name, Color color) {
        super(name, CLERK_INIT_MONEY, color);
        this.loadPortrait("main/Resources/clerk.jpg");
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
        this.addMoney(500);
        System.out.println("Just another day");
    }
}
