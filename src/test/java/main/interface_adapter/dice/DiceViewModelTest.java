package main.interface_adapter.dice;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiceViewModelTest {

    @Test
    void testDiceViewModelCreation() {
        DiceViewModel viewModel = new DiceViewModel(3, 4, 7);

        assertEquals(3, viewModel.getDice1(), "Dice1 should be 3");
        assertEquals(4, viewModel.getDice2(), "Dice2 should be 4");
        assertEquals(7, viewModel.getSum(), "Sum should be 7");
    }

    @Test
    void testGetSumText() {
        DiceViewModel viewModel = new DiceViewModel(2, 5, 7);
        assertEquals("Sum: 7", viewModel.getSumText(), "Sum text should be formatted correctly");
    }

    @Test
    void testGetSumTextWithMinSum() {
        DiceViewModel viewModel = new DiceViewModel(1, 1, 2);
        assertEquals("Sum: 2", viewModel.getSumText(), "Sum text should handle min value");
    }

    @Test
    void testGetSumTextWithMaxSum() {
        DiceViewModel viewModel = new DiceViewModel(6, 6, 12);
        assertEquals("Sum: 12", viewModel.getSumText(), "Sum text should handle max value");
    }

    @Test
    void testGetters() {
        DiceViewModel viewModel = new DiceViewModel(5, 3, 8);

        assertEquals(5, viewModel.getDice1());
        assertEquals(3, viewModel.getDice2());
        assertEquals(8, viewModel.getSum());
    }
}