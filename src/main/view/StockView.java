package main.view;

import main.Constants.Constants;
import main.interface_adapter.StockMarket.*;
import main.use_case.Stocks.BuyStockInteractor;
import main.use_case.Stocks.SellStockInteractor;
import main.use_case.Stocks.StockOutputBoundary;

import javax.swing.*;
import java.awt.*;

public class StockView extends JPanel{
    private final JTextField quantityInput;
    private final JLabel quantityOwnedLabel;

    private final StockPlayerViewModel stockPlayerViewModel;
    private final StockState stockState;

    private final StockBuyController stockBuyController;
    private final StockSellController stockSellController;

    public StockView(StockPlayerViewModel stockPlayerViewModel) {
        this.stockPlayerViewModel = stockPlayerViewModel;
        this.stockState = stockPlayerViewModel.getState().getStockState();

        StockOutputBoundary stockPresenter = new StockPresenter(stockPlayerViewModel);
        this.stockBuyController = new StockBuyController(new BuyStockInteractor(stockPresenter));
        this.stockSellController = new StockSellController(new SellStockInteractor(stockPresenter));

        setPreferredSize(new Dimension(Constants.STOCK_WIDTH, Constants.STOCK_HEIGHT));
        setLayout(new GridLayout(Constants.STOCK_VIEW_ROWS, Constants.STOCK_VIEW_COLUMNS, Constants.STOCK_VIEW_PADDING_H, Constants.STOCK_VIEW_PADDING_V));
        setBorder(BorderFactory.createLineBorder(Color.black));

        JLabel tickerLabel = new JLabel(stockState.getTicker());
        JLabel priceLabel = new JLabel("$" + String.format("%.2f", stockState.getPrice()));
        JLabel percentChangeLabel = new JLabel(String.format("%.2f", stockState.getChange()) + "%");
        this.quantityOwnedLabel = new JLabel(String.valueOf(stockPlayerViewModel.getState().getQuantity()));

        this.quantityInput = new JTextField(5);
        JButton buyButton = new JButton("Buy");
        if (!stockState.isAllowBuy()) {
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
        quantityOwnedLabel.setText(String.valueOf(stockPlayerViewModel.getState().getQuantity()));
        System.out.println(stockPlayerViewModel.getState().getQuantity());
        revalidate();
        repaint();
    }

    private void buyStock() {
        String quantityText = quantityInput.getText();
        if (quantityText.isEmpty() || !quantityText.matches("\\d+")) {
            System.out.println("Invalid Input: Please enter a valid quantity.");
            return;
        }
        stockBuyController.execute(
                stockPlayerViewModel.getState().getPlayer(),
                stockPlayerViewModel.getState().getStock(),
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
        stockSellController.execute(
                stockPlayerViewModel.getState().getPlayer(),
                stockPlayerViewModel.getState().getStock(),
                Integer.parseInt(quantityText)
        );
        refreshView();
    }
}