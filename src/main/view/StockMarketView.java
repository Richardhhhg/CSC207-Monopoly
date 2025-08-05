package main.view;

import main.Constants.Constants;
import main.data_access.StockMarket.DefaultStockInfoRepository;
import main.data_access.StockMarket.StockInfoDataOutputObject;
import main.entity.players.DefaultPlayer;
import main.entity.players.Player;
import main.entity.Stocks.Stock;
import main.interface_adapter.StockMarket.StockViewModel;
import main.interface_adapter.StockMarket.StockPresenter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Map;

/**
 * StockMarketView is a JFrame that represents the stock market view in the application.
 */
public class StockMarketView extends JFrame {
    /**
     * Constructor for StockMarketView that initializes the view with a map of stocks and their owned quantities.
     *
     * @param player player to which the stock market view is unique to
     */
    public StockMarketView(Player player) {
        super("Stock Market");
        Map<Stock, Integer> stockQuantities = player.getStocks();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(Constants.STOCK_MARKET_WIDTH, Constants.STOCK_MARKET_HEIGHT);
        setLocationRelativeTo(null);
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
            StockViewModel stockViewModel = new StockPresenter().execute(stock, player);
            StockView stockview = new StockView(
                    stockViewModel
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
        Player player = new DefaultPlayer("Test Player", Color.RED);
        java.util.List<Stock> stocks = new java.util.ArrayList<>();
        DefaultStockInfoRepository stockInfoRepository = new DefaultStockInfoRepository();
        for (int i = 0; i < 5; i++) {
            StockInfoDataOutputObject info = stockInfoRepository.getStockInfo("TEST_" + i);
            Stock stock = new Stock(info.ticker(), info.currentPrice(),
                    info.meanDailyReturnPct(), info.standardDeviationPct());
            stocks.add(stock);
        }
        player.initializeStocks(stocks);
        StockMarketView marketView = new StockMarketView(player);
        marketView.setVisible(true);
    }
}