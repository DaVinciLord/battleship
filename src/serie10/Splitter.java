package serie10;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class Splitter {
	

	private JFrame mainFrame;
	private SplitManager model;
	private JTextField filepathTF;
	private JTextField sizeofTF;
	private JTextArea dispTA;
	private JButton fragB;
	private JButton browseB;
	private JComboBox<Integer> scrollCB;
	private int lastChanged;
	private static final int NONE = 0;
	private static final int FRAGNB = 1;	
	private static final int FRAGSZ = 2;	
	
   // CONSTRUCTEURS
	
    
    public Splitter() {
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
    

        
 
    
    private void createModel() {

    	File f = new File("temp");
        model = new StdSplitManager(f);
    }
    
    private void createView() {
          final int frameWidth = 700;
          final int frameHeight = 400;
        
          mainFrame = new JFrame("Wrecker");
          mainFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));
    	  lastChanged = NONE;
    	  filepathTF = new JTextField(10);
    	  sizeofTF = new JTextField(5);
    	  dispTA = new JTextArea();
    	  fragB = new JButton("Concasser");
    	  browseB = new JButton("Parcourir ...");
    	  Integer[] tab = new Integer[SplitManager.MAX_FRAGMENT_NB];
    	  for (int i = 1; i <= SplitManager.MAX_FRAGMENT_NB; i++) {
    		  tab[i - 1] = i;
    	  }
    	  scrollCB = new JComboBox<Integer>(tab);
    }
    
    
    private void placeComponents() {
    	JPanel p = new JPanel(new BorderLayout()); {
    		JPanel p1 = new JPanel(new FlowLayout()); {
    			p1.add(new JLabel("Fichier à fragmenter"));
    			p1.add(filepathTF);
    			p1.add(browseB);
    		}
    		p.add(p1, BorderLayout.NORTH);
    		p1 = new JPanel(new GridLayout(2, 1)); {
    			JPanel p2 = new JPanel(new BorderLayout()); {
    				JPanel p3 = new JPanel(new GridLayout(2, 2)); {
    					JPanel p4 = new JPanel(
    							new FlowLayout(FlowLayout.RIGHT)); {
    						p4.add(new JLabel("Nb Fragments"));
    					}
    					p3.add(p4);
    					p4 = new JPanel(new FlowLayout(FlowLayout.LEFT)); {
    						p4.add(scrollCB);
    					}
    					p3.add(p4);
    					p4 = new JPanel(new FlowLayout(FlowLayout.RIGHT)); {
    						p4.add(new JLabel("Taille Fragments"));
    					}
    					p3.add(p4);
    					p4 = new JPanel(new FlowLayout(FlowLayout.LEFT)); {
    						JPanel p5 = new JPanel(new FlowLayout()); {
    							p5.add(sizeofTF);
    							p5.add(new JLabel("octets*"));
    						}
    						p4.add(p5);
    					}
    					p3.add(p4);
    				}
    				p2.add(p3, BorderLayout.SOUTH);
    			}
    			p1.add(p2);
    			p2 = new JPanel(new BorderLayout()); {
    				JPanel p3 = new JPanel(new FlowLayout()); {
    					p3.add(fragB);
    				}
    				p2.add(p3, BorderLayout.NORTH);
    			}
    			p1.add(p2);
    		}
    		p.add(p1, BorderLayout.WEST);
    		JScrollPane p2 = new JScrollPane(dispTA); {
    		}
    		p.add(p2, BorderLayout.CENTER);
    		p1 = new JPanel(); {
    			p1.add(new JLabel("* à un octet pret, sauf le dernier"));
    		}
    		p.add(p1, BorderLayout.SOUTH);
    	}
    	mainFrame.add(p);
    }
    
    private void createController() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        model.addObserver(new Observer() {
            public void update(Observable o, Object arg) {
            	refresh();
            }
            
        });
        fragB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					model.split();
				} catch (IOException e1) {
					// TODO catch block
				}				
			}
		});
        browseB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				JOptionPane.showMessageDialog(browseB, fc);
				filepathTF.setText(
						fc.getSelectedFile().getAbsolutePath());			
				model.setFile(new File(filepathTF.getText()));	
			}
		});
        filepathTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setFile(new File(filepathTF.getText()));	
			}
		});
        sizeofTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lastChanged == FRAGNB) {
					lastChanged = NONE;
				} else {
					lastChanged = FRAGSZ;
					model.setSplitsSizes(Long.valueOf(sizeofTF.getText()));
					scrollCB.setSelectedIndex(model.getSplitsSizes().length);
				}
			}
		});
        scrollCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lastChanged == FRAGSZ) {
					lastChanged = NONE;
				} else {
					lastChanged = FRAGNB;
					model.setSplitsNumber(scrollCB.getSelectedIndex());
					sizeofTF.setText("" + model.getSplitsSizes()[0]);
				}
			}
		});
        
			

    }
    
    
    private void refresh() {
    	if (model.getFile().length() != 0) {
    	String result;
    	result = "Taille du fichier : " 
    			+ model.getFile().length() + "\n\n"
    			+ "Nom des fichiers crees : \n";
    	for (String s : model.getSplitsNames()) {
    		result += s + "\n";
    	}
    	result += "\nTaille du premier fragment : \n";
    	result += model.getSplitsSizes()[0];
    	dispTA.setText(result);
    	}
    	lastChanged = FRAGSZ;
    	scrollCB.removeAllItems();
	  	for (int i = 2; i < model.getMaxFragmentNb(); i++) {
	  		lastChanged = FRAGSZ;
	  	    scrollCB.addItem(i);
	  	lastChanged = FRAGNB;
	  	sizeofTF.setText("" + model.getSplitsSizes()[0]);
	  	}
	  	
	  	
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Splitter().display();
            }
        });
    }    

}
