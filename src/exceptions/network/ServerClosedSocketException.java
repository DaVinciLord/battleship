package exceptions.network;

public class ServerClosedSocketException extends ServerException{
	private static final long serialVersionUID = 3807213060977263611L;
	public ServerClosedSocketException(String msg) {
		super(msg);
	}
}
