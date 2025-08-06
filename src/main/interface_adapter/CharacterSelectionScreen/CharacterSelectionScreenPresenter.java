package main.interface_adapter.CharacterSelectionScreen;

import main.use_case.CharacterSelectionScreen.CharacterSelectionScreenOutputBoundary;
import main.use_case.CharacterSelectionScreen.PlayerOutputData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterSelectionScreenPresenter implements CharacterSelectionScreenOutputBoundary {
    private GameLaunchOutputData launchOutputData;

    @Override
    public void prepareLaunchData(List<PlayerOutputData> selectedPlayers) {
        List<String> names = new ArrayList<>();
        List<String> types = new ArrayList<>();
        List<String> colors = new ArrayList<>();

        for (PlayerOutputData p : selectedPlayers) {
            names.add(p.getName());
            types.add(p.getType());
            colors.add(toHex(p.getColor()));
        }

        this.launchOutputData = new GameLaunchOutputData(names, types, colors);
    }

    public GameLaunchOutputData getLaunchOutputData() {
        return launchOutputData;
    }

    private String toHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
}
