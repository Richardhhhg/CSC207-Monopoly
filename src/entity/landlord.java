package entity;

import use_case.Player;

/**
 * A Player subclass representing a Landlord.
 * Landlords gain extra rent and can sell properties for more money.
 */
public class landlord extends Player {
    public landlord(String name) {
        super(name, 800);
        this.loadPortrait("Resources/landlord.webp");
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
     *
     */
    @Override
    public void applyTurnEffects() {

    }
}
