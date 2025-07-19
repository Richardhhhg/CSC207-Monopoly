package main.app;

import javax.swing.*;
import main.view.BoardView;
import main.Constants.Constants;

import java.awt.*;

public class Main{
    public static void main(String[] args) {
        JFrame frame = new JFrame(Constants.GAME_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        BoardView boardView = new BoardView();
        frame.add(boardView);
        frame.getContentPane().setBackground(Color.lightGray);

        frame.setVisible(true);
    }
}