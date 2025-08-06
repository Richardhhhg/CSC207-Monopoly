package main.app;

import main.entity.Game;

public class GameHolder {
    private static Game game;

    public static void setGame(Game g) {
        game = g;
    }

    public static Game getGame() {
        return game;
    }

    public static void clearGame() {
        game = null;
    }
}
