package main.interface_adapter.PlayerStats;

public class DisplayProperty {
    /**
     * A property ready for display in the view.
     */
    private final String propertyName;

    public DisplayProperty(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * Returns the property's name.
     * @return propertyName.
     */
    public String getPropertyName() {
        return propertyName;
    }
}
