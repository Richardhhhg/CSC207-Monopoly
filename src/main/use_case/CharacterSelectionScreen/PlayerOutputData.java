package main.use_case.CharacterSelectionScreen;

import java.awt.*;

public class PlayerOutputData {
    private final String name;
    private final String type;
    private final Color color;

    public PlayerOutputData(String name, String type, Color color) {
        this.name = name;
        this.type = type;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }
}
