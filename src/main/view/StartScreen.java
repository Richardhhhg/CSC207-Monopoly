package main.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.Constants.Constants;
import main.app.Main;
import main.interface_adapter.start_screen.StartScreenController;
import main.interface_adapter.start_screen.StartScreenPresenter;
import main.interface_adapter.start_screen.StartScreenViewModel;
import main.use_case.start_screen.StartGame;

/**
 * Main frame that displays the start screen for the Monopoly game.
 */
public class StartScreen extends JFrame {
    private static final int WIDTH = Constants.START_SCREEN_WIDTH;
    private static final int HEIGHT = Constants.START_SCREEN_HEIGHT;
    private static final String FONT_FAMILY = Constants.UI_FONT_FAMILY;
    private static final int TITLE_FONT_STYLE = Font.BOLD;
    private static final int TITLE_FONT_SIZE = Constants.TITLE_FONT_SIZE;
    private static final int BUTTON_FONT_STYLE = Font.PLAIN;
    private static final int START_BUTTON_FONT_SIZE = Constants.START_BUTTON_FONT_SIZE;
    private static final int RULES_BUTTON_FONT_SIZE = Constants.RULES_BUTTON_FONT_SIZE;

    private final StartScreenController controller;
    private StartScreenViewModel viewModel;

    /**
     * Constructs the start screen frame and initializes its contents.
     */
    public StartScreen() {
        this.controller = new StartScreenController();
        initializeScreen();
    }

    private void initializeScreen() {
        final StartGame.StartGameResult result = controller.execute();
        final StartScreenPresenter presenter = new StartScreenPresenter();
        viewModel = presenter.execute(result);

        setTitle("Monopoly Game - Start");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        final JLabel titleLabel =
                new JLabel(viewModel.getWelcomeMessage(), SwingConstants.CENTER);
        titleLabel.setFont(new Font(FONT_FAMILY, TITLE_FONT_STYLE, TITLE_FONT_SIZE));
        panel.add(titleLabel, BorderLayout.CENTER);

        final JPanel buttonPanel = createButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private JPanel createButtonPanel() {
        final JButton startButton = new JButton(viewModel.getStartButtonText());
        startButton.setFont(
                new Font(FONT_FAMILY, BUTTON_FONT_STYLE, START_BUTTON_FONT_SIZE));
        startButton.addActionListener(actionEvent -> {
            dispose();
            new CharacterSelectionScreen();
        });

        final JButton rulesButton = new JButton(viewModel.getRulesButtonText());
        rulesButton.setFont(
                new Font(FONT_FAMILY, BUTTON_FONT_STYLE, RULES_BUTTON_FONT_SIZE)
        );
        rulesButton.addActionListener(actionEvent -> showRules());

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(startButton);
        buttonPanel.add(rulesButton);
        return buttonPanel;
    }

    private void showRules() {
        JOptionPane.showMessageDialog(
                this,
                viewModel.getRules(),
                "Game Rules",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
