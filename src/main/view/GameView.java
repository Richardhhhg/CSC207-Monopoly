package main.view;

import main.interface_adapter.GameController;
import main.use_case.GameState;
import main.use_case.GameUseCase;
import main.interface_adapter.BoardPositionCalculator;
import main.interface_adapter.GameViewModel;
import main.interface_adapter.DiceController;
import main.entity.Stock;
import main.data_access.StockMarket.StockInfoDataOutputObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Main game view - now properly separated with dependency injection
 */
public class GameView extends JFrame {
    private BoardView boardView;
    private StockMarketView stockMarketView;
    private GameController gameController;

    public GameView() {
        super("Stock Market Game");
        initializeWindow();
        initializeDependencies();
        initializeViews();
    }

    private void initializeWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBackground(Color.lightGray);
        setLayout(new BorderLayout());
    }

    private void initializeDependencies() {
        // Create dependency chain following clean architecture
        GameState gameState = new GameState();
        GameUseCase gameUseCase = new GameUseCase(gameState);
        BoardPositionCalculator positionCalculator = new BoardPositionCalculator();
        GameViewModel gameViewModel = new GameViewModel(positionCalculator);
        DiceController diceController = new DiceController();

        this.gameController = new GameController(gameUseCase, gameViewModel, diceController);
    }

    private void initializeViews() {
        this.boardView = new BoardView(gameController);

        // Initialize stock market (temporary implementation)
        java.awt.List<Stock> stocks = createTestStocks();
        this.stockMarketView = new StockMarketView(stocks);
        stockMarketView.setAlwaysOnTop(true);
    }

    private java.awt.List<Stock> createTestStocks() {
        List<Stock> stocks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            StockInfoDataOutputObject info = new StockInfoDataOutputObject("TEST_" + i, 100, 0.01, 0.1);
            stocks.add(new Stock(info));
        }
        return stocks;
    }

    public void addBoard() {
        this.add(boardView, BorderLayout.CENTER);
        setVisible(true);
    }

    public void showStockMarket() {
        stockMarketView.setVisible(true);
    }

    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameView game = new GameView();
            game.addBoard();
            game.showStockMarket();
        });
    }
}