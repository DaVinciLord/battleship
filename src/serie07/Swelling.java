package serie07;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Swelling {
	
    private JFrame mainFrame;
    private JButton changeSizeButton;
    private SwellingModel model;
    private JTextField textfield;

    // CONSTRUCTEURS
    
    public Swelling() {
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
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    // OUTILS
    
    private void createModel() {
        model = new StdSwellingModel();
    }
    
    private void createView() {
        int frameWidth = model.getDimension().width;
        int frameHeight = model.getDimension().height;
        
        mainFrame = new JFrame("Balloon");
        mainFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));
        
        changeSizeButton = new JButton("Modifier");
        textfield = new JTextField();
        
    }
    
    private void placeComponents() {
        JPanel p = new JPanel(); {
            p.add(changeSizeButton);
        }
        mainFrame.add(p, BorderLayout.CENTER);
        p = new JPanel(); {
            p.add(textfield);
        }
        mainFrame.add(p, BorderLayout.SOUTH);
        
    }
    
    private void createController() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        model.addObserver(new Observer() {
            public void update(Observable o, Object arg) {
            }
        });

        changeSizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.scale(Double.valueOf(textfield.getText()));
            }
        });
    }
    


    // POINT D'ENTREE
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Swelling().display();
            }
        });
    }
}
	
	
	
