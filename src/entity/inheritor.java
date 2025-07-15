package entity;

import use_case.Player;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

/**
 * A special type of Player called "Inheritor".
 * This character pays more for stocks but may later get passive bonuses.
 * Starts with $1000.
 */
public class inheritor extends Player {
    private static final int INHERITOR_INIT_MONEY = 1800;
    public inheritor(String name) {
        super(name, INHERITOR_INIT_MONEY);
        this.loadPortrait("Resources/inheritor.jpg");
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
