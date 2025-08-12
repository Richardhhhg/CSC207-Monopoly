package main.view;

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
import main.interface_adapter.stock_market.StockPlayerViewModel;
import main.interface_adapter.stock_market.StockPresenter;
import main.interface_adapter.stock_market.StockSellController;
import main.interface_adapter.stock_market.StockState;
import main.use_case.stocks.BuyStockInteractor;
import main.use_case.stocks.SellStockInteractor;
import main.use_case.stocks.StockOutputBoundary;

public class StockView extends JPanel {
    private final JTextField quantityInput;
    private final JLabel quantityOwnedLabel;

    private final StockPlayerViewModel stockPlayerViewModel;
    private final StockState stockState;

    private final StockBuyController stockBuyController;
    private final StockSellController stockSellController;

    public StockView(StockPlayerViewModel stockPlayerViewModel) {
        this.stockPlayerViewModel = stockPlayerViewModel;
        this.stockState = stockPlayerViewModel.getState().getStockState();

        final StockOutputBoundary stockPresenter = new StockPresenter(stockPlayerViewModel);
        this.stockBuyController = new StockBuyController(new BuyStockInteractor(stockPresenter));
        this.stockSellController = new StockSellController(new SellStockInteractor(stockPresenter));

        setPreferredSize(new Dimension(Constants.STOCK_WIDTH, Constants.STOCK_HEIGHT));
        setLayout(new GridLayout(Constants.STOCK_VIEW_ROWS, Constants.STOCK_VIEW_COLUMNS,
                Constants.STOCK_VIEW_PADDING_H, Constants.STOCK_VIEW_PADDING_V));
        setBorder(BorderFactory.createLineBorder(Color.black));

        final JLabel tickerLabel = new JLabel(stockState.getTicker());
        final JLabel priceLabel = new JLabel("$" + String.format("%.2f", stockState.getPrice()));
        final JLabel percentChangeLabel = new JLabel(String.format("%.2f", stockState.getChange()) + "%");
        this.quantityOwnedLabel = new JLabel(String.valueOf(stockPlayerViewModel.getState().getQuantity()));

        this.quantityInput = new JTextField(Constants.STOCK_QUANTITY_WIDTH);
        final JButton buyButton = new JButton("Buy");
        if (!stockState.isAllowBuy()) {
            buyButton.setEnabled(false);
        }

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
        quantityOwnedLabel.setText(String.valueOf(stockPlayerViewModel.getState().getQuantity()));
        System.out.println(stockPlayerViewModel.getState().getQuantity());
        revalidate();
        repaint();
    }

    /**
     * Buys stock based on user input from the quantityInput JTextField.
     *
     * @throws NumberFormatException when quantityInput is empty or not a valid integer.
     */
    private void buyStock() throws NumberFormatException {
        final String quantityText = quantityInput.getText();
        if (quantityText.isEmpty() || !quantityText.matches("\\d+")) {
            throw new NumberFormatException("Invalid Input: Please enter a valid quantity.");
        }
        stockBuyController.execute(
                stockPlayerViewModel.getState().getPlayer(),
                stockPlayerViewModel.getState().getStock(),
                Integer.parseInt(quantityText)
        );

        refreshView();
    }

    /**
     * Sells stock based on user input from the quantityInput JTextField.
     *
     * @throws NumberFormatException when quantityInput is empty or not a valid integer.
     */
    private void sellStock() throws NumberFormatException {
        final String quantityText = quantityInput.getText();
        if (quantityText.isEmpty() || !quantityText.matches("\\d+")) {
            throw new NumberFormatException("Invalid Input: Please enter a valid quantity.");
        }
        stockSellController.execute(
                stockPlayerViewModel.getState().getPlayer(),
                stockPlayerViewModel.getState().getStock(),
                Integer.parseInt(quantityText)
        );
        refreshView();
    }
}
