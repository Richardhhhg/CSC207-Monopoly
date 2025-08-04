package main.use_case.PlayerStats;

import main.entity.Game;
import main.entity.players.Player;
import main.entity.tiles.PropertyTile;

import java.util.ArrayList;
import java.util.List;

public class PlayerStatsInteractor implements PlayerStatsInputBoundary {
    private final PlayerStatsOutputBoundary presenter;

    public PlayerStatsInteractor(PlayerStatsOutputBoundary presenter) {
        this.presenter = presenter;
    }


    @Override
    public void execute(Game game) {
        PlayerStatsOutputData output = new PlayerStatsOutputData();
        for (Player p : game.getPlayers()) {
            List<String> propertyNames = new ArrayList<>();
            List<PropertyTile> props = p.getProperties();
            if (props != null) {
                for (PropertyTile prop : props) {
                    propertyNames.add(prop.getName());
                }
            }

            PlayerStatsOutput stat = new PlayerStatsOutput(
                    p.getName(),
                    p.getMoney(),
                    p.isBankrupt(),
                    p.getColor(),
                    p.getPortrait(),
                    propertyNames
            );
            output.add(stat);
        }

        presenter.presentPlayerStats(output);
    }


}
