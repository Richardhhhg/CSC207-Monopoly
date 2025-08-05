package main.interface_adapter.CharacterSelectionScreen;

import main.use_case.CharacterSelectionScreen.CharacterSelectionInputBoundary;
import main.use_case.CharacterSelectionScreen.CharacterSelectionScreenInteractor;
import main.use_case.CharacterSelectionScreen.CharacterSelectionScreenOutputBoundary;
import main.use_case.CharacterSelectionScreen.GameLauncher;

public class CharacterSelectionScreenAdapter {

    private final CharacterSelectionScreenController controller;

    public CharacterSelectionScreenAdapter(CharacterSelectionInputBoundary interactor) {
        this.controller = new CharacterSelectionScreenController(interactor);
    }

    public static CharacterSelectionScreenController inject() {
        CharacterSelectionScreenOutputBoundary presenter = new CharacterSelectionScreenPresenter(new GameLauncher());
        CharacterSelectionInputBoundary interactor = new CharacterSelectionScreenInteractor(presenter);
        return new CharacterSelectionScreenController(interactor);
    }
}