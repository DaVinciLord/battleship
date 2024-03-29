package gui.board;

import model.board.IBoard;
import model.coordinates.Coordinates;
import model.coordinates.CoordinatesListener;
import model.coordinates.CoordinatesListenerSupport;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class GraphicBoard<E> extends JComponent {

    private static final long serialVersionUID = -694145859923355617L;

    /**
     * Taille préférée d'une case.
     */
    public static final int DEFAULT_CASE_SIZE = 40;

    // ATTRIBUT

    /**
     * Liste des écouteurs.
     */
    private final CoordinatesListenerSupport cls;

    /**
     * Le tableau qui va être représenté graphiquement.
     */
    private final IBoard<E> model;

    /**
     * L'opacité avec laquelle ce composant doit être dessiné. (fond)
     */
    private float opacity;
    
    /**
     * La taille relativement à la taille par défaut
     */
    private float scale;
    
    /**
     * taille réelle de la case.
     */
    private int caseSize;

    /**
     * Indique si les cases sont cliquables. (composant au premier plan).
     */
    private boolean caseActive;

    /**
     * indique les axes et les composantes fixées.
     */
    private Coordinates axes;

    /**
     * nombre de colonnes.
     */
    private int dimX;

    /**
     * nombre de lignes.
     */
    private int dimY;
    
    /**
     * L'objet qui dessine spécifiquement ce qui est relatif au type E.
     */
    private final BoardDrawer<E> drawer;
    
    /**
     * Coordonnées de la case en surbrillance (relativement à this).
     */
    private int xCase;
    private int yCase;

    // CONSTRUCTEUR

    /**
     * Constructeur d'un affichage de tableau à deux dimensions.
     * axes doit contenir exactement deux composantes négatives :
     *     -1 pour l'axe X, -2 pour l'axe Y.
     * Les autres composantes représentent les composantes fixes communes à
     * toutes les cases de la représentation.
     * @pre <pre>
     *     model.dimensionNb() >= 2
     *     model.dimensionNb() == axes.length
     *     pour toute compsante non négative de axe, d'indice i :
     *     axe.get(i) < model.getDimensionsSizes().get(i) </pre>
     */
    public GraphicBoard(IBoard<E> model, Coordinates axes, BoardDrawer<E> drawer) {
        opacity = 1.f;
        scale = 1.f;
        caseSize = DEFAULT_CASE_SIZE;
        this.model = model;
        updateAxes(axes);
        caseActive = false;
        this.drawer = drawer;
        cls = new CoordinatesListenerSupport(this);
        createView();
        createController();
    }

    // OUTILS DE CONSTRUCTION

    /**
     * Cette méthode permet aussi d'updater la vue.
     */
    private void createView() {
        
        setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
        Dimension d = new Dimension(caseSize * dimX + 1, caseSize * dimY + 1);
        setPreferredSize(d);
        setMinimumSize(d);
        setMaximumSize(d);
    }

    private void createController() {
        
        xCase = -1;
        yCase = -2;
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }
            @Override
            public void mousePressed(MouseEvent e) {
                if (caseActive) {
                    Graphics g = getGraphics();
                    int oldXCase = xCase;
                    int oldYCase = yCase;
                    xCase = e.getX() / caseSize;
                    yCase = e.getY() / caseSize;
                    if (xCase >= dimX || xCase < 0 || yCase >= dimY || yCase < 0) {
                    	xCase = oldYCase;
                        yCase = oldYCase;
                    } else {
	                    int[] coord = axes.getCoordinates();
	                    for (int k = 0; k < coord.length; k++) {
	                        if (coord[k] == -1) {
	                            coord[k] = xCase;
	                        } else if (coord[k] == -2) {
	                            coord[k] = yCase;
	                        }
	                    }
	                    cls.fireCoord(new Coordinates(coord), "case selected");
	                    if (oldXCase != -1) {
	                        paintCase(oldXCase, oldYCase, g);
	                        int[] kaze = axes.getCoordinates();
	                        for (int k = 0; k < kaze.length; k++) {
	                            if (axes.get(k) == -1) {
	                                kaze[k] = oldXCase;
	                            } else if (axes.get(k) == -2) {
	                                kaze[k] = oldYCase;
	                            }
	                        }
	                        drawer.drawCase(g, model, axes, scale, opacity, new Coordinates(kaze));
	                    }
	                    paintCase(xCase, yCase, g);
	                    int[] kaze = axes.getCoordinates();
	                    for (int k = 0; k < kaze.length; k++) {
	                        if (axes.get(k) == -1) {
	                            kaze[k] = xCase;
	                        } else if (axes.get(k) == -2) {
	                            kaze[k] = yCase;
	                        }
	                    }
	                    drawer.drawCase(g, model, axes, scale, opacity, new Coordinates(kaze));
                    }
                } else {
                    cls.fireCoord(null, "GraphicBoardSelected");
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    // REQUETES

    /**
     * Opacité avec laquelle ce composant sera dessiné.
     */
    public float getOpacity() {
        return opacity;
    }
    
    public float getScale() {
        return scale;
    }

    /**
     * indique si le composant est au premier plan, c'est à dire apte à renvoyer
     * des CoordEvents avec coordonnée renseignée.
     */
    public boolean isCaseActive() {
        return caseActive;
    }

    /**
     * Le modèle de ce composant.
     */
    public IBoard<E> getModel() {
        return model;
    }

    /**
     * Les écouteurs de ce composant.
     */
    public CoordinatesListener[] getCoordListeners() {
        return cls.getCoordinatesListeners();
    }

    // COMMANDES
    
    /**
     * Indique à quelles composantes des coordoonnées doivent correspondre les
     * axes, et pour quelles valeurs sur les autres composantes les cases sont
     * représentées.
     * Exemple : un GraphicBoard représentant un IBoard de dimensions [6, 5, 4, 3]
     *     avec un axe [-2, 2, 0, -1] affichera toutes les cases de coordonnées
     *     [y, 2, 0, x] pour x de 0 à 2 et y de 0 à 5
     *     la valeur -1 signifie l'axe des X et la valeur -2 signifie l'axe des Y
     *     Ainsi, ce GraphicBoard s'affichera en 3 colonnes (axe horizontal) et 
     *     6 lignes (axe vertical).
     */
    public void updateAxesAndRepaint(Coordinates axes) {
        updateAxes(axes);
        createView();
        revalidate();
        repaint();
    }
    
    public void scaleAndRepaint(float scale) {

        this.scale = scale;
        caseSize = Math.round(DEFAULT_CASE_SIZE * scale);
        Dimension d = new Dimension(caseSize * dimX + 1, caseSize * dimY + 1);
        setPreferredSize(d);
        setMinimumSize(d);
        setMaximumSize(d);
        createView();
        repaint();
        revalidate();
    }

    /**
     * Dessine le tableau sans tenir compte du statut des éléments dans
     * getModel(). Cette méthode est appelée à être redéfinie dans les classes
     * héritières, avec un appel à super.paint(g);
     */
    @Override
    public void paintComponent(Graphics g) {
        for (int x = 0; x < dimX; x++) {
            for (int y = 0; y < dimY; y++) {
                paintCase(x, y, g);
            }
        }
        drawer.drawOnBoard(g, model, axes, scale, opacity);
    }

    /**
     * Dessine une case.
     */
    private void paintCase(int x, int y, Graphics g) {
        g.clearRect(x * caseSize, y * caseSize, caseSize, caseSize);
        // 
        // setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
        Color c  = new Color(0.7f, 0.7f, 1.0f, opacity);
        g.setColor(c);
        g.fillRect(x * caseSize, y * caseSize, caseSize, caseSize);
        c  = new Color(0.2f, 0.2f, 1.0f, 1.0f);
        g.setColor(c);
        g.drawRect(x * caseSize, y * caseSize, caseSize, caseSize);
        if (caseActive) {
            if (x == xCase && y == yCase) {
                c  = new Color(1.0f, 1.0f, 1.0f, 1.0f);
                g.setColor(c);
                g.drawRect(x * caseSize + 2, y * caseSize + 2, caseSize - 4, caseSize - 4);
            }
            g.drawRect(x * caseSize + 1, y * caseSize + 1, caseSize - 2, caseSize - 2);
        }

    }

    /**
     * Mets le plateau au premier plan ou en arriere-plan
     */

    public void setCaseActive(boolean b) {
        caseActive = b;
    }

    /**
     * Règle l'opacité du dessin.
     * @pre <pre>
     *     alpha >= 0 && alpha <= 1 </pre>
     */
    public void setOpacity(float alpha) {
        if (alpha < 0.0f || alpha > 1.0f) {
            throw new AssertionError("invalid alpha");
        }
        opacity = alpha;
        repaint();
    }
    
    /**
     * Met à jour le dessin d'une case.
     * @param kaze
     * @pre
     *     kaze doit être une coordonnée valide dans getModel()
     */
    public void updateCase(Coordinates kaze) {

        // trouver la position de la case
        int x = 0;
        int y = 0;
        for (int k = 0; k < axes.length; k++) {
            if (axes.get(k) == -1) {
                x = kaze.get(k);
            } else if (axes.get(k) == -2) {
                y = kaze.get(k);
            }
        }
        Graphics g = getGraphics();
        
        paintCase(x, y, g);
        drawer.drawCase(g, model, axes, scale, opacity, kaze);
    }

    public void addCoordListener(CoordinatesListener cl) {
        cls.addCoordinatesListener(cl);
    }

    public void removeCoordListener(CoordinatesListener cl) {
        cls.removeCoordinatesListener(cl);
    }

    // OUTILS

    private void updateAxes(Coordinates axes) {
        this.axes = axes;
        boolean xFixed = false;
        boolean yFixed = false;
        for (int k = 0; k < axes.length; k++) {
            int comp = axes.get(k);
            if (comp == -1) {
                if (!xFixed) {
                    dimX = model.getDimensionsSizes().get(k);
                    xFixed = true;
                } else {
                   
                }
            } else if (comp == -2) {
                if (!yFixed) {
                    dimY = model.getDimensionsSizes().get(k);
                    yFixed = true;
                } else {
                    
                }
            } else if (comp < -2 || comp >= model.getDimensionsSizes().get(k)) {
                
            }
        }
    }

}