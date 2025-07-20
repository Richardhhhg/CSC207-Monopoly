package main.view;

import main.entity.Stock;
import main.entity.StockMarket;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * StockMarketView is a JPanel that represents the stock market view in the application.
 */
public class StockMarketView extends JPanel {

    public StockMarketView(List<Stock> stocks) {
        // TODO: Clean up magic numbers
        // TODO: Clean up code in general
        // TODO: Make this prettier
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Stock Market"));

        for (Stock stock : stocks) {
            StockView stockview = new StockView(
                stock.getTicker(),
                stock.getCurrentPrice(),
                5.27, // TODO: This is temporary, replace with real data later
                1000 // TODO: This is temporary, replace with real data later
            );
            add(stockview);
            add(Box.createVerticalStrut(10));
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Stock Market View Example");

        // testing initialization of stockMarket
        StockMarket stockMarket = new StockMarket();
        StockMarketView marketView = new StockMarketView(stockMarket.getStocks());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(marketView);
        frame.pack();
        frame.setVisible(true);
    }
}
