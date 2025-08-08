package main.interface_adapter.StockMarket;

import main.use_case.Stocks.BuyStockOutputBoundary;
import main.use_case.Stocks.BuyStockOutputData;

/**
 * Presenter implementation for buy stock use case output.
 * Implements the output boundary interface and handles presentation logic.
 */
public class BuyStockPresenter implements BuyStockOutputBoundary {
    private String successMessage;
    private String errorMessage;
    private boolean success;

    @Override
    public void presentSuccess(BuyStockOutputData outputData) {
        this.success = true;
        this.successMessage = String.format("Successfully bought %d shares of %s for $%.2f. Remaining money: $%.2f",
            outputData.getQuantity(),
            outputData.getStockTicker(),
            outputData.getTotalCost(),
            outputData.getRemainingMoney());
        this.errorMessage = null;
    }

    @Override
    public void presentError(String error) {
        this.success = false;
        this.errorMessage = error;
        this.successMessage = null;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}