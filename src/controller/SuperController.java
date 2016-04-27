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
import exceptions.network.ServerClosedSocketException;
import exceptions.network.ServerEmptyDataException;
import exceptions.network.ServerNullDataException;
import exceptions.network.ServerNullSocketException;
import exceptions.network.ServerSocketAcceptException;
import exceptions.ship.OverPanamaException;
import gui.board.GraphicBoardShooter;
import gui.board.GraphicShipBoard;
import model.board.State;
import model.coordinates.Coordinates;
import model.coordinates.CoordinatesEvent;
import model.coordinates.CoordinatesListener;
import model.network.Server;
import model.player.Player;

public class SuperController {
    private static final int TIMEOUT = 30000;
    private boolean isHost;
    private Player p1;
    private GraphicBoardShooter gbs;
    private GraphicShipBoard gsb;
    private JFrame frame;
    private JTabbedPane jtp;
    private Server sc;
    private boolean alive;

    public SuperController(Coordinates c, String ipAddressLocal, String ipAddressEnnemy, boolean isHost)
            throws OverPanamaException {
        createModel(c, ipAddressLocal, ipAddressEnnemy, isHost);
        createView();
        placeComponents();
        createController();

    }

    private void createController() {
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
                    if (!isHost) {
                        tourdelennemie();
                    } else {
                        gbs.setMyTurn(isHost);
                    }
                }
                if (e.getActionType().equals("dead")) {
                    sc.sendData("Victory:Bravo");
                    alive = false;
                    JOptionPane.showMessageDialog(frame, "Perdu");
                    rageQuitServer();
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

        gsb = new GraphicShipBoard(p1);
        jtp = new JTabbedPane();
    }

    private void createModel(Coordinates c, String ipAddressLocal, String ipAddressEnnemy, boolean isHost)
            throws OverPanamaException {
        this.isHost = isHost;
        alive = true;
        c = createServer(ipAddressLocal, ipAddressEnnemy, isHost, c);
        createPlayer(c);
    }

    private void createPlayer(Coordinates c) throws OverPanamaException {
        p1 = new Player(c);
    }

    @SuppressWarnings("static-access")
    private Coordinates createServer(String ipAddressLocal, String ipAddressEnnemy, boolean isHost, Coordinates c) {
        try {
            sc = new Server(Server.PORT, Server.BACKLOG, InetAddress.getByName(ipAddressLocal));

            if (isHost) {
                sc.setConnectedSocket(sc.connectSocket());

                sc.setDistantServerSocket(new Socket(InetAddress.getByName(ipAddressEnnemy), Server.PORT));
                
                sc.sendData("Coordinates:" + c);
            } else {
                boolean ok = false;
                while (!ok) {
                    try {
                        sc.setDistantServerSocket(new Socket(InetAddress.getByName(ipAddressEnnemy), Server.PORT));
                        ok = true;
                    } catch (ConnectException e) {

                        JOptionPane jp = new JOptionPane();
                        jp.grabFocus();
                        ok = jp.showConfirmDialog(null, "Réésayer") != JOptionPane.YES_OPTION;

                    }
                }

                sc.setConnectedSocket(sc.connectSocket());
                sc.setTimeout(sc.getConnectedSocket(), TIMEOUT);
                sc.setTimeout(sc.getDistantServerSocket(), TIMEOUT);
                if (ServOut.receiveData(sc) != RetVal.COORDINATES) {
                    rageQuitServer();
                }

                c = ServOut.getCoordinates();
            }
        } catch (ServerSocketAcceptException e) {
            e.printStackTrace();
        } catch (ServerBadPortException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return c;
    }

    private void tourdujoueur(Coordinates c) {

        sc.sendData("Coordinates:" + c.toString());
        if (ServOut.receiveData(sc) != RetVal.STATE) {
            rageQuitServer();
        }
        if (!sc.getConnectedSocket().isClosed() && !sc.getDistantServerSocket().isClosed()) {
            p1.updateFireGrid(c, ServOut.getState());
        }
    }

    private void tourdelennemie() {
        if (!sc.getConnectedSocket().isClosed() && !sc.getDistantServerSocket().isClosed()) {
            jtp.setSelectedIndex(0);
            if (ServOut.receiveData(sc) != RetVal.COORDINATES) {
                rageQuitServer();
            }

            State st = p1.takeHit(ServOut.getCoordinates());
            if (!sc.getConnectedSocket().isClosed() && !sc.getDistantServerSocket().isClosed()) {
                sc.sendData("State:" + st.toString());
                gbs.setMyTurn(true);
            }
        }
    }

    public JFrame getFrame() {
        return frame;
    }

    private void rageQuitServer() {
        if (alive) {
            JOptionPane.showMessageDialog(frame, "Vous avez Gagné, Bravo");
        }

        try {
            sc.closeSocket(sc.getConnectedSocket());
            sc.closeSocket(sc.getDistantServerSocket());
        } catch (ServerClosedSocketException | ServerNullSocketException e) {

        }
        frame.dispose();
    }

    private static enum RetVal {
        COORDINATES("Coordinates"), STATE("State"), VICTORY("Victory"), ERROR("Erreur");

        private RetVal(String s) {

        }

    }

    private static class ServOut {
        private static Coordinates c;
        private static State state;

        private static State getState() {
            return state;
        }

        private static Coordinates getCoordinates() {
            return c;
        }

        private static RetVal receiveData(Server sc) {

            String data;
            try {
                data = sc.receiveData();

                String[] s = data.split(":");
                RetVal rv = RetVal.valueOf(s[0].toUpperCase());
                if (rv == RetVal.COORDINATES) {
                    c = new Coordinates(s[1]);
                }
                if (rv == RetVal.STATE) {
                    state = State.valueOf(s[1].toUpperCase());
                }
                if (rv == RetVal.ERROR) {
                    JOptionPane.showMessageDialog(null, "Erreur");
                }
                return rv;
            } catch (ServerNullDataException | ServerEmptyDataException | ServerBadDataException
                    | ServerBadFormatException e) {
            }

            return RetVal.ERROR;

        }

    }

    public void display() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
