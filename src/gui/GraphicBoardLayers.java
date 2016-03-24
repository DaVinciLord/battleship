package gui;

import model.board.IBoard;
import model.coordinates.Coordinates;
import model.coordinates.CoordinatesEvent;
import model.coordinates.CoordinatesListener;
import model.coordinates.CoordinatesListenerSupport;

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

    // CONSTRUCTEUR

    public GraphicBoardLayers(IBoard<E> model, Coordinates axes, BoardDrawer<E> drawer) {
        super(null);
        if (model == null || axes == null || drawer == null) {
            throw new AssertionError("paramètres null");
        }
        this.drawer = drawer;
        this.model = model;
        cls = new CoordinatesListenerSupport(this);
        setLayout(new OverlayLayout(this));
        updateAxes(axes);
        createView();
        placeComponents();
        createController();
    }

    // OUTILS DE CONSTRUCTION

    private void createView() {
        boards = new ArrayList<GraphicBoard<E>>();
        if (model.dimensionNb() >= 3) {
            int zIndex = 0;
            for (int k = 0; k < axes.length; k++) {
                if (axes.get(k) == -3) {
                    zIndex = k;
                    break;
                }
            }
            int[] axis = axes.getCoordinates();
            for (int k = 0; k < dimZ; k++) {
                axis[zIndex] = k;
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
        for (int k = 0; k < dimZ; k++) {
            GraphicBoard<E> graf = boards.get(k);
            float align = ((float) dimZ - k) / dimZ;
            graf.setAlignmentX(align);
            graf.setAlignmentY(align);
            this.add(graf);
        }
    }

    private void createController() {
        cl = new CoordinatesListener() {
            @Override
            public void doWithCoord(CoordinatesEvent e) {
                if (e.getCoordinates() != null) {
                    cls.fireCoord(e.getCoordinates());
                }
            }
        };
        for (GraphicBoard<E> graf : boards) {
            graf.addCoordListener(cl);
        }
        boards.get(0).setForeGround(true);
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
            } else if (comp == -3) {
                if (!zFixed && model.dimensionNb() >= 3) {
                    dimZ = model.getDimensionsSizes().get(k);
                    yFixed = true;
                } else {
                    throw new AssertionError("Deux axes Z impossibles");
                }
            } else if (comp < -2 || comp >= model.getDimensionsSizes().get(k)) {
                throw new AssertionError("composante hors des dimensions");
            }
        }
    }
}
