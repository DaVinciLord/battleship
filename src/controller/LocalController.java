package controller;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import exceptions.ship.OverPanamaException;
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
    
    public LocalController(Coordinates c, AIPlayer.AdvType at) throws OverPanamaException {
        createModel(c, at);
        createView();
        placeComponents();
        createController();
        
        
    }

    private void createController() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gbs.addCoordinatesListener(new CoordinatesListener() {
            
        @Override
        public void doWithCoord(CoordinatesEvent e) {
             playerTurn(e.getCoordinates());
             aiTurn();
        }
        });
        
        p1.addCoordinatesListener(new CoordinatesListener() {
            
            @Override
            public void doWithCoord(CoordinatesEvent e) {
                if (e.getActionType().equals("ready")) {
                	gbs.setMyTurn(turn);
                    if(!turn) {
                        aiTurn();
                    }
                }
                if (e.getActionType().equals("dead")) {
                    
                    JOptionPane.showMessageDialog(frame, "Perdu");
                    frame.dispose();
                }
            }
        });
        
        ai.addCoordinatesListener(new CoordinatesListener() {
            
            @Override
            public void doWithCoord(CoordinatesEvent e) {

                if (e.getActionType().equals("dead")) {
                    
                    JOptionPane.showMessageDialog(frame, "Gagné");
                    frame.dispose();
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
        frame = new JFrame("BattleShip !");
        
        turn = (Math.random() >= 0.5) ? true : false;
        gbs = new GraphicBoardShooter(p1);
        
        gsb = new GraphicShipBoard(p1);
        jtp = new JTabbedPane();
    }

    private void createModel(Coordinates c, AdvType at ) throws OverPanamaException {
        createPlayer(c);
        createIA(c, at);
    }

    private void createPlayer(Coordinates c) throws OverPanamaException {
        p1 = new Player(c);
    }

    private void createIA(Coordinates c, AIPlayer.AdvType at) throws OverPanamaException {
        ai = new AIPlayer(c, at);
    }



    private void playerTurn(Coordinates c) {
        
        State st = ai.takeHit(c);
        p1.updateFireGrid(c, st);
        jtp.setSelectedIndex(0);
    }
    
    
    private void aiTurn() {
        
        Coordinates c = ai.shoot();
        State st = p1.takeHit(c);
        ai.updateFireGrid(c, st);
        
        gbs.setMyTurn(true);
    }
    
    public JFrame getFrame() {
    	return frame;
    }
    
    public void display() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}
