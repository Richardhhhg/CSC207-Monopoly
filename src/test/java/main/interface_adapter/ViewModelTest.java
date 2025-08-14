package main.interface_adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.junit.jupiter.api.Assertions.*;

class ViewModelTest {

    private ViewModel<String> stringViewModel;
    private ViewModel<Integer> integerViewModel;
    private MockPropertyChangeListener mockListener;

    // Mock implementation of PropertyChangeListener for testing
    private static class MockPropertyChangeListener implements PropertyChangeListener {
        private PropertyChangeEvent lastEvent;
        private int eventCount = 0;

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            this.lastEvent = evt;
            this.eventCount++;
        }

        public PropertyChangeEvent getLastEvent() {
            return lastEvent;
        }

        public int getEventCount() {
            return eventCount;
        }

        public void reset() {
            lastEvent = null;
            eventCount = 0;
        }
    }

    @BeforeEach
    void setUp() {
        stringViewModel = new ViewModel<>("TestView");
        integerViewModel = new ViewModel<>("NumberView");
        mockListener = new MockPropertyChangeListener();
    }

    @Test
    void testConstructor_ValidViewName_CreatesViewModel() {
        // Arrange & Act
        ViewModel<String> viewModel = new ViewModel<>("MyView");

        // Assert
        assertNotNull(viewModel);
        assertNull(viewModel.getState()); // Initial state should be null
    }

    @Test
    void testSetState_ValidState_SetsState() {
        // Arrange
        String testState = "Test State";

        // Act
        stringViewModel.setState(testState);

        // Assert
        assertEquals(testState, stringViewModel.getState());
    }

    @Test
    void testSetState_NullState_SetsNullState() {
        // Act
        stringViewModel.setState(null);

        // Assert
        assertNull(stringViewModel.getState());
    }

    @Test
    void testGetState_InitialState_ReturnsNull() {
        // Act & Assert
        assertNull(stringViewModel.getState());
    }

    @Test
    void testGetState_AfterSetState_ReturnsCorrectState() {
        // Arrange
        String expectedState = "Expected State";
        stringViewModel.setState(expectedState);

        // Act
        String actualState = stringViewModel.getState();

        // Assert
        assertEquals(expectedState, actualState);
    }

    @Test
    void testSetState_OverwriteState_UpdatesState() {
        // Arrange
        stringViewModel.setState("Initial State");

        // Act
        stringViewModel.setState("Updated State");

        // Assert
        assertEquals("Updated State", stringViewModel.getState());
    }

    @Test
    void testFirePropertyChanged_WithoutListener_DoesNotThrowException() {
        // Arrange
        stringViewModel.setState("Test State");

        // Act & Assert
        assertDoesNotThrow(() -> stringViewModel.firePropertyChanged());
    }

    @Test
    void testGenericType_IntegerViewModel_WorksCorrectly() {
        // Arrange
        Integer testValue = 42;

        // Act
        integerViewModel.setState(testValue);

        // Assert
        assertEquals(testValue, integerViewModel.getState());
        assertInstanceOf(Integer.class, integerViewModel.getState());
    }

    @Test
    void testGenericType_CustomObject_WorksCorrectly() {
        // Arrange
        ViewModel<TestState> customViewModel = new ViewModel<>("CustomView");
        TestState testState = new TestState("Test", 123);

        // Act
        customViewModel.setState(testState);

        // Assert
        assertEquals(testState, customViewModel.getState());
        assertEquals("Test", customViewModel.getState().getName());
        assertEquals(123, customViewModel.getState().getValue());
    }

    @Test
    void testMultipleViewModels_IndependentStates() {
        // Arrange
        ViewModel<String> viewModel1 = new ViewModel<>("View1");
        ViewModel<String> viewModel2 = new ViewModel<>("View2");

        // Act
        viewModel1.setState("State1");
        viewModel2.setState("State2");

        // Assert
        assertEquals("State1", viewModel1.getState());
        assertEquals("State2", viewModel2.getState());
        assertNotEquals(viewModel1.getState(), viewModel2.getState());
    }

    // Helper class for testing custom objects
    private static class TestState {
        private final String name;
        private final int value;

        public TestState(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }
    }
}

