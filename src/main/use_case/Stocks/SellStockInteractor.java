package main.use_case.Stocks;

import main.entity.Stocks.Stock;
import main.entity.players.Player;

public class SellStockInteractor implements SellStockInputBoundary {
    private final StockOutputBoundary stockPresenter;

    public SellStockInteractor(StockOutputBoundary stockPresenter) {
        this.stockPresenter = stockPresenter;
    }

    public void execute(SellStockInputData inputData) throws IllegalArgumentException {
        int quantity = inputData.getQuantity();
        Player player = inputData.getPlayer();
        Stock stock = inputData.getStock();

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (quantity > player.getStockQuantity(stock)) {
            throw new IllegalArgumentException("Not enough stock to sell");
        }

        player.sellStock(stock, quantity);
        StockOutputData outputData = new SellStockOutputData(player, stock, true);
        stockPresenter.execute(outputData);
    }
}
