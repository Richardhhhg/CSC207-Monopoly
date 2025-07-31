package main.interface_adapter;

import main.entity.players.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerStatsPresenter {
    public PlayerStatsViewModel toViewModel(List<Player> players) {
        List<PlayerStatsViewModel.Card> cards = new ArrayList<>(players.size());
        for (Player p : players) {
            cards.add(new PlayerStatsViewModel.Card(
                    p.getName(),
                    p.getMoney(),
                    p.getProperties(),
                    p.isBankrupt(),
                    p.getColor(),
                    p.getPortrait()
            ));
        }
        return new PlayerStatsViewModel(cards);
    }
}
