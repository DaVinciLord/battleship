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
import model.player.IPlayer;

/**
 * 
 * @author Nicolas GILLE
 * @date 25 mars 2016
 */
public class ServerController implements IServerController {
	private IPlayer model;
	private String lastData;
	private IServer server;
	private Socket socket;
	
	public ServerController(IPlayer model, IServer server) throws ServerSocketAcceptException {
		this.model = model;
		this.lastData = null;
		this.server = server;
		this.socket = this.server.connectSocket();
	}

	@Override
	public void run() {
		while(this.server.isRunning()) {
			try {
				this.receiveData();
			} catch (ServerNullDataException | ServerEmptyDataException | ServerBadDataException
					| ServerBadFormatException e) {
				e.printStackTrace();
			}
			this.sendData();
		}
	}

	/**
	 * @TODO
	 * 	Il faut que je vois comment je peux récuperer les bonnes données aifn de pouvoir
	 * écrire dans mon flux.
	 */
	@Override
	public void sendData() {
		try {
			String data = null;
			DataOutputStream out = new DataOutputStream(this.socket.getOutputStream());
			out.writeUTF(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	/**
	 * @TODO
	 * 	Il faut que je fasse un objet permettant de convertir un String en object et inversement.
	 */
	@Override
	public void receiveData() throws ServerNullDataException, ServerEmptyDataException, ServerBadDataException, ServerBadFormatException {
		try {
			DataInputStream in = new DataInputStream(this.socket.getInputStream());
			this.lastData = in.readUTF();
			if (this.verifyData(this.lastData)) {
//				this.model.LAMETHODEAUTILISER(this.lastData);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean verifyData(String data) throws ServerNullDataException, ServerEmptyDataException, ServerBadDataException, ServerBadFormatException {
		if (data == null) 
			throw new ServerNullDataException("Aucune donnée reçu");
		if (data.equals("")) 
			throw new ServerEmptyDataException("Les données reçus sont vide");
		String[] split = data.split(":");
		if (split.length == 1) 
			throw new ServerBadFormatException("Les données reçus ne sont pas au bon format");
		if (!(split[0].equals("String") || split[0].equals("Coordinates") || split[0].equals("Boolean") || split[0].equals("State")))
			throw new ServerBadDataException("Les données reçus ne sont pas du bon type");
		return true;
	}

	@Override
	public String getHostName() {
		return this.server.getHostName();
	}

	@Override
	public String getData() {
		return this.lastData;
	}

	@Override
	public void setData(String s) {
		this.lastData = s;
	}

	@Override
	public IPlayer getModel() {
		return this.model;
	}
	
	public Socket getSocket() { 
		return this.socket; 
	}
}
