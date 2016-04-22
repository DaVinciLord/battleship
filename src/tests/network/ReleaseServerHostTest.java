package tests.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import exceptions.network.ServerBadDataException;
import exceptions.network.ServerBadFormatException;
import exceptions.network.ServerBadPortException;
import exceptions.network.ServerClosedSocketException;
import exceptions.network.ServerEmptyDataException;
import exceptions.network.ServerNullDataException;
import exceptions.network.ServerNullSocketException;
import exceptions.network.ServerSocketAcceptException;
import model.network.Server;
import model.network.ServerController;

public class ReleaseServerHostTest {
	public static void main(String[] args) throws ServerBadPortException, ServerSocketAcceptException, InterruptedException, ServerNullDataException, ServerEmptyDataException, ServerBadDataException, ServerBadFormatException, IOException, ServerClosedSocketException, ServerNullSocketException {
		/**
		 * Le host crée son ServerController prenant en paramètre un serveur avec son ip, son port et son backlog.
		 */
		ServerController sc = new ServerController(new Server(6969, 2, InetAddress.getByName("10.130.162.31")));
		
		/**
		 * On attends que le guest se connecte au serveur,
		 * et on modifie la connectSocket en conséquence.
		 */
		sc.setConnectedSocket(sc.getServer().connectSocket());	// Celle de mon adversaire
		
		/**
		 * On connecte une socket distante au serveur de son adversaire avec son ip et le port choisi.
		 */
		sc.setDistantServerSocket(new Socket(InetAddress.getByName("10.130.162.30"), 6969)); // Sert de bouche pour dial avec adversaire

		/**
		 * Envoie des données brutes.
		 */
		Scanner scan = new Scanner(System.in);
		
		/**
		 * On boucle 10 fois pour envoyé et recevoir les données et les afficher.
		 */
		for (int i = 0; i < 10; i++) {
			sc.sendData(scan.nextLine());
			System.out.println(sc.receiveData());
		}
		
		/**
		 * A la fin de la partie, on ferme le scanner, servant de dialogue.
		 * On ferme ensuite les deux sockets afin de nettoyer la mémoire.
		 */
		scan.close();
		sc.getServer().closeSocket(sc.getConnectedSocket());
		sc.getServer().closeSocket(sc.getDistantServerSocket());
	}
}
