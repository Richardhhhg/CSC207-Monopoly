package main.use_case.CharacterSelectionScreen;

import main.entity.players.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.Constants.Constants.MAX_NP_BAR;

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
        List<PlayerOutputData> outputDataList = new ArrayList<>();
        for (Player p : selectPlayers) {
            if (!p.isNullPlayer()) {
                String name = p.getName();
                String type = p.getClass().getSimpleName();
                Color color = p.getColor();
                outputDataList.add(new PlayerOutputData(name, type, color));
            }
        }
        presenter.prepareLaunchData(outputDataList);
    }

    @Override
    public boolean canStartGame() {
        int numOfNullPlayers = 0;
        for  (Player player : selectPlayers) {
            if (player.isNullPlayer()) {
                numOfNullPlayers++;
            }
        }
        return numOfNullPlayers < MAX_NP_BAR;
    }

    @Override
    public Player selectPlayer(int index, String name, String type) {
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
        Color color = colors[index];
        return switch (type) {
            case "Clerk" -> new Clerk(name, color);
            case "Landlord" -> new Landlord(name, color);
            case "Inheritor" -> new Inheritor(name, color);
            case "College Student" -> new CollegeStudent(name, color);
            case "Poor Man" -> new PoorMan(name, color);
            default -> new NullPlayer();
        };
    }
}