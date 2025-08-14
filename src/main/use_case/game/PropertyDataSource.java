package main.use_case.game;

import java.util.List;

/**
 * Interface for accessing property data.
 * Follows Dependency Inversion Principle - use case depends on abstraction, not concrete implementation.
 */
public interface PropertyDataSource {
    /**
     * Retrieves the list of property data.
     *
     * @return list of PropertyInfo objects containing property details
     */
    List<PropertyInfo> getPropertyData();

    /**
     * Data structure for property information.
     */
    class PropertyInfo {
        private final String name;
        private final int basePrice;

        public PropertyInfo(String name, int basePrice) {
            this.name = name;
            this.basePrice = basePrice;
        }

        public String getName() {
            return name;
        }

        public int getBasePrice() {
            return basePrice;
        }
    }
}
