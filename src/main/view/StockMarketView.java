package main.view;

import main.Constants.Constants;
import main.data_access.StockMarket.StockInfoDataOutputObject;
import main.entity.Stock;
import main.interface_adapter.StockMarket.StockMarketViewModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

/**
 * StockMarketView is a JFrame that represents the stock market view in the application.
 */
public class StockMarketView extends JFrame {

    /**
     * Constructor for StockMarketView that initializes the view with a StockMarketViewModel.
     *
     * @param stockMarketViewModel The StockMarketViewModel containing the stocks to be displayed.
     */
    public StockMarketView(StockMarketViewModel stockMarketViewModel) {
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

        List<Stock> stocks = stockMarketViewModel.getStocks();

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
     * Main method to run the StockMarketView for testing purposes.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        List<Stock> stocks = new java.util.ArrayList<>();
        for (int i = 0; i < 5; i++) {
            StockInfoDataOutputObject info = new StockInfoDataOutputObject("TEST_" + i, 100, 0.01, 0.1);
            stocks.add(new Stock(info));
        }
        StockMarketView marketView = new StockMarketView(stocks);
        marketView.setVisible(true);
    }
}