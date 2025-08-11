package main.interface_adapter.Game;

import main.entity.Game;
import main.infrastructure.FallbackPropertyDataSource;
import main.interface_adapter.CharacterSelectionScreen.PlayerOutputData;
import main.use_case.BoardSizeSelection.BoardSizeSelection.BoardSize;
import main.use_case.Game.GameInitializeTiles;
import main.use_case.Game.GameInitializeStocks;
import main.use_case.Game.PropertyDataSource;
import java.util.List;

/**
 * Controller responsible for game creation and initialization.
 */
public class GameCreationController {
    private final GameInitializeTiles gameInitializeTiles;

    public GameCreationController() {
        // Use simple fallback data source - composition should be handled elsewhere
        PropertyDataSource dataSource = new FallbackPropertyDataSource();
        this.gameInitializeTiles = new GameInitializeTiles(dataSource);
    }

    /**
     * Create a game with the specified board size and players.
     */
    public Game createGameWithBoardSize(List<PlayerOutputData> players, BoardSize boardSize) {
        Game game = new Game();
        game.setPlayersFromOutputData(players);

        // Initialize tiles
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

        // Initialize stocks
        GameInitializeStocks stockInitializer = new GameInitializeStocks(game);
        stockInitializer.execute();

        return game;
    }
}
