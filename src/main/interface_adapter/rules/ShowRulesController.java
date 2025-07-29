package main.interface_adapter.rules;

import main.use_case.rules.ShowRulesInputBoundary;

/**
 * Controller for the Show Rules use case.
 */
public class ShowRulesController {
    private final ShowRulesInputBoundary interactor;

    public ShowRulesController(ShowRulesInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void showRules() {
        interactor.showRules();
    }
}
