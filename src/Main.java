import javax.swing.SwingUtilities;

import controller.LocalController;
import model.coordinates.Coordinates;
import model.player.AIPlayer.AdvType;


public class Main {


        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new LocalController(new Coordinates(6, 6), AdvType.MEDIUM).display();;
                }
            });
        } 
    

}
