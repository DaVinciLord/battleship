package model.coordinates;

import java.util.EventObject;

public class CoordinatesEvent extends EventObject {
    
    /**
     * 
     */
    private static final long serialVersionUID = -6525573398129926451L;
    private final Coordinates coordinates;
    
    public CoordinatesEvent(Object source, Coordinates coordinates) {
        super(source);
        this.coordinates = coordinates;
    }
    
    public Coordinates getCoordinates() {
        return coordinates;
    }

}
