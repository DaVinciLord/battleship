package tests.model;

import exceptions.ship.ShipBadLengthException;
import exceptions.ship.ShipCaseRaceException;
import exceptions.ship.ShipNotAlignException;
import exceptions.ship.ShipOffLimitException;
import model.board.Board;
import model.board.Case;
import model.board.IBoard;
import model.coordinates.Coordinates;
import model.ship.IShip;
import model.ship.Ship;
import model.ship.ShipType;

public class ShipPlacementAndShootTest {
	public static void main(String[] args) throws ShipCaseRaceException, ShipBadLengthException, ShipOffLimitException, ShipNotAlignException {
		IBoard<Case> sea = new Board<Case>(new Coordinates(5, 5, 5));
		Coordinates coords = new Coordinates(6, 6, 6);
		for (int i = 0; i < 5; i++) {
			coords.getCoordinates()[0] = i;
			for (int j = 0; j < 5; j++) {
				coords.getCoordinates()[1] = j;
				for (int k = 0; k < 5; k++) {
					coords.getCoordinates()[2] = k;
					sea.setItem(coords, new Case());
				}
			}
		}
		// test placement correct
		IShip navire = new Ship(sea, ShipType.BATTLESHIP);
		navire.setPosition(new Coordinates(2 ,2 ,1), new Coordinates(2, 2, 4));
		
		// test placement d'un navire déjà placé
		// navire.setPosition(new int[]{2 ,3 ,1}, new int[]{2, 3, 4});
		
		IShip navire2 = new Ship(sea, ShipType.CRUISER);
		
		// test mauvaise longueur
		// navire2.setPosition(new int[]{2 ,3 ,1}, new int[]{2, 3, 4});
		
		// test mauvais alignement
		// navire2.setPosition(new int[]{2 ,3 ,1}, new int[]{2, 0, 4});
		
		// test dépassement
		// navire2.setPosition(new int[]{2 ,3 ,4}, new int[]{2, 3, 6});
		
		navire2.setPosition(new Coordinates(0 ,0 ,0), new Coordinates(0, 0, 2));
		
		// Tests de tir :
		
		// avant les tirs
		System.out.println("case 2, 2, 3 : "
					+ sea.getItem(new Coordinates(2 ,2 ,3)).getState().toString());
		System.out.println("case 4, 2, 1 : "
				+ sea.getItem(new Coordinates(4 ,2 ,1)).getState().toString());
		
		System.out.println("points de vie du battleship : " + navire.getHP());
		System.out.println("points de vie du croiseur : " + navire2.getHP());
		// tir 2, 2, 3
		sea.getItem(new Coordinates(2 ,2 ,3)).fireAt();
		
		// entre les deux tirs
		System.out.println("case 2, 2, 3 : "
				+ sea.getItem(new Coordinates(2 ,2 ,3)).getState().toString());
		System.out.println("case 4, 2, 1 : "
				+ sea.getItem(new Coordinates(4 ,2 ,1)).getState().toString());
		
		System.out.println("points de vie du battleship : " + navire.getHP());
		System.out.println("points de vie du croiseur : " + navire2.getHP());
		
		// tir 4, 2, 1
		sea.getItem(new Coordinates(4 ,2 ,1)).fireAt();
		
		// après les tirs
		System.out.println("case 2, 2, 3 : "
				+ sea.getItem(new Coordinates(2 ,2 ,3)).getState().toString());
		System.out.println("case 4, 2, 1 : "
				+ sea.getItem(new Coordinates(4 ,2 ,1)).getState().toString());
		
		System.out.println("points de vie du battleship : " + navire.getHP());
		System.out.println("points de vie du croiseur : " + navire2.getHP());
		 
	}
}
