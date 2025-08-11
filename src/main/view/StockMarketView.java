package main.view;

import main.constants.Constants;
import main.entity.players.Player;
import main.entity.Stocks.Stock;
import main.interface_adapter.StockMarket.StockPlayerViewModel;
import main.interface_adapter.StockMarket.StockState;

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
     * @param allowBuy boolean indicating if the player is allowed to buy stocks. Only true for landing on tiles.
     */
    public StockMarketView(Player player, boolean allowBuy) {
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
            int quantity = entry.getValue();
            StockState stockState = new StockState();
            stockState.setTicker(stock.getTicker());
            stockState.setPrice(stock.getCurrentPrice());
            stockState.setChange(stock.getChange());
            stockState.setAllowBuy(allowBuy);

            StockPlayerViewModel stockPlayerViewModel = new StockPlayerViewModel(stockState);
            stockPlayerViewModel.getState().setPlayer(player);
            stockPlayerViewModel.getState().setStock(stock);
            stockPlayerViewModel.getState().setQuantity(quantity);
            StockView stockview = new StockView(stockPlayerViewModel);
            mainPanel.add(stockview);
            mainPanel.add(Box.createVerticalStrut(Constants.STOCK_MKT_PADDING));
        }

        setContentPane(mainPanel);
    }
}