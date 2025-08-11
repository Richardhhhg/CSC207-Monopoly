package main.infrastructure;

import main.use_case.game.PropertyDataSource;
import java.util.ArrayList;
import java.util.List;
import main.Constants.Constants;

/**
 * Fallback implementation that provides hardcoded property data.
 * Used when primary data source fails.
 */
public class FallbackPropertyDataSource implements PropertyDataSource {

    @Override
    public List<PropertyInfo> getPropertyData() {
        List<PropertyInfo> propertyData = new ArrayList<>();
        String[] fallbackNames = {
            "Mediterranean Ave", "Baltic Ave", "Oriental Ave", "Vermont Ave",
            "Connecticut Ave", "St. James Place", "Tennessee Ave", "New York Ave",
            "Kentucky Ave", "Indiana Ave", "Illinois Ave", "Atlantic Ave",
            "Ventnor Ave", "Marvin Gardens", "Pacific Ave", "North Carolina Ave",
            "Pennsylvania Ave", "Boardwalk", "Park Place", "Luxury Tax",
            "Short Line", "B&O Railroad", "Reading Railroad", "Pennsylvania Railroad"
        };

        for (int i = 0; i < fallbackNames.length; i++) {
            int price = Constants.PROPERTY_BASE_PRICE + i * Constants.PROPERTY_PRICE_INCREMENT;
            propertyData.add(new PropertyInfo(fallbackNames[i], Math.min(price, 400)));
        }

        return propertyData;
    }
}
