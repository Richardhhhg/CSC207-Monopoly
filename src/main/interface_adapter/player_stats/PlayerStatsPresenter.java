package main.interface_adapter.player_stats;

import java.util.ArrayList;
import java.util.List;

import main.use_case.player_stats.PlayerStatsOutput;
import main.use_case.player_stats.PlayerStatsOutputBoundary;
import main.use_case.player_stats.PlayerStatsOutputData;

/**
 * Turns use case data into display objects for view.
 */
public class PlayerStatsPresenter implements PlayerStatsOutputBoundary {

    private final PlayerStatsViewModel viewModel;

    public PlayerStatsPresenter(PlayerStatsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentPlayerStats(PlayerStatsOutputData outputData) {
        final List<DisplayPlayer> players = new ArrayList<>();

        for (PlayerStatsOutput s : outputData.getPlayerStats()) {
            final List<DisplayProperty> props = new ArrayList<>();
            for (String name : s.getPropertyNames()) {
                props.add(new DisplayProperty(name));
            }
            players.add(new DisplayPlayer(
                    s.getName(),
                    s.getMoney(),
                    s.isBankrupt(),
                    s.getColor(),
                    s.getPortrait(),
                    props
            ));
        }
        viewModel.setState(new PlayerStatsState(players));
    }
}
