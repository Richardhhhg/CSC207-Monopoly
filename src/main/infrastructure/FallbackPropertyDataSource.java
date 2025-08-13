package main.infrastructure;

import java.util.ArrayList;
import java.util.List;

import main.constants.Constants;
import main.use_case.game.PropertyDataSource;

/**
 * Fallback implementation that provides hardcoded property data.
 * Used when primary data source fails.
 */
public class FallbackPropertyDataSource implements PropertyDataSource {

    private static final int MAX_PROPERTY_PRICE = 400;

    @Override
    public List<PropertyInfo> getPropertyData() {
        final List<PropertyInfo> propertyData = new ArrayList<>();
        final String[] fallbackNames = {
            "Mediterranean Ave", "Baltic Ave", "Oriental Ave", "Vermont Ave",
            "Connecticut Ave", "St. James Place", "Tennessee Ave", "New York Ave",
            "Kentucky Ave", "Indiana Ave", "Illinois Ave", "Atlantic Ave",
            "Ventnor Ave", "Marvin Gardens", "Pacific Ave", "North Carolina Ave",
            "Pennsylvania Ave", "Boardwalk", "Park Place", "Luxury Tax",
            "Short Line", "B&O Railroad", "Reading Railroad", "Pennsylvania Railroad",
        };

        for (int i = 0; i < fallbackNames.length; i++) {
            final int price = Constants.PROPERTY_BASE_PRICE + i * Constants.PROPERTY_PRICE_INCREMENT;
            propertyData.add(new PropertyInfo(fallbackNames[i], Math.min(price, MAX_PROPERTY_PRICE)));
        }

        return propertyData;
    }
}
