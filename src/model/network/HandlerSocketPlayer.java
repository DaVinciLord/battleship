package project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class HandlerSocketPlayer implements IHandlerSocketPlayer {
	private IServerController controller;
	private String data;
	
	public HandlerSocketPlayer(ServerController c) {
		this.controller = c;
		this.data = null;
	}
	
	@Override
	public void run() {
		/*
		 * Si getController.getModel.getTurn() == true
		 * 	sendData
		 * 	receiveData
		 * else
		 * 	receiveData
		 * 	sendData
		 */
	}

	@Override
	public void sendData() {
		DataOutputStream out;
		try {
			out = new DataOutputStream(this.getController().getSocket().getOutputStream());
			out.writeUTF(this.getController().getData());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Méthode permettant de recevoir les données issues de la Socket de l'autre client.
	 */
	@Override
	public void receiveData() {
		DataInputStream in;
		try {
			in = new DataInputStream(this.getController().getSocket().getInputStream());
			this.data = in.readUTF();
			this.getController().setData(this.data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public IServerController getController() { return this.controller; }
}
