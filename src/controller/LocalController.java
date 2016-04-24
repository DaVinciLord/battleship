package controller;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import gui.board.GraphicBoardShooter;
import gui.board.GraphicShipBoard;
import model.board.State;
import model.coordinates.Coordinates;
import model.coordinates.CoordinatesEvent;
import model.coordinates.CoordinatesListener;
import model.player.AIPlayer;
import model.player.AIPlayer.AdvType;
import model.player.Player;

public class LocalController {
    private boolean turn;
    private Player p1;
    private AIPlayer ai;
    private GraphicBoardShooter gbs;
    private GraphicShipBoard gsb;
    private JFrame frame;
    private JTabbedPane jtp;
    
    public LocalController(Coordinates c, AIPlayer.AdvType at) {
        createModel(c, at);
        createView();
        placeComponents();
        createController();
        
        
    }

    private void createController() {
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gbs.addCoordinatesListener(new CoordinatesListener() {
            
        @Override
        public void doWithCoord(CoordinatesEvent e) {
             tourdujoueur(e.getCoordinates());
             tourdelia();
        }
        });
        
        p1.addCoordinatesListener(new CoordinatesListener() {
            
            @Override
            public void doWithCoord(CoordinatesEvent e) {
                if (e.getActionType().equals("ready")) {
                    if(!turn) {
                        tourdelia();
                    }
                }
                if (e.getActionType().equals("dead")) {
                    
                    JOptionPane.showMessageDialog(frame, "Perdu");
                }
            }
        });
        
        ai.addCoordinatesListener(new CoordinatesListener() {
            
            @Override
            public void doWithCoord(CoordinatesEvent e) {

                if (e.getActionType().equals("dead")) {
                    
                    JOptionPane.showMessageDialog(frame, "Gagné");
                }
            }
        });
     
    }

    private void placeComponents() {
        jtp.add("Mon Board", gsb);
        jtp.add("Son Board", gbs);
        frame.add(jtp);
    }

    private void createView() {
        frame = new JFrame("test tableau de tir");
        
        turn = (Math.random() >= 0.5) ? true : false;
        gbs = new GraphicBoardShooter(p1);
        gbs.setMyTurn(turn);
        gsb = new GraphicShipBoard(p1);
        jtp = new JTabbedPane();
    }

    private void createModel(Coordinates c, AdvType at ) {
        createPlayer(c);
        createIA(c, at);
    }

    private void createPlayer(Coordinates c) {
        p1 = new Player(c);
    }

    private void createIA(Coordinates c, AIPlayer.AdvType at) {
        ai = new AIPlayer(c, at);
    }



    private void tourdujoueur(Coordinates c) {
        
        State st = ai.takeHit(c);
        p1.updateFireGrid(c, st);
        jtp.setSelectedIndex(0);
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
    }
    
}
