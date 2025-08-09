package main.interface_adapter.board;

import main.interface_adapter.ViewModel;

public class BoardViewModel extends ViewModel<BoardState> {

    public BoardViewModel() {
        super("Board");
        setState(new BoardState());
    }
}
