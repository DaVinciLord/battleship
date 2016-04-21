package controller;

import javax.swing.JFrame;

import exceptions.ship.ShipBadLengthException;
import exceptions.ship.ShipCaseRaceException;
import exceptions.ship.ShipNotAlignException;
import exceptions.ship.ShipOffLimitException;
import gui.board.GraphicBoardShooter;
import model.board.State;
import model.coordinates.Coordinates;
import model.coordinates.CoordinatesEvent;
import model.coordinates.CoordinatesListener;
import model.player.AIPlayer;
import model.player.Player;
import model.ship.ShipType;

public class LocalController {
    private boolean turn;
    private Player p1;
    private AIPlayer ai;
    private GraphicBoardShooter gbs;
    private JFrame frame;
    
    public LocalController(Coordinates c, AIPlayer.AdvType at) {
        createplayer(c);
        createIA(c, at);
        turn = (Math.random() >= 0.5) ? true : false;
        gbs = new GraphicBoardShooter(p1);
        
        gbs.setMyTurn(turn);
        frame = new JFrame("test tableau de tir");
        frame.add(gbs);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gbs.addCoordinatesListener(new CoordinatesListener() {
            
            @Override
            public void doWithCoord(CoordinatesEvent e) {
                tourdujoueur(e.getCoordinates());
                tourdelia();
            }
        });
        
        
        
    }

    private void createplayer(Coordinates c) {
        p1 = new Player(c);
        try {
        p1.placeShip(ShipType.AIRCRAFT.getName(), new Coordinates(1,2,1), new Coordinates(5, 2,1));
        p1.placeShip(ShipType.BATTLESHIP.getName(),new Coordinates(7,3,4), new Coordinates(4, 3,4));
        p1.placeShip(ShipType.CRUISER.getName(), new Coordinates(6,6,0), new Coordinates(6, 6,2));
        p1.placeShip(ShipType.DESTROYER.getName(), new Coordinates(9,8,1), new Coordinates(9, 9,1));
        p1.placeShip(ShipType.SUBMARINE.getName(), new Coordinates(9,1,3), new Coordinates(9, 3,3));
        p1.setReady();
        } catch (ShipCaseRaceException | ShipBadLengthException | ShipOffLimitException | ShipNotAlignException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void createIA(Coordinates c, AIPlayer.AdvType at) {
        ai = new AIPlayer(c, at);
    }



    private void tourdujoueur(Coordinates c) {
        State st = ai.takeHit(c);
        p1.updateFireGrid(c, st);    
    }
    
    
    private void tourdelia() {
        
        Coordinates c = ai.shoot();
        State st = p1.takeHit(c);
        System.out.println(c + "  " + st);
        ai.updateFireGrid(c, st);
        
        gbs.setMyTurn(true);
    }
    
    public void display() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        if (!turn) {
            tourdelia();
        }
    }
    
}
