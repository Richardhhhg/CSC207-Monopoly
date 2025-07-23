package main.use_case;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A single square on the board.
 */
public abstract class Tile {
    private final int position;
    private final String name;

    /**
     * @param name       human-readable tile name
     * @param position   index on the board (0–n)
     */
    /*TODO Figure out position for potential future scaling*/

    public Tile(String name, int position) {
        this.name = name;
        this.position = position;
    }

    /** @return tile’s name */
    public String getName() {
        return name;
    }

    /** @return tile’s position index */
    public int getPosition() {
        return position;
    }

    /**
     * Hook for when a player lands here.
     * @param p landing player
     */
    public abstract void onLanding(Player p);
}
