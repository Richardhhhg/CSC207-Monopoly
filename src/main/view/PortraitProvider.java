package main.view;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class PortraitProvider {
    /**
     * Returns the portrait image based on the type of character.
     *
     * @param type The type of character (e.g., "Clerk", "Landlord", etc.)
     * @return The corresponding portrait image, or a default image if the type is unknown.
     */
    public static Image getPortrait(String type) {
        final Image result;
        switch (type) {
            case "Clerk":
                result = loadImage("CharacterPortrait/clerk.jpg");
                break;
            case "Landlord":
                result = loadImage("CharacterPortrait/landlord.png");
                break;
            case "Inheritor":
                result = loadImage("CharacterPortrait/inheritor.png");
                break;
            case "College Student":
                result = loadImage("CharacterPortrait/Computer-nerd.jpg");
                break;
            case "Poor Man":
                result = loadImage("CharacterPortrait/poorman.png");
                break;
            default:
                result = loadImage("CharacterPortrait/default portrait.png");
                break;
        }
        return result;
    }

    private static Image loadImage(String path) {
        try (InputStream is = PortraitProvider.class.getResourceAsStream("/" + path)) {
            if (is != null) {
                return ImageIO.read(is);
            }
            else {
                System.err.println("Image not found: " + path);
            }
        }
        catch (IOException exception) {
            System.err.println("Error loading image: " + path);
            exception.printStackTrace();
        }
    }

    public static Image getDefaultPortrait() {
        return loadImage("CharacterPortrait/default portrait.png");
    }
}
