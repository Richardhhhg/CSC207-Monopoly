package main.view.stock;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.constants.Constants;
import main.interface_adapter.stock_market.StockBuyController;
import main.interface_adapter.stock_market.StockSellController;
import main.interface_adapter.stock_market.StockViewModel;

public class StockView extends JPanel {
    private static final int QUANTITY_WIDTH = 5;

    private final JTextField quantityInput;
    private final JLabel quantityOwnedLabel;

    private final StockViewModel stockViewModel;

    public StockView(StockViewModel stockViewModel) {
        this.stockViewModel = stockViewModel;
        setPreferredSize(new Dimension(Constants.STOCK_WIDTH, Constants.STOCK_HEIGHT));
        setLayout(new GridLayout(Constants.STOCK_VIEW_ROWS, Constants.STOCK_VIEW_COLUMNS,
                Constants.STOCK_VIEW_PADDING_H, Constants.STOCK_VIEW_PADDING_V));
        setBorder(BorderFactory.createLineBorder(Color.black));

        this.quantityOwnedLabel = new JLabel(String.valueOf(stockViewModel.getAmount()));

        this.quantityInput = new JTextField(QUANTITY_WIDTH);
        final JButton buyButton = new JButton("Buy");
        if (!stockViewModel.isAllowBuy()) {
            buyButton.setEnabled(false);
        }

        final JButton sellButton = new JButton("Sell");

        final JLabel tickerLabel = new JLabel(stockViewModel.getTicker());
        final JLabel priceLabel = new JLabel("$" + String.format("%.2f", stockViewModel.getPrice()));
        final JLabel percentChangeLabel = new JLabel(String.format("%.2f", stockViewModel.getChange()) + "%");

        add(tickerLabel);
        add(priceLabel);
        add(percentChangeLabel);
        add(quantityOwnedLabel);
        add(buyButton);
        add(sellButton);
        add(quantityInput);

        buyButton.addActionListener(event -> buyStock());
        sellButton.addActionListener(event -> sellStock());
    }

    private void refreshView() {
        quantityOwnedLabel.setText(String.valueOf(stockViewModel.getAmount()));
        revalidate();
        repaint();
    }

    private void buyStock() throws IllegalArgumentException {
        final String quantityText = quantityInput.getText();
        try {
            final int quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero.");
            }
        }
        catch (NumberFormatException exception) {
            System.out.println("Invalid Input: Please enter a valid quantity.");
        }
        final StockBuyController stockBuyController = new StockBuyController();
        stockBuyController.execute(
                stockViewModel.getPlayer(),
                stockViewModel.getStock(),
                Integer.parseInt(quantityText)
        );
        refreshView();
    }

    private void sellStock() throws IllegalArgumentException {
        final String quantityText = quantityInput.getText();
        try {
            final int quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero.");
            }
        }
        catch (NumberFormatException exception) {
            System.out.println("Invalid Input: Please enter a valid quantity.");
        }
        final StockSellController stockSellController = new StockSellController();
        stockSellController.execute(
                stockViewModel.getPlayer(),
                stockViewModel.getStock(),
                Integer.parseInt(quantityText)
        );
        refreshView();
    }
}
