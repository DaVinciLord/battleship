package model.player;

import model.ai.EasyAdvisor;
import model.ai.IAdvisor;
import model.ai.MediumAdvisor;
import model.ai.NoobAdvisor;
import model.coordinates.Coordinates;
import model.ship.IShip;
import model.ship.Ship;

import java.util.ArrayList;
import java.util.Map;


public class AIPlayer extends APlayer {
    
    IAdvisor adv;
    
    public static enum AdvType {
        NOOB(new NoobAdvisor()),
        EASY(new EasyAdvisor()),
        MEDIUM(new MediumAdvisor());
     // HARD(new HardAdvisor());
        private IAdvisor elconsiglieri;
        
        AdvType(IAdvisor adv) {elconsiglieri = adv; }
        IAdvisor getAdv() { return elconsiglieri; }
    }
    
    public AIPlayer(Coordinates dimensions, Map<String, Integer> shipNaL) {
        super(dimensions, shipNaL);
        adv = new NoobAdvisor();
        adv.setEnemyBoard(getShootGrid()); 
        adv.setShipLeft( new ArrayList<Integer>(shipNaL.values()));
    }


    public AIPlayer(Coordinates dimensions) {
        super(dimensions);
        adv = new NoobAdvisor();
        adv.setEnemyBoard(getShootGrid()); 
        ArrayList<Integer> l = new ArrayList<Integer>();
        for (IShip s : getShips()) {
            l.add(s.getMaxHP());
        }
        adv.setShipLeft(l);
    }
    
    public AIPlayer(Coordinates dimensions, Map<String, Integer> shipNaL, AdvType advisor) {
        super(dimensions, shipNaL);
        adv = advisor.getAdv();
        adv.setEnemyBoard(getShootGrid()); 
        adv.setShipLeft( new ArrayList<Integer>(shipNaL.values()));
    }
    
    public AIPlayer(Coordinates dimensions, AdvType advisor) {
        super(dimensions);
        adv = advisor.getAdv();
        adv.setEnemyBoard(getShootGrid()); 
        ArrayList<Integer> l = new ArrayList<Integer>();
        for (IShip s : getShips()) {
            l.add(s.getMaxHP());
        }
        adv.setShipLeft(l);      
    }

    public Coordinates shoot() {
        return adv.getAdvise();
    }
    
    

}
