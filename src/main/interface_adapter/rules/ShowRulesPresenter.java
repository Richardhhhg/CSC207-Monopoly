package main.interface_adapter.rules;

import main.use_case.rules.ShowRulesOutputBoundary;
import javax.swing.JOptionPane;
import java.awt.Component;

/**
 * Presenter for the Show Rules use case.
 * Shows the rules dialog when called.
 */
public class ShowRulesPresenter implements ShowRulesOutputBoundary {
    private final Component parent;

    public ShowRulesPresenter(Component parent) {
        this.parent = parent;
    }

    @Override
    public void presentRules(String rules) {
        JOptionPane.showMessageDialog(parent, rules, "Game Rules", JOptionPane.INFORMATION_MESSAGE);
    }
}
