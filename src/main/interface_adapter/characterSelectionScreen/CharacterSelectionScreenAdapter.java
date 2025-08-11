package main.interface_adapter.characterSelectionScreen;

import main.data_access.StockMarket.InMemoryCharacterDataAccess;
import main.use_case.characterSelectionScreen.CharacterSelectionInputBoundary;
import main.use_case.characterSelectionScreen.CharacterSelectionScreenDataAccessInterface;
import main.use_case.characterSelectionScreen.CharacterSelectionScreenInteractor;

/**
 * Adapter class responsible for creating and injecting the dependencies (controller and view model)
 * required by the CharacterSelectionScreen. This ensures the correct wiring of presentation and use case layers.
 */
public class CharacterSelectionScreenAdapter {
    private final CharacterSelectionScreenController controller;
    private final CharacterSelectionScreenViewModel viewModel;

    /**
     * Constructs an adapter with a controller and view model.
     *
     * @param controller The controller for the character selection screen.
     * @param viewModel  The view model for the character selection screen.
     */
    public CharacterSelectionScreenAdapter(CharacterSelectionScreenController controller,
                                           CharacterSelectionScreenViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
    }

    /**
     * Performs dependency injection and returns a bundle containing the controller and view model
     * for use in the CharacterSelectionScreen.
     *
     * @return A CharacterSelectionScreenAdapterBundle with wired dependencies.
     */
    public static CharacterSelectionScreenAdapterBundle inject() {
        final CharacterSelectionScreenViewModel viewModel = new CharacterSelectionScreenViewModel();
        final CharacterSelectionScreenPresenter presenter = new CharacterSelectionScreenPresenter(viewModel);
        final CharacterSelectionScreenDataAccessInterface dao = new InMemoryCharacterDataAccess();
        final CharacterSelectionInputBoundary interactor = new CharacterSelectionScreenInteractor(presenter, dao);
        final CharacterSelectionScreenController controller = new CharacterSelectionScreenController(interactor);

        return new CharacterSelectionScreenAdapterBundle(controller, viewModel);
    }
}
