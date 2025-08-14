package main.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.interface_adapter.board_size_selection.BoardSizeController;
import main.interface_adapter.board_size_selection.BoardSizeViewModel;
import main.use_case.board_size_selection.BoardSizeSelection.BoardSize;

/**
 * A panel for board size selection.
 */
public class BoardSizeSelectionPanelView extends JPanel {
    
    private static final int VERTICAL_SPACING = 10;
    private static final int RED = 173;
    private static final int GREEN = 216;
    private static final int BLUE = 230;
    
    private final BoardSizeController controller;
    private final BoardSizeViewModel viewModel;

    private JButton smallButton;
    private JButton mediumButton;
    private JButton largeButton;

    public BoardSizeSelectionPanelView(BoardSizeController controller, BoardSizeViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Board Size"));

        final JLabel instructionLabel = new JLabel("Select board size:");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(instructionLabel);
        add(Box.createVerticalStrut(VERTICAL_SPACING));

        final JPanel buttonPanel = new JPanel(new FlowLayout());

        smallButton = new JButton(viewModel.getSmallButtonText());
        mediumButton = new JButton(viewModel.getMediumButtonText());
        largeButton = new JButton(viewModel.getLargeButtonText());

        // Set initial selection (medium is default)
        updateButtonSelection(viewModel.getSelectedBoardSize());

        smallButton.addActionListener(actionEvent -> {
            controller.selectBoardSize(BoardSize.SMALL);
            updateButtonSelection(BoardSize.SMALL);
        });

        mediumButton.addActionListener(actionEvent -> {
            controller.selectBoardSize(BoardSize.MEDIUM);
            updateButtonSelection(BoardSize.MEDIUM);
        });

        largeButton.addActionListener(actionEvent -> {
            controller.selectBoardSize(BoardSize.LARGE);
            updateButtonSelection(BoardSize.LARGE);
        });

        buttonPanel.add(smallButton);
        buttonPanel.add(mediumButton);
        buttonPanel.add(largeButton);

        add(buttonPanel);
    }

    private void updateButtonSelection(BoardSize selectedSize) {
        // Reset all buttons
        smallButton.setBackground(null);
        mediumButton.setBackground(null);
        largeButton.setBackground(null);

        // Highlight selected button with a light blue color
        final Color selectedColor = new Color(RED, GREEN, BLUE);
        switch (selectedSize) {
            case SMALL:
                smallButton.setBackground(selectedColor);
                break;
            case MEDIUM:
                mediumButton.setBackground(selectedColor);
                break;
            case LARGE:
                largeButton.setBackground(selectedColor);
                break;
            default:
                // No default selection needed, but required for checkstyle
                break;
        }

        // Make buttons opaque so background color shows
        smallButton.setOpaque(true);
        mediumButton.setOpaque(true);
        largeButton.setOpaque(true);
    }

    /**
     * Get the currently selected board size from the view model.
     * @return the selected board size
     */
    public BoardSize getSelectedBoardSize() {
        return viewModel.getSelectedBoardSize();
    }
}
