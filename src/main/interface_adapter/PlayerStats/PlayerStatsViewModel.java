package main.interface_adapter.PlayerStats;

import java.util.List;

public class PlayerStatsViewModel {

    private final List<PlayerCard> cards;

    public PlayerStatsViewModel(List<PlayerCard> cards) {
        this.cards = cards;
    }

    public List<PlayerCard> getCards() {
        return cards;
    }
}