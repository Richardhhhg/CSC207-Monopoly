package main.app;

import main.view.GameView;
import main.view.StartScreen;

public class Main {
    /**
     * Main method to start the application.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        new StartScreen();
    }

    /**
     * Starts the game by initializing the GameView.
     */
    public static void startGame() {
        new GameView();
    }
}
