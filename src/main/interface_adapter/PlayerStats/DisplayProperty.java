package main.interface_adapter.PlayerStats;

public class DisplayProperty {
    private final String propertyName;

    public DisplayProperty(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @return propertyName.
     */
    public String getPropertyName() {
        return propertyName;
    }
}
