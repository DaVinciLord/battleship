package tests.gui;

import gui.board.BoardDrawer;
import gui.board.GraphicBoardLayers;
import model.board.Board;
import model.board.Case;
import model.board.IBoard;
import model.coordinates.Coordinates;
import model.coordinates.CoordinatesEvent;
import model.coordinates.CoordinatesListener;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class GraphicLayersTest {
    
    // ATTRIBUTS
    private JTextField text;
    private JFrame frame;
    private GraphicBoardLayers<Case> board;
    
    public GraphicLayersTest() {
        BoardDrawer<Case> pinceau = new BoardDrawer<Case>() {

            @Override
            public void drawOnBoard(Graphics g, IBoard<Case> board,
                    Coordinates axes, float scale, float alpha) {
                // TODO Stub de la méthode généré automatiquement
                
            }

            @Override
            public void drawCase(Graphics g, IBoard<Case> board,
                    Coordinates axes, float scale, float alpha,
                    Coordinates position) {
                // TODO Stub de la méthode généré automatiquement
                
            }
        };
        // création de la vue
        frame = new JFrame("test tableau graphique");
        board = new GraphicBoardLayers<Case>(new Board<Case>(new Coordinates(9, 9, 5)), new Coordinates(-2, -1, -3), pinceau);
        // test changements d'axe :
        board.updateAxesAndView(new Coordinates(-3, -2, -1));
        text = new JTextField(10);
        text.setEditable(false);
        // placement des composants
        frame.add(board, BorderLayout.CENTER);
        frame.add(text, BorderLayout.SOUTH);
        // création du controleur
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.addCoordinatesListener(new CoordinatesListener() {
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
                new GraphicLayersTest().display();
            }
        });
    }

}