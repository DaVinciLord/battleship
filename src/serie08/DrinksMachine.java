package serie08;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import serie06.CoinTypes;
import serie06.DrinkTypes;



public class DrinksMachine {

    // ATTRIBUTS
    
    private JFrame mainFrame;
    private DrinksMachineModel model;
    private JLabel changeInfo;
    private JLabel creditInfo;
    private JButton insertButton;
    private JButton cancelButton;
    private JButton consumeButton;
    private JTextField coinInput;
    private JTextField drinkOutput;
    private JTextField changeOutput;
    private JButton coffeeButton;
    private JButton chocolateButton;
    private JButton orangeButton;
    

    // CONSTRUCTEURS
    
    public DrinksMachine() {
        createModel();
        createView();
        placeComponents();
        createController();
    }
    
    // COMMANDES
    
    /**
     * Rend l'application visible au centre de l'écran.
     */
    public void display() {
    	refresh();
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    // OUTILS
    
    private void createModel() {
        model = new StdDrinksMachineModel();
    }
    
    private void createView() {
        final int frameWidth = 700;
        final int frameHeight = 400;
        
        mainFrame = new JFrame("Bellissima");
        mainFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));
        changeInfo = new JLabel(model.canGetChange() 
        		? "Cet appareil rend la monnaie" 
        		: "Cet appareil ne rend pas la monnaie");
        creditInfo = new JLabel("Vous disposez de " 
        		+ model.getCreditAmount() + " Cents");
        insertButton = new JButton("Inserer");
        cancelButton = new JButton("Annuler");
        consumeButton = new JButton("Consommer");
        coffeeButton = new JButton("Café");
        chocolateButton = new JButton("Chocolat");
        orangeButton = new JButton("Orange");
        coinInput = new JTextField(10);
        drinkOutput = new JTextField(10);
        drinkOutput.setEnabled(false);
        changeOutput = new JTextField(10);
        changeOutput.setEnabled(false);
        changeOutput.setText("" + model.getChangeAmount());
        
    }
    
    private void placeComponents() {
        JPanel p = new JPanel(new BorderLayout()); {
        	JPanel p2 = new JPanel(new FlowLayout()); {
        		p2.add(changeInfo);
        	}
        p.add(p2, BorderLayout.NORTH);
        	p2 = new JPanel(new FlowLayout()); {
        		p2.add(creditInfo);
        	}
        p.add(p2, BorderLayout.SOUTH);
        }
        mainFrame.add(p, BorderLayout.NORTH);
        p = new JPanel(new GridLayout(3, 2)); {
        	 p.add(coffeeButton);
        	 p.add(new JLabel("0 E 30"));
        	 p.add(chocolateButton);
        	 p.add(new JLabel("0 E 45"));
        	 p.add(orangeButton);
        	 p.add(new JLabel("1 E 10"));
        }
        mainFrame.add(p, BorderLayout.WEST);
        p = new JPanel(new BorderLayout()); {
        	JPanel p2 = new JPanel(new GridLayout(2, 2)); {
        		p2.add(insertButton);
        		JPanel p3 = new JPanel(); {
        			p3.add(coinInput, BorderLayout.WEST);
        			p3.add(new JLabel("cents"), BorderLayout.CENTER);
        		}
        		p2.add(p3);
        		p2.add(cancelButton);
        	}
        p.add(p2, BorderLayout.CENTER);
        }
        mainFrame.add(p, BorderLayout.EAST);
        p = new JPanel(new BorderLayout()); {
        	JPanel p2 = new JPanel(new BorderLayout()); {
        		JPanel p3 = new JPanel(new GridLayout(1, 2)); {
        			JPanel p4 = new JPanel(new FlowLayout()); {
        				p4.add(new JLabel("Boisson :"));
        				p4.add(drinkOutput);
        			}
        			p3.add(p4);
        			p4 = new JPanel(new FlowLayout()); {
        				p4.add(new JLabel("Change :"));
        				p4.add(changeOutput);
        				p4.add(new JLabel(" Cents"));
        			}
        			p3.add(p4);
        		}
        		p2.add(p3, BorderLayout.NORTH);
        		p3 = new JPanel(new FlowLayout()); {
        			p3.add(consumeButton);
        		}
        		p2.add(p3, BorderLayout.CENTER);
        	}
        p.add(p2, BorderLayout.SOUTH);
        }
        mainFrame.add(p, BorderLayout.SOUTH);
    }
    
    private void createController() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        model.addObserver(new Observer() {
            public void update(Observable o, Object arg) {
            	refresh();
            }
        });

        insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CoinTypes c = 
					CoinTypes.getCoinType(Integer.valueOf(coinInput.getText()));
					if (c != null) {
					model.insertCoin(c);
					refresh();
					}
				} catch (java.lang.NumberFormatException e1) {
					JOptionPane.showMessageDialog(insertButton,
							"Ceci n'est pas une pièce");
				}
			}
		});
        cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.cancelCredit();
				refresh();
				
			}
		});
        coffeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model.getCreditAmount() > DrinkTypes.COFFEE.getPrice()) {
				model.selectDrink(DrinkTypes.COFFEE);
				refresh();
				}
				
			}
		});
        orangeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model.getCreditAmount() 
						> DrinkTypes.ORANGE_JUICE.getPrice()) {
				model.selectDrink(DrinkTypes.ORANGE_JUICE);
				refresh();
				}
				
			}
		});
        chocolateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model.getCreditAmount() > DrinkTypes.CHOCOLATE.getPrice()) {
				model.selectDrink(DrinkTypes.CHOCOLATE);
				refresh();
				}
				
			}
		});
        consumeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.takeChange();
				model.takeDrink();
				refresh();
			}
		});
        
    }
    
    
    private void refresh() {
    	 coinInput.setText(null);
    	 creditInfo.setText("Vous disposez de " 
        		+ model.getCreditAmount() + " Cents");
    	 changeOutput.setText("" + model.getChangeAmount());
    	 if (model.getDrinkNb(DrinkTypes.COFFEE) > 0
    			 && !(model.getLastDrink() != null)) {
    		 coffeeButton.setEnabled(true);
    	 } else {
    		 coffeeButton.setEnabled(false);
    	 }
    	 if (model.getDrinkNb(DrinkTypes.CHOCOLATE) > 0
    			 && !(model.getLastDrink() != null)) {
    		 chocolateButton.setEnabled(true);
    	 } else {
    		 chocolateButton.setEnabled(false);
    	 }
    	 if (model.getDrinkNb(DrinkTypes.ORANGE_JUICE) > 0
    			 && !(model.getLastDrink() != null)) {
    		 orangeButton.setEnabled(true);
    	 } else {
    		 orangeButton.setEnabled(false);
    	 }
    	 if (model.getLastDrink() != null) {
    		 drinkOutput.setText(model.getLastDrink().toString());
    	 } else {
    		 drinkOutput.setText(null);
    	 }
         changeInfo.setText(model.canGetChange() 
         		? "Cet appareil rend la monnaie" 
         		: "Cet appareil ne rend pas la monnaie");
    }

 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DrinksMachine().display();
            }
        });
    }    
    
}


