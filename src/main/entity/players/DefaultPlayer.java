package main.entity.players;

import java.awt.Color;

import main.constants.Constants;

public class DefaultPlayer extends AbstractPlayer {
    private static final int DEFAULT_INIT_MONEY = 1200;

    public DefaultPlayer(String name, Color color) {
        super(name, DEFAULT_INIT_MONEY, color);
        this.loadPortrait(Constants.NP_POR);
    }
}
