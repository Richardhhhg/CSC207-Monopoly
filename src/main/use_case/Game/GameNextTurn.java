package main.use_case.Game;

import main.entity.Game;
import main.use_case.Player;

import java.util.List;

/**
 * This is a use case of game for advancing to the next turn.
 */
public class GameNextTurn {
    private Game game;
    private static final int TURNS_PER_ROUND = 4; // TODO: This is hardcoded, this does not account for player deaths

    public GameNextTurn(Game game) {
        this.game = game;
    }

    public void execute() {
        List<Player> players = game.getPlayers();
        if (game.getGameEnded()) return;

        int currentPlayerIndex = game.getCurrentPlayerIndex();
        Player currentPlayer = players.get(currentPlayerIndex);

        // FIXME: There may be a bug if current player has no turn effects
        currentPlayer.applyTurnEffects();
        game.increaseTurn();

        // TODO: The next 2 blocks are horrible, make it prettier later - Richard
        int nextIndex = -1;
        for (int i = 1; i <= players.size(); i++) {
            int candidateIndex = (currentPlayerIndex + i) % players.size();
            if (!players.get(candidateIndex).isBankrupt()) {
                nextIndex = candidateIndex;
                break;
            }
        }

        if (nextIndex == -1) {
            // All players are bankrupt
            game.endGame("All players are bankrupt");
            game.setCurrentPlayerIndex(-1);
            return;
        } else {
            game.setCurrentPlayerIndex(nextIndex);
        }

        // FIXME: this does not account for player deaths
        if (game.getTotalTurns() % TURNS_PER_ROUND == 0) {
            GameNextRound nextRound = new GameNextRound(game);
            nextRound.execute();
        }

        // TODO: I don't think it's possible for any player but current player to be bankrupt on current player turn
        GameCheckBankrupt checkBankrupt = new GameCheckBankrupt(game);
        checkBankrupt.execute();

        game.setCurrentPlayerIndex((currentPlayerIndex+1) % (players.size()));
    }
}
