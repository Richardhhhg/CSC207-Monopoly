package main.interface_adapter.endScreen;

import java.util.List;
import java.util.stream.Collectors;

import main.constants.Constants;
import main.use_case.endScreen.EndScreenOutputBoundary;
import main.use_case.endScreen.EndScreenOutputData;

/**
 * Presenter class responsible for converting EndScreen output data into a
 * displayable EndScreenViewModel.
 * Implements the output boundary interface to receive results from the interactor.
 */
public class EndScreenPresenter implements EndScreenOutputBoundary {
    private static final String FORMAT_TWO_DECIMALS = Constants.TWO_DECIMALS;
    private final EndScreenViewModel viewModel;

    /**
     * Constructs the presenter with the given view model.
     *
     * @param viewModel The view model for the end screen.
     */
    public EndScreenPresenter(EndScreenViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Processes the end game results and updates the view model.
     *
     * @param outputData The processed end game results from the interactor.
     */
    @Override
    public void presentEndGameResults(EndScreenOutputData outputData) {
        final List<EndScreenViewModel.PlayerDisplayData> displayData = outputData.getPlayerResults()
                .stream()
                .map(this::toDisplayData)
                .collect(Collectors.toList());

        final String winnerText;
        if (outputData.getWinner() != null) {
            winnerText = "WINNER: " + outputData.getWinner().getName();
        }
        else {
            winnerText = "";
        }

        // Update the view model with the processed data
        updateViewModel(
                "GAME OVER",
                outputData.getGameEndReason(),
                "Total Rounds Played: " + outputData.getTotalRounds(),
                winnerText,
                displayData,
                "New Game",
                "Exit"
        );
    }

    /**
     * Updates the view model with the display data.
     * This method updates the mutable view model state.
     *
     * @param gameOverTitle      the title text to display when the game ends
     * @param gameEndReason      the reason why the game ended
     * @param totalRoundsText    the text showing the total number of rounds played
     * @param winnerText         the text showing the winner's name, or empty if none
     * @param playerDisplayData  the list of player display data for the end screen
     * @param newGameButtonText  the text for the "New Game" button
     * @param exitButtonText     the text for the "Exit" button
     */
    private void updateViewModel(String gameOverTitle, String gameEndReason, String totalRoundsText,
                                 String winnerText, List<EndScreenViewModel.PlayerDisplayData> playerDisplayData,
                                 String newGameButtonText, String exitButtonText) {
        viewModel.updateAllData(gameOverTitle, gameEndReason, totalRoundsText,
                winnerText, playerDisplayData, newGameButtonText, exitButtonText);
    }

    /**
     * Builds a PlayerDisplayData row for the end-screen table.
     *
     * @param playerResult the per-player end-game result from the use case
     * @return a view-friendly representation for display
     */
    private EndScreenViewModel.PlayerDisplayData toDisplayData(EndScreenOutputData.PlayerResult playerResult) {
        final String finalCash = String.format(FORMAT_TWO_DECIMALS, playerResult.getFinalCash());
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
                totalPropertyValue,
                totalStockValue,
                netWorth,
                status
        );
    }

    /**
     * Returns the associated view model.
     *
     * @return The view model instance.
     */
    public EndScreenViewModel getViewModel() {
        return viewModel;
    }
}
