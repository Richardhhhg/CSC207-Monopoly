package main.interface_adapter.dice;

import main.use_case.dice.RollDice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class DicePresenterTest {
    private DicePresenter dicePresenter;

    @BeforeEach
    void setUp() {
        dicePresenter = new DicePresenter();
    }

    @Test
    void testExecuteTransformsDiceResult() {
        RollDice.DiceResult diceResult = new RollDice.DiceResult(4, 2, 6);
        DiceViewModel viewModel = dicePresenter.execute(diceResult);

        assertNotNull(viewModel, "ViewModel should not be null");
        assertEquals(4, viewModel.getDice1(), "Dice1 should match");
        assertEquals(2, viewModel.getDice2(), "Dice2 should match");
        assertEquals(6, viewModel.getSum(), "Sum should match");
    }

    @Test
    void testExecuteWithMinValues() {
        RollDice.DiceResult diceResult = new RollDice.DiceResult(1, 1, 2);
        DiceViewModel viewModel = dicePresenter.execute(diceResult);

        assertEquals(1, viewModel.getDice1());
        assertEquals(1, viewModel.getDice2());
        assertEquals(2, viewModel.getSum());
        assertEquals("Sum: 2", viewModel.getSumText());
    }

    @Test
    void testExecuteWithMaxValues() {
        RollDice.DiceResult diceResult = new RollDice.DiceResult(6, 6, 12);
        DiceViewModel viewModel = dicePresenter.execute(diceResult);

        assertEquals(6, viewModel.getDice1());
        assertEquals(6, viewModel.getDice2());
        assertEquals(12, viewModel.getSum());
        assertEquals("Sum: 12", viewModel.getSumText());
    }
}