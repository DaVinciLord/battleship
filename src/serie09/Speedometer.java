package serie09;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;


public class Speedometer {
	
	private static final double MAX_SPEED = 336;
	private JFrame mainFrame;
	private SpeedometerModel model;
	private JButton swit;
	private JButton plus;
	private JButton minus;
	private JRadioButton unitkmh;
	private JRadioButton unitmph;
	private GraphicSpeedometer disp;
	private Border eb;
	
	
   // CONSTRUCTEURS
	
    
    public Speedometer() {
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
        model = new StdSpeedometerModel(1, MAX_SPEED);
    }
    
    private void createView() {
        final int frameWidth = 700;
        final int frameHeight = 400;
        
        mainFrame = new JFrame("Revenge");
        mainFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));
        disp = new GraphicSpeedometer(model);
        swit = new JButton("Turn On");
        plus = new JButton("+");
        minus = new JButton("-");
        unitkmh = new JRadioButton("kmh");
        unitmph = new JRadioButton("mph");
        eb = javax.swing.BorderFactory.createEtchedBorder();
    }
    
    
    private void placeComponents() {
    	JPanel p = new JPanel(new BorderLayout()); {
    		JPanel p1 = new JPanel(new GridLayout(0, 1)); {
    			JPanel p2 = new JPanel(); {
    				JPanel p3 = new JPanel(new GridLayout(0, 1)); {
    					p3.add(unitkmh);
    					p3.add(unitmph);
    					p3.setBorder(eb);
    				}
    				p2.add(p3);
    			}
    			p1.add(p2);
    			p2 = new JPanel(); {
    				JPanel p3 = new JPanel(new GridLayout(0, 1)); {
    					p3.add(plus);
    					p3.add(minus);
    				}
    				p2.add(p3);
    			}
    			p1.add(p2);
    			p2 = new JPanel(); {
    				p2.add(swit);
    			}
    			p1.add(p2);
    		}
    		p.add(p1, BorderLayout.WEST);
    		p.add(disp, BorderLayout.CENTER);
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
        plus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.speedUp();
				refresh();
				
			}
		});
        minus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.slowDown();
				refresh();
				
			}
		});
        swit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model.isOn()) {
						model.turnOff();
						swit.setText("Turn on");
				} else {
						model.turnOn();
						swit.setText("Turn off");
				}
				refresh();
			} 
			
		});
    }
    
    
    private void refresh() {
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Speedometer().display();
            }
        });
    }    

}

