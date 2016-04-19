package model.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import exceptions.network.ServerBadDataException;
import exceptions.network.ServerBadFormatException;
import exceptions.network.ServerEmptyDataException;
import exceptions.network.ServerNullDataException;
import exceptions.network.ServerSocketAcceptException;

/**
 * 
 * @author Nicolas GILLE
 * @date 25 mars 2016
 */
public class ServerController implements IServerController {
	private String lastData;
	private IServer server;
	private Socket socket;
	
	public ServerController(IServer server) throws ServerSocketAcceptException {
		this.lastData = null;
		this.server = server;
	}

	public void sendData(String data) {
		try {
			DataOutputStream out = new DataOutputStream(this.getSocket().getOutputStream());
			out.writeUTF(data);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public String receiveData() throws ServerNullDataException, ServerEmptyDataException, ServerBadDataException, ServerBadFormatException {
		try {
			DataInputStream in = new DataInputStream(this.getSocket().getInputStream());
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
			throw new ServerNullDataException("Aucune donnée reçu");
		if (data.equals("")) 
			throw new ServerEmptyDataException("Les données reçus sont vide");
		String[] split = data.split(":");
		if (split.length == 1) 
			throw new ServerBadFormatException("Les données reçus ne sont pas au bon format");
		if (!(split[0].equals("String") || split[0].equals("Coordinates") || split[0].equals("State")))
			throw new ServerBadDataException("Les données reçus ne sont pas du bon type");
		return true;
	}

	public String getHostName() {
		return this.server.getHostName();
	}

	public String getData() {
		return this.lastData;
	}

	public void setData(String s) {
		this.lastData = s;
	}
	
	public Socket getSocket() { 
		return this.socket; 
	}
	
	public void setSocket(Socket s) {
		this.socket = s;
	}
	
	public IServer getServer() {
		return this.server;
	}
}
