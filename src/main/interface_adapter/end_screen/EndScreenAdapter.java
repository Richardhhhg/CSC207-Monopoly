package main.interface_adapter.end_screen;

import main.use_case.end_screen.EndScreenInputBoundary;
import main.use_case.end_screen.EndScreenInteractor;

/**
 * Adapter class responsible for creating and injecting the dependencies (controller and view model)
 * required by the EndScreen. This ensures the correct wiring of presentation and use case layers.
 */
public class EndScreenAdapter {
    private final EndScreenController controller;
    private final EndScreenViewModel viewModel;

    /**
     * Constructs an adapter with a controller and view model.
     *
     * @param controller The controller for the end screen.
     * @param viewModel  The view model for the end screen.
     */
    public EndScreenAdapter(EndScreenController controller, EndScreenViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
    }

    /**
     * Performs dependency injection and returns a bundle containing the controller and view model
     * for use in the EndScreen.
     *
     * @return An EndScreenAdapterBundle with wired dependencies.
     */
    public static EndScreenAdapterBundle inject() {
        final EndScreenViewModel viewModel = new EndScreenViewModel();
        final EndScreenPresenter presenter = new EndScreenPresenter(viewModel);
        final EndScreenInputBoundary interactor = new EndScreenInteractor(presenter);
        final EndScreenController controller = new EndScreenController(interactor);

        return new EndScreenAdapterBundle(controller, viewModel);
    }
}
