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
		Server server = new Server(6969, 2, InetAddress.getByName("10.130.162.31"));
		ServerController sc = new ServerController(server);
		Scanner scan = new Scanner(System.in);
		sc.setSocket(new Socket(InetAddress.getByName("10.130.162.30"), 6969));
		for (int i = 0; i < 10; i++) {
			sc.sendData(scan.nextLine());
			System.out.println(sc.receiveData());
			System.out.println(sc.getData());
		}
	}
}
