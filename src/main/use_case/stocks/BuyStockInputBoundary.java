package main.use_case.stocks;

public interface BuyStockInputBoundary {
    /**
     * Executes the stock buying process.
     *
     * @param inputData The data required to perform the stock purchase.
     * @throws IllegalArgumentException if quantity is invalid or if the player has insufficient funds.
     */
    void execute(BuyStockInputData inputData) throws IllegalArgumentException;
}
