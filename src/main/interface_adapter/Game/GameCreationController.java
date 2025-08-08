package main.interface_adapter.Game;

import main.entity.Game;
import main.infrastructure.FallbackPropertyDataSource;
import main.infrastructure.JsonPropertyDataSource;
import main.interface_adapter.CharacterSelectionScreen.PlayerOutputData;
import main.use_case.BoardSizeSelection.BoardSizeSelection.BoardSize;
import main.use_case.Game.GameInitializeTiles;
import main.use_case.Game.PropertyDataSource;
import java.util.List;

/**
 * Controller responsible for game creation and initialization.
 * Follows Single Responsibility Principle - only handles game creation logic.
 */
public class GameCreationController {
    private final GameInitializeTiles gameInitializeTiles;

    public GameCreationController() {
        // Create composite data source with fallback
        PropertyDataSource primaryDataSource = new JsonPropertyDataSource();
        PropertyDataSource fallbackDataSource = new FallbackPropertyDataSource();
        PropertyDataSource compositeDataSource = new CompositePropertyDataSource(primaryDataSource, fallbackDataSource);

        this.gameInitializeTiles = new GameInitializeTiles(compositeDataSource, new FallbackPropertyDataSource());
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

    /**
     * Composite data source that handles fallback logic
     */
    private static class CompositePropertyDataSource implements PropertyDataSource {
        private final PropertyDataSource primary;
        private final PropertyDataSource fallback;

        public CompositePropertyDataSource(PropertyDataSource primary, PropertyDataSource fallback) {
            this.primary = primary;
            this.fallback = fallback;
        }

        @Override
        public List<PropertyInfo> getPropertyData() {
            List<PropertyInfo> data = primary.getPropertyData();
            return data != null && !data.isEmpty() ? data : fallback.getPropertyData();
        }
    }
}
