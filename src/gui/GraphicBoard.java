package gui;

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

    /**
     *
     */
    private static final long serialVersionUID = -694145859923355617L;

    /**
     * Taille préférée d'une case.
     */
    public static final int CASE_SIZE = 35;

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
     * L'opacité avec laquelle ce composant doit être dessiné.
     */
    private float opacity;

    /**
     * Indique si les cases sont cliquables. (composant au premier plan).
     */
    private boolean foreGround;

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
        if (model == null) {
            throw new AssertionError("model null");
        }
        if (model.dimensionNb() < 2 ) {
            throw new AssertionError("pas assez de dimensions");
        }
        if (drawer == null) {
            throw new AssertionError("pas assez de dimensions");
        }
        this.model = model;
        updateAxes(axes);
        foreGround = false;
        this.drawer = drawer;
        cls = new CoordinatesListenerSupport(this);
        createView();
        createController();
        // repaint();
    }

    // OUTILS DE CONSTRUCTION

    /**
     * Cette méthode permet aussi d'updater la vue.
     */
    private void createView() {
        opacity = 1.f;
        setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
        Dimension d = new Dimension(CASE_SIZE * dimX + 1, CASE_SIZE * dimY + 1);
        setPreferredSize(d);
        setMinimumSize(d);
        setMaximumSize(d);
    }

    private void createController() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (foreGround) {
                    int x = e.getX() / CASE_SIZE;
                    int y = e.getY() / CASE_SIZE;
                    int[] coord = axes.getCoordinates();
                    for (int k = 0; k < coord.length; k++) {
                        if (coord[k] == -1) {
                            coord[k] = x;
                        } else if (coord[k] == -2) {
                            coord[k] = y;
                        }
                    }
                    cls.fireCoord(new Coordinates(coord));
                } else {
                    cls.fireCoord(null);
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
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

    /**
     * indique si le composant est au premier plan, c'est à dire apte à renvoyer
     * des CoordEvents avec coordonnée renseignée.
     */
    public boolean isForeGround() {
        return foreGround;
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
        drawer.drawOnBoard(g, model, axes, 1.f);
    }

    /**
     * Dessine une case.
     */
    private void paintCase(int x, int y, Graphics g) {
        Color c  = new Color(0.7f, 0.7f, 1.0f, opacity);
        g.setColor(c);
        g.fillRect(x * CASE_SIZE, y * CASE_SIZE, CASE_SIZE, CASE_SIZE);
        c  = new Color(0.2f, 0.2f, 1.0f, opacity);
        g.setColor(c);
        g.drawRect(x * CASE_SIZE, y * CASE_SIZE, CASE_SIZE, CASE_SIZE);
        // g.drawRect(x * CASE_SIZE + 1, y * CASE_SIZE + 1, CASE_SIZE - 2, CASE_SIZE - 2);
        // /!\ Je n'ai pas trouvé comment régler l'épaisseur du trait.
    }

    /**
     * Mets le plateau au premier plan ou en arriere-plan
     */

    public void setForeGround(boolean b) {
        foreGround = b;
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
    }

    public void addCoordListener(CoordinatesListener cl) {
        cls.addCoordinatesListener(cl);
    }

    public void removeCoordListener(CoordinatesListener cl) {
        cls.removeCoordinatesListener(cl);
    }

    // OUTILS

    private void updateAxes(Coordinates axes) {
        if (axes == null) {
            throw new AssertionError("axes null");
        }
        if (axes.length != model.dimensionNb()) {
            throw new AssertionError("mauvais nombre de dimension");
        }
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
                    throw new AssertionError("Deux axes X impossibles");
                }
            } else if (comp == -2) {
                if (!yFixed) {
                    dimY = model.getDimensionsSizes().get(k);
                    yFixed = true;
                } else {
                    throw new AssertionError("Deux axes Y impossibles");
                }
            } else if (comp < -2 || comp >= model.getDimensionsSizes().get(k)) {
                throw new AssertionError("composante hors des dimensions");
            }
        }
    }

}