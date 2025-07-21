package main.app;

import javax.swing.*;

import main.entity.Stock;
import main.view.BoardView;
import main.Constants.Constants;
import main.view.GameView;
import main.view.StockMarketView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main{
    public static void main(String[] args) {
        GameView game = new GameView();
        game.addBoard();
        game.showStockMarket();
    }
}