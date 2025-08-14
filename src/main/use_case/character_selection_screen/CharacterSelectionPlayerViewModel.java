package main.use_case.character_selection_screen;

import java.awt.Color;
import java.awt.Image;

/**
 * A data class representing the output information for a player during character selection.
 * This class is used to transfer player selection data from the use case layer to the view model and view.
 */
public class CharacterSelectionPlayerViewModel {
    private final String name;
    private final String type;
    private final Color color;
    private final Image portrait;

    /**
     * Constructs a PlayerOutputData object.
     *
     * @param name     The player's display name.
     * @param type     The type or class of the player (e.g., "Clerk", "Landlord").
     * @param color    The color assigned to the player.
     * @param portrait The portrait image for the player.
     */
    public CharacterSelectionPlayerViewModel(String name, String type, Color color, Image portrait) {
        this.name = name;
        this.type = type;
        this.color = color;
        this.portrait = portrait;
    }

    /**
     * Returns the player's display name.
     *
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the type or class of the player.
     *
     * @return The player's type.
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the color assigned to the player.
     *
     * @return The player's color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the portrait image for the player.
     *
     * @return The player's portrait.
     */
    public Image getPortrait() {
        return portrait;
    }
}
