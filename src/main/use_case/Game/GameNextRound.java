package main.use_case.Game;

import main.entity.Game;
import main.entity.Stocks.Stock;

import java.util.List;

import static main.Constants.Constants.MAX_ROUNDS;

/**
 * This is a use case of game for advancing to the next round.
 * Currently it only updates the stock prices for all stocks in the game.
 * Note: This is different from nextTurn, which advances to the next turn within the same round.
 */
public class GameNextRound {
    private Game game;

    public GameNextRound(Game game) {
        this.game = game;
    }

    public void execute() {
        List<Stock> stocks = game.getStocks();
        for (Stock stock : stocks) {
            stock.updatePrice();
        }
    }
}