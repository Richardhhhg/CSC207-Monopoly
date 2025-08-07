package main.interface_adapter.CharacterSelectionScreen;

import java.awt.*;

public class PlayerOutputData {
    private final String name;
    private final String type;
    private final Color color;
    private final Image portrait;

    public PlayerOutputData(String name, String type, Color color, Image portrait) {
        this.name = name;
        this.type = type;
        this.color = color;
        this.portrait = portrait;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public Color getColor() { return color; }
    public Image getPortrait() { return portrait; }
}
