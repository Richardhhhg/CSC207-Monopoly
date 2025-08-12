package main.entity;

import static main.constants.Constants.MAX_ROUNDS;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import main.entity.stocks.Stock;
import main.entity.players.CharacterFactory;
import main.entity.players.Player;
import main.entity.tiles.Tile;
import main.interface_adapter.characterSelectionScreen.CharacterSelectionPlayerViewModel;


public class Game {
    private List<Tile> tiles;
    private List<Player> players;
    private List<Stock> stocks;
    private int currentPlayerIndex = 0;
    private int tileCount;
    private int totalTurns = 0;
    private int currentRound = 1;
    private int turnsInCurrentRound = 0;
    private int roundStartPlayerIndex = 0; // Track which player started the current round
    private boolean gameEnded = false;
    private String gameEndReason = "";

    public boolean isGameOver() {
        return gameEnded;
    }

    public String getGameEndReason() { return gameEndReason; }

    /**
     * Get the current round number (1-based)
     */
    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * Get the number of active (non-bankrupt) players
     */
    public int getActivePlayers() {
        return (int) players.stream().filter(p -> !p.isBankrupt()).count();
    }

    /**
     * Get turns completed in the current round
     */
    public int getTurnsInCurrentRound() {
        return turnsInCurrentRound;
    }

    public int getTotalTurns() {
        return totalTurns;
    }

    public Player getCurrentPlayer() {
        if (currentPlayerIndex == -1 || gameEnded) return null;
        return players.get(currentPlayerIndex);
    }

    /**
     * Helper function for getting the position of a tile on the board
     * @param position
     * @param startX
     * @param startY
     * @param tileSize
     * @return
     */
    public Point getTilePosition(int position, int startX, int startY, int tileSize) {
        int tilesPerSide = this.tileCount / 4;
        int cool_number = tilesPerSide * tileSize;

        if (position >= 0 && position <= tilesPerSide) {
            // Bottom row (left to right)
            return new Point(startX + position * tileSize, startY + cool_number);
        } else if (position >= (tilesPerSide+1) && position <= tilesPerSide*2) {
            // Right column (bottom to top)
            return new Point(startX + cool_number, startY + cool_number - (position - tilesPerSide) * tileSize);
        } else if (position >= (tilesPerSide*2 + 1) && position <= tilesPerSide*3) {
            // Top row (right to left)
            return new Point(startX + cool_number - (position - tilesPerSide*2) * tileSize, startY);
        } else {
            // Left column (top to bottom)
            return new Point(startX, startY + (position - tilesPerSide*3) * tileSize);
        }
    }

    /**
     * Gets the property at a specific position
     * @param position Board position
     * @return The PropertyTile at that position
     */
    public Tile getPropertyAt(int position) {
        if (position >= 0 && position < tiles.size()) {
            return tiles.get(position);
        }
        return null;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Player> getPlayers() {
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

    public int getRoundStartPlayerIndex() {
        return roundStartPlayerIndex;
    }

    public boolean getGameEnded() {
        return gameEnded;
    }

    /**
     * Increment total turns and track turns in current round
     */
    public void increaseTurn() {
        totalTurns++;
        turnsInCurrentRound++;
    }

    /**
     * Start a new round - reset turn counter, increment round number, and set new round start player
     */
    public void startNewRound(int newRoundStartPlayerIndex) {
        // Check if we've reached the maximum number of rounds BEFORE incrementing
        if (currentRound >= MAX_ROUNDS) {
            endGame("Maximum " + MAX_ROUNDS + " rounds reached");
            return;
        }

        currentRound++;
        turnsInCurrentRound = 0;
        roundStartPlayerIndex = newRoundStartPlayerIndex;
    }

    /**
     * Check if the current round is complete
     * A round is complete when we've cycled back to the player who started the round
     * and all active players have had at least one turn
     */
    public boolean isRoundComplete(int nextPlayerIndex) {
        // Round is complete when:
        // 1. We have at least one turn in this round
        // 2. The next player would be the one who started the round (or the next active player after them if they died)
        if (turnsInCurrentRound == 0) {
            return false;
        }

        // Find the next active player from the round start position
        int expectedRoundStartPlayer = findNextActivePlayerFrom(roundStartPlayerIndex - 1);
        return nextPlayerIndex == expectedRoundStartPlayer;
    }

    /**
     * Find the next active player starting from a given index
     */
    public int findNextActivePlayerFrom(int startIndex) {
        for (int i = 1; i <= players.size(); i++) {
            int candidateIndex = (startIndex + i) % players.size();
            if (!players.get(candidateIndex).isBankrupt()) {
                return candidateIndex;
            }
        }
        return -1; // No active players found
    }

    public void setCurrentPlayerIndex(int index) {
        if (index >= 0 && index < players.size()) {
            this.currentPlayerIndex = index;
        } else {
            throw new IndexOutOfBoundsException("Invalid player index: " + index);
        }
    }

    public void endGame(String message) {
        this.gameEnded = true;
        this.gameEndReason = message != null && !message.isEmpty() ? message : "Game Over";
    }

    public void setTiles(List<Tile> tiles) {
        if (tiles != null && !tiles.isEmpty()) {
            this.tiles = tiles;
            this.tileCount = tiles.size();
        } else {
            throw new IllegalArgumentException("Tiles list cannot be null or empty");
        }
    }

    public void setPlayers(List<Player> players) {
        if (players != null && !players.isEmpty()) {
            this.players = players;
        } else {
            throw new IllegalArgumentException("Players list cannot be null or empty");
        }
    }

    public void setStocks(List<Stock> stocks) {
        if (stocks != null && !stocks.isEmpty()) {
            this.stocks = stocks;
        } else {
            throw new IllegalArgumentException("Stocks list cannot be null or empty");
        }
    }

    public void setPlayersFromOutputData(List<CharacterSelectionPlayerViewModel> players) {
        List<Player> result = new ArrayList<>();
        for (CharacterSelectionPlayerViewModel data : players) {
            if (data != null && !"None".equals(data.getType())) {
                Player player = CharacterFactory.createPlayer(
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
