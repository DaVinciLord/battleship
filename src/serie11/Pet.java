package serie11;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import serie11.model.PetException;
import serie11.model.PetModel;
import serie11.model.StdPetModel;


public class Pet {
	

	private JFrame mainFrame;
	private PetModel model;
	private JLabel info;
	private JTextArea doc;
	private JMenuBar menu;
	private JMenu fichier;
	private JMenu edition;
	private JMenu quitter;
	private JMenuItem creer;
	private JMenuItem creerapartir;
	private JMenuItem ouvrir;
	private JMenuItem reouvrir;
	private JMenuItem sauv;
	private JMenuItem sauvcom;
	private JMenuItem fermer;
	private JMenuItem netoyer;
	private JMenuItem exitter;

	
   // CONSTRUCTEURS
	
    
    public Pet() {
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
    	model = new StdPetModel();
    }
    
    private void createView() {
          final int frameWidth = 700;
          final int frameHeight = 400;
        
          mainFrame = new JFrame("XxNote++");
          mainFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));
          
          info = new JLabel(model.getFile() != null 
        		  ? model.getFile().getName() : "");
          doc = new JTextArea();
          menu = new JMenuBar();
          fichier = new JMenu("fichier");
          edition = new JMenu("edtion");
          quitter = new JMenu("quitter");
          creer = new JMenuItem("creer");
          creerapartir = new JMenuItem("creer a partir de ...");
          ouvrir = new JMenuItem("ouvrir ...");
          reouvrir = new JMenuItem("reouvrir");
          sauv = new JMenuItem("sauvegarder");
          sauvcom = new  JMenuItem("sauvegarder comme ...");
          fermer = new JMenuItem("fermer");
          netoyer = new JMenuItem("netoyer");
          exitter = new  JMenuItem("quitter");
    }
    
    
    private void placeComponents() {
    	fichier.add(creer);
    	fichier.add(creerapartir);
    	fichier.addSeparator();
    	fichier.add(ouvrir);
    	fichier.add(reouvrir);
    	fichier.addSeparator();
    	fichier.add(sauv);
    	fichier.add(sauvcom);
    	fichier.addSeparator();
    	fichier.add(fermer);
    	menu.add(fichier);
    	edition.add(netoyer);
    	menu.add(edition);
    	quitter.add(exitter);
    	menu.add(quitter);
    	mainFrame.setJMenuBar(menu);
    	JScrollPane p = new JScrollPane(doc);
    	mainFrame.add(p, BorderLayout.CENTER);  
    	JPanel p1 = new JPanel(new FlowLayout()); {
    		p1.add(info);
    	}
    	mainFrame.add(p1, BorderLayout.SOUTH);
    }
    
    private void createController() {
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(mainFrame,
						"Voulez vous vraiment quitter ?"
						, "le choix", JOptionPane.YES_NO_OPTION) 
						== JOptionPane.YES_OPTION) {
				System.exit(0);
				}
			}

		});
        model.addObserver(new Observer() {
            public void update(Observable o, Object arg) {
            	refresh();
            }
            
        });
        creer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setNewDocWithoutFile();
			}
		});        
        creerapartir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				JOptionPane.showMessageDialog(mainFrame, fc);
				try {
					model.setNewDocAndNewFile(fc.getSelectedFile());
				} catch (PetException e1) {
					JOptionPane.showMessageDialog(mainFrame,
							"erreur lors de l'ouverture du fichier",
							"404", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
        ouvrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				JOptionPane.showMessageDialog(mainFrame, fc);
				try {
					model.setNewDocFromFile(fc.getSelectedFile());
				} catch (PetException e1) {
					JOptionPane.showMessageDialog(mainFrame,
							"erreur lors de l'ouverture du fichier",
							"404", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
        reouvrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					model.resetCurrentDocWithCurrentFile();
				} catch (PetException e1) {
					JOptionPane.showMessageDialog(mainFrame,
							"erreur lors de l'ouverture du fichier",
							"404", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace(); 
					}
			}
		});  
        sauv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					model.saveCurrentDocIntoCurrentFile();
				} catch (PetException e1) {
					JOptionPane.showMessageDialog(mainFrame,
							"erreur lors de l'ouverture du fichier",
							"404", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace(); 
					}
			}
		});
        sauvcom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				JOptionPane.showMessageDialog(mainFrame, fc);
				try {
					model.saveCurrentDocIntoFile(fc.getSelectedFile());
				} catch (PetException e1) {
					JOptionPane.showMessageDialog(mainFrame,
							"erreur lors de l'ouverture du fichier",
							"404", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
        exitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(mainFrame,
						"Voulez vous vraiment quitter ?"
						, "le choix", JOptionPane.YES_NO_OPTION) 
						== JOptionPane.YES_OPTION) {
				System.exit(0);
				}
			}
        });
        fermer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.removeDocAndFile();
			}
        });
        netoyer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					model.clearDoc();
				} catch (PetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
        });
        doc.addFocusListener(new FocusAdapter() {
        	public void focusLost(FocusEvent e) {
        		try {
					model.clearDoc();
					model.getDocument().insertString(0, doc.getText(), null);
				} catch (PetException | BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        

    }
    
    
    private void refresh() {
    	
	  	
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Pet().display();
            }
        });
    }    

}
