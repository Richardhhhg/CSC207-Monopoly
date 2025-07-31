package main.interface_adapter;

import java.awt.Color;
import java.awt.Image;
import java.util.List;

public class PlayerStatsViewModel {

    public static class Card {
        private final String name;
        private final float money;
        private final boolean bankrupt;
        private final Color color;
        private final Image portrait;
        private final List<String> propertyNames;

        public Card(String name,
                    float money,
                    boolean bankrupt,
                    Color color,
                    Image portrait,
                    List<String> propertyNames) {
            this.name = name;
            this.money = money;
            this.bankrupt = bankrupt;
            this.color = color;
            this.portrait = portrait;
            this.propertyNames = propertyNames;
        }

        public String getName() { return name; }
        public float getMoney() { return money; }
        public boolean isBankrupt() { return bankrupt; }
        public Color getColor() { return color; }
        public Image getPortrait() { return portrait; }
        public List<String> getPropertyNames() { return propertyNames; }
    }

    private final List<Card> cards;

    public PlayerStatsViewModel(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }
}