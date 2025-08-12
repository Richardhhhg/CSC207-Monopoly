package main.view;

import java.awt.GridLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import main.constants.Constants;
import main.entity.players.Player;
import main.entity.stocks.Stock;
import main.interface_adapter.stock_market.StockPlayerViewModel;
import main.interface_adapter.stock_market.StockState;

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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(Constants.STOCK_MARKET_WIDTH, Constants.STOCK_MARKET_HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Stock Market",
                TitledBorder.CENTER,
                TitledBorder.TOP
        ));

        final JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(1, Constants.STOCK_MKT_COLUMNS));

        headerPanel.add(new JLabel("Symbol"));
        headerPanel.add(new JLabel("Price"));
        headerPanel.add(new JLabel("Change %"));
        headerPanel.add(new JLabel("Owned"));
        headerPanel.add(new JLabel(""));
        headerPanel.add(new JLabel(""));
        headerPanel.add(new JLabel("Quantity"));

        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(Constants.STOCK_MKT_PADDING));

        final Map<Stock, Integer> stockQuantities = player.getStocks();

        for (Map.Entry<Stock, Integer> entry : stockQuantities.entrySet()) {
            final Stock stock = entry.getKey();
            final int quantity = entry.getValue();
            final StockView stockView = makeStockView(stock, quantity, allowBuy);
            mainPanel.add(stockView);
            mainPanel.add(Box.createVerticalStrut(Constants.STOCK_MKT_PADDING));
        }

        setContentPane(mainPanel);
    }

    private StockView makeStockView(Stock stock, int quantity, boolean allowBuy) {
        final StockState stockState = new StockState();
        stockState.setTicker(stock.getTicker());
        stockState.setPrice(stock.getCurrentPrice());
        stockState.setChange(stock.getChange());
        stockState.setAllowBuy(allowBuy);

        final StockPlayerViewModel stockPlayerViewModel = new StockPlayerViewModel(stockState);
        stockPlayerViewModel.getState().setQuantity(quantity);
        return new StockView(stockPlayerViewModel);
    }
}
