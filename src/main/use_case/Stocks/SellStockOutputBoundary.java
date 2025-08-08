package main.use_case.Stocks;

/**
 * Output boundary interface for the sell stock use case.
 * This interface defines how the use case communicates results back to the interface adapter layer.
 */
public interface SellStockOutputBoundary {
    /**
     * Presents the result of a successful stock sale.
     *
     * @param outputData the data about the successful sale
     */
    void presentSuccess(SellStockOutputData outputData);

    /**
     * Presents an error that occurred during stock sale.
     *
     * @param error the error message
     */
    void presentError(String error);
}