package main.use_case.player;

import java.util.ArrayList;
import java.util.List;

import main.entity.players.AbstractPlayer;
import main.entity.tiles.PropertyTile;

public class DeclareBankruptcy {
    /**
     * Executes the bankruptcy declaration for a player.
     * This method sets all properties owned by the player to unowned and clears the player's property list.
     *
     * @param player the player who is declaring bankruptcy
     * @throws IllegalArgumentException if the player is null
     */
    public void execute(AbstractPlayer player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        final List<PropertyTile> owned = new ArrayList<>(player.getProperties());
        for (PropertyTile p : owned) {
            p.setOwned(false, null);
        }
        player.getProperties().clear();
    }
}
