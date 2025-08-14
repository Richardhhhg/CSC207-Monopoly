package main.entity.players;

import java.awt.*;

import static main.constants.Constants.DEFAULT_INIT_MONEY;
import static main.constants.Constants.NP_POR;

public class DefaultAbstractPlayer extends AbstractPlayer {

    public DefaultAbstractPlayer(String name, Color color) {
        super(name, DEFAULT_INIT_MONEY, color);
        this.loadPortrait(NP_POR);
    }
}
