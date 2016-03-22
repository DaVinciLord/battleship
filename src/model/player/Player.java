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
    

    public Coordinates shoot(String hit) {
        return new Coordinates(hit);
    }

    @Override
    public Coordinates shoot() {
        // TODO Stub de la méthode généré automatiquement
        return null;
    }


}
