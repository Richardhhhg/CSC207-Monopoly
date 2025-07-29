package main.entity.players;

import main.entity.Stock;

import java.awt.*;

import static main.Constants.Constants.DEFAULT_INIT_MONEY;

public class DefaultPlayer extends Player {
    private String name;

    public DefaultPlayer(String name, Color color) {
        super(name, DEFAULT_INIT_MONEY, color);
        this.loadPortrait("main/Resources/default portrait.png");
    }

    public String getName() {
        return this.name;
    }

    public float getMoney() {
        return this.money;
    }

    public int getPosition() {
        return this.position;
    }
}
