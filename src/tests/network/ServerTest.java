package tests.network;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

import exceptions.network.ServerBadPortException;
import exceptions.network.ServerClosedSocketException;
import exceptions.network.ServerNullSocketException;
import exceptions.network.ServerSchrodingerException;
import model.network.Server;

public class ServerTest {
	private final static int BAD_BACKLOG = -2;
	private final static int GOOD_BACKLOG = 2;
	private final static int NEGATIVE_PORT = -12345;
	private final static int BAD_PORT = 99999;
	private final static int GOOD_PORT = 12345;
	
	private final static String SUCCESS = "Test passé avec succès : ";
	
//	private final static Server s = new Server(6666, 10);

	private static void constructor1() throws ServerSchrodingerException {
		try {
			Server server = new Server();
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(SUCCESS + "constructor1()");
	}

	private static void constructor2() throws ServerSchrodingerException{
		try {
			Server server = new Server(BAD_PORT, BAD_BACKLOG);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(SUCCESS + "constructor2()");
	}
	
	private static void constructor3() throws ServerSchrodingerException {
		try {
			Server server = new Server(GOOD_PORT, BAD_BACKLOG);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(SUCCESS + "constructor3()");
	}

	/**
	 * @TODO
	 * 	Il faudra que je traite le port negatif car il ne fonctionne pas.
	 * @throws ServerSchrodingerException
	 */
	private static void constructor4() throws ServerSchrodingerException {
		try {
			Server server = new Server(NEGATIVE_PORT, GOOD_BACKLOG);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(SUCCESS + "constructor4()");
	}

	private static void constructor5() throws ServerSchrodingerException {
		try {
			Server server = new Server(GOOD_PORT, GOOD_BACKLOG);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(SUCCESS + "constructor5()");
	}

	private static void startServeur1() throws ServerSchrodingerException {
		Server s = null;
		try {
			s = new Server(6666, 10);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.start();
		System.out.println(SUCCESS + "startServeur1()");
	}

	private static void startServeur2() throws ServerSchrodingerException {
		Server s = null;
		try {
			s = new Server(6666, 10);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.start();
		s.start();
		System.out.println(SUCCESS + "startServeur2()");
	}

	private static void stopServeur1() throws ServerSchrodingerException {
		Server s = null;
		try {
			s = new Server(6666, 10);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.start();
		s.shutdown();
		System.out.println(SUCCESS + "stopServeur1()");
	}

	private static void stopServeur2() throws ServerSchrodingerException {
		Server s = null;
		try {
			s = new Server(6666, 10);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.shutdown();
		System.out.println(SUCCESS + "stopServeur2()");
	}

	private static void stopServeur3() throws ServerSchrodingerException {
		Server s = null;
		try {
			s = new Server(6666, 10);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.start();
		s.shutdown();
		s.shutdown();
		System.out.println(SUCCESS + "stopServeur3()");
	}

	private static void toStringServer() throws ServerSchrodingerException {
		Server s = null;
		try {
			s = new Server(6666, 10);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(s.toString());
		System.out.println(SUCCESS + "toStringServer()");
	}

	private static void closeSocket1() throws ServerSchrodingerException, ServerClosedSocketException, ServerNullSocketException {
		Server s = null;
		try {
			s = new Server(6666, 10);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Socket sock = new Socket();
		s.closeSocket(sock);
		System.out.println(SUCCESS + "closeSocket1()");		
	}

	private static void closeSocket2() throws ServerSchrodingerException, ServerClosedSocketException, IOException, ServerNullSocketException {
		Server s = null;
		try {
			s = new Server(6666, 10);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Socket sock = new Socket();
		sock.close();
		s.closeSocket(sock);
		System.out.println(SUCCESS + "closeSocket2()");		
	}

	private static void closeSocket3() throws ServerSchrodingerException, ServerClosedSocketException, ServerNullSocketException {
		Server s = null;
		try {
			s = new Server(6666, 10);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Socket sock = null;
		s.closeSocket(sock);
		System.out.println(SUCCESS + "closeSocket3()");		
	}

	private static void setTimeout() throws SocketException {
		Server s = null;
		try {
			s = new Server(6666, 10);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Socket sock = new Socket();
		s.setTimeout(sock, 10);
		System.out.println(sock.getSoTimeout() + "");
		s.setTimeout(sock, -10);
		System.out.println(sock.getSoTimeout() + "");
		s.setTimeout(sock, 50);
		System.out.println(sock.getSoTimeout() + "");
		s.setTimeout(sock, 0);
		System.out.println(sock.getSoTimeout() + "");
		System.out.println(SUCCESS + "setTimeout()");		
	}

	private static void isRunning() throws ServerSchrodingerException {
		Server s = null;
		try {
			s = new Server(6666, 10);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.start();
		System.out.println(s.isRunning());
		s.shutdown();
		System.out.println(s.isRunning());
	}

	private static void getIP() throws ServerSchrodingerException {
		Server s = null;
		try {
			s = new Server(6666, 10);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(s.getIP());
		s.start();
		System.out.println(s.getIP());
		s.shutdown();
		System.out.println(s.getIP());
	}
	
	private static void getHostName() throws ServerSchrodingerException {
		Server s = null;
		try {
			s = new Server(6666, 10);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(s.getHostName());
		s.start();
		System.out.println(s.getHostName());
		s.shutdown();
		System.out.println(s.getHostName());
	}
	
	private static void getPort() throws ServerSchrodingerException {
		Server s = null;
		try {
			s = new Server(6666, 10);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(s.getPort());
		s.start();
		System.out.println(s.getPort());
		s.shutdown();
		System.out.println(s.getPort());
	}
	
	private static void setPort() throws ServerSchrodingerException, ServerBadPortException {
		Server s = null;
		try {
			s = new Server(6666, 10);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(s.toString());
		s.setPort(7676);
		System.out.println(s.toString());
		s.setPort(-7676);
		System.out.println(s.toString());
		s.setPort(87676);
		System.out.println(s.toString());
	}
	
	private static void scanningNetworkInterface() {
		Server s = null;
		try {
			s = new Server(6666, 10);
		} catch (ServerBadPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("IPv4");
		Map<String, String> map = s.getNetworkInterfaceScan().getIPv4();
		for (String str : map.keySet()) {
			System.out.println(str.toString() + " " + map.get(str).toString());
		}
		System.out.println("IPv6");
		map = s.getNetworkInterfaceScan().getIPv6();
		for (String str : map.keySet()) {
			System.out.println(str.toString() + " " + map.get(str).toString());
		}
	}
	
	public static void main(String[] args) throws ServerSchrodingerException, ServerBadPortException, ServerClosedSocketException, IOException, ServerNullSocketException {
//		constructor1();
//		constructor2();
//		constructor3();
//		constructor4();
//		constructor5();
//		startServeur1();
//		startServeur2();
//		stopServeur1();
//		stopServeur2();
//		stopServeur3();
//		toStringServer();
//		closeSocket1();
//		closeSocket2();
//		closeSocket3();
//		setTimeout();
//		isRunning();
//		getIP();
//		getHostName();
//		getPort();
//		setPort();
//		scanningNetworkInterface();
		System.out.println(Server.class.getSimpleName());
	}
}
