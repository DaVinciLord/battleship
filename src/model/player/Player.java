package model.player;

import model.coordinates.Coordinates;

import java.util.Map;

public class Player extends APlayer {
    public Player(Coordinates dimensions) {
        super(dimensions, null);
    }
    
    public Player(Coordinates dimensions, Map<String, Integer> shipNaL) {
        super(dimensions, shipNaL);
    }
    
}
