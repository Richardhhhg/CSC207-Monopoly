package main.use_case.Stocks;

import main.entity.players.Player;
import main.entity.Stocks.Stock;

public class BuyStockInteractor implements BuyStockInputBoundary {
    private final StockOutputBoundary stockPresenter;

    public BuyStockInteractor(StockOutputBoundary stockPresenter) {
        this.stockPresenter = stockPresenter;
    }

    public void execute(BuyStockInputData inputData) throws IllegalArgumentException {
        int quantity = inputData.getQuantity();
        Player player = inputData.getPlayer();
        Stock stock = inputData.getStock();

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (player.getMoney() < stock.getCurrentPrice() * quantity) {
            throw new IllegalArgumentException("Insufficient funds to buy stocks");
        }

        player.buyStock(stock, quantity);
        StockOutputData outputData = new BuyStockOutputData(player, stock, true);
        stockPresenter.execute(outputData);
    }
}
