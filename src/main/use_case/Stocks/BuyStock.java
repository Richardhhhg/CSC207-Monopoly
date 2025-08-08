package main.use_case.Stocks;

/**
 * Use case for buying stocks.
 * Implements the input boundary interface and follows clean architecture principles.
 */
public class BuyStock implements BuyStockInputBoundary {
    private final PlayerStockRepository playerRepository;
    private final BuyStockOutputBoundary outputBoundary;

    public BuyStock(PlayerStockRepository playerRepository, BuyStockOutputBoundary outputBoundary) {
        this.playerRepository = playerRepository;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(BuyStockInputData inputData) {
        try {
            // Validate input
            if (inputData.getQuantity() <= 0) {
                outputBoundary.presentError("Quantity must be greater than 0");
                return;
            }

            // Check player's money
            double playerMoney = playerRepository.getPlayerMoney(inputData.getPlayerId());
            double totalCost = inputData.getStockPrice() * inputData.getQuantity();
            
            if (playerMoney < totalCost) {
                outputBoundary.presentError("Insufficient funds to buy stocks");
                return;
            }

            // Execute the purchase
            playerRepository.buyStockForPlayer(
                inputData.getPlayerId(),
                inputData.getStockTicker(),
                inputData.getQuantity(),
                totalCost
            );

            // Present success
            double remainingMoney = playerMoney - totalCost;
            BuyStockOutputData outputData = new BuyStockOutputData(
                inputData.getPlayerId(),
                inputData.getStockTicker(),
                inputData.getQuantity(),
                totalCost,
                remainingMoney
            );
            outputBoundary.presentSuccess(outputData);

        } catch (Exception e) {
            outputBoundary.presentError("Failed to buy stock: " + e.getMessage());
        }
    }
}
