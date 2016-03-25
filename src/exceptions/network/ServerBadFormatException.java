package project.exceptions;

public class ServerBadFormatException extends ServerException {
	private static final long serialVersionUID = 7240105840403497700L;
	public ServerBadFormatException(String msg) {
		super(msg);
	}
}
