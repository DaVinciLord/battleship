package project;

/**
 * Classe s'occupant uniquement de l'échange des données entre les clients.
 * Celle-ci effectue constamment la même routine sur l'envoie et la reception des données.
 * 
 * @author Nicolas GILLE
 * @date 25 mars 2016
 */
public interface IHandlerSocketPlayer extends Runnable {
	
	/**
	 * Routine d'execution du HandleSocketPlayer.
	 */
	public void run();
	
	/**
	 * Méthode permettant d'envoyer les données au l'autre client.
	 */
	public void sendData();
	
	/**
	 * Méthode permettant de recevoir les données issues de la Socket de l'autre client.
	 */
	public void receiveData();
}
