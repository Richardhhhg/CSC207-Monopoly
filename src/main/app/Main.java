package main.app;

import main.view.GameView;
import main.view.StartScreen;

public class Main {
    /**
     * Main method to start the application.
     * @param args
     */
    public static void main(String[] args) {
        new StartScreen();
    }

    /**
     *
     */
    public static void startGame() {
        new GameView();
    }
}
