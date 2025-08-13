package main.use_case.stocks;

import main.entity.players.Player;
import main.entity.stocks.Stock;

public class SellStockInteractor implements SellStockInputBoundary {
    private final StockOutputBoundary stockPresenter;

    public SellStockInteractor(StockOutputBoundary stockPresenter) {
        this.stockPresenter = stockPresenter;
    }

    /**
     * Executes the sell stock use case.
     *
     * @param inputData The input data containing player, stock, and quantity information.
     * @throws IllegalArgumentException if the quantity is invalid or if the player does not have enough stock to sell.
     */
    public void execute(SellStockInputData inputData) throws IllegalArgumentException {
        final int quantity = inputData.getQuantity();
        final Player player = inputData.getPlayer();
        final Stock stock = inputData.getStock();

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (quantity > player.getStockQuantity(stock)) {
            throw new IllegalArgumentException("Not enough stock to sell");
        }

        player.sellStock(stock, quantity);
        final AbstractStockOutputData outputData = new SellStockOutputData(player, stock, true);
        stockPresenter.execute(outputData);
    }
}
