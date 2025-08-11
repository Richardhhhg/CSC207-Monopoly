package main.use_case.playerStats;

import java.awt.Color;
import java.awt.Image;
import java.util.List;

/**
 * Stats for one player.
 * Includes name, money, bankruptcy status, color, portrait, and owned property names.
 */
public class PlayerStatsOutput {
    private final String name;
    private final float money;
    private final boolean bankrupt;
    private final Color color;
    private final Image portrait;
    private final List<String> propertyNames;

    public PlayerStatsOutput(String name,
                             float money,
                             boolean bankrupt,
                             Color color,
                             Image portrait,
                             List<String> propertyNames) {
        this.name = name;
        this.money = money;
        this.bankrupt = bankrupt;
        this.color = color;
        this.portrait = portrait;
        this.propertyNames = propertyNames;
    }

    /**
     * Get output name.
     * @return name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get output bankrupt status.
     * @return bankrupt.
     */
    public boolean isBankrupt() {
        return this.bankrupt;
    }

    /**
     * Get output money.
     * @return money.
     */
    public float getMoney() {
        return this.money;
    }

    /**
     * Get output color.
     * @return color.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Get output portrait.
     * @return portrait.
     */
    public Image getPortrait() {
        return this.portrait;
    }

    /**
     * Get a list of output property names.
     * @return property names.
     */
    public List<String> getPropertyNames() {
        return propertyNames;
    }

}
