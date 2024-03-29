package gui.board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.board.IBoard;
import model.coordinates.Coordinates;
import model.coordinates.CoordinatesEvent;
import model.coordinates.CoordinatesListener;
import model.coordinates.CoordinatesListenerSupport;

public class GraphicBoardLayer<E> extends JPanel {

    private static final long serialVersionUID = 6849697290917379392L;
    
    
    // ATTRIBUTS
    
    /**
     * Liste des JPanels qui vont supporter les boards.
     */
    private List<JPanel> boardPanels;
    
    /**
     * Liste des boards.
     */
    private List<GraphicBoard<E>> boards;
    
    /**
     * indique les axes et les composantes fixées.
     */
    private Coordinates axes;
    
    /**
     * tailles des axes.
     */

    private int dimZ;
    
    /**
     * Composante correspondante à l'axe Z.
     */
    private int axeZ;
    /**
     * Liste des écouteurs.
     */
    private final CoordinatesListenerSupport cls;

    /**
     * Le tableau qui va etre représenté graphiquement.
     */
    private final IBoard<E> model;
    
    /**
     * L'écouteur commun à tous les GraphicBoard contenus.
     */
    private CoordinatesListener cl;
    
    /**
     * L'objet qui dessine spécifiquement ce qui est relatif au type E.
     */
    private BoardDrawer<E> drawer;
    
    /**
     * index du GraphicBoard actif.
     */
    private int caseActiveIndex;
    
    /**
     * Support des GraphicBoard.
     */
    private JPanel boardLayers;
    
    /**
     * RadioButtons de sélection des axes.
     */
    private List<JRadioButton> selectAxeX;
    private List<JRadioButton> selectAxeY;
    private List<JRadioButton> selectAxeZ;
    
    /**
     * JTextFields des coordonnées fixées. (toutes les coordonnées : 
     * celles correspondant aux axes seront vides et disabled).
     */
    private List<JTextField> fixedCoords;
    
    /**
     * Valide le changement d'axe.
     */
    private JButton changeAxe;
    
    /**
     * Règle le niveau de profondeur Z.
     */
    private JSlider sliderZ;
    
    // CONSTRUCTEUR
    
    /**
     * Construit une vue fausse 3D d'un IBoard en utilisant des GraphicBoard.
     * @param model
     *     Le IBoard à représenter.
     * @param axes
     *     Détermine quelles composantes des coordonnées vont être.
     * @param drawer
     *     L'objet outil qui va dessiner plus spécifiquement le contenu du IBoard.
     */
    public GraphicBoardLayer(IBoard<E> model, Coordinates axes, BoardDrawer<E> drawer) {
        super(null);
        this.drawer = drawer;
        this.model = model;
        dimZ = 1; // sera écrasé si 3D ou plus
        cls = new CoordinatesListenerSupport(this);
        setLayout(new BorderLayout());
        updateAxes(axes);
        createView();
        placeComponents();
        createController();
    }
    
    // OUTILS DE CONSTRUCTION
    
    private void createView() {
        boardPanels = new ArrayList<JPanel>();
        boards = new ArrayList<GraphicBoard<E>>();
        boardLayers = new JPanel(null);
        boardLayers.setLayout(new OverlayLayout(boardLayers));
        createGraphicBoards();
        createButtonsView();
    }
    
    private void createGraphicBoards() {
        boardPanels.clear();
        boards.clear();
        if (model.dimensionNb() >= 3) {
            // gestion 3 dimensions
            int[] localAxe = axes.getCoordinates();
            for (int k = 0; k < dimZ; k++) {
                localAxe[axeZ] = k;
                GraphicBoard<E> gb = new GraphicBoard<E>(model, new Coordinates(localAxe), drawer);
                gb.setOpacity(0.25f);
                JPanel p = new JPanel(null);
                p.setLayout(new GridLayout(0, 2));
                float align = 0.625f - ((((float) k) / (dimZ - 1)) / 4.f); // va de 0.625f à 0.375f inclus
                p.setAlignmentX(align);
                p.setAlignmentY(1.f - align);
                p.setBackground(new Color(0, 0, 0, 0));

                Dimension size = new Dimension(gb.getMaximumSize().width * 2 + gb.getInsets().left + gb.getInsets().right,
                        gb.getMaximumSize().height + gb.getInsets().top + gb.getInsets().bottom);
                p.setMinimumSize(size);
                p.setPreferredSize(size);
                p.setMaximumSize(size);
                boards.add(gb);
                p.add(new JComponent() {


                    private static final long serialVersionUID = 339550300913704206L;
                });
                p.add(gb);
                boardPanels.add(p);
            }
        } else {
            GraphicBoard<E> gb = new GraphicBoard<E>(model, axes, drawer);
            gb.setOpacity(0.5f);
            JPanel p = new JPanel();
            p.setAlignmentX(0.5f);
            p.setAlignmentY(0.5f);
            p.setBackground(new Color(0, 0, 0, 0));
            Dimension size = new Dimension(gb.getMaximumSize().width + gb.getInsets().left + gb.getInsets().right,
                    gb.getMaximumSize().height + gb.getInsets().top + gb.getInsets().bottom);
            p.setMinimumSize(size);
            p.setPreferredSize(size);
            p.setMaximumSize(size);
            boards.add(gb);
            p.add(gb);
            boardPanels.add(p);
        }
        caseActiveIndex = 0;
        boards.get(0).setCaseActive(true);
        boards.get(0).setOpacity(0.5f);
    }
    
    private void createButtonsView() {
        changeAxe = new JButton("changer orientation");
        selectAxeX = new ArrayList<JRadioButton>();
        for (int k = 0; k < model.dimensionNb(); k++) {
            selectAxeX.add(new JRadioButton());
        }
        for (int k = 0; k < model.dimensionNb(); k++) {
            if (axes.get(k) == -1) {
                selectAxeX.get(k).setSelected(true);
                break;
            }
        }
        selectAxeY = new ArrayList<JRadioButton>();
        for (int k = 0; k < model.dimensionNb(); k++) {
            selectAxeY.add(new JRadioButton());
        }
        for (int k = 0; k < model.dimensionNb(); k++) {
            if (axes.get(k) == -2) {
                selectAxeY.get(k).setSelected(true);
                break;
            }
        }
        if (model.dimensionNb() >= 3) {
            selectAxeZ = new ArrayList<JRadioButton>();
            for (int k = 0; k < model.dimensionNb(); k++) {
                selectAxeZ.add(new JRadioButton());
            }
            for (int k = 0; k < model.dimensionNb(); k++) {
                if (axes.get(k) == -3) {
                    selectAxeZ.get(k).setSelected(true);
                    break;
                }
            }
            if (model.dimensionNb() > 3) {
                fixedCoords = new ArrayList<JTextField>();
                for (int k = 0; k < model.dimensionNb(); k++) {
                    fixedCoords.add(new JTextField(2));
                    if (axes.get(k) >= 0) {
                        fixedCoords.get(k).setText("" + axes.get(k));
                    }
                }
            }
            sliderZ = new JSlider(SwingConstants.VERTICAL, 0, dimZ - 1, 0);
            sliderZ.setLabelTable(sliderZ.createStandardLabels(1));
            sliderZ.setPaintLabels(true);

        }
    }
    
    private void placeComponents() {
        placeGraphicBoards();
        add(boardLayers, BorderLayout.CENTER);
        placeButtons();
    }
    
    private void placeGraphicBoards() {
        for (int k = 0; k < dimZ; k++) {
            boardLayers.add(boardPanels.get(k));
        }
    }
    
    private void placeButtons() {
        JPanel p = new JPanel(new GridLayout(0,1)); {
            // Selecteurs d'axes
            JPanel p2 = new JPanel(); {
                p2.add(new JLabel("Axe Horizontal : "));
                for (JRadioButton jrb : selectAxeX) {
                    p2.add(new JLabel(" " + selectAxeX.indexOf(jrb) + " :"));
                    p2.add(jrb);
                }
                p2.add(new JLabel("Axe Vertical : "));
                for (JRadioButton jrb : selectAxeY) {
                    p2.add(new JLabel(" " + selectAxeY.indexOf(jrb) + " :"));
                    p2.add(jrb);
                }
                if (model.dimensionNb() >= 3) {
                    p2.add(new JLabel("Axe Profondeur : "));
                    for (JRadioButton jrb : selectAxeZ) {
                        p2.add(new JLabel(" " + selectAxeZ.indexOf(jrb) + " :"));
                        p2.add(jrb);
                    }
                }
            }
            p.add(p2);
            // valideur d'axes et selecteurs de coordonnées fixées (facultatif).
            p2 = new JPanel(); {
                if (model.dimensionNb() > 3) {
                    p2.add(new JLabel("Dimensions : " + model.getDimensionsSizes().toString()));
                    p2.add(new JLabel("Composantes fixes :"));
                    for (JTextField jtf : fixedCoords) {
                        p2.add(new JLabel(" " + fixedCoords.indexOf(jtf) + " : "));
                        p2.add(jtf);
                    }
                }
                p2.add(changeAxe);
            }
            p.add(p2);
        }
        add(p, BorderLayout.SOUTH);
        if (model.dimensionNb() >= 3) {
            add(sliderZ, BorderLayout.EAST);
        }
    }
    
    private void createController() {
        cl = new CoordinatesListener() {
            @Override
            public void doWithCoord(CoordinatesEvent e) {
                if (e.getCoordinates() != null) {
                    cls.fireCoord(e.getCoordinates(), e.getActionType());
                } else {
                    if (model.dimensionNb() >= 3) {
                        int n = boards.indexOf(e.getSource());
                        showBoardAtIndex(n);
                        sliderZ.setValue(n);
                    }
                }
            }
        };
        addListenerToGraphicBoard();
        createButtonController();
        if (model.dimensionNb() >= 3) {
            boardLayers.addMouseWheelListener(new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent arg0) {
                    int n = caseActiveIndex - arg0.getWheelRotation();
                    if (n < 0) {
                        n = 0;
                    } else if (n >= dimZ) {
                        n = dimZ - 1;
                    }
                    showBoardAtIndex(n);
                    sliderZ.setValue(n);
                }
            });
        }
    }
    
    private void addListenerToGraphicBoard() {
        for (int k = 0; k < dimZ; k++) {
            boards.get(k).addCoordListener(cl);
        }
    }
    
    private void showBoardAtIndex(int n) {
        boards.get(caseActiveIndex).setCaseActive(false);
        boards.get(caseActiveIndex).setOpacity(0.25f);
        caseActiveIndex = n;
        boards.get(caseActiveIndex).setCaseActive(true);
        boards.get(caseActiveIndex).setOpacity(0.5f);
        for (int k = caseActiveIndex; k < dimZ; k++) {
            JPanel p = boardPanels.get(k);
            p.removeAll();
            p.add(new JComponent() {

                private static final long serialVersionUID = -8040986933978664177L;
            });
            p.add(boards.get(k));
        }
        for (int k = 0; k < caseActiveIndex; k++) {
            JPanel p = boardPanels.get(k);
            p.removeAll();
            p.add(boards.get(k));
            p.add(new JComponent() {

                private static final long serialVersionUID = -1211875283772444314L;
            });
        }
        revalidate();
        repaint();
    }
    
    private void createButtonController() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JRadioButton jrb = (JRadioButton) arg0.getSource();
                int n = selectAxeX.indexOf(jrb);
                if (n >= 0) {
                    for (int k = 0; k < model.dimensionNb(); k++) {
                        if (k != n) {
                            selectAxeX.get(k).setSelected(false);
                        }
                        selectAxeY.get(n).setSelected(false);
                        if (model.dimensionNb() >= 3) {
                            selectAxeZ.get(n).setSelected(false);
                        }
                    }
                } else {
                    n = selectAxeY.indexOf(jrb);
                    if (n >= 0) {
                        for (int k = 0; k < model.dimensionNb(); k++) {
                            if (k != n) {
                                selectAxeY.get(k).setSelected(false);
                            }
                            selectAxeX.get(n).setSelected(false);
                            if (model.dimensionNb() >= 3) {
                                selectAxeZ.get(n).setSelected(false);
                            }
                        }
                    } else {
                        n = selectAxeZ.indexOf(jrb);
                        if (n >= 0) {
                            for (int k = 0; k < model.dimensionNb(); k++) {
                                if (k != n) {
                                    selectAxeZ.get(k).setSelected(false);
                                }
                                selectAxeX.get(n).setSelected(false);
                                selectAxeY.get(n).setSelected(false);
                            }
                        } else {
                           
                        }
                    }
                }
                if (model.dimensionNb() > 3) {
                    enableFixedFields();
                }
            }
        };
        // selection d'axes
        for (JRadioButton jrb : selectAxeX) {
            jrb.addActionListener(al);
        }
        for (JRadioButton jrb : selectAxeY) {
            jrb.addActionListener(al);
        }
        if (model.dimensionNb() >= 3) {
            for (JRadioButton jrb : selectAxeZ) {
                jrb.addActionListener(al);
            }
        }
        
        // selection de profondeur
        if (model.dimensionNb() >= 3) {
            sliderZ.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent arg0) {
                    showBoardAtIndex(sliderZ.getValue());
                }
            });
        }
        
        // changement d'axe
        changeAxe.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int[] newAxe = new int[model.dimensionNb()];
                boolean goodAxe = true;
                for (int k = 0; k < model.dimensionNb(); k++) {
                    if (selectAxeX.get(k).isSelected()) {
                        newAxe[k] = -1;
                    } else if (selectAxeY.get(k).isSelected()) {
                        newAxe[k] = -2;
                    } else if (model.dimensionNb() >= 3 && selectAxeZ.get(k).isSelected()) {
                        newAxe[k] = -3;
                    } else if (model.dimensionNb() > 3) {
                        int n = -1;
                        try {
                            n = new Integer(fixedCoords.get(k).getText());
                        } catch (NumberFormatException e) {
                            goodAxe = false;
                            break;
                        }
                        if (n >= 0 && n < model.getDimensionsSizes().get(k)) {
                            newAxe[k] = n;
                        } else {
                            goodAxe = false;
                            break;
                        }
                    } else {
                        goodAxe = false;
                        break;
                    }
                }
                if (goodAxe) {
                    updateAxes(new Coordinates(newAxe));
                    updateView();
                    if (model.dimensionNb() >= 3) {
                        sliderZ.setMaximum(dimZ - 1);
                        sliderZ.setValue(0);
                    }
                } else {
                    JOptionPane.showMessageDialog(GraphicBoardLayer.this,
                            "Il faut renseigner tous les axes et les coordonnées fixées !",
                            "Erreur d'axe", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    

    private void enableFixedFields() {
        for (int k = 0; k < model.dimensionNb(); k++) {
            if (!selectAxeX.get(k).isSelected() && !selectAxeY.get(k).isSelected()
                    && !selectAxeZ.get(k).isSelected()) {
                fixedCoords.get(k).setEnabled(true);
                fixedCoords.get(k).setEditable(true);

            } else {
                fixedCoords.get(k).setEnabled(false);
                fixedCoords.get(k).setEditable(false);

            }
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
        this.axes = axes;
        boolean xFixed = false;
        boolean yFixed = false;
        boolean zFixed = false;
        for (int k = 0; k < axes.length; k++) { 
            int comp = axes.get(k);
            if (comp == -1) {
                if (!xFixed) {
                    
                    xFixed = true;
                } else {
                    
                }
            } else if (comp == -2) {
                if (!yFixed) {
                   
                    yFixed = true;
                } else {
                    
                }
            } else if (comp == -3) {
                if (!zFixed && model.dimensionNb() >= 3) {
                    dimZ = model.getDimensionsSizes().get(k);
                    axeZ = k;
                    zFixed = true;
                } else {
                    
                }
            } else if (comp < -3 || comp >= model.getDimensionsSizes().get(k)) {
               
            }
        } 
  

    }
    
    private void updateView() {
        for (GraphicBoard<E> gb : boards) {
            gb.removeCoordListener(cl);
        }
        boardLayers.removeAll();
        createGraphicBoards();
        placeGraphicBoards();
        addListenerToGraphicBoard();
        revalidate();
        repaint();
    }
    
    public void updateCase(Coordinates kaze) {
        boolean really = true;
        int profondeur = 0; 
        if (kaze.length != model.dimensionNb()) {
        }
        for (int k = 0; k < kaze.length; k++) {
            if (kaze.get(k) < 0 || kaze.get(k) >= model.getDimensionsSizes().get(k)) {
            }
            if (axes.get(k) >= 0 && axes.get(k) != kaze.get(k)) {
  
                really = false;
                break;
            }
            if (axes.get(k) == -3) {

                profondeur = kaze.get(k);
            }
        }
        if (really) {
            boards.get(profondeur).updateCase(kaze);
        }
    }
    

    public int getActiveGridDepth() {
        return caseActiveIndex;
    }
    
 
}
