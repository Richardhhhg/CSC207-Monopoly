package main.use_case.property;

import main.constants.Constants;
import main.entity.players.AbstractPlayer;
import main.entity.players.DefaultPlayer;
import main.entity.tiles.PropertyTile;
import main.use_case.tiles.property.RentPaymentOutputBoundary;
import main.use_case.tiles.property.RentPaymentUseCase;
import main.use_case.tiles.property.RentPaymentUseCase.RentPaymentData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RentPaymentTest {

    private RentPaymentUseCase rentPaymentUseCase;
    private PropertyTile property;
    private MockRentPaymentPresenter presenter;

    static class MockRentPaymentPresenter implements RentPaymentOutputBoundary {
        RentPaymentData lastRentData;

        @Override
        public void presentRentPayment(RentPaymentData data) {
            this.lastRentData = data;
        }
    }

    @BeforeEach
    void setUp() {
        // Use calculated rent: 500 * 0.25f = 125.0f
        float calculatedRent = 500 * Constants.RENT_MULTIPLIER;
        property = new PropertyTile("TestProperty", 500, calculatedRent);
        presenter = new MockRentPaymentPresenter();
        rentPaymentUseCase = new RentPaymentUseCase(presenter);
    }

    @Test
    void testExecute_RentPayment_DeductsFromPayerAddsToOwner() {
        // Arrange
        AbstractPlayer payer = new DefaultPlayer("Payer", Color.BLUE);
        AbstractPlayer owner = new DefaultPlayer("Owner", Color.RED);
        // DefaultPlayer starts with 1200, so after adding 500, payer has 1700
        // owner starts with 1200, after adding 1000, owner has 2200
        payer.addMoney(500.0f);
        owner.addMoney(1000.0f);
        float rentAmount = 100.0f;

        // Act
        rentPaymentUseCase.execute(payer, owner, property, rentAmount);

        // Assert
        assertEquals(1600.0f, payer.getMoney()); // 1700 - 100
        assertEquals(2300.0f, owner.getMoney()); // 2200 + 100
    }

    @Test
    void testExecute_RentPayment_CallsPresentRentPaymentWithCorrectData() {
        // Arrange
        AbstractPlayer payer = new DefaultPlayer("Payer", Color.BLUE);
        AbstractPlayer owner = new DefaultPlayer("Owner", Color.RED);
        payer.addMoney(500.0f); // 1200 + 500 = 1700
        owner.addMoney(1000.0f); // 1200 + 1000 = 2200
        float rentAmount = 100.0f;

        // Act
        rentPaymentUseCase.execute(payer, owner, property, rentAmount);

        // Assert
        assertNotNull(presenter.lastRentData);
        assertEquals("Payer", presenter.lastRentData.payerName());
        assertEquals("Owner", presenter.lastRentData.ownerName());
        assertEquals("TestProperty", presenter.lastRentData.propertyName());
        assertEquals(100.0f, presenter.lastRentData.rentAmount());
        assertEquals(1600.0f, presenter.lastRentData.payerNewMoney()); // 1700 - 100
        assertEquals(2300.0f, presenter.lastRentData.ownerNewMoney()); // 2200 + 100
    }

    @Test
    void testExecute_ZeroRent_StillProcessesPayment() {
        // Arrange
        AbstractPlayer payer = new DefaultPlayer("Payer", Color.BLUE);
        AbstractPlayer owner = new DefaultPlayer("Owner", Color.RED);
        payer.addMoney(500.0f); // 1200 + 500 = 1700
        owner.addMoney(1000.0f); // 1200 + 1000 = 2200
        float rentAmount = 0.0f;

        // Act
        rentPaymentUseCase.execute(payer, owner, property, rentAmount);

        // Assert
        assertEquals(1700.0f, payer.getMoney()); // No change
        assertEquals(2200.0f, owner.getMoney()); // No change
        assertNotNull(presenter.lastRentData);
        assertEquals(0.0f, presenter.lastRentData.rentAmount());
    }

    @Test
    void testExecute_HighRentAmount_ProcessesCorrectly() {
        // Arrange
        AbstractPlayer payer = new DefaultPlayer("Payer", Color.BLUE);
        AbstractPlayer owner = new DefaultPlayer("Owner", Color.RED);
        // For this test, we want payer to have exactly 500 to pay as rent
        // DefaultPlayer starts with 1200, so deduct 700 to get 500
        payer.deductMoney(700.0f); // 1200 - 700 = 500
        owner.addMoney(1000.0f); // 1200 + 1000 = 2200
        float rentAmount = 500.0f; // All of payer's money

        // Act
        rentPaymentUseCase.execute(payer, owner, property, rentAmount);

        // Assert
        assertEquals(0.0f, payer.getMoney()); // 500 - 500
        assertEquals(2700.0f, owner.getMoney()); // 2200 + 500

        assertNotNull(presenter.lastRentData);
        assertEquals(500.0f, presenter.lastRentData.rentAmount());
        assertEquals(0.0f, presenter.lastRentData.payerNewMoney());
        assertEquals(2700.0f, presenter.lastRentData.ownerNewMoney());
    }

    @Test
    void testExecute_NegativeRentAmount_ProcessesCorrectly() {
        // Arrange
        AbstractPlayer payer = new DefaultPlayer("Payer", Color.BLUE);
        AbstractPlayer owner = new DefaultPlayer("Owner", Color.RED);
        payer.addMoney(500.0f); // 1200 + 500 = 1700
        owner.addMoney(1000.0f); // 1200 + 1000 = 2200
        float rentAmount = -10.0f; // Negative rent (edge case)

        // Act
        rentPaymentUseCase.execute(payer, owner, property, rentAmount);

        // Assert
        assertEquals(1710.0f, payer.getMoney()); // 1700 - (-10) = 1710
        assertEquals(2190.0f, owner.getMoney()); // 2200 + (-10) = 2190

        assertNotNull(presenter.lastRentData);
        assertEquals(-10.0f, presenter.lastRentData.rentAmount());
    }

    // Test data object
    @Test
    void testRentPaymentData_AllGetters() {
        // Arrange & Act
        RentPaymentData data = new RentPaymentData(
            "Payer1", "Owner1", "Property1", 150.0f, 350.0f, 1150.0f
        );

        // Assert
        assertEquals("Payer1", data.payerName());
        assertEquals("Owner1", data.ownerName());
        assertEquals("Property1", data.propertyName());
        assertEquals(150.0f, data.rentAmount());
        assertEquals(350.0f, data.payerNewMoney());
        assertEquals(1150.0f, data.ownerNewMoney());
    }
}
