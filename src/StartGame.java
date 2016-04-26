import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controller.LocalController;
import controller.SuperController;
import exceptions.ship.OverPanamaException;
import model.coordinates.Coordinates;
import model.player.AIPlayer;
import model.player.AIPlayer.AdvType;


public class StartGame {
    
    public static File ADRESSES = new File("bataille navale, adresses IP");
    
    // ATTRIBUTS
    
    private JFrame frame;
    
    private JTextField yourAdress;
    private JTextField advAdress;
    private JButton startAsHost;
    private JButton startAsGuest;
    private JButton memYourAdress;
    private JButton memAdvAdress;
    private Vector<String> adresses;
    private JComboBox<String> chooseYourAdress;
    private JComboBox<String> chooseAdvAdress;
    
    private JButton startLocal;
    private JComboBox<String> chooseIA;
    AIPlayer.AdvType advIA;
    
    private Coordinates dimensions;
    private JTextField dimensionsField;
    
    private WindowListener wl;
    
    // CONSTRUCTEUR
    
    public StartGame() throws IOException {

        dimensions = null;
        adresses = new Vector<String>();
        ADRESSES.createNewFile(); // crée le fichier s'il n'existe 
        BufferedReader br = new BufferedReader(new FileReader(ADRESSES));
        String buf;
        while ((buf = br.readLine()) != null) {
            adresses.add(buf);
        }
        br.close();
        
        advIA = AdvType.NOOB;
                
        createView();
        placeComponents();
        createController();
    }
    
    // OUTILS DE CONSTRUCTION
    
    private void createView() {
        frame = new JFrame("Bataille navale, spatiale et interdimensionnelle");
        yourAdress = new JTextField(16);
        advAdress = new JTextField(16);
        dimensionsField = new JTextField(16);
        startLocal = new JButton("commencer une partie contre l'IA");
        startAsHost = new JButton("créer une partie en ligne");
        startAsGuest = new JButton("rejoindre une partie en ligne");
        memYourAdress = new JButton("mémorisez cette adresse");
        memAdvAdress = new JButton("mémorisez cette adresse");
        
        chooseYourAdress = new JComboBox<String>(adresses);
        chooseAdvAdress = new JComboBox<String>(adresses);
        
        chooseIA = new JComboBox<String>(new String[] {"trop facile", "facile", "medium"});
        
    }
    
    private void placeComponents() {
        frame.setLayout(new GridBagLayout());
        // Panel des dimensions
        JPanel p = new JPanel(); {
            p.add(new JLabel("Entrez les dimensions du plateau (2 ou plus), séparés par des virgules : "));
            p.add(dimensionsField);
        }
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        frame.add(p, gbc);
        // Panel partie locale
        p = new JPanel(new GridLayout(0,1)); {
            p.add(chooseIA);
            p.add(startLocal);
        }
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        frame.add(p, gbc);
        // Panel partie en ligne
        p = new JPanel(new GridBagLayout()); {
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            p.add(new JLabel("Votre adresse ip"), gbc);
            gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            p.add(yourAdress, gbc);
            gbc = new GridBagConstraints();
            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            p.add(chooseYourAdress, gbc);
            gbc = new GridBagConstraints();
            gbc.gridx = 3;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            p.add(memYourAdress, gbc);
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            p.add(new JLabel("adresse ip de l'adversaire"), gbc);
            gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            p.add(advAdress, gbc);
            gbc = new GridBagConstraints();
            gbc.gridx = 2;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            p.add(chooseAdvAdress, gbc);
            gbc = new GridBagConstraints();
            gbc.gridx = 3;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            p.add(memAdvAdress, gbc);
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.gridheight = 1;
            p.add(startAsHost, gbc);
            gbc = new GridBagConstraints();
            gbc.gridx = 2;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.gridheight = 1;
            p.add(startAsGuest, gbc);
        }
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        frame.add(p, gbc);
    }
    
    
    private void createController() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        wl = new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
                frame.setVisible(true);
                e.getWindow().removeWindowListener(wl);
            }
        };
        
        chooseIA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                advIA = AdvType.values()[chooseIA.getSelectedIndex()];
            }
        });
        
        chooseYourAdress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yourAdress.setText((String) chooseYourAdress.getSelectedItem());
            }
        });
        
        chooseAdvAdress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                advAdress.setText((String) chooseAdvAdress.getSelectedItem());
            }
        });
        
        memYourAdress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!adresses.contains(yourAdress.getText())) {
                    adresses.add(yourAdress.getText());
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(ADRESSES, true));
                        bw.append(yourAdress.getText());
                        bw.newLine();
                        bw.close();
                    } catch (IOException e1) {
                        // TODO Bloc catch généré automatiquement
                        e1.printStackTrace();
                    }
                }
            }
        });
        
        memAdvAdress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!adresses.contains(advAdress.getText())) {
                    adresses.add(advAdress.getText());
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(ADRESSES, true));
                        bw.append(advAdress.getText());
                        bw.newLine();
                        bw.close();
                    } catch (IOException e1) {
                        // TODO Bloc catch généré automatiquement
                        e1.printStackTrace();
                    }
                }
            }
        });
        
        startAsHost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                if (!dimensionsField.getText().equals("") && !yourAdress.getText().equals("") && !advAdress.getText().equals("")) {
                    dimensions = new Coordinates(dimensionsField.getText());
                    try {
                        SuperController sc = new SuperController(dimensions, yourAdress.getText(),advAdress.getText(), true);
	                    sc.getFrame().addWindowListener(wl);
	                    sc.display();
                    } catch (NumberFormatException e) {
                		JOptionPane.showMessageDialog(frame,
                                "Les dimensions doivent être des entiers séparés par des virgules",
                                "Erreur de dimensions", JOptionPane.ERROR_MESSAGE);
                	} catch (OverPanamaException e) {
                		JOptionPane.showMessageDialog(frame,
                                "Avec les dimensions choisies, vous ne pourrez pas placer les plus grands navires",
                                "Tableau trop petit", JOptionPane.ERROR_MESSAGE);
					}
                }
            }
            
        });
        
        startAsGuest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!dimensionsField.getText().equals("") && !yourAdress.getText().equals("") && !advAdress.getText().equals("")) {
                    try {
                        dimensions = new Coordinates(dimensionsField.getText());
                        SuperController sc = new SuperController(dimensions, yourAdress.getText(), advAdress.getText(), false);
                        sc.getFrame().addWindowListener(wl);
                        sc.display();
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(frame,
                                "Les dimensions doivent être des entiers séparés par des virgules",
                                "Erreur de dimensions", JOptionPane.ERROR_MESSAGE);
                    } catch (OverPanamaException e) {
                    	JOptionPane.showMessageDialog(frame,
                            "Avec les dimensions choisies, vous ne pourrez pas placer les plus grands navires",
                            "Tableau trop petit", JOptionPane.ERROR_MESSAGE);
					}
                }
            }
        });
        
        startLocal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!dimensionsField.getText().equals("")) {
                	try {
	                	dimensions = new Coordinates(dimensionsField.getText());
	                	frame.setVisible(false);
	                    LocalController lc = new LocalController(dimensions, advIA);
	                    lc.getFrame().addWindowListener(wl);
	                    lc.display();
                	} catch (NumberFormatException e) {
                		JOptionPane.showMessageDialog(frame,
                                "Les dimensions doivent être des entiers séparés par des virgules",
                                "Erreur de dimensions", JOptionPane.ERROR_MESSAGE);
                	} catch (OverPanamaException e) {
                		JOptionPane.showMessageDialog(frame,
                                "Avec les dimensions choisies, vous ne pourrez pas placer les plus grands navires",
                                "Tableau trop petit", JOptionPane.ERROR_MESSAGE);
					}
                }
            }
            
        });
    }
    
    public void display() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new StartGame().display();
                } catch (IOException e) {
                    // TODO Bloc catch généré automatiquement
                    e.printStackTrace();
                }
            }
        });
    } 
}
