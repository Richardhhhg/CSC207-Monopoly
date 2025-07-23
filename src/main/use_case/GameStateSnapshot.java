package main.use_case;

import main.entity.Player;
import org.json.Property;

import java.util.List;

public class GameStateSnapshot {
    private final List<Property> properties;
    private final List<Player> players;
    private final int currentPlayerIndex;
    private final int tileCount;

    public GameStateSnapshot(List<Property> properties, List<Player> players,
                             int currentPlayerIndex, int tileCount) {
        this.properties = properties;
        this.players = players;
        this.currentPlayerIndex = currentPlayerIndex;
        this.tileCount = tileCount;
    }

    // Getters
    public List<Property> getProperties() { return properties; }
    public List<Player> getPlayers() { return players; }
    public int getCurrentPlayerIndex() { return currentPlayerIndex; }
    public int getTileCount() { return tileCount; }
    public Player getCurrentPlayer() {
        return currentPlayerIndex >= 0 ? players.get(currentPlayerIndex) : null;
    }
}