package main.use_case.CharacterSelectionScreen;

import main.entity.Game;
import main.entity.players.*;
import main.interface_adapter.CharacterSelectionScreen.GameLaunchOutputData;
import main.use_case.Game.GameInitializeSelectedPlayers;
import main.use_case.Game.GameInitializeStocks;
import main.use_case.Game.GameInitializeTiles;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameLauncher {
    public Game launch(GameLaunchOutputData data) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < data.getPlayerNames().size(); i++) {
            String name = data.getPlayerNames().get(i);
            String type = data.getPlayerTypes().get(i);
            Color color = Color.decode(data.getPlayerColors().get(i));
            Player p = switch (type) {
                case "Clerk" -> new Clerk(name, color);
                case "Landlord" -> new Landlord(name, color);
                case "Inheritor" -> new Inheritor(name, color);
                case "CollegeStudent" -> new CollegeStudent(name, color);
                case "PoorMan" -> new PoorMan(name, color);
                default -> new NullPlayer();
            };
            players.add(p);
        }

        Game game = new Game();
        new GameInitializeSelectedPlayers(game, players).execute();
        new GameInitializeTiles(game).execute();
        new GameInitializeStocks(game).execute();
        return game;
    }
}

