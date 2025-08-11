package main.view;

import main.interface_adapter.boardSizeSelection.BoardSizeController;
import main.interface_adapter.boardSizeSelection.BoardSizeViewModel;
import main.use_case.boardSizeSelection.BoardSizeSelection.BoardSize;

import javax.swing.*;
import java.awt.*;

/**
 * A panel for board size selection.
 */
public class BoardSizeSelectionPanelView extends JPanel {
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

        JLabel instructionLabel = new JLabel("Select board size:");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(instructionLabel);
        add(Box.createVerticalStrut(10));

        JPanel buttonPanel = new JPanel(new FlowLayout());

        smallButton = new JButton(viewModel.getSmallButtonText());
        mediumButton = new JButton(viewModel.getMediumButtonText());
        largeButton = new JButton(viewModel.getLargeButtonText());

        // Set initial selection (medium is default)
        updateButtonSelection(viewModel.getSelectedBoardSize());

        smallButton.addActionListener(e -> {
            controller.selectBoardSize(BoardSize.SMALL);
            updateButtonSelection(BoardSize.SMALL);
        });

        mediumButton.addActionListener(e -> {
            controller.selectBoardSize(BoardSize.MEDIUM);
            updateButtonSelection(BoardSize.MEDIUM);
        });

        largeButton.addActionListener(e -> {
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

        // Highlight selected button
        Color selectedColor = new Color(173, 216, 230); // Light blue
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
        }

        // Make buttons opaque so background color shows
        smallButton.setOpaque(true);
        mediumButton.setOpaque(true);
        largeButton.setOpaque(true);
    }

    /**
     * Get the currently selected board size from the view model.
     */
    public BoardSize getSelectedBoardSize() {
        return viewModel.getSelectedBoardSize();
    }
}

