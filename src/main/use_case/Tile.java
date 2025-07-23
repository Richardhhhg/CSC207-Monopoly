package main.use_case;

import main.entity.Player;

/**
 * A single square on the board.
 */
public abstract class Tile {
    //private final int position;
    private final String name;

    /**
     * @param name human-readable tile name
     */
    /*TODO Figure out position for potential future scaling*/

    public Tile(String name) {
        this.name = name;

    }

    /** @return tile’s name */
    public String getName() {
        return name;
    }

    /** @return tile’s position index
    public int getPosition() {
        return position;
    } */

    /**
     * Hook for when a player lands here.
     * @param p landing player
     */
    public abstract void onLanding(Player p);
}
