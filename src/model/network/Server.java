package model.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import exceptions.network.ServerBadPortException;
import exceptions.network.ServerClosedSocketException;
import exceptions.network.ServerNullSocketException;
import exceptions.network.ServerSchrodingerException;

public class Server implements IServer {
	private static final int MS_TO_S = 1000;
	private static final int PORT = 24680;
	private static final int BACKLOG = 2;
	private static final int TIMEOUT = 30 * MS_TO_S;
			
	private ServerSocket listen;
	private ServerState state;
	private int port;
	
	public Server() throws ServerBadPortException {
		this(Server.PORT, Server.BACKLOG);
		this.port = Server.PORT;
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
			
		}
	}

	@Override
	public void listenSocket() {
		// TODO Auto-generated method stub

	}

	@Override
	public Socket connectSocket() {
		// TODO Auto-generated method stub
		return null;
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
	
	private ServerState getState() { 
		return this.state;
	}
	
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
