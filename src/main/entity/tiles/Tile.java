package main.entity.tiles;

/**
 * A single square on the board.
 */
public abstract class Tile {
    private final String name;

    /**
     * @param name human-readable tile name
     */
    public Tile(String name) {
        this.name = name;

    }

    /**
     * @return tile’s name
     */
    public String getName() {
        return name;
    }

}
