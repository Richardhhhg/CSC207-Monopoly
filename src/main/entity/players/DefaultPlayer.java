package main.entity.players;

import main.entity.Stocks.Stock;
import main.entity.players.Player;
import main.entity.Stocks.Stock;

import java.awt.*;

import static main.Constants.Constants.DEFAULT_INIT_MONEY;

public class DefaultPlayer extends Player {

    public DefaultPlayer(String name, Color color) {
        super(name, DEFAULT_INIT_MONEY, color);
        this.loadPortrait("main/Resources/default portrait.png");
    }
}
