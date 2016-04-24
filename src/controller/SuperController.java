package controller;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import exceptions.network.ServerBadDataException;
import exceptions.network.ServerBadFormatException;
import exceptions.network.ServerBadPortException;
import exceptions.network.ServerEmptyDataException;
import exceptions.network.ServerNullDataException;
import exceptions.network.ServerSocketAcceptException;
import gui.board.GraphicBoardShooter;
import gui.board.GraphicShipBoard;
import model.board.State;
import model.coordinates.Coordinates;
import model.coordinates.CoordinatesEvent;
import model.coordinates.CoordinatesListener;
import model.network.Server;
import model.network.ServerController;
import model.player.AIPlayer;
import model.player.Player;

public class SuperController {
    private boolean isHost;
    private Player p1;
    private GraphicBoardShooter gbs;
    private GraphicShipBoard gsb;
    private JFrame frame;
    private JTabbedPane jtp;
    private ServerController sc;
    
    public SuperController(Coordinates c, String ipAddressLocal, String ipAddressEnnemy, boolean isHost) {
        createModel(c, ipAddressLocal, ipAddressEnnemy, isHost);
        createView();
        placeComponents();
        createController();
        
        
    }

    private void createController() {
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gbs.addCoordinatesListener(new CoordinatesListener() {
            
            @Override
            public void doWithCoord(CoordinatesEvent e) {
                tourdujoueur(e.getCoordinates());
                tourdelennemie();
            }
        });
        
        p1.addCoordinatesListener(new CoordinatesListener() {
            
            @Override
            public void doWithCoord(CoordinatesEvent e) {
                if (e.getActionType().equals("ready")) {
                    if(!isHost) {
                        tourdelennemie();
                    }
                }
                if (e.getActionType().equals("dead")) {
                    
                    JOptionPane.showMessageDialog(frame, "Perdu");
                }
            }
        });
     
    }

    private void placeComponents() {
        jtp.add("Mon Board", gsb);
        jtp.add("Son Board", gbs);
        frame.add(jtp);
    }

    private void createView() {
        frame = new JFrame("test tableau de tir");
        
        gbs = new GraphicBoardShooter(p1);
        gbs.setMyTurn(isHost);
        gsb = new GraphicShipBoard(p1);
        jtp = new JTabbedPane();
    }

    private void createModel(Coordinates c, String ipAddressLocal, String ipAddressEnnemy, boolean isHost) {
        this.isHost = isHost;
        createPlayer(c);
        createServer(ipAddressLocal, ipAddressEnnemy, isHost);
    }

    private void createPlayer(Coordinates c) {
        p1 = new Player(c);
    }

    private void createServer(String ipAddressLocal, String ipAddressEnnemy, boolean isHost) {
        try {
            sc = new ServerController(new Server(Server.PORT, Server.BACKLOG, InetAddress.getByName(ipAddressLocal)));                     
           
            if(isHost) {
                sc.setConnectedSocket(sc.getServer().connectSocket());

                sc.setDistantServerSocket(new Socket(InetAddress.getByName(ipAddressEnnemy), Server.PORT));
                
            } else {
                boolean ok = false;
                while(!ok) {
                    try {
                        sc.setDistantServerSocket(new Socket(InetAddress.getByName(ipAddressEnnemy), Server.PORT));
                        ok = true;
                    } catch (ConnectException e) {
                        System.out.println("Je suis dans le catch");
                        JOptionPane jp = new JOptionPane();
                        jp.grabFocus();
                        ok = jp.showConfirmDialog(null, "Réésayer") != 0;
                        System.out.println(ok);
                    }
                }

                System.out.println("Je suis après le catch");
                sc.setConnectedSocket(sc.getServer().connectSocket());
            }
        } catch (ServerSocketAcceptException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ServerBadPortException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }



    private void tourdujoueur(Coordinates c) {
        sc.sendData("Coordinates:" + c.toString());
        try {
            String state = sc.receiveData();
            String datas[] = state.split(":");
            if(datas[0].equals("State")) {
                p1.updateFireGrid(c, State.valueOf(datas[1].toUpperCase()));
            }

        } catch (ServerNullDataException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ServerEmptyDataException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ServerBadDataException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ServerBadFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    private void tourdelennemie() {
        jtp.setSelectedIndex(0);
        try {
            String data = sc.receiveData();
            String[] s = data.split(":");
            if(s[0].equals("Coordinates")) {
                Coordinates c = new Coordinates(s[1]);
                State st = p1.takeHit(c);
                sc.sendData("State:" + st.toString());
                gbs.setMyTurn(true);
            }
        } catch (ServerNullDataException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ServerEmptyDataException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ServerBadDataException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ServerBadFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    
    
    public void display() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}
