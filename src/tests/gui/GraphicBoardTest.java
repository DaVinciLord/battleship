package tests.gui;

import gui.GraphicBoard;
import model.board.Board;
import model.board.Case;
import model.coordinates.Coordinates;
import model.coordinates.CoordinatesEvent;
import model.coordinates.CoordinatesListener;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class GraphicBoardTest {

    // ATTRIBUTS
    private JTextField text;
    private JFrame frame;
    private GraphicBoard<Case> board;

    public GraphicBoardTest() {
        // création de la vue
        frame = new JFrame("test tableau graphique");
        board = new GraphicBoard<Case>(new Board<Case>(new Coordinates(10, 10)), new Coordinates(-2, -1));
        board.setForeGround(true);
        text = new JTextField(10);
        text.setEditable(false);
        // placement des composants
        frame.add(board, BorderLayout.CENTER);
        frame.add(text, BorderLayout.SOUTH);
        // création du controleur
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.addCoordListener(new CoordinatesListener() {
            @Override
            public void doWithCoord(CoordinatesEvent e) {
                text.setText(e.getCoordinates().toString());
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
                new GraphicBoardTest().display();
            }
        });
    }

}