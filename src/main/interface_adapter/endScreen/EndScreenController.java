package main.interface_adapter.endScreen;

import main.entity.players.Player;
import main.use_case.endScreen.EndGame;

import java.util.List;

public class EndScreenController {
    private final EndGame endGameUseCase;

    public EndScreenController() {
        this.endGameUseCase = new EndGame();
    }

    public EndGame.EndGameResult execute(List<Player> players, String gameEndReason, int totalRounds) {
        return endGameUseCase.execute(players, gameEndReason, totalRounds);
    }
}