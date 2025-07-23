package main.interface_adapter;

import main.use_case.GameUseCase;
import main.use_case.MovePlayerResult;
import main.use_case.NextPlayerResult;
import main.interface_adapter.GameViewModel;

/**
 * Controller handles user input and coordinates between use cases and view models
 */
public class GameController {
    private final GameUseCase gameUseCase;
    private final GameViewModel gameViewModel;
    private final DiceController diceController;
    private boolean rollInProgress = false;

    public GameController(GameUseCase gameUseCase, GameViewModel gameViewModel,
                          DiceController diceController) {
        this.gameUseCase = gameUseCase;
        this.gameViewModel = gameViewModel;
        this.diceController = diceController;

        // Initialize game
        gameUseCase.initializeGame();
        updateViewModel();
    }

    public void handleRollDice() {
        if (rollInProgress) return;

        rollInProgress = true;
        gameViewModel.setRollButtonEnabled(false);

        diceController.startDiceAnimation(
                // On animation frame
                () -> gameViewModel.notifyViewUpdate(),
                // On animation complete
                () -> {
                    int currentPlayerIndex = gameViewModel.getCurrentPlayerIndex();
                    int diceSum = diceController.getLastDiceSum();

                    MovePlayerResult result = gameUseCase.movePlayer(currentPlayerIndex, diceSum);

                    if (result.isSuccess()) {
                        updateViewModel();
                        gameViewModel.startPlayerMovementAnimation(currentPlayerIndex, diceSum);
                    } else {
                        gameViewModel.showError(result.getErrorMessage());
                    }

                    rollInProgress = false;
                }
        );
    }

    public void handleEndTurn() {
        NextPlayerResult result = gameUseCase.nextPlayer();

        if (result.isGameOver()) {
            gameViewModel.showGameOver();
            gameViewModel.setRollButtonEnabled(false);
        } else {
            updateViewModel();
            gameViewModel.setRollButtonEnabled(true);
        }
    }

    private void updateViewModel() {
        gameViewModel.updateGameState(gameUseCase.getGameState());
    }

    public GameViewModel getGameViewModel() {
        return gameViewModel;
    }

    public DiceController getDiceController() {
        return diceController;
    }
}