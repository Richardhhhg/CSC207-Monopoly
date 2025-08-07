package main.entity.players;

import java.awt.*;

import static main.constants.Constants.DEFAULT_INIT_MONEY;

public class DefaultPlayer extends Player {

    public DefaultPlayer(String name, Color color) {
        super(name, DEFAULT_INIT_MONEY, color);
        this.loadPortrait("main/Resources/default portrait.png");
    }
}
