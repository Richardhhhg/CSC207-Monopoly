package main.view;

import main.interface_adapter.start.StartGameController;
import main.interface_adapter.start.StartGamePresenter;
import main.use_case.start.StartGameInteractor;
import main.use_case.start.StartGameInputBoundary;

import main.interface_adapter.rules.ShowRulesController;
import main.interface_adapter.rules.ShowRulesPresenter;
import main.use_case.rules.ShowRulesInteractor;
import main.use_case.rules.ShowRulesInputBoundary;

import javax.swing.*;
import java.awt.*;

/**
 * Refactored StartScreen using Clean Architecture for both
 * “Start Game” and “Show Rules” actions.  No layout or text has changed.
 */
public class StartScreen extends JFrame {
    private final StartGameController startGameController;
    private final ShowRulesController showRulesController;

    public StartScreen() {
        setTitle("Monopoly Game - Start");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Start Game Clean‑Architecture stack
        StartGamePresenter sgPresenter = new StartGamePresenter(this);
        StartGameInputBoundary sgInteractor = new StartGameInteractor(sgPresenter);
        this.startGameController = new StartGameController(sgInteractor);

        // Show Rules Clean‑Architecture stack
        ShowRulesPresenter srPresenter = new ShowRulesPresenter(this);
        ShowRulesInputBoundary srInteractor = new ShowRulesInteractor(srPresenter);
        this.showRulesController = new ShowRulesController(srInteractor);

        // Build UI (unchanged layout/text)
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Welcome to our Monopoly!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        panel.add(title, BorderLayout.CENTER);
        panel.add(createButtonPanel(), BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private JPanel createButtonPanel() {
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.addActionListener(e -> startGameController.startGame());

        JButton rulesButton = new JButton("Rules");
        rulesButton.setFont(new Font("Arial", Font.PLAIN, 16));
        rulesButton.addActionListener(e -> showRulesController.showRules());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(startButton);
        buttonPanel.add(rulesButton);
        return buttonPanel;
    }
}
