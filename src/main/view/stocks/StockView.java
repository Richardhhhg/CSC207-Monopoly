package main.view.stocks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.constants.constants;
import main.interface_adapter.stock_market.StockBuyController;
import main.interface_adapter.stock_market.StockSellController;
import main.interface_adapter.stock_market.StockViewModel;

public class StockView extends JPanel {
    private static final int QUANTITY_INPUT_WIDTH = 5;

    private final JTextField quantityInput;
    private final JLabel quantityOwnedLabel;

    private final StockViewModel stockViewModel;

    public StockView(StockViewModel stockViewModel) {
        this.stockViewModel = stockViewModel;
        setPreferredSize(new Dimension(constants.STOCK_WIDTH, constants.STOCK_HEIGHT));
        setLayout(new GridLayout(constants.STOCK_VIEW_ROWS, constants.STOCK_VIEW_COLUMNS,
                constants.STOCK_VIEW_PADDING_H, constants.STOCK_VIEW_PADDING_V));
        setBorder(BorderFactory.createLineBorder(Color.black));

        final JLabel tickerLabel = new JLabel(stockViewModel.getTicker());
        final JLabel priceLabel = new JLabel("$" + String.format("%.2f", stockViewModel.getPrice()));
        final JLabel percentChangeLabel = new JLabel(String.format("%.2f", stockViewModel.getChange()) + "%");
        this.quantityOwnedLabel = new JLabel(String.valueOf(stockViewModel.getAmount()));

        this.quantityInput = new JTextField(QUANTITY_INPUT_WIDTH);
        final JButton buyButton = new JButton("Buy");
        final JButton sellButton = new JButton("Sell");

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
            if (quantityText.isEmpty() || !quantityText.matches("\\d+")) {
                throw new IllegalArgumentException("Invalid Input: Please enter a valid quantity.");
            }
        }
        catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
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
            if (quantityText.isEmpty() || !quantityText.matches("\\d+")) {
                throw new IllegalArgumentException("Invalid Input: Please enter a valid quantity.");
            }
        }
        catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
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
