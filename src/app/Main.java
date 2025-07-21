package app;

import javax.swing.*;
import view.BoardView;
import Constants.Constants;
import view.StartScreen;
import java.awt.*;

public class Main{
    public static void main(String[] args) {
        new StartScreen();
    }

    public static void startGame() {
        JFrame frame = new JFrame(Constants.GAME_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        BoardView boardView = new BoardView();
        frame.add(boardView);
        frame.getContentPane().setBackground(Color.lightGray);
        frame.setVisible(true);
    }
}