package main.interface_adapter.Game;

import main.entity.Game;
import main.use_case.Game.GameNextTurn;

public class GameEndTurnController {
    public void execute(Game game) {
        GameNextTurn gameNextTurn = new GameNextTurn(game);
        gameNextTurn.execute();
    }
}
