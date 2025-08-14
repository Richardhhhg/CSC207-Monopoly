package main.interface_adapter.game;

import java.io.IOException;
import java.util.List;

import main.entity.Game;
import main.entity.tiles.AbstractTile;
import main.infrastructure.FallbackPropertyDataSource;
import main.infrastructure.JsonPropertyDataSource;
import main.interface_adapter.character_selection_screen.CharacterSelectionPlayerViewModel;
import main.use_case.board_size_selection.BoardSizeSelection.BoardSize;
import main.use_case.game.GameInitializeStocks;
import main.use_case.game.GameInitializeTiles;
import main.use_case.game.PropertyDataSource;

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
        }
        catch (IllegalArgumentException | IllegalStateException exception) {
            System.out.println("JSON data source failed, using fallback: " + exception.getMessage());
            dataSource = new FallbackPropertyDataSource();
        }
        this.gameInitializeTiles = new GameInitializeTiles(dataSource);
    }

    /**
     * Create a game with the specified board size and players.
     *
     * @param players   the list of players to add to the game
     * @param boardSize the size of the board to create
     * @return the initialized game instance
     * @throws IllegalStateException if tile initialization fails
     */
    public Game createGameWithBoardSize(List<CharacterSelectionPlayerViewModel> players, BoardSize boardSize) {
        final Game game = new Game();
        game.setPlayersFromOutputData(players);

        final List<AbstractTile> tiles;
        try {
            tiles = switch (boardSize) {
                case SMALL -> gameInitializeTiles.executeSmallBoard();
                case MEDIUM -> gameInitializeTiles.executeMediumBoard();
                case LARGE -> gameInitializeTiles.executeLargeBoard();
            };
        }
        catch (IllegalArgumentException | IllegalStateException exception) {
            throw new IllegalStateException("Failed to initialize tiles for board size: " + boardSize
                    + ". Error: " + exception.getMessage(), exception);
        }

        // Validate tiles before setting
        if (tiles == null || tiles.isEmpty()) {
            throw new IllegalStateException("Failed to initialize tiles for board size: " + boardSize);
        }

        game.setTiles(tiles);

        // Initialize stocks
        final GameInitializeStocks stockInitializer = new GameInitializeStocks(game);
        try {
            stockInitializer.execute();
        }
        catch (IOException | InterruptedException exception) {
            throw new IllegalStateException("Failed to initialize stocks. Error: " + exception.getMessage(), exception);
        }

        return game;
    }
}
