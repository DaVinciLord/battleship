package model.coordinates;

import javax.swing.event.EventListenerList;

public class CoordinatesListenerSupport {
    
    private EventListenerList listenersList;
    private final Object owner;
    
    public CoordinatesListenerSupport(Object owner) {
        this.owner = owner;
    }

    public synchronized CoordinatesListener[] getCoordinatesListeners() {
        if (listenersList == null) {
            return new CoordinatesListener[0];
        }
        return listenersList.getListeners(CoordinatesListener.class);
    }
    
    public synchronized void addCoordinatesListener(CoordinatesListener listener) {
        if (listener == null) {
            return;
        }
        if (listenersList == null) {
            listenersList = new EventListenerList();
        }
        listenersList.add(CoordinatesListener.class, listener);
    }
    
    public synchronized void removeCoordinatesListener(CoordinatesListener listener) {
        if (listener == null || listenersList == null) {
            return;
        }
        listenersList.remove(CoordinatesListener.class, listener);
    }
    
    public void fireCoord(Coordinates coordinates) {
        CoordinatesListener[] list = getCoordinatesListeners();
        if (list.length > 0) {
            CoordinatesEvent e = new CoordinatesEvent(owner, coordinates);
            for (CoordinatesListener lst : list) {
                lst.doWithCoord(e);
            }
        }
    }
}
