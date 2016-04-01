package tests.gui;

import gui.board.BoardDrawer;
import gui.board.GraphicBoard;
import model.board.Board;
import model.board.Case;
import model.board.IBoard;
import model.coordinates.Coordinates;
import model.coordinates.CoordinatesEvent;
import model.coordinates.CoordinatesListener;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class GraphicBoardTest {

    // ATTRIBUTS
    private JTextField text;
    private JFrame frame;
    private GraphicBoard<Case> board;
    private JButton scale;
    private float scaleFactor;

    public GraphicBoardTest() {
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
        board = new GraphicBoard<Case>(new Board<Case>(new Coordinates(10, 10, 10)), new Coordinates(-2, -1, 3), pinceau);
        scale = new JButton("scale");
        // test de changement d'axe
        // board.updateAxesAndRepaint(new Coordinates(-1, 5, -2));
        // test de scale
        // board.scaleAndRepaint(0.5f);
        
        board.setCaseActive(true);
        text = new JTextField(10);
        text.setEditable(false);
        // placement des composants
        frame.add(board, BorderLayout.CENTER);
        frame.add(text, BorderLayout.SOUTH);
        frame.add(scale, BorderLayout.NORTH);
        // création du controleur
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.addCoordListener(new CoordinatesListener() {
            @Override
            public void doWithCoord(CoordinatesEvent e) {
                text.setText(e.getCoordinates().toString());
            }
        });
        scaleFactor = 1.f;
        scale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (scaleFactor < 2.f) {
                    scaleFactor += 0.5f;
                } else {
                    scaleFactor = 0.5f;
                }
                board.scaleAndRepaint(scaleFactor);
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