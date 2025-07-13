package use_case;

public abstract class Tile {
    protected String name;

    public Tile(String name) {
        this.name = name;
    }

    /**
     * Event that occurs when a player lands on this tile.
     * @param player The player who landed on the tile.
     */
    public abstract void event(Player player);
}