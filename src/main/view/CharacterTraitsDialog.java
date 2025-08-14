package main.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CharacterTraitsDialog extends JDialog {
    private static final int DIAGSIZEH = 600;
    private static final int DIAGSIZEW = 400;

    public CharacterTraitsDialog(Frame owner, String text) {
        super(owner, "Character Traits", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        final JTextArea area = new JTextArea(text);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        final JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(DIAGSIZEH, DIAGSIZEW));

        final JPanel buttons = new JPanel();
        final JButton close = new JButton("Close");
        close.addActionListener(event -> dispose());
        buttons.add(close);

        add(scroll, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(owner);
    }
}
