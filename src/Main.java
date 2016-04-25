import javax.swing.SwingUtilities;

import controller.LocalController;
import controller.SuperController;
import exceptions.ship.OverPanamaException;
import model.coordinates.Coordinates;
import model.player.AIPlayer.AdvType;


public class Main {


        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    // new SuperController(new Coordinates(6, 6),"10.130.162.30", "10.130.162.31",false).display(); 
                    try {
						new LocalController(new Coordinates(6, 6, 6), AdvType.MEDIUM).display();
					} catch (OverPanamaException e) {
						// TODO Bloc catch généré automatiquement
						e.printStackTrace();
					}
                }
            });
        } 
    

}
