package main.view.stocks;

import java.awt.GridLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import main.constants.constants;
import main.entity.players.Player;
import main.entity.stocks.Stock;
import main.interface_adapter.stock_market.StockPresenter;
import main.interface_adapter.stock_market.StockViewModel;

/**
 * StockMarketView is a JFrame that represents the stock market view in the application.
 */
public class StockMarketView extends JFrame {
    private static final int STOCK_COLUMNS = 7;

    /**
     * Constructor for StockMarketView that initializes the view with a map of stocks and their owned quantities.
     *
     * @param player player to which the stock market view is unique to
     */
    public StockMarketView(Player player) {
        super("Stock Market");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(constants.STOCK_MARKET_WIDTH, constants.STOCK_MARKET_HEIGHT);
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
        headerPanel.setLayout(new GridLayout(1, STOCK_COLUMNS));

        headerPanel.add(new JLabel("Symbol"));
        headerPanel.add(new JLabel("Price"));
        headerPanel.add(new JLabel("Change %"));
        headerPanel.add(new JLabel("Owned"));
        headerPanel.add(new JLabel(""));
        headerPanel.add(new JLabel(""));
        headerPanel.add(new JLabel("Quantity"));

        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(constants.STOCK_MKT_PADDING));
        final Map<Stock, Integer> stockQuantities = player.getStocks();

        // TODO: This should create a presenter for the stock which then creates a StockViewModel - Richard
        for (Map.Entry<Stock, Integer> entry : stockQuantities.entrySet()) {
            final Stock stock = entry.getKey();
            final StockViewModel stockViewModel = new StockPresenter().execute(stock, player);
            final StockView stockview = new StockView(
                    stockViewModel
            );
            mainPanel.add(stockview);
            mainPanel.add(Box.createVerticalStrut(constants.STOCK_MKT_PADDING));
        }

        setContentPane(mainPanel);
    }
}
