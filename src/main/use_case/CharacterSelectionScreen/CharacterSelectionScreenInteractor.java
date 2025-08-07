package main.use_case.CharacterSelectionScreen;

import main.entity.players.*;
import main.interface_adapter.CharacterSelectionScreen.PlayerOutputData;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.Constants.Constants.MAX_NP_BAR;

public class CharacterSelectionScreenInteractor implements CharacterSelectionInputBoundary {
    private final CharacterSelectionScreenOutputBoundary presenter;
    private final CharacterSelectionScreenDataAccessInterface dao;

    private final List<Player> selectedPlayers = new ArrayList<>(
            Arrays.asList(new NullPlayer(), new NullPlayer(), new NullPlayer(), new NullPlayer()));

    public CharacterSelectionScreenInteractor(CharacterSelectionScreenOutputBoundary presenter,
                                        CharacterSelectionScreenDataAccessInterface dao) {
        this.presenter = presenter;
        this.dao = dao;
    }

    @Override
    public void execute(CharacterSelectionInputData inputData) {
        int index = inputData.getIndex();
        String name = inputData.getName();
        String type = inputData.getType();
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
        Color color = colors[index];

        Player player = CharacterFactory.createPlayer(name, type, color);
        selectedPlayers.set(index, player);
    }

    @Override
    public void confirmSelection() {
        List<PlayerOutputData> outputList = new ArrayList<>();
        for (Player p : selectedPlayers) {
            if (!p.isNullPlayer()) {
                outputList.add(new PlayerOutputData(p.getName(), p.getClass().getSimpleName(), p.getColor(), p.getPortrait()));
            }
        }
        presenter.prepareLaunchData(outputList);
    }

    @Override
    public boolean canStartGame() {
        int count = 0;
        for (Player p : selectedPlayers) {
            if (!p.isNullPlayer() && !(p instanceof NullPlayer)) {
                count++;
            }
        }
        return count >= MAX_NP_BAR;
    }

    @Override
    public void selectPlayer(CharacterSelectionInputData inputData) {
        int index = inputData.getIndex();
        String name = inputData.getName();
        String type = inputData.getType();

        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
        Color color = colors[index];

        Player player = CharacterFactory.createPlayer(name, type, color);
        selectedPlayers.set(index, player);
        PlayerOutputData output = new PlayerOutputData(name, type, color, player.getPortrait());
        presenter.preparePlayer(output, index);
    }
}
