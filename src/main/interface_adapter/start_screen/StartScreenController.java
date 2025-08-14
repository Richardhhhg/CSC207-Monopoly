package main.interface_adapter.start_screen;

import main.use_case.start_screen.StartScreenInputBoundary;

public class StartScreenController {
    private final StartScreenInputBoundary startScreenInputBoundary;

    public StartScreenController(StartScreenInputBoundary startScreenInputBoundary) {
        this.startScreenInputBoundary = startScreenInputBoundary;
    }

    /**
     * Executes the start screen use case.
     */
    public void execute() {
        startScreenInputBoundary.execute();
    }
}
