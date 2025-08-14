package main.interface_adapter.end_screen;

/**
 * A bundle class that holds the controller and view model needed for end screen injection.
 * Used to facilitate dependency injection for the EndScreen.
 */
public class EndScreenAdapterBundle {
    private final EndScreenController controller;
    private final EndScreenViewModel viewModel;

    /**
     * Constructs an EndScreenAdapterBundle.
     *
     * @param controller The controller for the end screen.
     * @param viewModel  The view model for the end screen.
     */
    public EndScreenAdapterBundle(EndScreenController controller, EndScreenViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
    }

    /**
     * Returns the controller for the end screen.
     *
     * @return The EndScreenController.
     */
    public EndScreenController getController() {
        return this.controller;
    }

    /**
     * Returns the view model for the end screen.
     *
     * @return The EndScreenViewModel.
     */
    public EndScreenViewModel getViewModel() {
        return viewModel;
    }
}
