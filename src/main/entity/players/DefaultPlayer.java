package main.entity.players;

import java.awt.*;

import static main.Constants.Constants.DEFAULT_INIT_MONEY;
import static main.Constants.Constants.NP_POR;

public class DefaultPlayer extends Player {

    public DefaultPlayer(String name, Color color) {
        super(name, DEFAULT_INIT_MONEY, color);
        this.loadPortrait(NP_POR);
    }
}
