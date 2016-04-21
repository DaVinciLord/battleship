package tests.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.coordinates.Coordinates;
import model.player.IPlayer;
import model.player.Player;

import gui.board.GraphicShipBoard;

public class GraphicShipTest {
	
	private GraphicShipBoard gsb;
    private JFrame frame;
    private IPlayer player;
    
    public GraphicShipTest() {
        player = new Player(new Coordinates(6, 5, 4));
        gsb = new GraphicShipBoard(player);
        frame = new JFrame("test tableau de navires");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gsb);
    }
    
    public void display() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GraphicShipTest().display();
            }
        });
	}
}
