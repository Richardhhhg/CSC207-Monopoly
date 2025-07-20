package main.view;

import main.entity.Stock;
import main.entity.StockMarket;
import main.interface_adapter.StockMarket.StockMarketViewModel;

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
                0,
                0
            );
            add(stockview);
            add(Box.createVerticalStrut(10));
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Stock Market View Test");
        StockMarketViewModel stockMarketViewModel = new StockMarketViewModel();
        List<Stock> stocks = stockMarketViewModel.getStocks();

        // testing initialization of stockMarket
        StockMarketView marketView = new StockMarketView(stocks);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(marketView);
        frame.pack();
        frame.setVisible(true);
    }
}
