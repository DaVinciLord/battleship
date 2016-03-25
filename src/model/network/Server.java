package model.network;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import exceptions.network.ServerBadPortException;
import exceptions.network.ServerClosedSocketException;
import exceptions.network.ServerNullSocketException;
import exceptions.network.ServerSchrodingerException;
import exceptions.network.ServerSocketAcceptException;

/**
 * 
 * @author Nicolas GILLE
 * @date 22 mars 2016
 */
public class Server implements IServer {
	private static final int MS_TO_S = 1000;
	private static final int TIMEOUT = 30 * MS_TO_S;
	public static final int PORT = 24680;
	public static final int BACKLOG = 2;
			
	private ServerSocket listen;
	private ServerState state;
	private InetAddress addr;
	private NetworkInterfaceScan nis;
	private int port;
	
	public Server() throws ServerBadPortException {
		this(Server.PORT, Server.BACKLOG);
	}
	
	public Server(int port, int backlog) throws ServerBadPortException {
		try {
			if ((port > 1024 && port <= 65535) || backlog > 1)
				this.listen = new ServerSocket(port, backlog);
			else
				throw new ServerBadPortException("Port invalide");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.state = ServerState.OFF;
		this.port = port;
		this.nis = new NetworkInterfaceScan();
	}
	
	public Server(int port, int backlog, InetAddress addr) throws ServerBadPortException {
		try {
			if ((port > 1024 && port <= 65535) || backlog > 1)
				this.listen = new ServerSocket(port, backlog, addr);
			else
				throw new ServerBadPortException("Port invalide");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.state = ServerState.OFF;
		this.addr = addr;
		this.port = port;
		this.nis = new NetworkInterfaceScan();
	}
	
	@Override
	public void start() throws ServerSchrodingerException {
		if (!this.state.getState()) {
			this.state = ServerState.ON;	
		} else {
			throw new ServerSchrodingerException("Le serveur tourne deja");
		}
	}

	@Override
	public void shutdown() throws ServerSchrodingerException {
		if (this.state.getState()) {
			this.state = ServerState.OFF;
			try {
				this.listen.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new ServerSchrodingerException("Le serveur est déjà coupé");
		}
	}

	@Override
	public void run() {
		while(this.isRunning()) {
			Thread t = new Thread();
			t.start();
		}
	}

	@Override
	public Socket connectSocket() throws ServerSocketAcceptException {
		Socket s;
		try {
			s = this.listen.accept();
		} catch (IOException e) {
			throw new ServerSocketAcceptException("Le serveur n'a pas pu accepter la connexion");
		}
		return s;
	}

	@Override
	public void closeSocket(Socket s) throws ServerClosedSocketException, ServerNullSocketException {
		if (s == null) {
			throw new ServerNullSocketException("La socket n'existe pas");
		}
		if (!s.isClosed()) {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new ServerClosedSocketException("La socket est déjà fermée");
		}
	}

	@Override
	public void setTimeout(Socket s, int timeout) {
		int tm = timeout * Server.MS_TO_S;
		try {
			if (tm < Server.TIMEOUT && timeout > 0)
				s.setSoTimeout(tm);
			else
				s.setSoTimeout(Server.TIMEOUT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isRunning() {
		return this.state.getState();
	}

	@Override
	public InetAddress getIP() {
		return this.listen.getInetAddress();
	}

	@Override
	public String getHostName() {
		return this.getIP().toString() + ":" + this.getPort();
	}

	@Override
	public int getPort() {
		return this.port;
	}

	@Override
	public void setPort(int port) throws ServerBadPortException {
		if (port > 1024 && port < 65535)
			this.port = port;
		else 
			throw new ServerBadPortException("Port invalide");
	}
	
	/**
	 * addresse associé + ":" + port + " / " + etat 
	 */
	public String toString() {
		return this.getIP().getHostAddress() + ":" + this.getPort() + ";\n" + this.getState().toString();
	}
	
	public NetworkInterfaceScan getNetworkInterfaceScan() {
		return this.nis;
	}
	
	private ServerState getState() { 
		return this.state;
	}
	
	/**
	 * Classe interne gérant les IPv4/6 pour toutes les interfaces réseau présente dans l'OS.
	 * Celle-ci contient deux Map prenant en clé le nom de l'interface réseau et en valeur l'IP associé.
	 * 
	 * @author Nicolas GILLE
	 * @date 22 mars 2016
	 */
	public class NetworkInterfaceScan {
		private Map<String, String> networkfInterfaceIPv4;
		private Map<String, String> networkfInterfaceIPv6;
		
		public NetworkInterfaceScan() {
			try {
				this.networkfInterfaceIPv4 = new HashMap<String, String>();
				this.networkfInterfaceIPv6 = new HashMap<String, String>();
				this.scanningNetworkInterface();
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}

		public Map<String, String> getIPv4() { return this.networkfInterfaceIPv4; }
		public Map<String, String> getIPv6() { return this.networkfInterfaceIPv6; }
		
		private void scanningNetworkInterface() throws SocketException {
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
		    while (e.hasMoreElements()) {
		        NetworkInterface n = e.nextElement();
		        String name = n.getName();
		        Enumeration<InetAddress> ei = n.getInetAddresses();
		        while (ei.hasMoreElements()) {
		            InetAddress i = ei.nextElement();
		            if (i instanceof Inet6Address) {
		            	String s = i.toString().substring(1, i.toString().length());
		            	int percentIndex = s.indexOf("%");
		            	this.networkfInterfaceIPv6.put(name, s.substring(0, percentIndex));
		            } else {
		            	this.networkfInterfaceIPv4.put(name, i.getHostAddress());
		            }
		        }
		    }
		}
	}
	
	/**
	 * Etat du serveur représenter avec un boolean permettant de faire tourner en boucle le serveur jusqu'à extinction de l'application.
	 * @author Nicolas GILLE
	 * @date 22 mars 2016
	 */
	private enum ServerState {
		ON(true, "Serveur eteint"),
		OFF(false, "Serveur allumé");
		private boolean state;
		private String msg;
		
		private ServerState(boolean s, String msg) { 
			this.state = s; 
			this.msg = msg;
		}
		
		public boolean getState() { 
			return this.state; 
		}
		
		public String getMessage() {
			return this.msg;
		}
		
		public String toString() {
			return this.getMessage();
		}
	}
}

