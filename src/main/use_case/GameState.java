package main.use_case;

import main.entity.*;
import main.entity.tiles.PropertyTile;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

/**
 * Manages the core game state - acts as a repository for game entities
 */
public class GameState {
    private static final int FINISH_LINE_BONUS = 200;
    private static final int PLACEHOLDER_RENT = 50;
    private static final Color[] PLAYER_COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
    private static final String[] PLAYER_NAMES = {"Player 1", "Player 2", "Player 3", "Player 4"};

    private List<PropertyTile> properties;
    private List<Player> players;
    private int currentPlayerIndex = 0;
    private int tileCount;

    public void initializeGame() {
        initializeProperties();
        initializePlayers();
    }

    private void initializeProperties() {
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
            properties.add(new PropertyTile(propertyNames[i], prices[i], PLACEHOLDER_RENT));
        }
    }

    private void initializePlayers() {
        players = new ArrayList<>();
        players.add(new DefaultPlayer(PLAYER_NAMES[0], PLAYER_COLORS[0]));
        players.add(new clerk(PLAYER_NAMES[1], PLAYER_COLORS[1]));
        players.add(new collegeStudent(PLAYER_NAMES[2], PLAYER_COLORS[2]));
        players.add(new landlord(PLAYER_NAMES[3], PLAYER_COLORS[3]));
    }

    // Getters and setters
    public List<PropertyTile> getProperties() { return properties; }
    public List<Player> getPlayers() { return players; }
    public Player getPlayer(int index) {
        return (index >= 0 && index < players.size()) ? players.get(index) : null;
    }
    public Player getCurrentPlayer() {
        return currentPlayerIndex >= 0 ? players.get(currentPlayerIndex) : null;
    }
    public int getCurrentPlayerIndex() { return currentPlayerIndex; }
    public void setCurrentPlayerIndex(int index) { this.currentPlayerIndex = index; }
    public int getTileCount() { return tileCount; }
    public int getPlayerCount() { return players.size(); }
    public int getFinishLineBonus() { return FINISH_LINE_BONUS; }
}