package model.network;

import java.net.InetAddress;
import java.net.Socket;

import exceptions.network.ServerBadDataException;
import exceptions.network.ServerBadFormatException;
import exceptions.network.ServerBadPortException;
import exceptions.network.ServerClosedSocketException;
import exceptions.network.ServerEmptyDataException;
import exceptions.network.ServerNullDataException;
import exceptions.network.ServerNullSocketException;
import exceptions.network.ServerSocketAcceptException;

/**
 * Interface de la classe Server.
 * 
 * @author Nicolas GILLE, Vincent METTON
 * @date 18 mars 2016
 */
public interface IServer {
    /**
     * 
     * @param data
     */
    public void sendData(String data);

    /**
     * 
     * @return
     * @throws ServerNullDataException
     * @throws ServerEmptyDataException
     * @throws ServerBadDataException
     * @throws ServerBadFormatException
     */
    public String receiveData() throws ServerNullDataException, ServerEmptyDataException, ServerBadDataException, ServerBadFormatException;

    /**
     * 
     * @param data
     * @return
     * @throws ServerNullDataException
     * @throws ServerEmptyDataException
     * @throws ServerBadDataException
     * @throws ServerBadFormatException
     */
    public boolean verifyData(String data) throws ServerNullDataException, ServerEmptyDataException, ServerBadDataException, ServerBadFormatException;

    /**
     * 
     * @return
     */
    public String getData();

    /**
     * 
     * @param s
     */
    public void setData(String s);

    /**
     * 
     * @return
     */
    public Socket getConnectedSocket();
    
    /**
     * 
     * @param s
     */
    public void setConnectedSocket(Socket s);

    /**
     * 
     * @return
     */
    public Socket getDistantServerSocket();
    
    /**
     * 
     * @param s
     */
    public void setDistantServerSocket(Socket s);
    
    /**
     * Permet de connecter la socket issues de l'Ã©coute.
     * @return La socket permettant de communiquer avec le second joueur.
     * @throws ServerSocketAcceptException 
     */
    public Socket connectSocket() throws ServerSocketAcceptException;
    
    /**
     * MÃ©thode deconnectant la <code>Socket</code> et fermant son flux.  
     * @throws ServerClosedSocketException 
     * @throws ServerNullSocketException 
     * @pre 
     *  isRunning() == true
     *  s != null
     */
    public void closeSocket(Socket s) throws ServerClosedSocketException, ServerNullSocketException;
    
    /**
     * MÃ©thode permettant de dÃ©finir un <code>Timeout</code> en milliseconde.
     * @pre 
     *  s != null
     *  0 < timeout < MAX_TIMEOUT
     */
    public void setTimeout(Socket s, int timeout);
    
    /**
     * Retourne L'IP sous forme d'InetAddress.
     */
    public InetAddress getIP();
    
    /**
     * Retourne le nom du serveur.
     */
    public String getHostName();
    
    /**
     * Retourne le port sur lequel Ã©coute le serveur.
     */
    public int getPort();
    
    /**
     * Permet de changer le port que l'on utilise par dÃ©faut sur le serveur.
     * @throws ServerBadPortException 
     * @pre
     *  1024 < port < 49152
     * @post
     *  getPort() == port
     */
    public void setPort(int port) throws ServerBadPortException;
}
