package model.network;

import model.player.IPlayer;

public class ServerController implements IServerController {
	
	private IPlayer model;
	private String lastData;
	private IServer server;
	
	public ServerController(IPlayer model, IServer server) {
		this.model = model;
		this.lastData = null;
		this.server = server;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveData(String data) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean verifyData(String data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getHostName() {
		// TODO Auto-generated method stub
		return this.server.getHostName();
	}

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return this.lastData;
	}

	@Override
	public void setData(String s) {
		// TODO Auto-generated method stub
		this.lastData = s;
	}

	@Override
	public IPlayer getModel() {
		// TODO Auto-generated method stub
		return this.model;
	}

}
