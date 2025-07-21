package main.view;

import main.entity.Stock;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class displays the entire game view.
 */
public class GameView extends JFrame{
    private BoardView boardView;
    private StockMarketView stockMarketView;

    public GameView() {
        super("Stock Market Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBackground(Color.lightGray);
        setLayout(new BorderLayout());
        setVisible(true);

        // TODO: There should be a way to intiial the stock market elsewhere and display it here.
        List<Stock> stocks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            stocks.add(new Stock("TEST_" + i, 100, 0.01, 0.1));
        }
        this.boardView = new BoardView();
        this.stockMarketView = new StockMarketView(stocks);
        stockMarketView.setAlwaysOnTop(true);
    }

    public void addBoard() {
        this.add(boardView, BorderLayout.CENTER);
        setVisible(true);
    }

    public void showStockMarket() {
        stockMarketView.setVisible(true);
    }

    public static void main(String[] args) {
        GameView game = new GameView();
        game.addBoard();
        game.showStockMarket();
    }
}
