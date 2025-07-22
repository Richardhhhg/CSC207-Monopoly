package main.app;

import main.entity.StockMarket;
import main.view.GameView;
import main.view.StartScreen;

public class Main{
    private StockMarket stockMarket;

    public static void main(String[] args) {
        new StartScreen();
    }

    public static void startGame() {
        GameView game = new GameView();
        game.addBoard();
        game.showStockMarket();
    }
}