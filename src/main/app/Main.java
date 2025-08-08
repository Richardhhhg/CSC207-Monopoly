package main.app;

import main.view.GameView;
import main.view.StartScreen;

public class Main{
    public static void main(String[] args) {
        new StartScreen();
    }

    public static void startGame() {
        new GameView();
    }
}