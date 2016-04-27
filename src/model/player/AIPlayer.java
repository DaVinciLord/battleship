package model.player;

import java.util.ArrayList;
import java.util.Map;

import exceptions.ship.OverPanamaException;
import model.ai.MediumAdvisor;
import model.ai.IAdvisor;
import model.ai.HardAdvisor;
import model.ai.EasyAdvisor;
import model.board.Case;
import model.board.IBoard;
import model.coordinates.Coordinates;
import model.ship.IShip;


public class AIPlayer extends APlayer {
    
    IAdvisor adv;
    
    public static enum AdvType {
        EASY(new EasyAdvisor()),
        MEDIUM(new MediumAdvisor()),
        HARD(new HardAdvisor());
        private IAdvisor elconsiglieri;
        
        AdvType(IAdvisor adv) {elconsiglieri = adv; }
        IAdvisor getAdv() { return elconsiglieri; }
    }
    
    public AIPlayer(Coordinates dimensions, Map<String, Integer> shipNaL) throws OverPanamaException {
        super(dimensions, shipNaL);
        adv = new EasyAdvisor();
        adv.setEnemyBoard(getShootGrid()); 
        adv.setShipLeft( new ArrayList<Integer>(shipNaL.values()));
        placeShipRandomly();
        setReady();
    }


    public AIPlayer(Coordinates dimensions) throws OverPanamaException {
        super(dimensions);
        adv = new EasyAdvisor();
        adv.setEnemyBoard(getShootGrid()); 
        ArrayList<Integer> l = new ArrayList<Integer>();
        for (IShip s : getShips()) {
            l.add(s.getMaxHP());
        }
        adv.setShipLeft(l);
        placeShipRandomly();
        setReady();
    }
    
    public AIPlayer(Coordinates dimensions, Map<String, Integer> shipNaL, AdvType advisor) throws OverPanamaException {
        super(dimensions, shipNaL);
        adv = advisor.getAdv();
        adv.setEnemyBoard(getShootGrid()); 
        adv.setShipLeft( new ArrayList<Integer>(shipNaL.values()));
        placeShipRandomly();
        setReady();
    }
    
    public AIPlayer(Coordinates dimensions, AdvType advisor) throws OverPanamaException {
        super(dimensions);
        adv = advisor.getAdv();
        adv.setEnemyBoard(getShootGrid()); 
        ArrayList<Integer> l = new ArrayList<Integer>();
        for (IShip s : getShips()) {
            l.add(s.getMaxHP());
        }
        adv.setShipLeft(l);   
        placeShipRandomly();
        setReady();
    }

    /**
     * Permet de consulter son conseillé, et de retourner le tir que l'IA fait
     * @return les coordonnées du tir
     */
    public Coordinates shoot() {
        return adv.getAdvise();
    }
    
    private void placeShipRandomly() {
        for (IShip s : getShips()) {
            boolean ok = false;
            while (!ok ) {
            int dim = (int) (Math.random() * getShipGrid().dimensionNb());
            Coordinates proue = getAdvise();
            
            try {  
            
            int[] locpoupe = proue.getCoordinates();
            locpoupe[dim] = locpoupe[dim] + s.getMaxHP() - 1;
            Coordinates poupe = new Coordinates(locpoupe);
            
            
            placeShip(s.getName(), proue, poupe);
            ok = true;
            } catch (Exception e) {
                
            }
            }
        }
    }
    
    private Coordinates getAdvise() {
        IBoard<Case> eboard = getShipGrid();
        int[] coords = new int[eboard.dimensionNb()];
        Coordinates dims = eboard.getDimensionsSizes();
        Coordinates result;
        do {
                
            int dist = (int) (Math.random() * eboard.size());
            int size = eboard.size();
            
            for(int i = 0; i < eboard.dimensionNb(); i++) {
                size /= dims.get(eboard.dimensionNb() - 1 - i);            
                coords[eboard.dimensionNb() - 1 - i] = dist / size;
                dist = dist % size;
            }
            result = new Coordinates(coords);
        } while (eboard.getItem(result).getShip() != null);
        
        return result;
    }
    

}
