package tests.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

import exceptions.network.ServerBadDataException;
import exceptions.network.ServerBadFormatException;
import exceptions.network.ServerBadPortException;
import exceptions.network.ServerEmptyDataException;
import exceptions.network.ServerNullDataException;
import exceptions.network.ServerSocketAcceptException;
import model.coordinates.Coordinates;
import model.network.Server;
import model.network.ServerController;
import model.player.Player;

public class ServerControllerTest {
	private final static String NULL_DATA = null;
	private final static String EMPTY_DATA = "";
	private final static String BAD_FORMAT = "String lol";
	private final static String BAD_TYPE = "Integer: ";
	private final static String GOOD_FORMAT_STR = "String:bonjour";
	private final static String GOOD_FORMAT_COORD = "Coord:1,2,3,4";
	
	private static void vefiryData() throws ServerBadPortException, ServerNullDataException, ServerEmptyDataException, ServerBadDataException, ServerBadFormatException, ServerSocketAcceptException, UnknownHostException {
		Player model = new Player(new Coordinates(10, 10));
		Server server = new Server();
		ServerController sc = new ServerController(new Server(Server.PORT, Server.BACKLOG, InetAddress.getByName("127.0.0.1")));
		sc.verifyData(NULL_DATA);
		sc.verifyData(EMPTY_DATA);
		sc.verifyData(BAD_FORMAT);
		sc.verifyData(BAD_TYPE);
		sc.verifyData(GOOD_FORMAT_STR);
		sc.verifyData(GOOD_FORMAT_COORD);
	}
	
	public static void main(String[] args) throws ServerBadPortException, ServerNullDataException, ServerEmptyDataException, ServerBadDataException, ServerBadFormatException, ServerSocketAcceptException {
		
	}
}
