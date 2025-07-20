package main.view;

import javax.swing.*;
import java.util.List;

/**
 * StockMarketView is a JPanel that represents the stock market view in the application.
 */
public class StockMarketView extends JPanel {

    public StockMarketView(List<StockView> stockViews) {
        // TODO: Clean up magic numbers
        // TODO: Clean up code in general
        // TODO: Make this prettier
        setPreferredSize(new java.awt.Dimension(800, 600));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Stock Market"));

        for (StockView stockView : stockViews) {
            add(stockView);
            add(Box.createVerticalStrut(10));
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Stock Market View Example");
        // Create some dummy StockViews for demonstration
        StockView stock1 = new StockView("AAPL", 150.00, 1.5, 10);
        StockView stock2 = new StockView("GOOGL", 2800.00, -0.5, 5);
        StockMarketView marketView = new StockMarketView(List.of(stock1, stock2));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(marketView);
        frame.pack();
        frame.setVisible(true);
    }
}
