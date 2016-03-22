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
		// Coordinates coords = new Coordinates(6, 6, 6); Attention : Coordinates est non mutable !
		for (int i = 0; i < 5; i++) {
			// coords.getCoordinates()[0] = i; Par conséquent le tableau renvoyé par getCoordinates est une copie !
			for (int j = 0; j < 5; j++) {
				// coords.getCoordinates()[1] = j; Et on ne modifie pas réellement l'objet coordinates !
				for (int k = 0; k < 5; k++) {
					// coords.getCoordinates()[2] = k; Qui reste [6, 6, 6] : the number of the beast !
					// sea.setItem(coords, new Case()); Et du coup on a une IndexOutOfBoundsException !
				    sea.setItem(new Coordinates(i, j, k), new Case()); // il faut donc faire comme ça !
				    // (ou alors on peut utiliser le dimZeroIterator() qui est prévu pour ça).
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
