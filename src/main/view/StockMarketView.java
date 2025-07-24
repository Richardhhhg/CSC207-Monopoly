package main.view;

import main.Constants.Constants;
import main.data_access.StockMarket.StockInfoDataOutputObject;
import main.entity.Stock;
import main.interface_adapter.StockMarket.StockMarketViewModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * StockMarketView is a JFrame that represents the stock market view in the application.
 */
public class StockMarketView extends JFrame {

    /**
     * Constructor for StockMarketView that initializes the view with a list of stocks.
     * This way of constructing StockMarketView will be removed in the future
     *
     * @param stocks List of Stock objects to be displayed in the stock market view.
     */
    public StockMarketView(List<Stock> stocks) {
        super("Stock Market");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(Constants.STOCK_MARKET_WIDTH, Constants.STOCK_MARKET_HEIGHT);
        setLocationRelativeTo(null); // Center on screen
        setAlwaysOnTop(true);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Stock Market",
                TitledBorder.CENTER,
                TitledBorder.TOP
        ));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(1, 7)); // 7 columns

        headerPanel.add(new JLabel("Symbol"));
        headerPanel.add(new JLabel("Price"));
        headerPanel.add(new JLabel("Change %"));
        headerPanel.add(new JLabel("Owned"));
        headerPanel.add(new JLabel("")); // Buy button slot
        headerPanel.add(new JLabel("")); // Sell button slot
        headerPanel.add(new JLabel("Quantity"));

        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(Constants.STOCK_MKT_PADDING));

        for (Stock stock : stocks) {
            StockView stockview = new StockView(
                    stock.getTicker(),
                    stock.getCurrentPrice(),
                    Constants.STARTER_PCT_CHANGE,
                    Constants.STARTER_QUANTITY
            );
            mainPanel.add(stockview);
            mainPanel.add(Box.createVerticalStrut(Constants.STOCK_MKT_PADDING));
        }

        setContentPane(mainPanel);
    }

    /**
     * Constructor for StockMarketView that initializes the view with a map of stocks and their owned quantities.
     *
     * @param stockQuantities Map of Stock objects to the quantity owned by the player.
     */
    public StockMarketView(Map<Stock, Integer> stockQuantities) {
        super("Stock Market");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(Constants.STOCK_MARKET_WIDTH, Constants.STOCK_MARKET_HEIGHT);
        setLocationRelativeTo(null); // Center on screen
        setAlwaysOnTop(true);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Stock Market",
                TitledBorder.CENTER,
                TitledBorder.TOP
        ));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(1, 7)); // 7 columns

        headerPanel.add(new JLabel("Symbol"));
        headerPanel.add(new JLabel("Price"));
        headerPanel.add(new JLabel("Change %"));
        headerPanel.add(new JLabel("Owned"));
        headerPanel.add(new JLabel("")); // Buy button slot
        headerPanel.add(new JLabel("")); // Sell button slot
        headerPanel.add(new JLabel("Quantity"));

        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(Constants.STOCK_MKT_PADDING));

        for (Map.Entry<Stock, Integer> entry : stockQuantities.entrySet()) {
            Stock stock = entry.getKey();
            int quantityOwned = entry.getValue();
            StockView stockview = new StockView(
                    stock.getTicker(),
                    stock.getCurrentPrice(),
                    Constants.STARTER_PCT_CHANGE, // TODO: Replace with actual percent change if available
                    quantityOwned
            );
            mainPanel.add(stockview);
            mainPanel.add(Box.createVerticalStrut(Constants.STOCK_MKT_PADDING));
        }

        setContentPane(mainPanel);
    }

    /**
     * Main method to run the StockMarketView for testing purposes.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        Map<Stock, Integer> stocks = new java.util.HashMap<>();
        for (int i = 0; i < 5; i++) {
            StockInfoDataOutputObject info = new StockInfoDataOutputObject("TEST_" + i, 100, 0.01, 0.1);
            Stock stock = new Stock(info);
            stocks.put(stock, i*5);
        }
        StockMarketView marketView = new StockMarketView(stocks);
        marketView.setVisible(true);
    }
}