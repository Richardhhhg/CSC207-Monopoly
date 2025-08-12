package main.interface_adapter.endScreen;

import java.util.List;
import java.util.stream.Collectors;

import main.constants.Constants;
import main.use_case.endScreen.EndGame;

/**
 * Presenter class responsible for converting EndGame results into a
 * displayable EndScreenViewModel.
 */
public class EndScreenPresenter {

    private static final String FORMAT_TWO_DECIMALS = Constants.TWO_DECIMALS;

    /**
     * Converts the EndGame result into a view model for the end screen.
     *
     * @param result the final result of the game
     * @return an EndScreenViewModel containing the display data
     */
    public EndScreenViewModel execute(EndGame.EndGameResult result) {
        final List<EndScreenViewModel.PlayerDisplayData> displayData = result.getPlayerResults()
                .stream()
                .map(this::toDisplayData)
                .collect(Collectors.toList());

        final String winnerText;
        if (result.getWinner() != null) {
            winnerText = " WINNER: " + result.getWinner().getName();
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
     * Builds a PlayerDisplayData row for the end-screen table.
     *
     * @param playerResult the per-player end-game result
     * @return a view-friendly representation for display
     */
    private EndScreenViewModel.PlayerDisplayData toDisplayData(EndGame.PlayerResult playerResult) {
        final String finalCash = String.format(FORMAT_TWO_DECIMALS, playerResult.getFinalCash());
        final String propertiesCount = String.valueOf(playerResult.getPlayer().getProperties().size());
        final String totalPropertyValue = String.format(FORMAT_TWO_DECIMALS, playerResult.getTotalPropertyValue());
        final String totalStockValue = String.format(FORMAT_TWO_DECIMALS, playerResult.getTotalStockValue());
        final String netWorth = String.format(FORMAT_TWO_DECIMALS, playerResult.getNetWorth());

        final String status;
        if (playerResult.getPlayer().isBankrupt()) {
            status = "BANKRUPT";
        }
        else {
            status = "SOLVENT";
        }

        return new EndScreenViewModel.PlayerDisplayData(
                playerResult.getPlayer(),
                playerResult.getRank(),
                finalCash,
                propertiesCount,
                totalPropertyValue,
                totalStockValue,
                netWorth,
                status
        );
    }
}
