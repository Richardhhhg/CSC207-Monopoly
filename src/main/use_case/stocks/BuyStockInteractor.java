package main.use_case.stocks;

import main.entity.players.AbstractPlayer;
import main.entity.stocks.Stock;

public class BuyStockInteractor implements BuyStockInputBoundary {
    private final StockOutputBoundary stockPresenter;

    public BuyStockInteractor(StockOutputBoundary stockPresenter) {
        this.stockPresenter = stockPresenter;
    }

    /**
     * Executes the buy stock use case.
     *
     * @param inputData The data required to perform the stock purchase.
     * @throws IllegalArgumentException if quantity is invalid or if the player has insufficient funds.
     */
    public void execute(BuyStockInputData inputData) throws IllegalArgumentException {
        final int quantity = inputData.getQuantity();
        final AbstractPlayer abstractPlayer = inputData.getPlayer();
        final Stock stock = inputData.getStock();

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (abstractPlayer.getMoney() < stock.getCurrentPrice() * quantity) {
            throw new IllegalArgumentException("Insufficient funds to buy stocks");
        }

        abstractPlayer.buyStock(stock, quantity);
        final AbstractStockOutputData outputData = new BuyStockOutputData(abstractPlayer, stock, true);
        stockPresenter.execute(outputData);
    }
}
