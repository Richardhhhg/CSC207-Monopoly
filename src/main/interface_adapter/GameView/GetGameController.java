package main.interface_adapter.GameView;

import main.use_case.GameView.GetGame;

public class GetGameController {
    private final GetGame interactor;

    public GetGameController(GetGame interactor) {
        this.interactor = interactor;
    }
}
