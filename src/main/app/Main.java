package main.app;

import main.entity.Game;
import main.view.GameView;
import main.view.StartScreen;

public class Main{
    public static void main(String[] args) {
        new StartScreen();
    }

    public static void startGame() {
        GameView game = new GameView(new Game());
        game.showStockMarket();
    }
}