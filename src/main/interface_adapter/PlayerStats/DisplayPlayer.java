package main.interface_adapter.PlayerStats;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Player data ready for display.
 */
public class DisplayPlayer {
    private final String name;
    private final float money;
    private final boolean bankrupt;
    private final Color color;
    private final Image portrait;
    private final List<DisplayProperty> properties;

    public DisplayPlayer(String name,
                         float money,
                         boolean bankrupt,
                         Color color,
                         Image portrait,
                         List<DisplayProperty> properties) {
        this.name = name;
        this.money = money;
        this.bankrupt = bankrupt;
        this.color = color;
        this.portrait = portrait;
        this.properties = new ArrayList<>(properties);
    }

    /**
     * Returns player is bankruped or not.
     * @return bankrupt
     */
    public boolean isBankrupt() {
        return bankrupt;
    }

    /**
     * Returns player color.
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns player name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns player money worth.
     * @return money
     */
    public float getMoney() {
        return money;
    }

    /**
     * Returns a list of player's property.
     * @return Collections.unmodifiableList(properties);
     */
    public List<DisplayProperty> getProperties() {
        return Collections.unmodifiableList(properties);
    }

    /**
     * Returns player's portrait.
     * @return portrait.
     */
    public Image getPortrait() {
        return portrait;
    }
}
