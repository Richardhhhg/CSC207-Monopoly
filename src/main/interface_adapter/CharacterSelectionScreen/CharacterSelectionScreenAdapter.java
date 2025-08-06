package main.interface_adapter.CharacterSelectionScreen;

import main.use_case.CharacterSelectionScreen.CharacterSelectionInputBoundary;
import main.use_case.CharacterSelectionScreen.CharacterSelectionScreenInteractor;
import main.use_case.CharacterSelectionScreen.CharacterSelectionScreenOutputBoundary;

public class CharacterSelectionScreenAdapter {

    private final CharacterSelectionScreenController controller;
    private final CharacterSelectionScreenPresenter presenter;

    public CharacterSelectionScreenAdapter() {
        this.presenter = new CharacterSelectionScreenPresenter();
        CharacterSelectionScreenOutputBoundary outputBoundary = presenter;
        CharacterSelectionInputBoundary interactor = new CharacterSelectionScreenInteractor(outputBoundary);
        this.controller = new CharacterSelectionScreenController(interactor);
    }

    public CharacterSelectionScreenController getController() {
        return controller;
    }

    public CharacterSelectionScreenPresenter getPresenter() {
        return presenter;
    }

    public static CharacterSelectionScreenAdapter inject() {
        return new CharacterSelectionScreenAdapter();
    }
}
