package gui.board;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.board.State;
import model.coordinates.Coordinates;
import model.coordinates.CoordinatesEvent;
import model.coordinates.CoordinatesListener;
import model.coordinates.CoordinatesListenerSupport;
import model.player.IPlayer;

public class GraphicBoardShooter extends JPanel {
    
    private static final long serialVersionUID = -8730832953409133345L;
    
    /**
     * Le GraphicBoardLayer des tirs.
     */
    private GraphicBoardLayer<State> gbl;
    
    /**
     * Le joueur de ce board.
     */
    private IPlayer player;
    
    /**
     * Bouton "Feu" qui déclenche le tir.
     */
    private JButton fire;
    
    /**
     * Affichage des coordonnées de la case pointée.
     * Il peut être rempli manuellement.
     */
    private JTextField targetField;
    
    /**
     * Coordonnée de la case actuellement sélectionnée.
     */
    private Coordinates target;
    
    /**
     * Branchement d'écouteurs.
     */
    private CoordinatesListenerSupport cls;    
    
    /**
     * Indique si c'est au tour du joueur (décidé par le SuperController ou le LocalController).
     */
    private boolean myTurn;
    
    /**
     * Label d'information : affichera la dernière info.
     */
    private JLabel infoLabel;
    
    // CONSTRUCTEUR
    
    public GraphicBoardShooter(IPlayer p) {
        if (p == null) {
            throw new AssertionError("pas de joueur");
        }
        player = p;
        createView();
        placeComponents();
        createController();
    }
    
    public GraphicBoardShooter() {
        
    }
    
    // OUTILS DE CONSTRUCTION
    
    private void createView() {
        // un axe par défaut
        int[] axe = new int[player.getShootGrid().dimensionNb()];
        axe[0] = -1;
        axe[1] = -2;
        if (player.getShootGrid().dimensionNb() >= 3) {
            axe[2] = -3;
        }
        for (int k = 3; k < player.getShootGrid().dimensionNb(); k++) {
            axe[k] = 0;
        }
        gbl = new GraphicBoardLayer<State>(player.getShootGrid(), new Coordinates(axe), new ShootDrawer());
        
        // pour faire feu.
        fire = new JButton("Feu !");
        fire.setEnabled(false);
        targetField = new JTextField(player.getShootGrid().dimensionNb() * 3);
        infoLabel = new JLabel("informations sur les tirs ici");
    }
    
    private void placeComponents() {
        setLayout(new BorderLayout());
        add(gbl, BorderLayout.CENTER);
        JPanel p = new JPanel(new GridLayout(0,1)); {
            JPanel p1 = new JPanel(); {
                p1.add(new JLabel("coordonnées visées : "));
                p1.add(targetField);
                p1.add(fire);
            }
            p.add(p1);
            p.add(infoLabel);
        }
        add(p, BorderLayout.NORTH);
    }
    
    private void createController() {
    	cls = new CoordinatesListenerSupport(this);
    	myTurn = false;
        gbl.addCoordinatesListener(new CoordinatesListener() {
            @Override
            public void doWithCoord(CoordinatesEvent e) {
                target = e.getCoordinates();
                targetField.setText(target.toString());
                if (player.getShootGrid().getItem(target) == State.NOTAIMED) {
                    fire.setEnabled(isMyTurn());
                } else {
                    fire.setEnabled(false);
                }
            }
        });
        
        player.addCoordinatesListener(new CoordinatesListener() {
            @Override
            public void doWithCoord(CoordinatesEvent e) {
                if (e.getActionType().equals("shoot fired")) {
                    gbl.updateCase(e.getCoordinates());
                    StringBuilder resString = new StringBuilder("Vous avez tiré en " + e.getCoordinates().toString() + ".");
                    State result = player.getShootGrid().getItem(e.getCoordinates());
                    if (result == State.HIT) {
                        resString.append(" Le tir a touché un navire !");
                    } else if (result == State.MISSED) {
                        resString.append(" Le tir n'a rien touché.");
                    } else if (result == State.SUNK) {
                        resString.append(" Le tir a touché un navire et l'a coulé !");
                    }
                    infoLabel.setText(resString.toString());
                    if (target.equals(e.getCoordinates())) {
                        fire.setEnabled(false);
                    }
                }
            }
        });
        
        fire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                cls.fireCoord(target, "fire");
            }
        });
        
        targetField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Coordinates c = null;
                boolean good = true;
                try {
                    c = new Coordinates(targetField.getText());
                } catch (NumberFormatException exc) {
                    good = false;
                }
                if (good) {
                    if (c.length == player.getShootGrid().dimensionNb()) {
                        for (int k = 0; k < c.length; k++) {
                            if (c.get(k) < 0 || c.get(k) >=
                                    player.getShootGrid().getDimensionsSizes().get(k)) {
                                good = false;
                                break;
                            }
                        }
                    } else {
                        good = false;
                    }
                }
                if (good) {
                    target = c;
                    if (player.getShootGrid().getItem(target) == State.NOTAIMED) {
                        fire.setEnabled(isMyTurn());
                    } else {
                        fire.setEnabled(false);
                    }
                } else {
                    targetField.setText(target.toString());
                }
            }
        });
    }
    
    // MÉTHODES D'ÉCOUTEURS
    
    public CoordinatesListener[] getCoordinatesListeners() {
    	return cls.getCoordinatesListeners();
    }
    
    public void addCoordinatesListener(CoordinatesListener listener) {
    	cls.addCoordinatesListener(listener);
    }
    
    public void removeCoordinatesListener(CoordinatesListener listener) {
    	cls.removeCoordinatesListener(listener);
    }
    
    // AUTORISATION DE TIR
    
    /**
     * Indique si c'est au tour du joueur de tirer.
     */
    public boolean isMyTurn() {
    	return myTurn;
    }
    
    /**
     * Autorise ou interdit au joueur de tirer.
     */
    public void setMyTurn(boolean b) {
    	myTurn = b;
    	if (b && target != null && player.getShootGrid().getItem(target) == State.NOTAIMED) {
    		fire.setEnabled(true);
    	} else {
    		fire.setEnabled(false);
    	}
    }
}
