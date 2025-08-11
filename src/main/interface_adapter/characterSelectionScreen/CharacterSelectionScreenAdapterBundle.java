package main.interface_adapter.characterSelectionScreen;

/**
 * A bundle class that holds the controller and view model needed for character selection screen injection.
 * Used to facilitate dependency injection for the CharacterSelectionScreen.
 */
public class CharacterSelectionScreenAdapterBundle {
    private final CharacterSelectionScreenController controller;
    private final CharacterSelectionScreenViewModel viewModel;

    /**
     * Constructs a CharacterSelectionScreenAdapterBundle.
     *
     * @param controller The controller for the character selection screen.
     * @param viewModel  The view model for the character selection screen.
     */
    public CharacterSelectionScreenAdapterBundle(CharacterSelectionScreenController controller,
                                                 CharacterSelectionScreenViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
    }

    /**
     * Returns the controller for the character selection screen.
     *
     * @return The CharacterSelectionScreenController.
     */
    public CharacterSelectionScreenController getController() {
        return this.controller;
    }

    /**
     * Returns the view model for the character selection screen.
     *
     * @return The CharacterSelectionScreenViewModel.
     */
    public CharacterSelectionScreenViewModel getViewModel() {
        return viewModel;
    }
}
