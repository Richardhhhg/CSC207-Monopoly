package main.use_case.Stocks;

/**
 * Output boundary interface for the buy stock use case.
 * This interface defines how the use case communicates results back to the interface adapter layer.
 */
public interface BuyStockOutputBoundary {
    /**
     * Presents the result of a successful stock purchase.
     *
     * @param outputData the data about the successful purchase
     */
    void presentSuccess(BuyStockOutputData outputData);

    /**
     * Presents an error that occurred during stock purchase.
     *
     * @param error the error message
     */
    void presentError(String error);
}