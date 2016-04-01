package model.coordinates;

import java.util.EventObject;

public class CoordinatesEvent extends EventObject {
    
    /**
     * 
     */
    private static final long serialVersionUID = -6525573398129926451L;
    private final Coordinates coordinates;
    private final String actionType;
    
    public CoordinatesEvent(Object source, Coordinates coordinates, String actionType) {
        super(source);
        this.coordinates = coordinates;
        this.actionType = actionType;
    }
    
    public Coordinates getCoordinates() {
        return coordinates;
    }
    
    public String getActionType() {
        return actionType;
    }

}
