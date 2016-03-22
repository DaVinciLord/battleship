package model.player;


import model.board.State;
import model.coordinates.Coordinates;

/**
 * Specifie les méthodes qui seront utilisées par
 * les joueurs humains, comme par les IA.
 * 
 * 
 * 
 *
 */

public interface IPlayer {
    
    public State takeHit(Coordinates coords);
    
    public Coordinates shoot();
    
    public void updateFireGrid(Coordinates fire, State s);
}
