package main.use_case.game;

import main.entity.Game;
import main.entity.players.Player;

import static main.constants.Constants.FINISH_LINE_BONUS;

public class GameMoveCurrentPlayer  {
    private final Game game;

    public GameMoveCurrentPlayer (Game game) {
        this.game = game;
    }

    public void execute(int steps) {
        if (game.getGameEnded()) return;
        Player p = game.getCurrentPlayer();
        if (p == null) return;

        int tileCount = game.getTileCount();
        if (p.getPosition() + steps >= tileCount) {
            p.addMoney(FINISH_LINE_BONUS);
        }
    }
}
