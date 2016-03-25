package project.exceptions;

public class ServerBadDataException extends ServerException {
	private static final long serialVersionUID = -1632212606178869077L;
	public ServerBadDataException(String msg) {
		super(msg);
	}
}
