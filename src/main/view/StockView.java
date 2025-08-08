package main.view;

import main.Constants.Constants;
import main.entity.Stocks.Stock;
import main.entity.players.Player;
import main.interface_adapter.StockMarket.StockBuyController;
import main.interface_adapter.StockMarket.StockSellController;
import main.interface_adapter.StockMarket.StockViewModel;

import javax.swing.*;
import java.awt.*;

public class StockView extends JPanel {
    private final JTextField quantityInput;
    private final JLabel quantityOwnedLabel;

    private final StockViewModel stockViewModel;

    public StockView(StockViewModel stockViewModel) {
        this.stockViewModel = stockViewModel;
        setPreferredSize(new Dimension(Constants.STOCK_WIDTH, Constants.STOCK_HEIGHT));
        setLayout(new GridLayout(Constants.STOCK_VIEW_ROWS, Constants.STOCK_VIEW_COLUMNS, Constants.STOCK_VIEW_PADDING_H, Constants.STOCK_VIEW_PADDING_V));
        setBorder(BorderFactory.createLineBorder(Color.black));

        JLabel tickerLabel = new JLabel(stockViewModel.getTicker());
        JLabel priceLabel = new JLabel("$" + String.format("%.2f", stockViewModel.getPrice()));
        JLabel percentChangeLabel = new JLabel(String.format("%.2f", stockViewModel.getChange()) + "%");
        this.quantityOwnedLabel = new JLabel(String.valueOf(stockViewModel.getAmount()));

        this.quantityInput = new JTextField(5);
        JButton buyButton = new JButton("Buy");
        if (!stockViewModel.isAllowBuy()) {
            buyButton.setEnabled(false);
        }

        JButton sellButton = new JButton("Sell");

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

    private void refreshView() {
        quantityOwnedLabel.setText(String.valueOf(stockViewModel.getAmount()));
        revalidate();
        repaint();
    }

    private void buyStock() {
        String quantityText = quantityInput.getText();
        if (quantityText.isEmpty() || !quantityText.matches("\\d+")) {
            System.out.println("Invalid Input: Please enter a valid quantity.");
            return;
        }
        StockBuyController stockBuyController = new StockBuyController();
        stockBuyController.execute(
                stockViewModel.getPlayer(),
                stockViewModel.getStock(),
                Integer.parseInt(quantityText)
        );
        refreshView();
    }

    private void sellStock() {
        String quantityText = quantityInput.getText();
        if (quantityText.isEmpty() || !quantityText.matches("\\d+")) {
            System.out.println("Invalid Input: Please enter a valid quantity.");
            return;
        }
        StockSellController stockSellController = new StockSellController();
        stockSellController.execute(
                stockViewModel.getPlayer(),
                stockViewModel.getStock(),
                Integer.parseInt(quantityText)
        );
        refreshView();
    }
}