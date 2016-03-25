package controller;

import java.net.InetAddress;

import exceptions.network.ServerBadPortException;
import model.coordinates.Coordinates;
import model.network.Server;
import model.network.ServerController;
import model.player.IPlayer;

public class SuperController {

    private IPlayer p1;
    private ServerController sc;



    public SuperController(String adresse) {
        createModel();
        createView();
        createServerController(adresse);
    }
    
    private void createView() {
        // TODO Auto-generated method stub
        
    }

    private void createModel() {
        
    }


    private void createServerController(String adresse, int port) {
        try {
            this.sc = new ServerController(this.p1, new Server(port == 0 ? port : Server.PORT, Server.BACKLOG, InetAddress.getByName(adresse)));
        } catch (ServerBadPortException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    public void startGameSinglePlayer(Coordinates dimension) {
        
    }
}