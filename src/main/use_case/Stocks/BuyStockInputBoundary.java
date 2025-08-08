package main.use_case.Stocks;

/**
 * Input boundary interface for the buy stock use case.
 * This interface defines the contract for buying stocks and follows the dependency inversion principle.
 */
public interface BuyStockInputBoundary {
    /**
     * Executes the buy stock use case.
     *
     * @param inputData the input data containing all necessary information for buying a stock
     */
    void execute(BuyStockInputData inputData);
}