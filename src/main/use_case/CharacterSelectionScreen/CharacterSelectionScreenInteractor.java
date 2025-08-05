package main.use_case.CharacterSelectionScreen;

import main.entity.players.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharacterSelectionScreenInteractor implements CharacterSelectionInputBoundary{
    private final CharacterSelectionScreenOutputBoundary presenter;
    private final List<Player> selectPlayers = new ArrayList<>(Arrays.asList(new NullPlayer(),
            new NullPlayer(),
            new NullPlayer(),
            new NullPlayer()));

    public CharacterSelectionScreenInteractor(CharacterSelectionScreenOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void selectPlayer(int index, Player player) {
        this.selectPlayers.set(index, player);
    }

    @Override
    public void confirmSelection() {
        List<Player> realPlayers = new ArrayList<>(); // real list of charaters
        for (Player player : selectPlayers) {
            if (!player.isNullPlayer()) {
                realPlayers.add(player);
            }
        }
        presenter.launchGame(realPlayers);
    }

    @Override
    public boolean canStartGame() {
        int numOfNullPlayers = 0;
        for  (Player player : selectPlayers) {
            if (player.isNullPlayer()) {
                numOfNullPlayers++;
            }
        }
        if (numOfNullPlayers < 3) {
            return true;
        }
        return false;
    }

    @Override
    public Player selectPlayer(int index, String name, String type) {
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
        Color color = colors[index];
        return switch (type) {
            case "Clerk" -> new clerk(name, color);
            case "Landlord" -> new landlord(name, color);
            case "Inheritor" -> new inheritor(name, color);
            case "College Student" -> new collegeStudent(name, color);
            case "Poor Man" -> new PoorMan(name, color);
            default -> new NullPlayer();
        };
    }
}