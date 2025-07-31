package main.interface_adapter;

import main.entity.tiles.PropertyTile;

import java.awt.Color;
import java.awt.Image;
import java.util.List;

public class PlayerStatsViewModel {
    public static class Card {
        private final String name;
        private final float money;
        private final List<PropertyTile> properties;
        private final boolean bankrupt;
        private final Color color;
        private final Image portrait;

        public Card(String name, float money, List<PropertyTile> properties,
                    boolean bankrupt, Color color, Image portrait) {
            this.name = name;
            this.money = money;
            this.properties = properties;
            this.bankrupt = bankrupt;
            this.color = color;
            this.portrait = portrait;
        }

        // Match Player API so your paint code is unchanged
        public String getName() { return name; }
        public float getMoney() { return money; }
        public List<PropertyTile> getProperties() { return properties; }
        public boolean isBankrupt() { return bankrupt; }
        public Color getColor() { return color; }
        public Image getPortrait() { return portrait; }
    }

    private final List<Card> cards;
    public PlayerStatsViewModel(List<Card> cards) { this.cards = cards; }
    public List<Card> getCards() { return cards; }
}
