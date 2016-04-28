package model.player;

import exceptions.ship.OverPanamaException;
import exceptions.ship.ShipBadLengthException;
import exceptions.ship.ShipCaseRaceException;
import exceptions.ship.ShipNotAlignException;
import exceptions.ship.ShipOffLimitException;
import model.board.Board;
import model.board.Case;
import model.board.IBoard;
import model.board.State;
import model.coordinates.Coordinates;
import model.coordinates.CoordinatesListener;
import model.coordinates.CoordinatesListenerSupport;
import model.ship.IShip;
import model.ship.Ship;
import model.ship.ShipType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class APlayer implements IPlayer {
    
    // ATTRIBUTS
    /**
     * Grille de navires.
     */
    private IBoard<Case> selfGrid;
    
    /**
     * Grille de tirs.
     */
    private IBoard<State> opponentGrid;
    
    /**
     * Liste des navires du joueur.
     */
    private Map<String, Ship> ships;
    
    /**
     * nombre de sections encore intactes de tous les navires du joueur.
     */
    private int life;
    
    /**
     * Indique que le joueur est prêt à jouer.
     */
    private boolean ready;
    
    /**
     * Les écouteurs du joueur.
     */
    private CoordinatesListenerSupport cls;
    
    // CONSTRUCTEURS
    
    public APlayer(Coordinates dimensions) throws OverPanamaException {
        this(dimensions, null);
    }
    
    public APlayer(Coordinates dimensions, Map<String, Integer> shipNaL) throws OverPanamaException {
        cls = new CoordinatesListenerSupport(this);
        selfGrid = new Board<Case>(dimensions);
        opponentGrid = new Board<State>(dimensions);
        Iterator<IBoard<Case>> isg = selfGrid.dimZeroIterator();
        Coordinates tab = new Coordinates();
        while (isg.hasNext()) {
            isg.next().setItem(tab , new Case());
        }
        Iterator<IBoard<State>> iog = opponentGrid.dimZeroIterator();
        while (iog.hasNext()) {
            iog.next().setItem(tab, State.NOTAIMED);
        }
        
        life = 0;
        ready = false;
        
        if (shipNaL != null) {
            consCustomShipList(shipNaL);
        } else {
            consStandardShipList();
        }
    }
    
    private void consCustomShipList(Map<String, Integer> shipNaL) {
        ships = new HashMap<String, Ship>();
        for (String s : shipNaL.keySet()) {
            ships.put(s, new Ship(selfGrid, shipNaL.get(s) ,s));
            life +=shipNaL.get(s);
        }
    }
    
    private void consStandardShipList() throws OverPanamaException {
        ships = new HashMap<String, Ship>();
        for (ShipType st : ShipType.values()) {
            ships.put(st.getName(), new Ship(selfGrid, st));
            life += st.getMaxHP();
        }
    }
    
    // REQUÊTES
    
    public int life() {
        return life;
    }
    
    public boolean isAlive() {
        return life > 0;
    }
    
    public boolean isReady() {
        return ready;
    }
    
    public List<String> shipNames() {
        return new ArrayList<String>(ships.keySet());
    }
    
    public List<IShip> getShips() {
        return new ArrayList<IShip>(ships.values());
    }
    
    public Map<String, IShip> getShipsAndNames() {
    	return new HashMap<String, IShip>(ships);
    }
    
    public IBoard<Case> getShipGrid() {
        return selfGrid;
    }
    
    public IBoard<State> getShootGrid() {
        return opponentGrid;
    }
    
    public boolean isShipPlaced(String name) {
	    Ship s = ships.get(name);
	    if (s == null) {
	        throw new AssertionError("navire inconnu à l'amirauté");
	    }
    	return s.isPlaced();
    }
    
    public boolean isAllShipPlaced() {
    	for (Ship s : ships.values()) {
    		if (!s.isPlaced()) {
    			return false;
    		}
    	}
    	return true;
    }
    
    // COMMANDES
    
    public void placeShip(String name, Coordinates proue, Coordinates poupe)
            throws ShipCaseRaceException, ShipBadLengthException,
            ShipOffLimitException, ShipNotAlignException {
        if (ready) {
            throw new AssertionError("la partie va commencer");
        }
        Ship s = ships.get(name);
        if (s == null) {
            throw new AssertionError("navire inconnu à l'amirauté");
        }
        s.setPosition(proue, poupe);
        for (Coordinates c :s.getPosition()) {
            cls.fireCoord(c, "ship placed");
        }
    }
    
    public void removeShip(String name) {
        Ship s = ships.get(name);
        List<Coordinates> oldPositions = s.getPosition();
        s.removePosition();
        for (Coordinates c : oldPositions) {
            cls.fireCoord(c, "ship removed");
        }
    }
    
    public void setReady() {
        ready = true;
        
        cls.fireCoord(selfGrid.getDimensionsSizes(), "ready");
    }
    
    public State takeHit(Coordinates fire) {
        State s = selfGrid.getItem(fire).fireAt();
        if (s != State.MISSED) {
            life--;
        }
        cls.fireCoord(fire, "shoot received");
        
        if (!isAlive()) {
            cls.fireCoord(fire, "dead");
        }
        
        return s;
        
        
        
    }
    
    public void updateFireGrid(Coordinates fire, State s) {
        opponentGrid.setItem(fire, s);
        cls.fireCoord(fire, "shoot fired");
    }
    
    // LA PARTIE OÙ ON ÉCOUTE
    
    public void addCoordinatesListener(CoordinatesListener cl) {
        cls.addCoordinatesListener(cl);
    }
    
    public void removeCoordinatesListener(CoordinatesListener cl) {
        cls.removeCoordinatesListener(cl);
    }
    
    public CoordinatesListener[] getCoordinatesListeners() {
        return cls.getCoordinatesListeners();
    }
    
}