package main.use_case;

import main.entity.Player;
import main.entity.tiles.PropertyTile;
import main.use_case.GameState;
import main.use_case.GameStateSnapshot;
import main.use_case.MovePlayerResult;
import main.use_case.NextPlayerResult;

/**
 * Use case for managing game state and business rules
 */
public class GameUseCase {
    private final GameState gameState;

    public GameUseCase(GameState gameState) {
        this.gameState = gameState;
    }

    public void initializeGame() {
        gameState.initializeGame();
    }

    public MovePlayerResult movePlayer(int playerId, int steps) {
        Player player = gameState.getPlayer(playerId);
        if (player == null || player.isBankrupt()) {
            return new MovePlayerResult(false, "Invalid player", 0, false);
        }

        int oldPosition = player.getPosition();
        int newPosition = (oldPosition + steps) % gameState.getTileCount();
        boolean passedGo = newPosition < oldPosition;

        player.setPosition(newPosition);

        if (passedGo) {
            player.addMoney(gameState.getFinishLineBonus());
        }

        return new MovePlayerResult(true, "", newPosition, passedGo);
    }

    public NextPlayerResult nextPlayer() {
        gameState.getCurrentPlayer().applyTurnEffects();

        int currentIndex = gameState.getCurrentPlayerIndex();
        int playerCount = gameState.getPlayerCount();

        for (int i = 1; i <= playerCount; i++) {
            int nextIndex = (currentIndex + i) % playerCount;
            Player nextPlayer = gameState.getPlayer(nextIndex);
            if (!nextPlayer.isBankrupt()) {
                gameState.setCurrentPlayerIndex(nextIndex);
                return new NextPlayerResult(true, nextIndex, false);
            }
        }

        // All players bankrupt
        gameState.setCurrentPlayerIndex(-1);
        return new NextPlayerResult(false, -1, true);
    }

    public GameStateSnapshot getGameState() {
        return new GameStateSnapshot(
                gameState.getProperties(),
                gameState.getPlayers(),
                gameState.getCurrentPlayerIndex(),
                gameState.getTileCount()
        );
    }
}