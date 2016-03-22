package tests.model;

import model.coordinates.Coordinates;
import model.ship.GuessShipPlace;
import model.ship.ShipType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GuessShipPlaceTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        List<Integer> armada = new ArrayList<Integer>();
        
        for (ShipType st : ShipType.values()) {
            armada.add(st.getMaxHP());
        }
        /*
        armada.add(5);
        armada.add(4);
        armada.add(3);
        armada.add(3);
        */
        List<Coordinates> hits = new ArrayList<Coordinates>();
        List<Coordinates> sunks = new ArrayList<Coordinates>();
        
        // test longueur 5, deux coulés.
        /*
        for (int k = 0; k < 5; k++) {
            hits.add(new int[]{1, k, 3});
        }
        
        sunks.add(new int[]{1, 1, 3});
        sunks.add(new int[]{1, 3, 3});
        */
        
        // test en L : 2 et 3 ambigü.
        /*
        hits.add(new int[]{0, 1, 2});
        hits.add(new int[]{1, 1, 2});
        hits.add(new int[]{2, 1, 2});
        hits.add(new int[]{2, 2, 2});
        hits.add(new int[]{2, 3, 2});
        
        sunks.add(new int[]{0, 1, 2});
        sunks.add(new int[]{2, 3, 2});
        */
        
        // même test non ambigü : un coulé au coin du L.
        
        hits.add(new Coordinates(0, 1, 2));
        hits.add(new Coordinates(1, 1, 2));
        hits.add(new Coordinates(2, 1, 2));
        hits.add(new Coordinates(2, 2, 2));
        hits.add(new Coordinates(2, 3, 2));
        
        sunks.add(new Coordinates(2, 1, 2));
        sunks.add(new Coordinates(2, 3, 2));
        
        GuessShipPlace gsp = new GuessShipPlace(armada, hits, sunks);
        Set<GuessShipPlace.Possibility> possSet = gsp.fullPossibilities();
        System.out.println("Inventaire des possibilités : ");
        int i = 0;
        for (GuessShipPlace.Possibility poss : possSet) {
            i++;
            Set<List<Coordinates>> shipList = poss.placedShips();
            System.out.println("possibilité numéro : " + i);
            for (List<Coordinates> ship : shipList) {
                System.out.print("coordonnées :");
                for (Coordinates coord : ship) {
                    for (int k : coord) {
                        System.out.print(" " + k);
                    }
                    System.out.print(";");
                }
                System.out.println();
            }
        }
        
        // tests avec possibilités incomplètes :
        System.out.println("\nPossibilités incomplètes\n");
        List<Coordinates> notAimed = new ArrayList<Coordinates>();
        for (int l = 1; l <= 2; l++) {
	        for (int j = 1; j <= 2; j++) {
	        	for (int k = 0; k <= 4; k++) {
	            	notAimed.add(new Coordinates(l, j, k));
	            }
	        }
        }

        GuessShipPlace gsp2 = new GuessShipPlace(armada, notAimed);
        Set<GuessShipPlace.Possibility> possSet2 = gsp2.fullPlacedPossibilities();
        System.out.println("Inventaire des possibilités : " + possSet2.size() + " possibilités");
        i = 0;
        boolean possfull = true;
        boolean doublon = false;
        Set<GuessShipPlace.Possibility> doublonCheck = new HashSet<GuessShipPlace.Possibility>();
        for (GuessShipPlace.Possibility poss : possSet2) {
        	if (doublonCheck.contains(poss)) {
        		doublon = true;
        	}
        	doublonCheck.add(poss);
            i++;
            Set<List<Coordinates>> shipList = poss.placedShips();
            if (shipList.size() != armada.size()) {
            	possfull = false;
            }
            System.out.println("possibilité numéro : " + i);
            for (List<Coordinates> ship : shipList) {
                System.out.print("coordonnées :");
                for (Coordinates coord : ship) {
                    for (int k : coord) {
                        System.out.print(" " + k);
                    }
                    System.out.print(";");
                }
                System.out.println();
            }
        }
        System.out.println(possfull ? "toutes possibilités correctes" : "possibilités incorrectes existantes");
        System.out.println(doublon ? "doublons présents" : "pas de doublons");
        
    }

}
