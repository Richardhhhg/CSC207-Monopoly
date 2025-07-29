package main.interface_adapter.start;

import main.use_case.start.StartGameOutputBoundary;
import main.app.Main;
import javax.swing.JFrame;

/**
 * Presenter for the Start Game use case.  Closes the start screen
 * and kicks off the real game.
 */
public class StartGamePresenter implements StartGameOutputBoundary {
    private final JFrame view;

    public StartGamePresenter(JFrame view) {
        this.view = view;
    }

    @Override
    public void launchGame() {
        view.dispose();
        new Main().startGame();
    }
}
