package main.use_case.game;

import main.entity.Game;
import main.entity.players.PoorMan;
import main.entity.players.clerk;
import main.entity.players.inheritor;
import main.entity.players.landlord;
import main.entity.players.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Use case for initializing players in the game.
 * This class creates a list of players with predefined names and colors.
 */
public class GameInitializePlayers {
    private static final Color[] PLAYER_COLORS = {Color.RED, Color.CYAN, Color.GREEN, Color.ORANGE}; // TODO: Later player should be able to choose this
    private static final String[] PLAYER_NAMES = {"Player 1", "Player 2", "Player 3", "Player 4"}; // TODO: Later player should be able to choose this

    private Game game;

    public GameInitializePlayers(Game game) {
        this.game = game;
    }

    public void execute() {
        List<Player> players = new ArrayList<>();
        inheritor inheritor = new inheritor(PLAYER_NAMES[0], PLAYER_COLORS[0]);
        clerk clerk = new clerk(PLAYER_NAMES[1], PLAYER_COLORS[1]);
        PoorMan poorman = new PoorMan(PLAYER_NAMES[2], PLAYER_COLORS[2]);
        landlord landlord = new landlord(PLAYER_NAMES[3], PLAYER_COLORS[3]);
        players.add(inheritor);
        players.add(clerk);
        players.add(poorman);
        players.add(landlord);

        game.setPlayers(players);
    }
}
