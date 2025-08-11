package main.use_case.playerStats;

import java.util.ArrayList;
import java.util.List;

import main.entity.Game;
import main.entity.players.Player;
import main.entity.tiles.PropertyTile;

/**
 * Gets stats for all players in the game and sends them to the presenter.
 */
public class PlayerStatsInteractor implements PlayerStatsInputBoundary {
    private final PlayerStatsOutputBoundary presenter;

    public PlayerStatsInteractor(PlayerStatsOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(Game game) {
        final PlayerStatsOutputData output = new PlayerStatsOutputData();
        for (Player p : game.getPlayers()) {
            final List<String> propertyNames = new ArrayList<>();
            final List<PropertyTile> props = p.getProperties();
            if (props != null) {
                for (PropertyTile prop : props) {
                    propertyNames.add(prop.getName());
                }
            }

            final PlayerStatsOutput stat = new PlayerStatsOutput(
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
