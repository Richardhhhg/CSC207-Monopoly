package main.use_case.Stocks;

/**
 * Input boundary interface for the sell stock use case.
 * This interface defines the contract for selling stocks and follows the dependency inversion principle.
 */
public interface SellStockInputBoundary {
    /**
     * Executes the sell stock use case.
     *
     * @param inputData the input data containing all necessary information for selling a stock
     */
    void execute(SellStockInputData inputData);
}