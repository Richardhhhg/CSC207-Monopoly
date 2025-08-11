package main.interface_adapter.game;

import main.entity.Game;
import main.use_case.game.GameNextTurn;

public class GameEndTurnController {
    public void execute(Game game) {
        GameNextTurn gameNextTurn = new GameNextTurn(game);
        gameNextTurn.execute();
    }
}
