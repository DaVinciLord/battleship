package model.coordinates;

import java.util.EventListener;

public interface CoordinatesListener extends EventListener {
    void doWithCoord(CoordinatesEvent e);
}
