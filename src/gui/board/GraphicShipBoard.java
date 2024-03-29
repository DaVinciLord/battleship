package gui.board;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import exceptions.ship.ShipBadLengthException;
import exceptions.ship.ShipCaseRaceException;
import exceptions.ship.ShipNotAlignException;
import exceptions.ship.ShipOffLimitException;
import model.board.Case;
import model.coordinates.Coordinates;
import model.coordinates.CoordinatesEvent;
import model.coordinates.CoordinatesListener;
import model.coordinates.CoordinatesListenerSupport;
import model.player.IPlayer;
import model.ship.IShip;

public class GraphicShipBoard extends JPanel {
	
    private static final long serialVersionUID = 8130087226957440117L;
    
	// ATTRIBUTS

    /**
     * Le GraphicBoardLayer des tirs.
     */
    private GraphicBoardLayer<Case> gbl;
    
    /**
     * Le joueur de ce board.
     */
    private IPlayer player;
    
    /**
     * Branchement d'écouteurs.
     */
    private CoordinatesListenerSupport cls;
    
    /**
     * Bouton "prêt".
     */
    private JButton ready;
    
    /**
     * Champ de coordonnée de proue.
     */
    private JTextField proueField;
    
    /**
     * Champ de coordonnée de poupe.
     */
    private JTextField poupeField;
    
    /**
     * Coordonnée de proue.
     */
    private Coordinates proue;
    
    /**
     * Coordonnée de poupe.
     */
    private Coordinates poupe;
    
    /**
     * Liste des noms de navire.
     */
    private JComboBox<String> ships;
    
    /**
     * Nom du navire qui va être placé.
     */
    private String selectedShip;
    
    /**
     * Bouton de placement du navire sélectionné.
     */
    private JButton placeShip;
    
    /**
     * Bouton de retrait du navire sélectionné.
     */
    private JButton removeShip;
    
    /**
     * Label d'information : affichera la dernière info.
     */
    private JLabel infoLabel;
    
    /**
     * Label de longueur
     */
    private JLabel lengthLabel;
    
    /**
     * Le panel de placement de navires, disparait après ready.
     */
    private JPanel placeShipPanel;
    /**
     * trigger pour le remplissage des champs
     */
    private boolean fieldTrigger;
    
    // CONSTRUCTEUR
    
    /**
     * Construit un plateau sur lequel on peut placer les navires et voir les tirs qu'ils subissent.
     * @param p
     * @pre<pre>
     *     p != null </pre>
     */
	public GraphicShipBoard(IPlayer p) {
        player = p;
        createView();
        placeComponents();
        createController();
	}
	
	// OUTILS DE CONSTRUCTION
	
	private void createView() {
        // on construit un axe par défaut
        int[] axe = new int[player.getShootGrid().dimensionNb()];
        axe[0] = -1;
        axe[1] = -2;
        if (player.getShootGrid().dimensionNb() >= 3) {
            axe[2] = -3;
        }
        for (int k = 3; k < player.getShootGrid().dimensionNb(); k++) {
            axe[k] = 0;
        }
        gbl = new GraphicBoardLayer<Case>(player.getShipGrid(), new Coordinates(axe), new ShipDrawer());
        
		ready = new JButton("prêt");
		ready.setEnabled(false);
		
		proueField = new JTextField(player.getShipGrid().dimensionNb() * 3);
		poupeField = new JTextField(player.getShipGrid().dimensionNb() * 3);
		ships = new JComboBox<String>(player.shipNames().toArray(new String[0]));
		selectedShip = (String) ships.getSelectedItem();
		placeShip = new JButton("placer ce navire");
		removeShip = new JButton("retirer ce navire");
		removeShip.setEnabled(false);
		infoLabel = new JLabel("Ici : Informations sur la partie");
		lengthLabel = new JLabel("Longueur : " + getSelectedShipLength());
		placeShipPanel = new JPanel();
	}
	
	private void placeComponents() {
		setLayout(new BorderLayout());
		add(gbl, BorderLayout.CENTER);
		JPanel p = new JPanel(new GridLayout(0,1)); {
			p.add(ships);
			p.add(lengthLabel);
			p.add(proueField);
			p.add(poupeField);
			p.add(placeShip);
			p.add(removeShip);
			p.add(ready);
		}
		placeShipPanel.add(p);
		add(placeShipPanel, BorderLayout.WEST);
		add(infoLabel, BorderLayout.NORTH);
	}
	
	private void createController() {
		cls = new CoordinatesListenerSupport(this);
		
		player.addCoordinatesListener(new CoordinatesListener() {
            @Override
            public void doWithCoord(CoordinatesEvent e) {
                if (e.getActionType().equals("ship placed")) {
                	gbl.updateCase(e.getCoordinates());
                	if (player.isAllShipPlaced()) {
                        ready.setEnabled(true);
                        infoLabel.setText("Vous avez placé le " + selectedShip + ". Tous vos navires sont placés.");
                    } else {
                        infoLabel.setText("Vous avez placé le " + selectedShip + ".");
                    }
                } else if (e.getActionType().equals("ship removed")) {
                	gbl.updateCase(e.getCoordinates());
                    ready.setEnabled(false);
                	infoLabel.setText("Vous avez retiré le " + selectedShip + ".");
                } else if (e.getActionType().equals("shoot received")) {
                	gbl.updateCase(e.getCoordinates());
                	StringBuilder stringRes = new StringBuilder("L'aversaire a tiré en "
                	        + e.getCoordinates().toString() + ".");
                	IShip s = player.getShipGrid().getItem(e.getCoordinates()).getShip();
                	if (s == null) {
                	    stringRes.append(" Vous n'avez pas été touché.");
                	} else {
                	    stringRes.append(" Votre " + s.getName() + " a été touché");
                	    if (s.getHP() == 0) {
                	        stringRes.append(" et a coulé");
                	    }
                	    stringRes.append(".");
                	}
                	infoLabel.setText(stringRes.toString());
                }
            }
        });
		
		ready.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player.setReady();
				placeShip.setEnabled(false);
				removeShip.setEnabled(false);
				ships.setEnabled(false);
				ready.setEnabled(false);
				proueField.setText("");
				proueField.setEditable(false);
				poupeField.setText("");
				poupeField.setEditable(false);
				infoLabel.setText("La partie va commencer. Vous ne pouvez plus modifier la position de vos navires");
				remove(placeShipPanel);
			}
		});
		ships.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedShip = (String) ships.getSelectedItem();
				boolean b = player.isShipPlaced(selectedShip);
				placeShip.setEnabled(!b);
				removeShip.setEnabled(b);
				lengthLabel.setText("Longueur : " + getSelectedShipLength());
			}
		});
		placeShip.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (proue != null || poupe != null) {
					try {
						player.placeShip(selectedShip, proue, poupe);
						placeShip.setEnabled(false);
						removeShip.setEnabled(true);
						ships.setSelectedIndex((ships.getSelectedIndex() + 1) % ships.getItemCount());
					} catch (ShipCaseRaceException | ShipBadLengthException | ShipOffLimitException
							| ShipNotAlignException e1) {
						infoLabel.setText("erreur de placement " + e1.toString());
					}
					
				}
			}
		});
		
		removeShip.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (proue != null || poupe != null) {
					player.removeShip(selectedShip);
					placeShip.setEnabled(true);
					removeShip.setEnabled(false);
				}
			}
		});
		
		proueField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Coordinates oldproue = proue;
				try {
					proue = new Coordinates(proueField.getText());
				} catch (NumberFormatException e1) {
					proue = oldproue;
					infoLabel.setText("erreur de saisie sur la coordonnée de proue");
					if (proue != null) {
						proueField.setText(proue.toString());
					} else {
						proueField.setText("");
					}
				}
			}
		});
		poupeField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Coordinates oldpoupe = poupe;
				try {
					poupe = new Coordinates(poupeField.getText());
				} catch (NumberFormatException e1) {
					poupe = oldpoupe;
					infoLabel.setText("erreur de saisie sur la coordonnée de poupe");
					if (poupe != null) {
						poupeField.setText(poupe.toString());
					} else {
						poupeField.setText("");
					}
				}
			}
		});
		
		fieldTrigger = true;
		gbl.addCoordinatesListener(new CoordinatesListener() {
            @Override
            public void doWithCoord(CoordinatesEvent e) {
            	if (fieldTrigger) {
            		proue = e.getCoordinates();
            		proueField.setText(proue.toString());
            	} else {
            		poupe = e.getCoordinates();
            		poupeField.setText(poupe.toString());
            	}
            	fieldTrigger = !fieldTrigger;
            }
        });
	}
	
	// OUTILS
	
	private int getSelectedShipLength() {
		return player.getShipsAndNames().get(selectedShip).getMaxHP();
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
}
