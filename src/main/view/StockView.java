package main.view;

import main.Constants.Constants;
import main.entity.Stock;
import main.entity.players.Player;

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

    private final Player player;
    private final Stock stock;

    public StockView(Player player, Stock stock, double percentChange) {
        this.player = player;
        this.stock = stock;

        setPreferredSize(new Dimension(Constants.STOCK_WIDTH, Constants.STOCK_HEIGHT));
        setLayout(new GridLayout(Constants.STOCK_VIEW_ROWS, Constants.STOCK_VIEW_COLUMNS, Constants.STOCK_VIEW_PADDING_H, Constants.STOCK_VIEW_PADDING_V));
        setBorder(BorderFactory.createLineBorder(Color.black));

        tickerLabel = new JLabel(stock.getTicker());
        priceLabel = new JLabel("$" + String.format("%.2f", stock.getCurrentPrice()));
        percentChangeLabel = new JLabel(String.format("%.2f", percentChange) + "%");
        quantityOwnedLabel = new JLabel(String.valueOf(player.getStockQuantity(stock)));

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

        buyButton.addActionListener(e -> buyStock());
        sellButton.addActionListener(e -> sellStock());
    }

    // TODO: This doesn't belong in View, refactor it out later - Richard
    private void buyStock() {
        String quantityText = quantityInput.getText();
        if (!quantityText.isEmpty()) {
            int quantity = Integer.parseInt(quantityText);
            player.buyStock(stock, quantity);
            setQuantityOwned(player.getStockQuantity(stock));
        }
    }

    // TODO: This doesn't belong in View, refactor it out later - Richard
    private void sellStock() {
        String quantityText = quantityInput.getText();
        if (!quantityText.isEmpty()) {
            int quantity = Integer.parseInt(quantityText);
            player.sellStock(stock, quantity);
            setQuantityOwned(player.getStockQuantity(stock));
        }
    }

    public void setPrice(double price) {
        priceLabel.setText("$" + String.format("%.2f", price));
    }

    public void setPercentChange(double percentChange) {
        percentChangeLabel.setText(String.format("%.2f", percentChange) + "%");
    }

    public void setQuantityOwned(int quantityOwned) {
        quantityOwnedLabel.setText(String.valueOf(quantityOwned));
    }
}