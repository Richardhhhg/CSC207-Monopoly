package main.use_case.Game;

import main.entity.Game;
import main.entity.tiles.PropertyTile;

import java.util.ArrayList;
import java.util.List;

import static main.Constants.Constants.PLACEHOLDER_RENT;

/**
 * Use case for initializing tiles in the game.
 * This class creates a list of PropertyTiles with predefined names and prices.
 */
public class GameInitializeTiles {
    private Game game;

    public GameInitializeTiles(Game game) {
        this.game = game;
    }

    public void execute() {
        // Initialize Tiles
        List<PropertyTile> tiles = new ArrayList<>();

        // TODO: Read this from json - Richard
        // TODO: Remove GO from properties - Richard
        // TODO: Also add more than just property tiles - Richard
        String[] propertyNames = {
                "GO", "Mediterranean Ave", "Baltic Ave", "Reading Railroad",
                "Oriental Ave", "Vermont Ave", "Connecticut Ave", "St. James Place",
                "Tennessee Ave", "New York Ave", "Kentucky Ave", "Indiana Ave",
                "Illinois Ave", "Atlantic Ave", "Ventnor Ave", "Marvin Gardens",
                "Pacific Ave", "North Carolina Ave", "Pennsylvania Ave", "Boardwalk"
        };

        int[] prices = {0, 60, 60, 200, 100, 100, 120, 140, 140, 160, 180, 180, 200, 220, 220, 280, 300, 300, 320, 400};
        int tileCount = propertyNames.length;

        for (int i = 0; i < tileCount; i++) {
            tiles.add(new PropertyTile(propertyNames[i], prices[i], PLACEHOLDER_RENT));
        }

        game.setTiles(tiles);
        game.setTileCount(tileCount);
    }
}
