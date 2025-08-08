package main.view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class PortraitProvider {
    public static Image getPortrait(String type) {
        switch (type) {
            case "Clerk":
                return loadImage("CharacterPortrait/clerk.jpg");
            case "Landlord":
                return loadImage("CharacterPortrait/landlord.png");
            case "Inheritor":
                return loadImage("CharacterPortrait/inheritor.jpg");
            case "College Student":
                return loadImage("CharacterPortrait/Computer-nerd.jpg");
            case "Poor Man":
                return loadImage("CharacterPortrait/poorman.png");
            default:
                return loadImage("CharacterPortrait/default portrait.png");
        }
    }

    private static Image loadImage(String path) {
        try (InputStream is = PortraitProvider.class.getResourceAsStream("/" + path)) {
            if (is != null) {
                return ImageIO.read(is);
            } else {
                System.err.println("Image not found: " + path);
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + path);
            e.printStackTrace();
        }
        return null;
    }

    public static Image getDefaultPortrait() {
        return loadImage("CharacterPortrait/default portrait.png");
    }
}

