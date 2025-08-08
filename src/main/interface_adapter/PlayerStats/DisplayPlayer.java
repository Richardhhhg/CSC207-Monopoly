package main.interface_adapter.PlayerStats;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
     * This class is a placeholder for constants used throughout the application.
     * @return bankrupt
     */
    public boolean isBankrupt() {
        return bankrupt;
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @return money
     */
    public float getMoney() {
        return money;
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @return Collections.unmodifiableList(properties);
     */
    public List<DisplayProperty> getProperties() {
        return Collections.unmodifiableList(properties);
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @return portrait.
     */
    public Image getPortrait() {
        return portrait;
    }
}
