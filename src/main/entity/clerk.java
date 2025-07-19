package main.entity;

import main.use_case.Player;

public class clerk extends Player {
    private static final int CLERK_INIT_MONEY = 1200;
    public clerk(String name, int initialMoney) {
        super(name, CLERK_INIT_MONEY);
        this.loadPortrait("Resources/clerk.jpg");
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
    }
}
