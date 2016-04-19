package controller;

import java.util.Scanner;

import exceptions.ship.ShipBadLengthException;
import exceptions.ship.ShipCaseRaceException;
import exceptions.ship.ShipNotAlignException;
import exceptions.ship.ShipOffLimitException;
import model.board.State;
import model.coordinates.Coordinates;
import model.player.AIPlayer;
import model.player.Player;
import model.ship.ShipType;

public class LocalController {
    boolean turn;
    Player p1;
    AIPlayer ai;
    Coordinates size;
    
    public LocalController(Coordinates c, AIPlayer.AdvType at) {
        createplayer(c);
        createIA(c, at);
        turn = (Math.random() >= 0.5) ? true : false;
        size = c;
        
    }

    private void createplayer(Coordinates c) {
        p1 = new Player(c);
        try {
        p1.placeShip(ShipType.AIRCRAFT.getName(), new Coordinates(1,2), new Coordinates(5, 2));
        p1.placeShip(ShipType.BATTLESHIP.getName(),new Coordinates(7,3), new Coordinates(4, 3));
        p1.placeShip(ShipType.CRUISER.getName(), new Coordinates(6,4), new Coordinates(6, 6));
        p1.placeShip(ShipType.DESTROYER.getName(), new Coordinates(9,8), new Coordinates(9, 9));
        p1.placeShip(ShipType.SUBMARINE.getName(), new Coordinates(9,1), new Coordinates(9, 3));
        p1.setReady();
        } catch (ShipCaseRaceException | ShipBadLengthException | ShipOffLimitException | ShipNotAlignException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void createIA(Coordinates c, AIPlayer.AdvType at) {
        ai = new AIPlayer(c, at);
        try {
        ai.placeShip(ShipType.AIRCRAFT.getName(), new Coordinates(1,2), new Coordinates(5, 2));
        ai.placeShip(ShipType.BATTLESHIP.getName(),new Coordinates(7,3), new Coordinates(4, 3));
        ai.placeShip(ShipType.CRUISER.getName(), new Coordinates(6,4), new Coordinates(6, 6));
        ai.placeShip(ShipType.DESTROYER.getName(), new Coordinates(9,8), new Coordinates(9, 9));
        ai.placeShip(ShipType.SUBMARINE.getName(), new Coordinates(9,1), new Coordinates(9, 3));
        ai.setReady();
        } catch (ShipCaseRaceException | ShipBadLengthException | ShipOffLimitException | ShipNotAlignException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void startgame() {
        while (p1.isAlive() && ai.isAlive()) {
            game();
            turn = !turn;
        }
    }
    private void game() {
        
        if (turn) {
            Scanner scan = new Scanner(System.in);
            String s = scan.nextLine();
            try {
            Coordinates c = new Coordinates(s);
            boolean good = true;
            if (c.length != size.length ) {
                good = false; 
            } else {  
                for (int i = 0; i < c.length; i++) {
                    if (c.get(i) < 0 || c.get(i) > size.get(i)) {
                        good = false;
                    }
                } 
            }
           
            if (good) {
            try {    
            State st = ai.takeHit(c);
            System.out.println(c + "  " + st);
            p1.updateFireGrid(c, st);
            } catch (AssertionError e) {
                System.out.println("mavaises coordonnées");
            }
            } else {
                System.out.println("mauvaises coordonnées");
            }
            } catch (NumberFormatException e) {
                System.out.println("mauvaises coordonnées");
            }
            
        } else {
            Coordinates c = ai.shoot();
            State st = p1.takeHit(c);
            System.out.println(c + "  " + st);
            ai.updateFireGrid(c, st);
 
          
        }
        
    }
    
}
