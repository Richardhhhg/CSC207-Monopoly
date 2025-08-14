package main.use_case.character_selection_screen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.constants.Constants;
import main.entity.players.AbstractPlayer;
import main.entity.players.CharacterFactory;
import main.entity.players.NullPlayer;

/**
 * Interactor for the character selection use case.
 * Handles business logic for selecting and validating players.
 */
public class CharacterSelectionScreenInteractor implements CharacterSelectionInputBoundary {
    private final CharacterSelectionScreenOutputBoundary presenter;
    private final CharacterSelectionScreenDataAccessInterface dao;

    private final List<AbstractPlayer> selectedPlayers = new ArrayList<>(
            Arrays.asList(new NullPlayer(), new NullPlayer(), new NullPlayer(), new NullPlayer()));

    /**
     * Constructs the interactor with the given presenter and DAO.
     *
     * @param presenter Output boundary for presenting results.
     * @param dao       Data access object for character data.
     */
    public CharacterSelectionScreenInteractor(CharacterSelectionScreenOutputBoundary presenter,
                                              CharacterSelectionScreenDataAccessInterface dao) {
        this.presenter = presenter;
        this.dao = dao;
    }

    /**
     * Finalizes the character selection process, triggering preparation of launch data.
     */
    @Override
    public void confirmSelection() {
        final List<CharacterSelectionPlayerViewModel> outputList = new ArrayList<>();
        for (AbstractPlayer p : selectedPlayers) {
            if (!p.isNullPlayer()) {
                outputList.add(new CharacterSelectionPlayerViewModel(p.getName(), p.getClass().getSimpleName(),
                        p.getColor(), p.getPortrait()));
            }
        }
        presenter.prepareLaunchData(outputList);
    }

    /**
     * Checks if the current selection state meets the minimum player requirement.
     *
     * @return true if enough players are selected, false otherwise.
     */
    @Override
    public boolean canStartGame() {
        int count = 0;
        for (AbstractPlayer p : selectedPlayers) {
            if (!p.isNullPlayer() && !(p instanceof NullPlayer)) {
                count++;
            }
        }
        return count >= Constants.MAX_NP_BAR;
    }

    /**
     * Sets a player in the selection list and notifies the presenter to update the UI.
     *
     * @param inputData The player's index, name, and type.
     */
    @Override
    public void execute(CharacterSelectionInputData inputData) {
        final int index = inputData.getIndex();
        final String name = inputData.getName();
        final String type = inputData.getType();

        final Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE};
        final Color color = colors[index];

        final AbstractPlayer player = CharacterFactory.createPlayer(name, type, color);
        selectedPlayers.set(index, player);
        final CharacterSelectionPlayerViewModel output = new
                CharacterSelectionPlayerViewModel(name, type, color, player.getPortrait());
        presenter.preparePlayer(output, index);
    }
}
