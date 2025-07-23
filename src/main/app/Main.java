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
        GameState gameState = new GameState();

        // Use Cases
        GameUseCase gameUseCase = new GameUseCase(gameState);

        // Interface Adapters
        BoardPositionCalculator calculator = new BoardPositionCalculator();
        GameViewModel gameViewModel = new GameViewModel(calculator);
        DiceController diceController = new DiceController();
        GameController gameController = new GameController(
                gameUseCase, gameViewModel, diceController);

        // Views
        SwingUtilities.invokeLater(() -> {
            GameView gameView = new GameView(gameController);
            gameView.addBoard();
            gameView.setVisible(true);
        });
    }
}