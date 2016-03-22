package tests.model;

import model.board.AleaBoard;
import model.coordinates.Coordinates;

public class AleaBoardTest {
	
	private static String intArrayToString(int[] array) {
		StringBuffer res = new StringBuffer("");
		for(int i : array) {
			res.append(" " + i + ";");
		}
		return new String(res);
	}
	
	public static void main(String[] args) {
		AleaBoard<String> alAr= new AleaBoard<String>(new Coordinates(3, 4));
		int[] coords = new int[2];
		for (int k = 0; k < 3; k++) {
			coords[0] = k;
			for (int l = 0; l < 4; l++) {
				coords[1] = l;
				alAr.setItem(new Coordinates(coords), k + " " + l);
				alAr.setProba(new Coordinates(coords), (k+1) * (l+1));
				// alAr.setProba(coords, 1.0);
			}
		}
		int[][] cumulCoords = new int[][] {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
		for (int k = 0; k < 50; k++) {
			AleaBoard.ItemCoordsCouple<String> res = alAr.getAleaItem();
			System.out.println(res.getItem() + res.getCoords() + alAr.getItem(res.getCoords()));
			cumulCoords[res.getCoords().get(0)][res.getCoords().get(1)]++;
		}
		
		for (int k = 0; k < 3; k++) {
			for (int l = 0; l < 4; l++) {
				System.out.println("coords : " + k + " " + l + ", poids : "
						+ ((k+1) * (l+1)) + ", nombre d'occurences : " + cumulCoords[k][l]);
				// System.out.println(alAr.getItem(new int[]{k, l}));
			}
		}
	}
}
