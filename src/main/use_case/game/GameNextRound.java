package main.use_case.game;

import java.util.List;

import main.entity.Game;
import main.entity.stocks.Stock;

/**
 * This is a use case of game for advancing to the next round.
 * Note: This is different from nextTurn, which advances to the next turn within the same round.
 */
public class GameNextRound {
    private Game game;

    public GameNextRound(Game game) {
        this.game = game;
    }

    /**
     * Executes the logic for advancing to the next round.
     * This method updates stock prices and handles any other round-end logic.
     */
    public void execute() {
        // Update stock prices at the end of each round
        final List<Stock> stocks = game.getStocks();
        for (Stock stock : stocks) {
            stock.updatePrice();
        }

        // The round increment and max rounds check is now handled in Game.startNewRound()
        // This method just handles the round-end logic like stock price updates
    }
}
