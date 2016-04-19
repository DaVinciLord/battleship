package tests.network;

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

public class ReleaseServerTest {
	public static void main(String[] args) throws ServerBadPortException, ServerSocketAcceptException, InterruptedException, ServerNullDataException, ServerEmptyDataException, ServerBadDataException, ServerBadFormatException, IOException {
		Server server = new Server(6969, 2, InetAddress.getByName("10.130.162.33"));
		ServerController sc = new ServerController(server);
		Socket socket = new Socket(InetAddress.getByName("10.130.162.32"), 6969);
		Scanner scan = new Scanner(System.in);
		sc.setSocket(socket);
		for (int i = 0; i < 10; i++) {
			System.out.println(sc.receiveData());
			System.out.println(sc.getData());
			sc.sendData(scan.nextLine());
		}
	}
}
