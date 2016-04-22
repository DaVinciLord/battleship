package tests.network;

import exceptions.network.ServerClosedSocketException;
import exceptions.network.ServerNullSocketException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import exceptions.network.ServerBadDataException;
import exceptions.network.ServerBadFormatException;
import exceptions.network.ServerBadPortException;
import exceptions.network.ServerEmptyDataException;
import exceptions.network.ServerNullDataException;
import exceptions.network.ServerSocketAcceptException;
import model.network.Server;
import model.network.ServerController;

public class ReleaseServerGuestTest {
	public static void main(String[] args) throws ServerBadPortException, ServerSocketAcceptException, InterruptedException, ServerNullDataException, ServerEmptyDataException, ServerBadDataException, ServerBadFormatException, IOException, ServerClosedSocketException, ServerNullSocketException {
		/**
		 * Le guest crée son ServerController prenant en paramètre un serveur avec son ip, son port et son backlog.
		 */
		ServerController sc = new ServerController(new Server(6969, 2, InetAddress.getByName("10.130.162.30")));

        /**
         * On connecte une socket distante au serveur de son adversaire avec son ip et le port choisi.
         */
		sc.setDistantServerSocket(new Socket(InetAddress.getByName("10.130.162.31"), 6969));


        /**
         * On attends que le host se connecte au serveur,
         * et on modifie la connectSocket en conséquence.
         */
        sc.setConnectedSocket(sc.getServer().connectSocket());	// Celle de mon adversaire

        /**
         * On boucle 10 fois pour envoyé et recevoir les données et les afficher.
         */
        Scanner scan = new Scanner(System.in);
		for (int i = 0; i < 10; i++) {
			System.out.println(sc.receiveData());
			sc.sendData(scan.nextLine());
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
