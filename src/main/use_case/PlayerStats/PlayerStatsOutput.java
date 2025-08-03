package main.use_case.PlayerStats;

import java.awt.*;
import java.util.List;

public class PlayerStatsOutput {
    public final String name;
    public final float money;
    public final boolean bankrupt;
    public final Color color;
    public final Image portrait;
    public final List<String> propertyNames;

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
}
