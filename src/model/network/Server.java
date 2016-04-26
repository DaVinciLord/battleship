package model.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import exceptions.network.ServerBadDataException;
import exceptions.network.ServerBadFormatException;
import exceptions.network.ServerBadPortException;
import exceptions.network.ServerClosedSocketException;
import exceptions.network.ServerEmptyDataException;
import exceptions.network.ServerNullDataException;
import exceptions.network.ServerNullSocketException;
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
	
	private String lastData;
	private Socket distantServerSocket;
	private Socket connectedSocket;	
	private ServerSocket listen;
	private int port;
	
	public Server() throws ServerBadPortException {
		this(Server.PORT, Server.BACKLOG);
	}
	
	public Server(int port, int backlog) throws ServerBadPortException {
		this(port, backlog, null);
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
		this.port = port;
		this.lastData = null;
	}
	
	public Socket connectSocket() throws ServerSocketAcceptException {
		Socket s;
		try {
			s = this.listen.accept();
			this.listen.close();
		} catch (IOException e) {
			throw new ServerSocketAcceptException("Le serveur n'a pas pu accepter la connexion");
		}
		return s;
	}

	public void sendData(String data) {
		try {
			DataOutputStream out = new DataOutputStream(this.getDistantServerSocket().getOutputStream());
			out.writeUTF(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String receiveData() throws ServerNullDataException, ServerEmptyDataException, ServerBadDataException, ServerBadFormatException {
		try {
			DataInputStream in = new DataInputStream(this.getConnectedSocket().getInputStream());
			this.lastData = in.readUTF();
			if (this.verifyData(this.lastData)) {
				return this.getData();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.getData();
	}

	public boolean verifyData(String data) throws ServerNullDataException, ServerEmptyDataException, ServerBadDataException, ServerBadFormatException {
		if (data == null) 
			throw new ServerNullDataException("Aucune donnÃ©e reÃ§u");
		if (data.equals("")) 
			throw new ServerEmptyDataException("Les donnÃ©es reÃ§us sont vide");
		String[] split = data.split(":");
		if (split.length == 1) 
			throw new ServerBadFormatException("Les donnÃ©es reÃ§us ne sont pas au bon format");
		if (!(split[0].equals("String") || split[0].equals("Coordinates") || split[0].equals("State")))
			throw new ServerBadDataException("Les donnÃ©es reÃ§us ne sont pas du bon type");
		return true;
	}

	public String getData() {
		return this.lastData;
	}

	public void setData(String s) {
		if (s == null) {
			throw new AssertionError("Vos donnÃ©es sont nulles");
		}
		this.lastData = s;
	}

	public Socket getConnectedSocket() { 
		return this.connectedSocket; 
	}
	
	public void setConnectedSocket(Socket s) {
		if (s == null) {
			throw new AssertionError("Socket is null");
		}
		this.connectedSocket= s;
	}

	public Socket getDistantServerSocket() { 
		return this.distantServerSocket; 
	}
	
	public void setDistantServerSocket(Socket s) {
		if (s == null) {
			throw new AssertionError("Socket is null");
		}
		this.distantServerSocket= s;
	}

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
			throw new ServerClosedSocketException("La socket est dÃ©jÃ  fermÃ©e");
		}
	}

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

	public InetAddress getIP() {
		return this.listen.getInetAddress();
	}

	public String getHostName() {
		return this.getIP().toString() + ":" + this.getPort();
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) throws ServerBadPortException {
		if (port > 1024 && port < 65535)
			this.port = port;
		else 
			throw new ServerBadPortException("Port invalide");
	}
	
	/**
	 * addresse associÃ© + ":" + port + " / " + etat 
	 */
	public String toString() {
		return this.getHostName() + "\n";
	}
}
