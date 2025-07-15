package entity;

import use_case.Player;

public class colledgeStudent extends Player {
    public colledgeStudent(String name, int initialMoney) {
        super(name, 1000);
        this.loadPortrait("Resources/Computer-nerd.jpg");
    }

    /**
     * @param basePrice
     * @return
     */
    @Override
    public float adjustStockBuyPrice(float basePrice) {
        return basePrice * 0.90f;
    }

    /**
     * @param basePrice
     * @return
     */
    @Override
    public float adjustStockSellPrice(float basePrice) {
        return basePrice * 1.3f;
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
        this.deductMoney(100);
    }
}
