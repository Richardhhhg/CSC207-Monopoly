package main.use_case.player;

import main.entity.players.Player;
import main.entity.tiles.PropertyTile;

import java.util.ArrayList;
import java.util.List;

public class DeclareBankruptcy {
    public void execute(Player player) {
        if (player == null) return;
        List<PropertyTile> owned = new ArrayList<>(player.getProperties());
        for (PropertyTile p : owned) {
            p.setOwned(false, null);
        }
        player.getProperties().clear();
    }
}
