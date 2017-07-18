package model;

import interfaces.ChangerListener;

import java.util.HashSet;
import java.util.Set;

public class DataChangeObserver {
    private static DataChangeObserver instance;
    private Set<ChangerListener> listeners;

    private DataChangeObserver() {
        listeners = new HashSet<ChangerListener>() {
        };
    }

    public static DataChangeObserver getInstance() {
        if (instance == null) {
            instance = new DataChangeObserver();
        }
        return instance;
    }

    public void dataChangeNotify() {
        if (listeners != null) {
            for (ChangerListener listener : listeners) {
                if (listener != null)
                    listener.onChangeData();
            }
        }
    }

    public void setListener(ChangerListener listener) {
        listeners.add(listener);
    }

    public void deleteListener(ChangerListener listener) {
        listeners.remove(listener);
    }
}
