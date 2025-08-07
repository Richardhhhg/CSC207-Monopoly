package main.entity.players;

import java.awt.*;

public class CharacterFactory {

    public static Player createPlayer(String name, String type, Color color) {
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
