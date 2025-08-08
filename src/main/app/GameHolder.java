package main.app;

import main.entity.Game;

public class GameHolder {
    private static Game game;

    public static void setGame(Game thisGame) {
        game = thisGame;
    }

    public static Game getGame() {
        return game;
    }
}
