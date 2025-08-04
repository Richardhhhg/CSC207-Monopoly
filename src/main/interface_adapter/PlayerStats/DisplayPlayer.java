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

    public boolean isBankrupt() { return bankrupt; }
    public Color getColor() { return color; }
    public String getName() { return name; }
    public float getMoney() { return money; }
    public List<DisplayProperty> getProperties() { return Collections.unmodifiableList(properties); }
    public Image getPortrait() { return portrait; }
}
