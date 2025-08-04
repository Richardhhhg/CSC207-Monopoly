package main.interface_adapter.PlayerStats;

import main.use_case.PlayerStats.PlayerStatsOutput;
import main.use_case.PlayerStats.PlayerStatsOutputBoundary;
import main.use_case.PlayerStats.PlayerStatsOutputData;

import java.util.ArrayList;
import java.util.List;

public class PlayerStatsPresenter implements PlayerStatsOutputBoundary {

    private final PlayerStatsViewModel viewModel;

    public PlayerStatsPresenter(PlayerStatsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentPlayerStats(PlayerStatsOutputData outputData) {
        List<DisplayPlayer> players = new ArrayList<>();

        for (PlayerStatsOutput s : outputData.getPlayerStats()) {
            List<DisplayProperty> props = new ArrayList<>();
            for (String name : s.propertyNames) {
                props.add(new DisplayProperty(name));
            }
            players.add(new DisplayPlayer(
                    s.name,
                    s.money,
                    s.bankrupt,
                    s.color,
                    s.portrait,
                    props
            ));
        }
        viewModel.setState(new PlayerStatsState(players));
    }
}
