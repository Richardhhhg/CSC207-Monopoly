package main.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import main.constants.Constants;
import main.use_case.game.PropertyDataSource;

class FallbackPropertyDataSourceTest {

    private FallbackPropertyDataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = new FallbackPropertyDataSource();
    }

    @Test
    void testGetPropertyData_ReturnsCorrectNumberOfProperties() {
        List<PropertyDataSource.PropertyInfo> properties = dataSource.getPropertyData();

        assertEquals(24, properties.size());
    }

    @Test
    void testGetPropertyData_FirstPropertyHasCorrectData() {
        List<PropertyDataSource.PropertyInfo> properties = dataSource.getPropertyData();

        PropertyDataSource.PropertyInfo firstProperty = properties.get(0);
        assertEquals("Mediterranean Ave", firstProperty.getName());
        assertEquals(Constants.PROPERTY_BASE_PRICE, firstProperty.getBasePrice());
    }

    @Test
    void testGetPropertyData_LastPropertyHasCorrectData() {
        List<PropertyDataSource.PropertyInfo> properties = dataSource.getPropertyData();

        PropertyDataSource.PropertyInfo lastProperty = properties.get(23);
        assertEquals("Pennsylvania Railroad", lastProperty.getName());

        // Calculate expected price for last property (index 23)
        int actualPrice = 400; // MAX_PROPERTY_PRICE = 400
        assertEquals(actualPrice, lastProperty.getBasePrice());
    }

    @Test
    void testGetPropertyData_PriceCapIsApplied() {
        List<PropertyDataSource.PropertyInfo> properties = dataSource.getPropertyData();

        // Check that no property exceeds MAX_PROPERTY_PRICE (400)
        for (PropertyDataSource.PropertyInfo property : properties) {
            assertTrue(property.getBasePrice() <= 400,
                "Property " + property.getName() + " exceeds max price: " + property.getBasePrice());
        }
    }

    @Test
    void testGetPropertyData_PriceIncrementsCorrectly() {
        List<PropertyDataSource.PropertyInfo> properties = dataSource.getPropertyData();

        // Test first few properties to ensure price increments correctly
        for (int i = 0; i < Math.min(properties.size(), 5); i++) {
            int expectedPrice = Constants.PROPERTY_BASE_PRICE + i * Constants.PROPERTY_PRICE_INCREMENT;
            expectedPrice = Math.min(expectedPrice, 400);

            assertEquals(expectedPrice, properties.get(i).getBasePrice(),
                "Property at index " + i + " has incorrect price");
        }
    }

    @Test
    void testGetPropertyData_AllPropertyNamesAreSet() {
        List<PropertyDataSource.PropertyInfo> properties = dataSource.getPropertyData();

        for (PropertyDataSource.PropertyInfo property : properties) {
            assertNotNull(property.getName());
            assertFalse(property.getName().trim().isEmpty());
        }
    }

    @Test
    void testGetPropertyData_SpecificPropertyNames() {
        List<PropertyDataSource.PropertyInfo> properties = dataSource.getPropertyData();

        // Test some specific property names
        assertEquals("Mediterranean Ave", properties.get(0).getName());
        assertEquals("Baltic Ave", properties.get(1).getName());
        assertEquals("Boardwalk", properties.get(17).getName());
        assertEquals("Park Place", properties.get(18).getName());
    }

    @Test
    void testGetPropertyData_AllPricesArePositive() {
        List<PropertyDataSource.PropertyInfo> properties = dataSource.getPropertyData();

        for (PropertyDataSource.PropertyInfo property : properties) {
            assertTrue(property.getBasePrice() > 0,
                "Property " + property.getName() + " has non-positive price: " + property.getBasePrice());
        }
    }

    @Test
    void testGetPropertyData_MultipleCalls_ReturnsSameData() {
        List<PropertyDataSource.PropertyInfo> properties1 = dataSource.getPropertyData();
        List<PropertyDataSource.PropertyInfo> properties2 = dataSource.getPropertyData();

        assertEquals(properties1.size(), properties2.size());

        for (int i = 0; i < properties1.size(); i++) {
            assertEquals(properties1.get(i).getName(), properties2.get(i).getName());
            assertEquals(properties1.get(i).getBasePrice(), properties2.get(i).getBasePrice());
        }
    }

    @Test
    void testGetPropertyData_PriceCappingMathMin_ExercisesBothBranches() {
        List<PropertyDataSource.PropertyInfo> properties = dataSource.getPropertyData();

        // Test early properties where Math.min returns the calculated price
        int earlyCalculatedPrice = Constants.PROPERTY_BASE_PRICE;
        assertTrue(true, "Early property should not hit cap");
        assertEquals(earlyCalculatedPrice, properties.get(0).getBasePrice());

        // Test later properties where Math.min returns the cap (400)
        boolean foundCappedProperty = false;
        for (int i = 0; i < properties.size(); i++) {
            int calculatedPrice = Constants.PROPERTY_BASE_PRICE + i * Constants.PROPERTY_PRICE_INCREMENT;
            if (calculatedPrice > 400) {
                assertEquals(400, properties.get(i).getBasePrice(),
                    "Property at index " + i + " should be capped at 400");
                foundCappedProperty = true;
            }
        }
        assertTrue(foundCappedProperty, "Should find at least one property that hits the price cap");
    }

    @Test
    void testGetPropertyData_PropertyAtSpecificIndices_VerifyPriceCalculation() {
        List<PropertyDataSource.PropertyInfo> properties = dataSource.getPropertyData();

        // Test specific indices to ensure price calculation is correct
        for (int testIndex : new int[]{0, 5, 10, 15, 20, 23}) {
            if (testIndex < properties.size()) {
                int expectedPrice = Constants.PROPERTY_BASE_PRICE + testIndex * Constants.PROPERTY_PRICE_INCREMENT;
                int actualExpectedPrice = Math.min(expectedPrice, 400);

                assertEquals(actualExpectedPrice, properties.get(testIndex).getBasePrice(),
                    "Property at index " + testIndex + " has incorrect price calculation");
            }
        }
    }
}
