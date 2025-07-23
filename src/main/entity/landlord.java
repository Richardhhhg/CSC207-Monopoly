package main.entity;

import java.awt.*;

/**
 * A Player subclass representing a Landlord.
 * Landlords gain extra rent and can sell properties for more money.
 */
public class landlord extends Player {
    private static final int LANDLORD_INIT_MONEY = 800;
    public landlord(String name, Color color) {
        super(name, LANDLORD_INIT_MONEY, color);
        this.loadPortrait("main/Resources/landlord.png");
    }

    /**
     * @param basePrice
     * @return
     */
    @Override
    public float adjustStockBuyPrice(float basePrice) {
        return 0;
    }

    /**
     * @param basePrice
     * @return
     */
    @Override
    public float adjustStockSellPrice(float basePrice) {
        return (float) (basePrice * 0.8);
    }

    /**
     * @param baseRent
     * @return
     */
    @Override
    public float adjustRent(float baseRent) {
        return (float) (baseRent * 1.15);
    }

    /**
     * Landlord has no TurnEffects.
     */
    @Override
    public void applyTurnEffects() {
        System.out.println("Gimme the Rent!");
    }
}
