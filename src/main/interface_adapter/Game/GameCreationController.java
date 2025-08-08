package main.interface_adapter.Game;

import main.entity.Game;
import main.interface_adapter.CharacterSelectionScreen.PlayerOutputData;
import main.use_case.BoardSizeSelection.BoardSizeSelection.BoardSize;
import main.use_case.Game.GameInitializeTiles;
import java.util.List;

/**
 * Controller responsible for game creation and initialization.
 * Follows Single Responsibility Principle - only handles game creation logic.
 */
public class GameCreationController {
    private final GameInitializeTiles gameInitializeTiles;

    public GameCreationController(GameInitializeTiles gameInitializeTiles) {
        this.gameInitializeTiles = gameInitializeTiles;
    }

    /**
     * Create a game with the specified board size and players.
     * Single responsibility: only game creation and initialization.
     */
    public Game createGameWithBoardSize(List<PlayerOutputData> players, BoardSize boardSize) {
        Game game = new Game();
        game.setPlayersFromOutputData(players);

        // Use injected tile initializer
        switch (boardSize) {
            case SMALL:
                game.setTiles(gameInitializeTiles.executeSmallBoard());
                break;
            case MEDIUM:
                game.setTiles(gameInitializeTiles.executeMediumBoard());
                break;
            case LARGE:
                game.setTiles(gameInitializeTiles.executeLargeBoard());
                break;
        }

        return game;
    }
}

