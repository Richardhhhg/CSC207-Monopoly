package main.interface_adapter.PlayerStats;

public class DisplayProperty {
    public final String propertyName;

    public DisplayProperty(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName(){
        return propertyName;
    }
}
