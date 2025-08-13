package main.interface_adapter.tile;

import java.awt.Color;

public class PropertyTileViewModel extends AbstractTileViewModel {
    private final int price;
    private final String owner;
    private final float rent;
    private final Color color;

    public PropertyTileViewModel(String name, int price, String owner, float rent, Color color) {
        super(name);
        this.owner = owner;
        this.price = price;
        this.rent = rent;
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public String getOwner() {
        return owner;
    }

    public float getRent() {
        return rent;
    }

    public boolean isOwned() {
        return owner != null && !owner.isEmpty();
    }

    /**
     * Returns the color associated with the property tile.
     * If the property is owned, returns the owner's color; otherwise, returns white.
     *
     * @return Color representing the owner or white if not owned
     */
    public Color getOwnerColor() {
        final Color result;
        if (isOwned()) {
            result = color;
        }
        else {
            result = Color.WHITE;
        }
        return result;
    }
}
