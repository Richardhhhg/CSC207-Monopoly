package main.view;

import main.entity.*;
import main.entity.tiles.Property;
import main.use_case.Player;
import main.use_case.PlayerManager;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;

import static main.Constants.Constants.FINISH_LINE_BONUS;

/**
 * GameBoard manages the game state and logic, separate from UI concerns.
 */
public class GameBoard {
    private static final int PLACEHOLDER_RENT = 50;
    private static final int PLAYER_COUNT = 4;
    private static final Color[] PLAYER_COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
    private static final String[] PLAYER_NAMES = {"Player 1", "Player 2", "Player 3", "Player 4"};

    private ArrayList<Property> properties;
    private PlayerManager playerManager;
    private int tileCount;

    public GameBoard() {
        initializeGame();
    }

    /**
     * Initializes Properties and Players
     */
    public void initializeGame() {
        // Initialize properties
        properties = new ArrayList<>();
        String[] propertyNames = {
                "GO", "Mediterranean Ave", "Baltic Ave", "Reading Railroad",
                "Oriental Ave", "Vermont Ave", "Connecticut Ave", "St. James Place",
                "Tennessee Ave", "New York Ave", "Kentucky Ave", "Indiana Ave",
                "Illinois Ave", "Atlantic Ave", "Ventnor Ave", "Marvin Gardens",
                "Pacific Ave", "North Carolina Ave", "Pennsylvania Ave", "Boardwalk"
        };

        int[] prices = {0, 60, 60, 200, 100, 100, 120, 140, 140, 160, 180, 180, 200, 220, 220, 280, 300, 300, 320, 400};
        this.tileCount = propertyNames.length;

        for (int i = 0; i < tileCount; i++) {
            properties.add(new Property(propertyNames[i], prices[i], PLACEHOLDER_RENT));
        }

        ArrayList<Player> playerList = new ArrayList<>();
        DefaultPlayer defaultPlayer = new DefaultPlayer(PLAYER_NAMES[0], PLAYER_COLORS[0]);
        clerk clerk = new clerk(PLAYER_NAMES[1], PLAYER_COLORS[1]);
        //collegeStudent collegeStudent = new collegeStudent(PLAYER_NAMES[2], PLAYER_COLORS[2]);
        poorGuy poorGuy = new poorGuy(PLAYER_NAMES[2], PLAYER_COLORS[2]);
        landlord landlord = new landlord(PLAYER_NAMES[3], PLAYER_COLORS[3]);
        playerList.add(defaultPlayer);
        playerList.add(clerk);
        playerList.add(poorGuy);
        playerList.add(landlord);

        playerManager = new PlayerManager(playerList);
    }

    public void moveCurrentPlayer(int steps) {
        playerManager.moveCurrentPlayer(steps, tileCount);
    }

    public void nextPlayer() {
        playerManager.advanceTurn();
    }

    public boolean isGameOver() {
        return playerManager.isGameOver();
    }

    public Player getCurrentPlayer() {
        return playerManager.getCurrentPlayer();
    }

    public Point getTilePosition(int position, int startX, int startY, int tileSize) {
        int tilesPerSide = this.tileCount / 4;
        int cool_number = tilesPerSide * tileSize;

        if (position >= 0 && position <= tilesPerSide) {
            // Bottom row (left to right)
            return new Point(startX + position * tileSize, startY + cool_number);
        } else if (position >= (tilesPerSide+1) && position <= tilesPerSide*2) {
            // Right column (bottom to top)
            return new Point(startX + cool_number, startY + cool_number - (position - tilesPerSide) * tileSize);
        } else if (position >= (tilesPerSide*2 + 1) && position <= tilesPerSide*3) {
            // Top row (right to left)
            return new Point(startX + cool_number - (position - tilesPerSide*2) * tileSize, startY);
        } else {
            // Left column (top to bottom)
            return new Point(startX, startY + (position - tilesPerSide*3) * tileSize);
        }
    }

    // Getters
    public List<Property> getProperties() {
        return properties;
    }

    public List<Player> getPlayers() {
        return playerManager.getPlayers();
    }

    public int getTileCount() {
        return tileCount;
    }

    public int getCurrentPlayerIndex() {
        return playerManager.getCurrentPlayerIndex();
    }
}