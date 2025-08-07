package main.interface_adapter.CharacterSelectionScreen;

import main.data_access.StockMarket.InMemoryCharacterDataAccess;
import main.use_case.CharacterSelectionScreen.CharacterSelectionInputBoundary;
import main.use_case.CharacterSelectionScreen.CharacterSelectionScreenDataAccessInterface;
import main.use_case.CharacterSelectionScreen.CharacterSelectionScreenInteractor;

public class CharacterSelectionScreenAdapter {
    public final CharacterSelectionScreenController controller;
    public final CharacterSelectionScreenViewModel viewModel;

    public CharacterSelectionScreenAdapter(CharacterSelectionScreenController controller, CharacterSelectionScreenViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
    }

    public static CharacterSelectionScreenAdapterBundle inject() {
        CharacterSelectionScreenViewModel viewModel = new CharacterSelectionScreenViewModel();
        CharacterSelectionScreenPresenter presenter = new CharacterSelectionScreenPresenter(viewModel);
        CharacterSelectionScreenDataAccessInterface dao = new InMemoryCharacterDataAccess();
        CharacterSelectionInputBoundary interactor = new CharacterSelectionScreenInteractor(presenter, dao);
        CharacterSelectionScreenController controller = new CharacterSelectionScreenController(interactor);

        return new CharacterSelectionScreenAdapterBundle(controller, viewModel);
    }

}


