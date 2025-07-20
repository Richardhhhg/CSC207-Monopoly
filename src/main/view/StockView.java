package main.view;

import main.Constants.Constants;

import javax.swing.*;
import java.awt.*;

public class StockView extends JPanel {
    private final JLabel tickerLabel;
    private final JLabel priceLabel;
    private final JLabel percentChangeLabel;
    private final JLabel quantityOwnedLabel;
    private final JTextField quantityInput;
    private final JButton buyButton;
    private final JButton sellButton;

    public StockView(String ticker, double price, double percentChange, int quantityOwned) {
        setPreferredSize(new Dimension(Constants.STOCK_WIDTH, Constants.STOCK_HEIGHT));
        setLayout(new GridLayout(Constants.STOCK_VIEW_ROWS, Constants.STOCK_VIEW_COLUMNS, Constants.STOCK_VIEW_PADDING_H, Constants.STOCK_VIEW_PADDING_V));
        setBorder(BorderFactory.createLineBorder(Color.black));

        tickerLabel = new JLabel("Ticker: " + ticker);
        priceLabel = new JLabel("Price: $" + String.format("%.2f", price));
        percentChangeLabel = new JLabel("Change: " + String.format("%.2f", percentChange) + "%");
        quantityOwnedLabel = new JLabel("Owned: " + quantityOwned);

        quantityInput = new JTextField(5);
        buyButton = new JButton("Buy");
        sellButton = new JButton("Sell");

        add(tickerLabel);
        add(priceLabel);
        add(percentChangeLabel);
        add(quantityOwnedLabel);
        add(buyButton);
        add(sellButton);
        add(quantityInput);
    }

    // Getters for input and buttons for event handling
    public JTextField getQuantityInput() { return quantityInput; }
    public JButton getBuyButton() { return buyButton; }
    public JButton getSellButton() { return sellButton; }

    // Methods to update displayed values
    public void setPrice(double price) {
        priceLabel.setText("Price: $" + String.format("%.2f", price));
    }

    public void setPercentChange(double percentChange) {
        percentChangeLabel.setText("Change: " + String.format("%.2f", percentChange) + "%");
    }

    public void setQuantityOwned(int quantityOwned) {
        quantityOwnedLabel.setText("Owned: " + quantityOwned);
    }

    // Testing Usage
    public static void main(String[] args) {
        JFrame frame = new JFrame("Stock View Example");
        StockView stockView = new StockView("AAPL", 150.00, 1.5, 10);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(stockView);
        frame.pack();
        frame.setVisible(true);
    }
}