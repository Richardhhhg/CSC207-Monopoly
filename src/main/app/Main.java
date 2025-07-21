package main.app;

import javax.swing.*;

import main.entity.Stock;
import main.view.BoardView;
import main.Constants.Constants;
import main.view.StockMarketView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main{
    public static void main(String[] args) {
        // Board background frame (maximized)
        JFrame boardFrame = new JFrame(Constants.GAME_TITLE);
        boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // maximize
        boardFrame.getContentPane().setBackground(Color.lightGray);
        boardFrame.setLayout(new BorderLayout());
        boardFrame.add(new BoardView(), BorderLayout.CENTER);
        boardFrame.setVisible(true);

        // Stock market foreground frame (smaller, centered)
        List<Stock> stocks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            stocks.add(new Stock("TEST_" + i, 100, 0.01, 0.1));
        }

        StockMarketView stockMarketView = new StockMarketView(stocks);
        stockMarketView.setAlwaysOnTop(true);
        stockMarketView.setVisible(true);
    }
}