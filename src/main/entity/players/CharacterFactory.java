package main.entity.players;

import java.awt.Color;

public class CharacterFactory {

    /**
     * Creates a player based on the given name, type, and color.
     *
     * @param name name of the player
     * @param type type of the player (e.g., "Clerk", "Landlord", "Inheritor", "College Student",
     * @param color color of the player
     * @return a Player object of the specified type, or a NullPlayer if the type is invalid
     */
    public static AbstractPlayer createPlayer(String name, String type, Color color) {
        return switch (type) {
            case "Clerk" -> new Clerk(name, color);
            case "Landlord" -> new Landlord(name, color);
            case "Inheritor" -> new Inheritor(name, color);
            case "College Student" -> new CollegeStudent(name, color);
            case "Poor Man" -> new PoorMan(name, color);
            default -> new NullPlayer();
        };
    }
}
