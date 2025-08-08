package main.interface_adapter.CharacterSelectionScreen;

import java.awt.*;

public class PlayerOutputData {
    private final String name;
    private final String type;
    private final Color color;
    private final Image portrait;

    /**
     * This class is a placeholder for constants used throughout the application.
     * @param name name.
     * @param type type.
     * @param color color
     * @param portrait portrait
     */
    public PlayerOutputData(String name, String type, Color color, Image portrait) {
        this.name = name;
        this.type = type;
        this.color = color;
        this.portrait = portrait;
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @return PlayerOutputData name.
     */
    public String getName() {
        return name;
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @return PlayerOutputData type.
     */
    public String getType() {
        return type;
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @return PlayerOutputData color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @return PlayerOutputData portrait.
     */
    public Image getPortrait() {
        return portrait;
    }
}
