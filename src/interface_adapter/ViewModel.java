package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * A viewmodel class that is meant to detect changes in the model
 * and change the view accordingly
 *
 * @param <T> The type of the model that this ViewModel is associated with
 */
public class ViewModel<T> {
    private final String viewName;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private T state;

    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public T getState() {
        return state;
    }

    public void setState(T state) {
        this.state = state;
    }


    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        this.support.firePropertyChange(propertyName, oldValue, newValue);
    }
}