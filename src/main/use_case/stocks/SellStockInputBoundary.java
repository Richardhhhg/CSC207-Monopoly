package main.use_case.stocks;

public interface SellStockInputBoundary {
    /**
     * Executes the sell stock operation.
     * @param inputData The input data containing player, stock, and quantity information.
     * @throws IllegalArgumentException if the quantity is invalid or if the player has insufficient stocks to sell.
     */
    void execute(SellStockInputData inputData) throws IllegalArgumentException;
}
