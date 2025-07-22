package main.entity;

import main.use_case.Player;

import java.awt.*;

/**
 * A special type of Player called "Inheritor".
 * This character pays more for stocks but may later get passive bonuses.
 * Starts with $1000.
 */
public class inheritor extends Player {
    private static final int INHERITOR_INIT_MONEY = 1800;
    public inheritor(String name, Color color) {
        super(name, INHERITOR_INIT_MONEY, color);
        this.loadPortrait("main/Resources/inheritor.jpg");
    }

    /**
     * Inheritors pay 10% more than the base price when buying stocks.
     *
     * @param basePrice The base stock price.
     * @return The adjusted stock buy price.
     */
    @Override
    public float adjustStockBuyPrice(float basePrice) {
        return (float) (basePrice * 1.1); // Pays 10% more when buying
    }

    /**
     * Inheritors receive no bonus when selling stocks (default 0 here; can be updated).
     *
     * @param basePrice The base stock price.
     * @return The basePrice.
     */
    @Override
    public float adjustStockSellPrice(float basePrice) {
        return basePrice;
    }

    /**
     * Inheritors do not affect rent calculation (default 0 here; can be updated).
     *
     * @param baseRent The base rent.
     * @return The adjusted baseRent.
     */
    @Override
    public float adjustRent(float baseRent) {
        return baseRent;
    }

    /**
     * Inheritor has no TurnEffects.
     */
    @Override
    public void applyTurnEffects() {

    }
}
