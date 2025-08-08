package main.use_case.Stocks;

/**
 * Use case for selling stocks.
 * Implements the input boundary interface and follows clean architecture principles.
 */
public class SellStock implements SellStockInputBoundary {
    private final PlayerStockRepository playerRepository;
    private final SellStockOutputBoundary outputBoundary;

    public SellStock(PlayerStockRepository playerRepository, SellStockOutputBoundary outputBoundary) {
        this.playerRepository = playerRepository;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(SellStockInputData inputData) {
        try {
            // Validate input
            if (inputData.getQuantity() <= 0) {
                outputBoundary.presentError("Quantity must be greater than 0");
                return;
            }

            // Check player's stock holdings
            int ownedQuantity = playerRepository.getPlayerStockQuantity(
                inputData.getPlayerId(), 
                inputData.getStockTicker()
            );
            
            if (ownedQuantity < inputData.getQuantity()) {
                outputBoundary.presentError("Cannot sell more stocks than owned");
                return;
            }

            // Execute the sale
            double totalSale = inputData.getStockPrice() * inputData.getQuantity();
            playerRepository.sellStockForPlayer(
                inputData.getPlayerId(),
                inputData.getStockTicker(),
                inputData.getQuantity(),
                totalSale
            );

            // Present success
            double newMoney = playerRepository.getPlayerMoney(inputData.getPlayerId());
            SellStockOutputData outputData = new SellStockOutputData(
                inputData.getPlayerId(),
                inputData.getStockTicker(),
                inputData.getQuantity(),
                totalSale,
                newMoney
            );
            outputBoundary.presentSuccess(outputData);

        } catch (Exception e) {
            outputBoundary.presentError("Failed to sell stock: " + e.getMessage());
        }
    }
}
