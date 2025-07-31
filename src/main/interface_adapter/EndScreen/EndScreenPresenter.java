package main.interface_adapter.EndScreen;

import main.use_case.EndScreen.EndGame;

import java.util.List;
import java.util.stream.Collectors;

public class EndScreenPresenter {
    public EndScreenViewModel execute(EndGame.EndGameResult result) {
        List<EndScreenViewModel.PlayerDisplayData> displayData = result.getPlayerResults()
                .stream()
                .map(playerResult -> new EndScreenViewModel.PlayerDisplayData(
                        playerResult.getPlayer(),
                        playerResult.getRank(),
                        String.format("%.2f", playerResult.getPlayer().getMoney()),
                        String.valueOf(playerResult.getPlayer().getProperties().size()),
                        String.format("%.2f", playerResult.getTotalPropertyValue()),
                        String.format("%.2f", playerResult.getNetWorth()),
                        String.valueOf(playerResult.getPlayer().getPosition()),
                        playerResult.getPlayer().isBankrupt() ? "BANKRUPT" : "SOLVENT"
                ))
                .collect(Collectors.toList());

        String winnerText = result.getWinner() != null
                ? "🏆 WINNER: " + result.getWinner().getName() + " 🏆"
                : "";

        return new EndScreenViewModel(
                "GAME OVER",
                result.getGameEndReason(),
                "Total Rounds Played: " + result.getTotalRounds(),
                winnerText,
                displayData,
                "New Game",
                "Exit"
        );
    }
}
