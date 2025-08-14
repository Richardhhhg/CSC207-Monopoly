package main.entity.players;

import java.awt.Color;

import main.constants.Constants;

public class DefaultPlayer extends AbstractPlayer {

    public DefaultPlayer(String name, Color color) {
        super(name, Constants.DEFAULT_INIT_MONEY, color);
        this.loadPortrait(Constants.NP_POR);
    }
}
