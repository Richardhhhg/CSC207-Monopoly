package main.interface_adapter.game;

import main.entity.Game;
import main.infrastructure.FallbackPropertyDataSource;
import main.infrastructure.JsonPropertyDataSource;
import main.interface_adapter.characterSelectionScreen.CharacterSelectionPlayerViewModel;
import main.use_case.boardSizeSelection.BoardSizeSelection.BoardSize;
import main.use_case.game.GameInitializeTiles;
import main.use_case.game.GameInitializeStocks;
import main.use_case.game.PropertyDataSource;
import java.util.List;

/**
 * Controller responsible for game creation and initialization.
 */
public class GameCreationController {
    private final GameInitializeTiles gameInitializeTiles;

    public GameCreationController() {
        // Try JSON first, fallback to hardcoded data if it fails
        PropertyDataSource dataSource;
        try {
            dataSource = new JsonPropertyDataSource();
            // Test if it works by calling getPropertyData
            dataSource.getPropertyData();
        } catch (Exception e) {
            System.out.println("JSON data source failed, using fallback: " + e.getMessage());
            dataSource = new FallbackPropertyDataSource();
        }
        this.gameInitializeTiles = new GameInitializeTiles(dataSource);
    }

    /**
     * Create a game with the specified board size and players.
     */
    public Game createGameWithBoardSize(List<CharacterSelectionPlayerViewModel> players, BoardSize boardSize) {
        Game game = new Game();
        game.setPlayersFromOutputData(players);

        List<main.entity.tiles.Tile> tiles;
        try {
            tiles = switch (boardSize) {
                case SMALL -> gameInitializeTiles.executeSmallBoard();
                case MEDIUM -> gameInitializeTiles.executeMediumBoard();
                case LARGE -> gameInitializeTiles.executeLargeBoard();
            };
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize tiles for board size: " + boardSize + ". Error: " + e.getMessage(), e);
        }

        // Validate tiles before setting
        if (tiles == null || tiles.isEmpty()) {
            throw new IllegalStateException("Failed to initialize tiles for board size: " + boardSize);
        }

        game.setTiles(tiles);

        // Initialize stocks
        GameInitializeStocks stockInitializer = new GameInitializeStocks(game);
        stockInitializer.execute();

        return game;
    }
}
