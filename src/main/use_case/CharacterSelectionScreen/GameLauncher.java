package main.use_case.CharacterSelectionScreen;

import main.entity.Game;
import main.entity.players.Player;
import main.use_case.Game.GameInitializeSelectedPlayers;
import main.use_case.Game.GameInitializeStocks;
import main.use_case.Game.GameInitializeTiles;

import java.util.List;

public class GameLauncher {

    public Game launch(List<Player> players) {
        Game game = new Game();

        new GameInitializeSelectedPlayers(game, players).execute();
        new GameInitializeTiles(game).execute();
        new GameInitializeStocks(game).execute();

        return game;
    }
}

