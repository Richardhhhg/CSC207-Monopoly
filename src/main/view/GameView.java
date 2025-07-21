package main.view;

import main.data_access.StockMarket.StockInfoDataOutputObject;
import main.entity.Stock;
import main.entity.StockMarket;
import main.interface_adapter.StockMarket.StockMarketViewModel;

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

    // TODO: There is a ton of coupling here, fix it
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
            StockInfoDataOutputObject info = new StockInfoDataOutputObject("TEST_" + i, 100, 0.01, 0.1);
            stocks.add(new Stock(info));
        }
        this.boardView = new BoardView();

        // TODO: This implementation is temporary until we get actual game entity.
        // TODO: This will later use stockMarketViewModel to get stocks rather than passing in stocks directly.
        this.stockMarketView = new StockMarketView(stocks);;
        stockMarketView.setAlwaysOnTop(true);
    }

    /**
     * Adds the board view to the game view.
     */
    public void addBoard() {
        this.add(boardView, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Displays the stock market JFrame
     */
    public void showStockMarket() {
        stockMarketView.setVisible(true);
    }

    /**
     * Main method to run the game view.
     * This is just for testing purposes.
     */
    public static void main(String[] args) {
        GameView game = new GameView();
        game.addBoard();
        game.showStockMarket();
    }
}
