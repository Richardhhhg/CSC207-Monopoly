package main.entity.tiles;

/**
 * A single square on the board.
 */
public abstract class AbstractTile {
    private final String name;

    /**
     * Creates a new tile with the specified name.
     *
     * @param name human-readable tile name
     */
    public AbstractTile(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this tile.
     *
     * @return tile's name
     */
    public String getName() {
        return name;
    }
}
