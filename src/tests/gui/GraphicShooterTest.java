package tests.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.coordinates.Coordinates;
import model.player.IPlayer;
import model.player.Player;

import gui.board.GraphicBoardShooter;

public class GraphicShooterTest {
    
    private GraphicBoardShooter gbs;
    private JFrame frame;
    private IPlayer player;
    
    public GraphicShooterTest() {
        player = new Player(new Coordinates(6, 5, 4));
        gbs = new GraphicBoardShooter(player);
        frame = new JFrame("test tableau de tir");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gbs);
    }
    
    public void display() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GraphicShooterTest().display();
            }
        });

    }

}
