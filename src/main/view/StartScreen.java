package main.view;

import main.app.Main;
import main.interface_adapter.StartScreen.StartScreenController;
import main.interface_adapter.StartScreen.StartScreenPresenter;
import main.interface_adapter.StartScreen.StartScreenViewModel;
import main.use_case.StartScreen.StartGame;

import javax.swing.*;
import java.awt.*;

public class StartScreen extends JFrame {
    private final StartScreenController controller;
    private StartScreenViewModel viewModel;

    public StartScreen() {
        this.controller = new StartScreenController();
        initializeScreen();
    }

    private void initializeScreen() {
        // Get data through controller and presenter
        StartGame.StartGameResult result = controller.execute();
        StartScreenPresenter presenter = new StartScreenPresenter();
        viewModel = presenter.execute(result);

        setTitle("Monopoly Game - Start");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel title = new JLabel(viewModel.getWelcomeMessage(), SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        panel.add(title, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);
        setVisible(true);
    }

    private JPanel createButtonPanel() {
        JButton startButton = new JButton(viewModel.getStartButtonText());
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.addActionListener(e -> {
            dispose();
            new Main().startGame();
        });

        JButton rulesButton = new JButton(viewModel.getRulesButtonText());
        rulesButton.setFont(new Font("Arial", Font.PLAIN, 16));
        rulesButton.addActionListener(e -> showRules());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(startButton);
        buttonPanel.add(rulesButton);
        return buttonPanel;
    }

    private void showRules() {
        JOptionPane.showMessageDialog(this, viewModel.getRules(), "Game Rules", JOptionPane.INFORMATION_MESSAGE);
    }
}