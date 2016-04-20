package controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import exceptions.network.ServerBadPortException;
import exceptions.network.ServerSocketAcceptException;
import gui.board.BoardDrawer;
import gui.board.GraphicBoard;
import model.board.IBoard;
import model.board.State;
import model.coordinates.Coordinates;
import model.network.Server;
import model.network.ServerController;
import model.player.AIPlayer;

public class SuperController {

    private AIPlayer p1;
    private ServerController sc;
    private JFrame mainFrame;
    private boolean turn;


    public SuperController(String adresse, Coordinates dimensions, boolean begin) {
        createModel(dimensions);
        createView();
        createServerController(adresse, Server.PORT);
        turn = begin;
    }
    
    private void createView() {
        BoardDrawer<State> pinceau = new BoardDrawer<State>() {

			@Override
			public void drawOnBoard(Graphics g, IBoard<State> board, Coordinates axes, float scale, float alpha) {
				// TODO Stub de la méthode généré automatiquement
				
			}

			@Override
			public void drawCase(Graphics g, IBoard<State> board, Coordinates axes, float scale, float alpha,
					Coordinates position) {
				// TODO Stub de la méthode généré automatiquement
				
			}
        };
        mainFrame = new JFrame();
        mainFrame.setPreferredSize(new Dimension(500, 500));
        mainFrame.add(new GraphicBoard<State>(p1.getShootGrid(), new Coordinates(-1, -2, 1), pinceau), BorderLayout.CENTER);
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void createModel(Coordinates dimensions) {
        p1 = new AIPlayer(dimensions);
    }


    private void createServerController(String adresse, int port) {
        try {
            try {
                this.sc = new ServerController(this.p1, new Server(port == 0 ? port : Server.PORT, Server.BACKLOG, InetAddress.getByName(adresse)));
            } catch (ServerSocketAcceptException | UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (ServerBadPortException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void startGame() {
        if (turn) {
        sc.setData(p1.shoot().toString());
        sc.sendData();
    }



    }
}