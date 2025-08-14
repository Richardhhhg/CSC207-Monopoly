package main.interface_adapter.character_selection_screen;

import java.awt.Color;
import java.awt.Image;

/**
 * ViewModel for a single player selection, used by the view to display player information.
 */
public class CharacterSelectionPlayerViewModel {
    private final String name;
    private final String type;
    private final Color color;
    private final Image portrait;

    public CharacterSelectionPlayerViewModel(String name, String type, Color color, Image portrait) {
        this.name = name;
        this.type = type;
        this.color = color;
        this.portrait = portrait;
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

    public Image getPortrait() {
        return portrait;
    }
}
