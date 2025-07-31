package main.use_case.Game;

import main.entity.Game;
import main.entity.Stocks.Stock;

import java.util.List;

import static main.Constants.Constants.MAX_ROUNDS;

/**
 * This is a use case of game for advancing to the next round.
 * Note: This is different from nextTurn, which advances to the next turn within the same round.
 */
public class GameNextRound {
    private Game game;

    public GameNextRound(Game game) {
        this.game = game;
    }

    public void execute() {
        // TODO: Get the stocks from the game rather than from the player
        List<Stock> stocks = game.getStocks();
        for (Stock stock : stocks) {
            stock.updatePrice();
        }

        // Check if maximum rounds reached (20 rounds = 80 turns for 4 players)
        if (game.getTotalTurns() >= MAX_ROUNDS * 4) {
            game.endGame("Maximum 20 rounds reached");
        }
    }
}
