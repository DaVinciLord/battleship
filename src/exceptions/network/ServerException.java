package exceptions.network;

import exceptions.BattleshipException;

public class ServerException extends BattleshipException {
	private static final long serialVersionUID = -2760844666563841388L;
	public ServerException(String msg) {
		super(msg);
	}
}
