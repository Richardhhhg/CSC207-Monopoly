package main.interface_adapter.CharacterSelectionScreen;

import main.data_access.StockMarket.InMemoryCharacterDataAccess;
import main.use_case.CharacterSelectionScreen.CharacterSelectionInputBoundary;
import main.use_case.CharacterSelectionScreen.CharacterSelectionScreenDataAccessInterface;
import main.use_case.CharacterSelectionScreen.CharacterSelectionScreenInteractor;

public class CharacterSelectionScreenAdapter {
    private final CharacterSelectionScreenController controller;
    private final CharacterSelectionScreenViewModel viewModel;

    public CharacterSelectionScreenAdapter(CharacterSelectionScreenController controller,
                                           CharacterSelectionScreenViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @return CharacterSelectionScreenAdapterBundle.
     */
    public static CharacterSelectionScreenAdapterBundle inject() {
        CharacterSelectionScreenViewModel viewModel = new CharacterSelectionScreenViewModel();
        CharacterSelectionScreenPresenter presenter = new CharacterSelectionScreenPresenter(viewModel);
        CharacterSelectionScreenDataAccessInterface dao = new InMemoryCharacterDataAccess();
        CharacterSelectionInputBoundary interactor = new CharacterSelectionScreenInteractor(presenter, dao);
        CharacterSelectionScreenController controller = new CharacterSelectionScreenController(interactor);

        return new CharacterSelectionScreenAdapterBundle(controller, viewModel);
    }
}
