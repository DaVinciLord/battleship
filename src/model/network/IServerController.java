package model.network;

import java.net.Socket;

import exceptions.network.ServerBadDataException;
import exceptions.network.ServerBadFormatException;
import exceptions.network.ServerEmptyDataException;
import exceptions.network.ServerNullDataException;
import model.player.IPlayer;

/**
 * Interface du controleur du serveur.
 * L'interet de ce controleur est de permettre l'échange de données
 * entre le controleur possèdant les sockets (et donc de communiquer avec l'adversaire)
 * et le modèle de notre application.
 * L'unique intéret de se controleur est de permettre de vérifier les données qui sont reçus par le
 * biais des Sockets, afin de vérifier leur intégrité avant de les envoyer au modèle.
 * Il permet aussi d'intéragir avec l'autre joueurs par le biais des Sockets.
 * 
 * @author Nicolas GILLE, Vincent METTON
 * @date 18 mars 2016
 */
public interface IServerController extends Runnable{

	/**
	 * Passe les données reçu du receiveData() au Model.
	 */
	public void sendData();
	
	/**
	 * Méthode permettant de recevoir les données issues de la socket
	 * sous forme de String.
	 * @param data Données reçu.
	 * @throws ServerBadFormatException 
	 * @throws ServerBadDataException 
	 * @throws ServerEmptyDataException 
	 * @throws ServerNullDataException 
	 * @pre
	 * 	data != null
	 * 	!data.equals("")
	 */
	public void receiveData() throws ServerNullDataException, ServerEmptyDataException, ServerBadDataException, ServerBadFormatException;
	
	/**
	 * Méthode permettant de vérifier l'intégrité des données reçus.
	 * @param data Données reçus.
	 * @return True si les données sont bonnes, fausses sinon.
	 * @throws ServerNullDataException 
	 * @throws ServerEmptyDataException 
	 * @throws ServerBadDataException 
	 * @throws ServerBadFormatException 
	 * @pre
	 * 	data != null
	 * 	!data.equals("")
	 */
	public boolean verifyData(String data) throws ServerNullDataException, ServerEmptyDataException, ServerBadDataException, ServerBadFormatException;
	
	/**
	 * Méthode retournant le nom canonique de notre adresse IP,
	 * que l'on fourni ensuite a l'adversaire pour qu'il se connecte.
	 * @return L'adresse IP sous forme canonique.
	 */
	public String getHostName();
	
	/**
	 * Méthode permettant de retourner les donnnées reçu
	 * de la socket.
	 * @return Les données sous formes de String.
	 */
	public String getData();
	
	/**
	 * Modificateur de l'attribut data.
	 * @param s Nouvelle valeur de data.
	 * @pre 
	 * 	s != null
	 * 	!s.equals("")
	 * @post
	 * 	getData() == s
	 */
	public void setData(String s);
	
	/**
	 * Permet d'accèder au Modèle du Controleur.
	 * @return Le modèle de l'application (ici le joueur).
	 */
	public IPlayer getModel();
	
	/**
	 * 
	 * @return La socket du client.
	 */
	public Socket getSocket();
}
