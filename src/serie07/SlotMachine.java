package serie07;

import java.awt.BorderLayout;
//import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SlotMachine {

    private JFrame mainFrame;
    private JButton gamble;
    private SlotModel model;
    private JLabel moneywon;
    private JLabel moneylost;
    private JLabel lpo;
    private JTextField result;
    // CONSTRUCTEURS
    
    public SlotMachine() {
        createModel();
        createView();
        placeComponents();
        createController();
    }
    
    public void display() {
        refresh();
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    
    // OUTILS
    
    private void createModel() {
        model = new StdSlotModel();
    }
    
    private void createView() {
        final int frameWidth = 300;
        final int frameHeight = 200;
        
        mainFrame = new JFrame("Casino");
        mainFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));
        
        gamble = new JButton("Gamble");
        moneywon = new JLabel("Gain = " + String.valueOf(model.moneyWon()));
        moneylost = new JLabel("Perte = " + String.valueOf(model.moneyLost()));
        lpo = new JLabel("Dernier Gain = " 
        + String.valueOf(model.lastPayout()));
        result = new JTextField(model.result());
        
    }
    
    private void placeComponents() {
        JPanel p = new JPanel(); {
            p.add(gamble);
        }
        mainFrame.add(p, BorderLayout.NORTH);
        p = new JPanel(); {
        	p.add(moneylost);
        	p.add(result);
        	p.add(moneywon);
        }
        mainFrame.add(p, BorderLayout.CENTER);

        p = new JPanel(); {
        	p.add(lpo);
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

        gamble.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.gamble();
                refresh();
            }
        });
    }
    
    private void refresh() {
    	moneywon.setText("Gain = " + String.valueOf(model.moneyWon()));
    	moneylost.setText("Perte = " + String.valueOf(model.moneyLost()));
    	lpo.setText("Dernier Gain = " + String.valueOf(model.lastPayout()));
    	result.setText(model.result());
    }

    // POINT D'ENTREE
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SlotMachine().display();
            }
        });
    }
}
