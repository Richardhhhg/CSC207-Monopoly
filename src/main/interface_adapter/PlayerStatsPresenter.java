package main.interface_adapter;

import main.entity.players.Player;
import main.entity.tiles.PropertyTile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerStatsPresenter {
    public PlayerStatsViewModel toViewModel(List<Player> players) {
        List<PlayerStatsViewModel.Card> cards = new ArrayList<>(players.size());
        for (Player p : players) {
            List<String> propertyNames = (p.getProperties() == null)
                    ? List.of()
                    : p.getProperties()
                    .stream()
                    .map(PropertyTile::getName)
                    .collect(Collectors.toList());

            cards.add(new PlayerStatsViewModel.Card(
                    p.getName(),
                    p.getMoney(),
                    p.isBankrupt(),
                    p.getColor(),
                    p.getPortrait(),
                    propertyNames
            ));
        }
        return new PlayerStatsViewModel(cards);
    }
}