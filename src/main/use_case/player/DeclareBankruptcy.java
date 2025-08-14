package main.use_case.player;

import main.entity.players.AbstractPlayer;
import main.entity.tiles.PropertyTile;

import java.util.ArrayList;
import java.util.List;

public class DeclareBankruptcy {
    public void execute(AbstractPlayer abstractPlayer) {
        if (abstractPlayer == null) return;
        List<PropertyTile> owned = new ArrayList<>(abstractPlayer.getProperties());
        for (PropertyTile p : owned) {
            p.setOwned(false, null);
        }
        abstractPlayer.getProperties().clear();
    }
}
