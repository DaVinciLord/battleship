package model.player;

import model.coordinates.Coordinates;

import java.util.Map;

import exceptions.ship.OverPanamaException;

public class Player extends APlayer {
    public Player(Coordinates dimensions) throws OverPanamaException {
        super(dimensions, null);
    }
    
    public Player(Coordinates dimensions, Map<String, Integer> shipNaL) throws OverPanamaException {
        super(dimensions, shipNaL);
    }
    
}
