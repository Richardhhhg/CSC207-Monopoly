package main.entity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import main.constants.Constants;
import main.entity.players.AbstractPlayer;
import main.entity.players.CharacterFactory;
import main.entity.stocks.Stock;
import main.entity.tiles.AbstractTile;
import main.interface_adapter.character_selection_screen.CharacterSelectionPlayerViewModel;

public class Game {
    private static final int ADJUST_THREE = 3;
    private static final int ADJUST_TWO = 2;

    private List<AbstractTile> tiles;
    private List<AbstractPlayer> players;
    private List<Stock> stocks;
    private int currentPlayerIndex;
    private int tileCount;
    private int totalTurns;
    private int currentRound = 1;
    private int turnsInCurrentRound;
    private int roundStartPlayerIndex;
    private boolean gameEnded;
    private String gameEndReason = "";

    public boolean isGameOver() {
        return gameEnded;
    }

    public String getGameEndReason() {
        return gameEndReason;
    }

    /**
     * Get the current round number (1-based).
     *
     * @return Current round number
     */
    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * Get current player.
     *
     * @return Current player or null if game has ended or no current player
     * @throws IllegalStateException if there is no current player or the game has ended
     */
    public AbstractPlayer getCurrentPlayer() {
        if (currentPlayerIndex == -1 || gameEnded) {
            throw new IllegalStateException("No current player or game has ended");
        }
        return players.get(currentPlayerIndex);
    }

    /**
     * Helper function for getting the position of a tile on the board.
     *
     * @param position The position of the tile (0-indexed)
     * @param startX Start x position of the board
     * @param startY Start y position of the board
     * @param tileSize Size of each tile
     * @return The (x, y) position of the tile
     */
    public Point getTilePosition(int position, int startX, int startY, int tileSize) {
        final int tilesPerSide = this.tileCount / 4;
        final int positionAdjust = tilesPerSide * tileSize;
        final Point tilePosition;

        if (position >= 0 && position <= tilesPerSide) {
            tilePosition = new Point(startX + position * tileSize, startY + positionAdjust);
        }
        else if (position >= (tilesPerSide + 1) && position <= tilesPerSide * 2) {
            tilePosition = new Point(startX + positionAdjust, startY
                    + positionAdjust - (position - tilesPerSide) * tileSize);
        }
        else if (position >= (tilesPerSide * 2 + 1) && position <= tilesPerSide * ADJUST_THREE) {
            // Top row (right to left)
            tilePosition = new Point(startX + positionAdjust
                    - (position - tilesPerSide * ADJUST_TWO) * tileSize, startY);
        }
        else {
            // Left column (top to bottom)
            tilePosition = new Point(startX, startY + (position - tilesPerSide * ADJUST_THREE) * tileSize);
        }
        return tilePosition;
    }

    /**
     * Gets the property at a specific position.
     *
     * @param position Board position
     * @return The PropertyTile at that position\
     * @throws IndexOutOfBoundsException if the position is invalid
     */
    public AbstractTile getPropertyAt(int position) {
        if (position >= 0 && position < tiles.size()) {
            return tiles.get(position);
        }
        throw new IndexOutOfBoundsException("Invalid tile position: " + position);
    }

    public List<AbstractTile> getTiles() {
        return tiles;
    }

    public List<AbstractPlayer> getPlayers() {
        return players;
    }

    public int getTileCount() {
        return tileCount;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public boolean getGameEnded() {
        return gameEnded;
    }

    /**
     * Increment total turns and track turns in current round.
     */
    public void increaseTurn() {
        totalTurns++;
        turnsInCurrentRound++;
    }

    /**
     * Start a new round - reset turn counter, increment round number, and set new round start player.
     *
     * @param newRoundStartPlayerIndex The index of the player who will start the new round
     * @throws IllegalArgumentException if the newRoundStartPlayerIndex is invalid
     */
    public void startNewRound(int newRoundStartPlayerIndex) {
        // Check if we've reached the maximum number of rounds BEFORE incrementing
        if (currentRound >= Constants.MAX_ROUNDS) {
            endGame("Maximum " + Constants.MAX_ROUNDS + " rounds reached");
        }

        currentRound++;
        turnsInCurrentRound = 0;
        roundStartPlayerIndex = newRoundStartPlayerIndex;
    }

    /**
     * Check if the current round is complete.
     * A round is complete when we've cycled back to the player who started the round
     * and all active players have had at least one turn
     *
     * @param nextPlayerIndex The index of the next player to take a turn
     * @return true if the round is complete, false otherwise
     */
    public boolean isRoundComplete(int nextPlayerIndex) {
        // Round is complete when:
        // 1. We have at least one turn in this round
        // 2. The next player would be the one who started the round (or the next active player after them if they died)

        // Find the next active player from the round start position
        final int expectedRoundStartPlayer = findNextActivePlayerFrom(roundStartPlayerIndex - 1);
        return nextPlayerIndex == expectedRoundStartPlayer;
    }

    /**
     * Find the next active player starting from a given index.
     *
     * @param startIndex The index to start searching from
     * @return The index of the next active player, or -1 if no active players are found
     * @throws IllegalStateException if no active players are found
     */
    public int findNextActivePlayerFrom(int startIndex) {
        for (int i = 1; i <= players.size(); i++) {
            final int candidateIndex = (startIndex + i) % players.size();
            if (!players.get(candidateIndex).isBankrupt()) {
                return candidateIndex;
            }
        }
        throw new IllegalStateException("No active players found in the game");
    }

    /**
     * Set the current player index.
     *
     * @param index The index of the player to set as current
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void setCurrentPlayerIndex(int index) {
        if (index >= 0 && index < players.size()) {
            this.currentPlayerIndex = index;
        }
        else {
            throw new IndexOutOfBoundsException("Invalid player index: " + index);
        }
    }

    /**
     * End the game with an optional message.
     *
     * @param message The reason for ending the game, or null for default message
     */
    public void endGame(String message) {
        this.gameEnded = true;
        if (message == null || message.isEmpty()) {
            this.gameEndReason = "Game Over";
        }
        else {
            this.gameEndReason = message.trim();
        }
    }

    /**
     * Set the tiles, ensuring the list is not null or empty.
     *
     * @param tiles List of AbstractTile objects to set
     * @throws IllegalArgumentException if the tiles list is null or empty
     */
    public void setTiles(List<AbstractTile> tiles) {
        if (tiles != null && !tiles.isEmpty()) {
            this.tiles = tiles;
            this.tileCount = tiles.size();
        }
        else {
            throw new IllegalArgumentException("Tiles list cannot be null or empty");
        }
    }

    /**
     * Set the players, ensuring the list is not null or empty.
     *
     * @param players List of AbstractPlayer objects to set
     * @throws IllegalArgumentException if the players list is null or empty
     */
    public void setPlayers(List<AbstractPlayer> players) {
        if (players != null && !players.isEmpty()) {
            this.players = players;
        }
        else {
            throw new IllegalArgumentException("Players list cannot be null or empty");
        }
    }

    /**
     * Set the stocks, ensuring the list is not null or empty.
     *
     * @param stocks List of Stock objects to set
     * @throws IllegalArgumentException if the stocks list is null or empty
     */
    public void setStocks(List<Stock> stocks) {
        if (stocks != null && !stocks.isEmpty()) {
            this.stocks = stocks;
        }
        else {
            throw new IllegalArgumentException("Stocks list cannot be null or empty");
        }
    }

    /**
     * Set players from a list of CharacterSelectionPlayerViewModel objects.
     * This method creates AbstractPlayer instances based on the view model data.
     *
     * @param playersList List of CharacterSelectionPlayerViewModel objects
     */
    public void setPlayersFromOutputData(List<CharacterSelectionPlayerViewModel> playersList) {
        final List<AbstractPlayer> result = new ArrayList<>();
        for (CharacterSelectionPlayerViewModel data : playersList) {
            if (data != null && !"None".equals(data.getType())) {
                final AbstractPlayer player = CharacterFactory.createPlayer(
                        data.getName(),
                        data.getType(),
                        data.getColor()
                );
                result.add(player);
            }
        }
        this.setPlayers(result);
    }
}
