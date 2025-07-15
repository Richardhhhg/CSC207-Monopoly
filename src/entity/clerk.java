package entity;

import use_case.Player;

public class clerk extends Player {
    public clerk(String name, int initialMoney) {
        super(name, 1200);
        this.loadPortrait("Resources/clerk.jpg");
    }

    /**
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
     *
     */
    @Override
    public void applyTurnEffects() {
        this.addMoney(500);
    }
}
