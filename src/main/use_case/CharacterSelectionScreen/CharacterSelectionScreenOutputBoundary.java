package main.use_case.CharacterSelectionScreen;

import java.util.List;

public interface CharacterSelectionScreenOutputBoundary {
    void prepareLaunchData(List<String> names, List<String> types, List<String> colors);
}