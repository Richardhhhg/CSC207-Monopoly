package main.use_case.game;

import main.entity.Game;
import main.entity.players.Player;
import main.entity.players.applyAfterEffects;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GameNextTurnTest {

    static class TestPlayer extends Player implements applyAfterEffects {
        private boolean turnEffectsApplied = false;
        private boolean bankrupt = false;
        private String name;

        public TestPlayer(String name, boolean bankrupt) {
            this.name = name;
            this.bankrupt = bankrupt;
        }

        @Override
        public void applyTurnEffects() {
            turnEffectsApplied = true;
        }

        @Override
        public boolean isBankrupt() {
            return bankrupt;
        }

        @Override
        public String getName() { return name; }

        @Override
        public List<main.entity.tiles.PropertyTile> getProperties() { return new ArrayList<>(); }
    }

    static class TestGame extends Game {
        private boolean gameEnded = false;
        private int currentPlayerIndex = 0;
        private List<Player> players = new ArrayList<>();
        private int turnCount = 0;
        private int nextPlayerIndex = 0;

        public TestGame(List<Player> players) {
            this.players = players;
        }

        @Override
        public List<Player> getPlayers() { return players; }

        @Override
        public boolean getGameEnded() { return gameEnded; }

        @Override
        public int getCurrentPlayerIndex() { return currentPlayerIndex; }

        @Override
        public void increaseTurn() { turnCount++; }

        @Override
        public void setCurrentPlayerIndex(int index) { nextPlayerIndex = index; }

        @Override
        public boolean isRoundComplete(int nextIndex) { return false; }

        @Override
        public int findNextActivePlayerFrom(int index) { return (index + 1) % players.size(); }

        @Override
        public void endGame(String reason) { gameEnded = true; }

        @Override
        public void startNewRound(int nextIndex) {}
    }

    @Test
    void testTurnEffectsAppliedAndBankruptcyChecked() {
        TestPlayer player1 = new TestPlayer("Player1", false);
        TestPlayer player2 = new TestPlayer("Player2", true);
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        TestGame game = new TestGame(players);
        GameNextTurn useCase = new GameNextTurn(game);
        useCase.execute();

        assertTrue(player1.turnEffectsApplied, "Turn effects should be applied for current player");
    }

    @Test
    void testAllPlayersBankruptEndsGame() {
        TestPlayer player1 = new TestPlayer("Player1", true);
        TestPlayer player2 = new TestPlayer("Player2", true);
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        TestGame game = new TestGame(players) {
            @Override
            public int findNextActivePlayerFrom(int index) { return -1; }
        };
        GameNextTurn useCase = new GameNextTurn(game);
        useCase.execute();

        assertTrue(game.getGameEnded(), "Game should end if all players are bankrupt");
    }
}