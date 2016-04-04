package gui.board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.board.IBoard;
import model.board.State;
import model.coordinates.Coordinates;
import model.coordinates.CoordinatesEvent;
import model.coordinates.CoordinatesListener;
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
    
    private Coordinates target;
    
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
        throw new AssertionError("Puisqu'il faut que ce soit un Bean");
    }
    
    // OUTILS DE CONSTRUCTION
    
    private void createView() {
     // provisoire jusqu'à ce qu'on aie deux vrais BoardDrawers.
        BoardDrawer<State> drawer = new BoardDrawer<State>() {
            @Override
            public void drawOnBoard(Graphics g, IBoard<State> board,
                    Coordinates axes, float scale, float alpha) {
                // TODO Stub de la méthode généré automatiquement
                int axeX = 0;
                int axeY = 0;
                // on repère sur quelle tranche de board on est (et son orientation)
                for (int k = 0; k < board.dimensionNb(); k++) {
                    if (axes.get(k) == -1) {
                        axeX = k;
                    } else if (axes.get(k) == -2) {
                        axeY = k;
                    }
                }
                // on parcourt la tranche
                int[] c = axes.getCoordinates();
                for (int x = 0; x < board.getDimensionsSizes().get(axeX); x++) {
                    for (int y = 0; y < board.getDimensionsSizes().get(axeY); y++) {
                        c[axeX] = x;
                        c[axeY] = y;
                        if (board.getItem(new Coordinates(c)) != State.NOTAIMED) {
                            // on dessine
                            int diameter = (int) ((scale * GraphicBoard.DEFAULT_CASE_SIZE) / 3);
                            g.setColor(new Color(1.f, 0.f, 0.f, alpha));
                            g.fillOval((int) (x * scale * GraphicBoard.DEFAULT_CASE_SIZE) + diameter,
                                    (int) (y * scale * GraphicBoard.DEFAULT_CASE_SIZE) + diameter,
                                    diameter, diameter);
                        }
                    }
                }
                
            }
            @Override
            public void drawCase(Graphics g, IBoard<State> board,
                    Coordinates axes, float scale, float alpha,
                    Coordinates position) {
                // TODO Stub de la méthode généré automatiquement
                if (board.getItem(position) != State.NOTAIMED) {
                    // on cherche les coordonnées "graphiques" de la case
                    int x = 0;
                    int y = 0;
                    for (int k = 0; k < board.dimensionNb(); k++) {
                        if (axes.get(k) == -1) {
                            x = position.get(k);
                        } else if (axes.get(k) == -2) {
                            y = position.get(k);
                        }
                    }
                    // on dessine
                    int diameter = (int) ((scale * GraphicBoard.DEFAULT_CASE_SIZE) / 3);
                    g.setColor(new Color(1.f, 0.f, 0.f, alpha));
                    g.fillOval((int) (x * scale * GraphicBoard.DEFAULT_CASE_SIZE) + diameter,
                            (int) (y * scale * GraphicBoard.DEFAULT_CASE_SIZE) + diameter,
                            diameter, diameter);
                }
                
            }
            
        };
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
        gbl = new GraphicBoardLayer<State>(player.getShootGrid(), new Coordinates(axe), drawer);
        
        // pour faire feu.
        fire = new JButton("Feu !");
        fire.setEnabled(false);
        targetField = new JTextField(player.getShootGrid().dimensionNb() * 3);
    }
    
    private void placeComponents() {
        setLayout(new BorderLayout());
        add(gbl, BorderLayout.CENTER);
        JPanel p = new JPanel(); {
            p.add(new JLabel("coordonnées visées : "));
            p.add(targetField);
            p.add(fire);
        }
        add(p, BorderLayout.NORTH);
    }
    
    private void createController() {
        gbl.addCoordinatesListener(new CoordinatesListener() {
            @Override
            public void doWithCoord(CoordinatesEvent e) {
                target = e.getCoordinates();
                targetField.setText(target.toString());
                if (player.getShootGrid().getItem(target) == State.NOTAIMED) {
                    fire.setEnabled(true);
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
                    if (target.equals(e.getCoordinates())) {
                        fire.setEnabled(false);
                    }
                }
            }
        });
        
        fire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                player.updateFireGrid(target, State.MISSED); // à modifier ensuite quand on aura vraiment de quoi tirer.
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
                // traitement selon si la coordonnée est bonne ou pas :
                if (good) {
                    target = c;
                    if (player.getShootGrid().getItem(target) == State.NOTAIMED) {
                        fire.setEnabled(true);
                    } else {
                        fire.setEnabled(false);
                    }
                } else {
                    targetField.setText(target.toString());
                }
            }
        });
    }
}
