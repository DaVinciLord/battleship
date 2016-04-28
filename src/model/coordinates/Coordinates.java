package model.coordinates;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Classe implémentant des tableaux d'entiers, non mutables et avec l'égalité redéfinie. 
 */

public class Coordinates implements Iterable<Integer> {

    // ATTRIBUTS
    
    /**
     * tableau des coordonnées représentées par cet objet.
     */
    private final int[] coord;
    
    /**
     * Longeur du tableau.
     */
    public final int length;
    
    // CONSTRUCTEUR
    
    public Coordinates(int... args) {
        this.coord = Arrays.copyOf(args, args.length);
        length = args.length;
    }
    /*
    public Coordinates(int[] coord) {
        this.coord = Arrays.copyOf(coord, coord.length);
        length = coord.length;
    }
    */
    public Coordinates(String s) {
        String[] a = s.split(",");
        length = a.length;
        coord = new int[length];
        for (int k = 0; k < length; k++) {
            coord[k] = Integer.parseInt(a[k].trim());
        }
    }
    
    // REQUETES
    
    public int[] getCoordinates() {
        return Arrays.copyOf(coord, coord.length);
    }
    
    public int get(int n) {
        return coord[n];
    }
    
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }
        return Arrays.equals(coord, ((Coordinates) o).getCoordinates());
    }
    
    public boolean isOnAxe(Coordinates c) {
    	if (c == null || length != c.length) {
    		throw new AssertionError("t'es même pas dans la bonne dimension");
    	}
    	for (int k = 0; k < length; k++) {
    		if (c.get(k) >= 0 && c.get(k) != get(k)) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public int hashCode() {
        int res = 0;
        for (int k : coord) {
            res *= 37;
            res += k;
        }
        return res;
    }
    
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (int k : coord) {
            buf.append(k + ",");
        }
        buf.deleteCharAt(buf.length() - 1);
        return new String(buf);
    }
    
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            int pointer = 0;
            @Override
            public boolean hasNext() {
                return pointer < coord.length;
            }

            @Override
            public Integer next() {
                int next = coord[pointer];
                pointer++;
                return next;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

}
