package gui;

import model.board.IBoard;
import model.coordinates.Coordinates;
import model.coordinates.CoordinatesEvent;
import model.coordinates.CoordinatesListener;
import model.coordinates.CoordinatesListenerSupport;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.OverlayLayout;

public class GraphicBoardLayers<E> extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 8991337378658393693L;

    /**
     * Liste des boards qui apparaitront superposÃ©s.
     */
    private List<GraphicBoard<E>> boards;

    /**
     * indique les axes et les composantes fixÃ©es.
     */
    private Coordinates axes;

    /**
     * tailles des axes.
     */
    private int dimX;
    private int dimY;
    private int dimZ;
    
    /**
     * Composante correspondante à l'axe  Z;
     */
    private int axeZ;
    /**
     * Liste des Ã©couteurs.
     */
    private final CoordinatesListenerSupport cls;

    /**
     * Le tableau qui va Ãªtre reprÃ©sentÃ© graphiquement.
     */
    private final IBoard<E> model;

    /**
     * L'Ã©couteur commun Ã  tous les GraphicNArrays contenus.
     */
    private CoordinatesListener cl;
    
    /**
     * L'objet qui dessine spécifiquement ce qui est relatif au type E.
     */
    private BoardDrawer<E> drawer;
    
    /**
     * JPanel des GraphicBoards qui sont au dessus du GraphicBoard actif.
     */
    private JPanel abovePanel;
    
    /**
     * JPanel du GraphicBoard actif et de ceux qui sont en dessous.
     */
    private JPanel activePanel;
    
    /**
     * index du GraphicBoard actif.
     */
    private int caseActiveIndex;
    
    // CONSTRUCTEUR
    
    /**
     * 
     * @param model
     * @param axes
     * @param drawer
     */
    public GraphicBoardLayers(IBoard<E> model, Coordinates axes, BoardDrawer<E> drawer) {
        super(null);
        if (model == null || axes == null || drawer == null) {
            throw new AssertionError("paramètres null");
        }
        this.drawer = drawer;
        this.model = model;
        cls = new CoordinatesListenerSupport(this);
        if (model.dimensionNb() >= 3) {
            setLayout(new GridLayout(0, 2));
        } else {
            setLayout(new GridLayout(0, 1));
        }
        updateAxes(axes);
        createView();
        placeComponents();
        createController();
    }

    // OUTILS DE CONSTRUCTION

    private void createView() {
        if (model.dimensionNb() >= 3) {
            abovePanel = new JPanel(null);
            abovePanel.setLayout(new OverlayLayout(abovePanel));
        }
        activePanel = new JPanel(null);
        activePanel.setLayout(new OverlayLayout(activePanel));
        boards = new ArrayList<GraphicBoard<E>>();
        if (model.dimensionNb() >= 3) {
            int[] axis = axes.getCoordinates();
            for (int k = 0; k < dimZ; k++) {
                axis[axeZ] = k;
                GraphicBoard<E> graf = new GraphicBoard<E>(model, new Coordinates(axis), drawer);
                float opac = ((float) k + 1.f) / dimZ;
                System.out.println(opac);
                graf.setOpacity(0.5f);
                boards.add(graf);
            }
        } else {
            GraphicBoard<E> graf = new GraphicBoard<E>(model, axes, drawer);
            boards.add(graf);
        }
    }

    private void placeComponents() {
        if (model.dimensionNb() >= 3) {
            add(abovePanel);
        }
        add(activePanel);
        for (int k = 0; k < dimZ; k++) {
            GraphicBoard<E> graf = boards.get(k);
            float align = ((float) dimZ - k) / dimZ;
            graf.setAlignmentX(align);
            graf.setAlignmentY(align);
            activePanel.add(graf);
        }
        boards.get(0).setCaseActive(true);
        caseActiveIndex = 0;
    }

    private void createController() {
        cl = new CoordinatesListener() {
            @Override
            public void doWithCoord(CoordinatesEvent e) {
                if (e.getCoordinates() != null) {
                    cls.fireCoord(e.getCoordinates());
                } else {
                    int n = boards.indexOf(e.getSource());
                    showBoardAtIndex(n);
                }
            }
        };
        for (GraphicBoard<E> graf : boards) {
            graf.addCoordListener(cl);
        }
    }

    // REQUETES

    public IBoard<E> getModel() {
        return model;
    }

    public Coordinates getAxes() {
        return axes;
    }

    public CoordinatesListener[] getCoordinatesListeners() {
        return cls.getCoordinatesListeners();
    }

    // COMMANDES

    public void addCoordinatesListener(CoordinatesListener cl) {
        cls.addCoordinatesListener(cl);
    }

    public void removeCoordinatesListener(CoordinatesListener cl) {
        cls.removeCoordinatesListener(cl);
    }
    
    public void updateAxesAndView(Coordinates axes) {
        updateAxes(axes);
        updateView();
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
        boolean zFixed = false;
        for (int k = 0; k < axes.length; k++) { // début de la méga boucle
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
            } else if (comp == -3) {
                if (!zFixed && model.dimensionNb() >= 3) {
                    dimZ = model.getDimensionsSizes().get(k);
                    axeZ = k;
                    zFixed = true;
                } else {
                    throw new AssertionError("Deux axes Z impossibles");
                }
            } else if (comp < -3 || comp >= model.getDimensionsSizes().get(k)) {
                throw new AssertionError("composante hors des dimensions");
            }
        } // fin de la méga boucle
        // vérification que tous les axes sont bien renseignés.
        if (!xFixed) {
            throw new AssertionError("Il manque l'axe X");
        }
        if (!yFixed) {
            throw new AssertionError("Il manque l'axe Y");
        }
        if (model.dimensionNb() >= 3 && !zFixed) {
            throw new AssertionError("Il manque l'axe Z");
        }
    }
    
    private void updateView() {
        // Traitement 2D
        if (model.dimensionNb() == 2) {
            boards.get(0).updateAxesAndRepaint(axes);
            revalidate();
            return;
        }
        // Traitement 3D et plus
        int[] axis = axes.getCoordinates();
        // Ajout de GraphicBoard si nécessaire et redéfinition des axes.
        for (int k = 0; k < dimZ; k++) {
            axis[axeZ] = k;
            if (k >= boards.size()) {
                GraphicBoard<E> graf = new GraphicBoard<E>(model, new Coordinates(axis), drawer);
                float opac = ((float) k + 1.f) / dimZ;
                System.out.println(opac); // pour le débug
                graf.setOpacity(0.5f);
                boards.add(graf);
                graf.addCoordListener(cl);
            } else {
                boards.get(k).updateAxesAndRepaint(new Coordinates(axis));
            }
        }
        activePanel.removeAll();
        abovePanel.removeAll();
        placeComponents();
        revalidate();
    }
    
    private void showBoardAtIndex(int n) {
        boards.get(caseActiveIndex).setCaseActive(false);
        caseActiveIndex = n;
        boards.get(caseActiveIndex).setCaseActive(true);
        activePanel.removeAll();
        abovePanel.removeAll();
        for (int k = 0; k < caseActiveIndex; k++) {
            GraphicBoard<E> graf = boards.get(k);
            abovePanel.add(graf);
        }
        for (int k = caseActiveIndex; k < dimZ; k++) {
            GraphicBoard<E> graf = boards.get(k);
            activePanel.add(graf);
        }
        revalidate();
        repaint();
    }
}
