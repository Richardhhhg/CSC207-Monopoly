package main.use_case;

import main.entity.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static main.Constants.Constants.FINISH_LINE_BONUS;

public class PlayerManager {
    private final ArrayList<Player> players;
    private int currentPlayerIndex;

    public PlayerManager(ArrayList<Player> players) {
        this.players = players;
        this.currentPlayerIndex = 0;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        if (players.isEmpty()) return null;
        return players.get(currentPlayerIndex);
    }

    public Image getCurrentPortrait() {
        return getCurrentPlayer().getPortrait();
    }
    public void advanceTurn() {
        getCurrentPlayer().applyTurnEffects();

        int start = currentPlayerIndex;
        for (int i = 1; i <= players.size(); i++) {
            int next = (start + i) % players.size();
            if (!players.get(next).isBankrupt()) {
                currentPlayerIndex = next;
                return;
            }
        }
    }

    public boolean isGameOver() {
        for (Player player : players) {
            if (!player.isBankrupt()) {
                return false;
            }
        }
        return true;
    }

    public void moveCurrentPlayer(int steps, int tileCount) {
        Player currentPlayer = getCurrentPlayer();
        if (currentPlayer.getPosition() + steps >= tileCount) {
            currentPlayer.addMoney(FINISH_LINE_BONUS);
        }
        // Note: Actual position update happens in animation
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int index) {
        if (index >= 0 && index < players.size()) {
            currentPlayerIndex = index;
        }
    }
}
