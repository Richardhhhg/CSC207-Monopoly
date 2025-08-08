package main.use_case.Stocks;

import main.data_access.StockMarket.InMemoryPlayerStockRepository;
import main.entity.Stocks.Stock;
import main.entity.players.DefaultPlayer;
import main.entity.players.Player;
import main.interface_adapter.StockMarket.BuyStockPresenter;
import main.interface_adapter.StockMarket.SellStockPresenter;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Test class demonstrating the new clean architecture interfaces for stock operations.
 * This test validates that the refactored stock system adheres to clean architecture principles.
 */
public class StockCleanArchitectureTest {
    
    private InMemoryPlayerStockRepository repository;
    private BuyStockPresenter buyPresenter;
    private SellStockPresenter sellPresenter;
    private BuyStock buyStockUseCase;
    private SellStock sellStockUseCase;
    private Player testPlayer;
    private Stock testStock;
    
    @Before
    public void setUp() {
        // Set up repository and entities
        repository = new InMemoryPlayerStockRepository();
        testPlayer = new DefaultPlayer("TestPlayer", Color.BLUE);
        // Add money to the player for testing
        testPlayer.addMoney(1000.0f);
        testStock = new Stock("AAPL", 150.0, 0.05, 0.02);
        
        // Register entities in repository
        repository.addPlayer("player1", testPlayer);
        repository.addStock("AAPL", testStock);
        
        // Set up presenters
        buyPresenter = new BuyStockPresenter();
        sellPresenter = new SellStockPresenter();
        
        // Set up use cases with dependency injection
        buyStockUseCase = new BuyStock(repository, buyPresenter);
        sellStockUseCase = new SellStock(repository, sellPresenter);
    }
    
    @Test
    public void testBuyStockWithCleanArchitecture() {
        // Arrange
        BuyStockInputData inputData = new BuyStockInputData("player1", "AAPL", 150.0, 5);
        
        // Act
        buyStockUseCase.execute(inputData);
        
        // Assert
        assertTrue("Buy operation should be successful", buyPresenter.isSuccess());
        assertNotNull("Success message should be present", buyPresenter.getSuccessMessage());
        assertEquals("Player should own 5 stocks", 5, 
                     repository.getPlayerStockQuantity("player1", "AAPL"));
        assertEquals("Player money should be reduced", 1450.0, 
                     repository.getPlayerMoney("player1"), 0.01);
    }
    
    @Test
    public void testSellStockWithCleanArchitecture() {
        // Arrange - first buy some stocks
        testPlayer.buyStock(testStock, 10);
        SellStockInputData inputData = new SellStockInputData("player1", "AAPL", 150.0, 3);
        
        // Act
        sellStockUseCase.execute(inputData);
        
        // Assert
        assertTrue("Sell operation should be successful", sellPresenter.isSuccess());
        assertNotNull("Success message should be present", sellPresenter.getSuccessMessage());
        assertEquals("Player should own 7 stocks after selling 3", 7, 
                     repository.getPlayerStockQuantity("player1", "AAPL"));
    }
    
    @Test
    public void testBuyStockInsufficientFunds() {
        // Arrange
        BuyStockInputData inputData = new BuyStockInputData("player1", "AAPL", 150.0, 20); // Costs $3000, player has $2200
        
        // Act
        buyStockUseCase.execute(inputData);
        
        // Assert
        assertFalse("Buy operation should fail", buyPresenter.isSuccess());
        assertEquals("Should have error message", "Insufficient funds to buy stocks", 
                     buyPresenter.getErrorMessage());
        assertEquals("Player should still own 0 stocks", 0, 
                     repository.getPlayerStockQuantity("player1", "AAPL"));
    }
    
    @Test
    public void testSellStockInsufficientQuantity() {
        // Arrange
        SellStockInputData inputData = new SellStockInputData("player1", "AAPL", 150.0, 5); // Player owns 0
        
        // Act
        sellStockUseCase.execute(inputData);
        
        // Assert
        assertFalse("Sell operation should fail", sellPresenter.isSuccess());
        assertEquals("Should have error message", "Cannot sell more stocks than owned", 
                     sellPresenter.getErrorMessage());
    }
    
    /**
     * This test demonstrates that the interface adapter layer no longer directly depends on entities.
     * The use cases communicate through well-defined boundaries using DTOs.
     */
    @Test
    public void testCleanArchitectureBoundaries() {
        // This test validates that:
        // 1. Use cases implement input boundary interfaces
        assertTrue("BuyStock should implement input boundary", 
                   buyStockUseCase instanceof BuyStockInputBoundary);
        assertTrue("SellStock should implement input boundary", 
                   sellStockUseCase instanceof SellStockInputBoundary);
        
        // 2. Presenters implement output boundary interfaces  
        assertTrue("BuyStockPresenter should implement output boundary", 
                   buyPresenter instanceof BuyStockOutputBoundary);
        assertTrue("SellStockPresenter should implement output boundary", 
                   sellPresenter instanceof SellStockOutputBoundary);
        
        // 3. Repository implements the repository interface
        assertTrue("Repository should implement interface", 
                   repository instanceof PlayerStockRepository);
    }
}