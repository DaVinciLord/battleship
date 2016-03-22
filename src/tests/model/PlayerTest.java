package tests.model;

import exceptions.ship.ShipBadLengthException;
import exceptions.ship.ShipCaseRaceException;
import exceptions.ship.ShipNotAlignException;
import exceptions.ship.ShipOffLimitException;
import model.ai.IAdvisor;
import model.ai.MediumAdvisor;
import model.board.State;
import model.coordinates.Coordinates;
import model.player.IPlayer;
import model.player.Player;
import model.ship.ShipType;

import java.util.ArrayList;
import java.util.List;

public class PlayerTest {

    /**
     * @param args
     * @throws ShipNotAlignException 
     * @throws ShipOffLimitException 
     * @throws ShipBadLengthException 
     * @throws ShipCaseRaceException 
     */
    public static void main(String[] args) 
            throws ShipCaseRaceException, ShipBadLengthException,
            ShipOffLimitException, ShipNotAlignException {
        Player j1 = new Player(new Coordinates(5, 5, 5));
        Player j2 = new Player(new Coordinates(5, 5, 5));
        
        // le joueur 1 place ses navires.
        j1.placeShip("Aircraft Carrier", new Coordinates(0, 1, 0), new Coordinates(0, 1, 4));
        j1.placeShip("Battleship", new Coordinates(0, 2, 1), new Coordinates(0, 2, 4));
        j1.placeShip("Submarine", new Coordinates(1, 2, 1), new Coordinates(3, 2, 1));
        j1.placeShip("Cruiser", new Coordinates(1, 2, 4), new Coordinates(3, 2, 4));
        j1.placeShip("Destroyer", new Coordinates(4, 2, 4), new Coordinates(4, 2, 3));
        j1.setReady();
        
        // au tour du joueur 2.
        j2.placeShip("Aircraft Carrier", new Coordinates(0, 1, 0), new Coordinates(0, 1, 4));
        j2.placeShip("Battleship", new Coordinates(0, 2, 1), new Coordinates(0, 2, 4));
        j2.placeShip("Submarine", new Coordinates(1, 2, 1), new Coordinates(3, 2, 1));
        j2.placeShip("Cruiser", new Coordinates(1, 2, 4), new Coordinates(3, 2, 4));
        j2.placeShip("Destroyer", new Coordinates(4, 2, 4), new Coordinates(4, 2, 3));
        j2.setReady();
        
        // on commence les tirs
        List<Integer> ships = new ArrayList<Integer>();
        for (ShipType st : ShipType.values()) {
            ships.add(st.getMaxHP());
        }
        Coordinates shoot1;
        Coordinates shoot2;
        
        IAdvisor ia1 = new MediumAdvisor(j1.getShootGrid(), ships);
        // IAdvisor ia1 = new EasyAdvisor(j1.getShootGrid(), ships);
        // IAdvisor ia1 = new NoobAdvisor(j1.getShootGrid(), ships);
        IAdvisor ia2 = new MediumAdvisor(j2.getShootGrid(), ships);
        // IAdvisor ia2 = new EasyAdvisor(j2.getShootGrid(), ships);
        // IAdvisor ia2 = new NoobAdvisor(j2.getShootGrid(), ships);
        
        boolean j1Turn = true;
        
        while (j1.isAlive() && j2.isAlive()) {
            if (j1Turn) {
                shoot1 = ia1.getAdvise();
                System.out.println("le joueur 1 tire en " + shoot1);
                State s = j2.takeHit(shoot1);
                j1.updateFireGrid(shoot1, s);
                if (s != State.MISSED) {
                    System.out.println("Et il touche ! " + s);
                } else {
                    j1Turn = false;
                }
            } else {
                shoot2 = ia2.getAdvise();
                System.out.println("le joueur 2 tire en " + shoot2);
                State s = j1.takeHit(shoot2);
                j2.updateFireGrid(shoot2, s);
                if (s != State.MISSED) {
                    System.out.println("Et il touche ! " + s);
                } else {
                    j1Turn = true;
                }
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (j1.isAlive()) {
            System.out.println("le joueur 1 l'emporte ! Il lui restait " + j1.life() + " sections de navires intacts.");
        } else {
            System.out.println("le joueur 2 l'emporte ! Il lui restait " + j2.life() + " sections de navires intacts.");
        }
    }

}
