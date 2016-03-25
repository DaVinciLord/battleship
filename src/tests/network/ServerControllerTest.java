package project.test;

import project.Server;
import project.ServerController;
import project.exceptions.ServerBadDataException;
import project.exceptions.ServerBadFormatException;
import project.exceptions.ServerBadPortException;
import project.exceptions.ServerEmptyDataException;
import project.exceptions.ServerNullDataException;
import project.exceptions.ServerSocketAcceptException;
import project.model.Player;

public class ServerControllerTest {
	private final static String NULL_DATA = null;
	private final static String EMPTY_DATA = "";
	private final static String BAD_FORMAT = "String lol";
	private final static String BAD_TYPE = "Integer: ";
	private final static String GOOD_FORMAT_STR = "String:bonjour";
	private final static String GOOD_FORMAT_COORD = "Coord:1,2,3,4";
	
	private static void vefiryData() throws ServerBadPortException, ServerNullDataException, ServerEmptyDataException, ServerBadDataException, ServerBadFormatException, ServerSocketAcceptException {
		Player model = new Player();
		Server server = new Server();
		ServerController sc = new ServerController(model, server);
		sc.verifyData(NULL_DATA);
		sc.verifyData(EMPTY_DATA);
		sc.verifyData(BAD_FORMAT);
		sc.verifyData(BAD_TYPE);
		sc.verifyData(GOOD_FORMAT_STR);
		sc.verifyData(GOOD_FORMAT_COORD);
	}
	
	public static void main(String[] args) throws ServerBadPortException, ServerNullDataException, ServerEmptyDataException, ServerBadDataException, ServerBadFormatException, ServerSocketAcceptException {
		vefiryData();
	}
}
