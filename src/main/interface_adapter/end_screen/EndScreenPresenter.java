package main.interface_adapter.end_screen;

import java.util.List;
import java.util.stream.Collectors;

import main.Constants.Constants;
import main.use_case.EndScreen.EndGame;

/**
 * Presenter for the end screen.
 * Converts an {@link EndGame.EndGameResult} into an {@link EndScreenViewModel}
 * containing display data for each player and a game summary.
 */
public class EndScreenPresenter {

    /**
     * Transforms the end-game result into a view model.
     *
     * @param result the result of the end-game use case, must not be null
     * @return an {@link EndScreenViewModel} for UI rendering
     */
    public EndScreenViewModel execute(EndGame.EndGameResult result) {
        final List<EndScreenViewModel.PlayerDisplayData> displayData =
                result.getPlayerResults()
                        .stream()
                        .map(this::mapToDisplayData)
                        .collect(Collectors.toList());

        final String winnerText;
        if (result.getWinner() != null) {
            winnerText = Constants.WINNER_PREFIX + result.getWinner().getName();
        }
        else {
            winnerText = "";
        }

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

    /**
     * Extracted mapping logic so the lambda body stays under the 10-line limit.
     *
     * @param playerResult the result for a single player to map, must not be null
     * @return a {@link EndScreenViewModel.PlayerDisplayData} populated with display values
     */
    private EndScreenViewModel.PlayerDisplayData mapToDisplayData(
            EndGame.PlayerResult playerResult
    ) {
        final String cash = String.format(Constants.DECIMAL_FORMAT,
                playerResult.getFinalCash());
        final String propertyValue = String.format(Constants.DECIMAL_FORMAT,
                playerResult.getTotalPropertyValue());
        final String stockValue = String.format(Constants.DECIMAL_FORMAT,
                playerResult.getTotalStockValue());
        final String netWorth = String.format(Constants.DECIMAL_FORMAT,
                playerResult.getNetWorth());

        final String status;
        if (playerResult.getPlayer().isBankrupt()) {
            status = Constants.STATUS_BANKRUPT;
        }
        else {
            status = Constants.STATUS_SOLVENT;
        }

        return new EndScreenViewModel.PlayerDisplayData(
                playerResult.getPlayer(),
                playerResult.getRank(),
                cash,
                String.valueOf(playerResult.getPlayer().getProperties().size()),
                propertyValue,
                stockValue,
                netWorth,
                status
        );
    }
}
