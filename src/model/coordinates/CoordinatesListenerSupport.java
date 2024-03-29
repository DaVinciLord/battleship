package model.coordinates;

import javax.swing.event.EventListenerList;

/**
 * 
 * @author vimarsyl
 * Adapté à partir de la classe serie06.event.SentenceSupport des TP d'IHM.
 * crédit : MM Andary && Patrou sous licence creative commons
 * http://creativecommons.org/licenses/by-nc-sa/2.0/fr/
 */
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
    
    public void fireCoord(Coordinates coordinates, String actionType) {
        CoordinatesListener[] list = getCoordinatesListeners();
        if (list.length > 0) {
            CoordinatesEvent e = new CoordinatesEvent(owner, coordinates, actionType);
            for (CoordinatesListener lst : list) {
                lst.doWithCoord(e);
            }
        }
    }
}
