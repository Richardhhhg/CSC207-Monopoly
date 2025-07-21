package main.app;

import main.entity.StockMarket;
import main.view.GameView;

public class Main{
    private StockMarket stockMarket;

    public static void main(String[] args) {
        GameView game = new GameView();
        game.addBoard();
        game.showStockMarket();
    }
}