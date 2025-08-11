package main.interface_adapter.endScreen;

import main.use_case.endScreen.EndGame;

import java.util.List;
import java.util.stream.Collectors;

public class EndScreenPresenter {
    public EndScreenViewModel execute(EndGame.EndGameResult result) {
        List<EndScreenViewModel.PlayerDisplayData> displayData = result.getPlayerResults()
                .stream()
                .map(playerResult -> new EndScreenViewModel.PlayerDisplayData(
                        playerResult.getPlayer(),
                        playerResult.getRank(),
                        String.format("%.2f", playerResult.getFinalCash()), // Just liquid cash
                        String.valueOf(playerResult.getPlayer().getProperties().size()),
                        String.format("%.2f", playerResult.getTotalPropertyValue()),
                        String.format("%.2f", playerResult.getTotalStockValue()), // Current stock value
                        String.format("%.2f", playerResult.getNetWorth()), // Cash + properties + stocks
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