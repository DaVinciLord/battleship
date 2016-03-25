package model.network;

import java.net.InetAddress;
import java.net.Socket;

import exceptions.network.ServerBadPortException;
import exceptions.network.ServerClosedSocketException;
import exceptions.network.ServerNullSocketException;
import exceptions.network.ServerSchrodingerException;
import exceptions.network.ServerSocketAcceptException;

/**
 * Interface du serveur.
 * Ce serveur permet de créer les sockets qui seront utilisé par les joueurs
 * pour communiquer entre eux.
 * 
 * @author Nicolas GILLE, Vincent METTON
 * @date 18 mars 2016
 */
public interface IServer extends Runnable{
	
	/**
	 * Méthode permettant de lancer le serveur.
	 * Elle passe le boolean shutdown a false, 
	 * permettant a la boucle while() de tourner de manière continue 
	 * en arrière plan afin de traiter chacunes des potentielles demandes
	 * émanant des clients.
	 * @throws ServerSchrodingerException 
	 * @pre 
	 * 	isRunning() == false
	 * @post
	 * 	isRunning() == true
	 */
	public void start() throws ServerSchrodingerException;

	/**
	 * Méthode permettant de fermer le serveur.
	 * Elle passe uniquement le boolean shutdown a false afin de couper la boucle infini.
	 * @throws ServerSchrodingerException 
	 * @pre 
	 * 	isRunning() == true 	
	 * @post
	 * 	isRunning() == false
	 */
	public void shutdown() throws ServerSchrodingerException;
	
	/**
	 * Corps principal du serveur.
	 */
	public void run();
	
	/**
	 * Permet de connecter la socket issues de l'écoute.
	 * @return La socket permettant de communiquer avec le second joueur.
	 * @throws ServerSocketAcceptException 
	 */
	public Socket connectSocket() throws ServerSocketAcceptException;
	
	/**
	 * Méthode deconnectant la <code>Socket</code> et fermant son flux.  
	 * @throws ServerClosedSocketException 
	 * @throws ServerNullSocketException 
	 * @pre 
	 * 	isRunning() == true
	 * 	s != null
	 */
	public void closeSocket(Socket s) throws ServerClosedSocketException, ServerNullSocketException;
	
	/**
	 * Méthode permettant de définir un <code>Timeout</code> en milliseconde.
	 * @pre 
	 * 	s != null
	 * 	0 < timeout < MAX_TIMEOUT
	 */
	public void setTimeout(Socket s, int timeout);
	
	/**
	 * Méthode permettant de savoir l'état du serveur.
	 */
	public boolean isRunning();

	/**
	 * Retourne L'IP sous forme d'InetAddress.
	 */
	public InetAddress getIP();
	
	/**
	 * Retourne le nom du serveur.
	 */
	public String getHostName();
	
	/**
	 * Retourne le port sur lequel écoute le serveur.
	 */
	public int getPort();
	
	/**
	 * Permet de changer le port que l'on utilise par défaut sur le serveur.
	 * @throws ServerBadPortException 
	 * @pre
	 * 	1024 < port < 49152
	 * @post
	 * 	getPort() == port
	 */
	public void setPort(int port) throws ServerBadPortException;
}
