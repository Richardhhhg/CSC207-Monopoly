package main.interface_adapter.EndScreen;

import main.entity.players.Player;
import main.use_case.EndScreen.EndGame;

import java.util.List;

public class EndScreenController {
    private final EndGame endGameUseCase;

    public EndScreenController() {
        this.endGameUseCase = new EndGame();
    }

    public EndScreenViewModel execute(List<Player> players, String gameEndReason, int totalRounds) {
        EndGame.EndGameResult result = endGameUseCase.execute(players, gameEndReason, totalRounds);
        EndScreenPresenter presenter = new EndScreenPresenter();
        return presenter.execute(result);
    }
}
