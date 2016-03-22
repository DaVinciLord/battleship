package model.board;

import model.coordinates.Coordinates;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * @unused Don't use it at the moment !
 */
public class AleaBoard<E> implements IBoard<E> {
    // ATTRIBUTS
    
    private final List<AleaBoard<E>> boards;
    private E item;
    private int nbDim;
    protected transient int modCount;
    
    private double poids;
    private final static Random alea = new Random(System.currentTimeMillis());

    // CONSTRUCTEUR
    
    public AleaBoard(Coordinates sizes) {
        this(sizes, 0);
        if (sizes == null) {
            throw new AssertionError("size null");
        }
    }
    
    private AleaBoard(Coordinates sizes, int k) {
        item = null;
        poids = 0.0;
        if (sizes.length == k) {
            boards = null;
        } else {
            if (sizes.get(k) < 0) {
                throw new AssertionError("taille négative pour " + k + " : " + sizes.get(k));
            }
            boards = new ArrayList<AleaBoard<E>>();
            for (int i = 0; i < sizes.get(k); i++) {
                boards.add(new AleaBoard<E>(sizes, k + 1));
            }
        }
        modCount = 0;
        nbDim = sizes.length - k;
    }
    
    /**
     * Constructeur simple utilisé pour getBoard.
     */
    private AleaBoard() {
        item = null;
        boards = new ArrayList<AleaBoard<E>>();
        modCount = 0;
    }
    
    // REQUETES
    
    public int dimensionNb() {
        return nbDim;
    }
    
    public int size() {
    	int size = 1;
    	for (int i : getDimensionsSizes()) {
    		size *= i;
    	}
    	return size;
    }
    
    public Coordinates getDimensionsSizes() {
        int[] res = new int[nbDim];
        fillSizesArray(res, 0);
        return new Coordinates(res);
    }
    
    private void fillSizesArray(int[] dims, int k) {
        if (k < dims.length) {
            dims[k] = boards.size();
            boards.get(0).fillSizesArray(dims, k + 1);
        }
    }
    
    public E getItem(Coordinates coords) {
        if (coords == null) {
            throw new AssertionError("coord null");
        }
        if (coords.length != nbDim) {
            throw new AssertionError("mauvais nombre de coordonnées");
        }
        return getItem(coords, 0);
    }
    
    private E getItem(Coordinates coords, int k) {
        // débug sauvage
        // System.out.println("getItem, étape : " + k + ", tableau : " + (boards == null));
        if (k == coords.length) {
            return item;
        }
        if (coords.get(k) < 0) {
            throw new AssertionError("coordonnée négative pour " + k + " : " + coords.get(k));
        }
        return boards.get(coords.get(k)).getItem(coords, k+1);
    }
    
    public double getProba(Coordinates coords) {
        if (coords == null) {
            throw new AssertionError("coord null");
        }
        if (coords.length > nbDim) {
            throw new AssertionError("mauvais nombre de coordonnées");
        }
        return getProba(coords, 0);
    }
    
    private double getProba(Coordinates coords, int k) {
        // débug sauvage
        // System.out.println("getItem, étape : " + k + ", tableau : " + (boards == null));
        if (k == coords.length) {
            return getCumulProba();
        }
        if (coords.get(k) < 0) {
            throw new AssertionError("coordonnée négative pour " + k + " : " + coords.get(k));
        }
        return boards.get(coords.get(k)).getProba(coords, k+1);
    }
    
    public double getCumulProba() {
    	if (nbDim == 0) {
    		return poids;
    	}
    	double cumul = 0.0;
    	for (AleaBoard<E> ana : boards) {
    		cumul += ana.getCumulProba();
    	}
    	return cumul;
    }
    
    public ItemCoordsCouple<E> getAleaItem() {
    	int[] coordsResult = new int[nbDim];
    	AleaBoard<E> stepBoard = this;
    	for (int k = 0; k < nbDim; k++) {
    		// on tire aléatoirement un nombre entre 0 et le cumul des poids de stepBoard
    		double rand = alea.nextDouble() * stepBoard.getCumulProba();
    		// On cherche sur 
    		for (int l = 0; l < stepBoard.boards.size(); l++) {
    			AleaBoard<E> ana = stepBoard.boards.get(l);
    			if (rand < ana.getCumulProba()) {
    				stepBoard = ana;
    				coordsResult[k] = l;
    				break;
    			}
    			rand -= ana.getCumulProba();
    		}
    	}
    	return new ItemCoordsCouple<E>(stepBoard.item, new Coordinates(coordsResult));
    }

    public AleaBoard<E> getBoard(Coordinates dimCoords) {
        if (dimCoords == null) {
            throw new AssertionError("coord null");
        }
        if (dimCoords.length != nbDim) {
            throw new AssertionError("mauvais nombre de coordonnées");
        }
        return getBoard(dimCoords, 0);
    }
    
    private AleaBoard<E> getBoard(Coordinates dimCoords, int k) {
        if (dimCoords.length == k) {
            return this;
        }
        if (dimCoords.get(k) == -1) {
            AleaBoard<E> b = new AleaBoard<E>();
            for (AleaBoard<E> board : boards) {
                b.addBoard(board.getBoard(dimCoords, k + 1));
            }
            b.nbDim = b.boards.get(0).nbDim + 1;
            return b;
        }
        return boards.get(dimCoords.get(k)).getBoard(dimCoords, k + 1);
    }
    
    // COMMANDES

    public void setItem(Coordinates coords, E item) {
        if (coords == null) {
            throw new AssertionError("coord null");
        }
        if (coords.length != nbDim) {
            throw new AssertionError("mauvais nombre de coordonnées");
        }
        setItem(coords, item, 0);
    }
    
    private void setItem(Coordinates coords, E item, int k) {
        // débug sauvage
        // System.out.println("getItem, étape : " + k + ", tableau : " + (boards == null));
        modCount++;
        // System.out.print(" " + modCount);
        if (k == coords.length) {
            this.item = item;
        } else {
            if (coords.get(k) < 0) {
                throw new AssertionError("coordonnée négative pour " + k + " : " + coords.get(k));
            }
            boards.get(coords.get(k)).setItem(coords, item, k+1);
        }
    }
    
    public void setProba(Coordinates coords, double proba) {
    	if (coords == null) {
            throw new AssertionError("coord null");
        }
        if (coords.length != nbDim) {
            throw new AssertionError("mauvais nombre de coordonnées");
        }
        if (proba < 0.0) {
        	throw new AssertionError("proba négative");
        }
        setProba(coords, proba, 0);
    }
    
    private void setProba(Coordinates coords, double proba, int k) {
        // débug sauvage
        // System.out.println("getItem, étape : " + k + ", tableau : " + (boards == null));
        modCount++;
        // System.out.print(" " + modCount);
        if (k == coords.length) {
            poids = proba;
        } else {
            if (coords.get(k) < 0) {
                throw new AssertionError("coordonnée négative pour " + k + " : " + coords.get(k));
            }
            boards.get(coords.get(k)).setProba(coords, proba, k+1);
        }
    }
    
    public E removeItem(Coordinates coords) {
        if (coords == null) {
            throw new AssertionError("coord null");
        }
        if (coords.length != nbDim) {
            throw new AssertionError("mauvais nombre de coordonnées");
        }
        return removeItem(coords, 0);
    }
    
    private E removeItem(Coordinates coords, int k) {
        modCount++;
        if (k == coords.length) {
            E echg = item;
            item = null;
            return echg;
        } else {
            if (coords.get(k) < 0) {
                throw new AssertionError("coordonnée négative pour " + k + " : " + coords.get(k));
            }
            return boards.get(coords.get(k)).removeItem(coords, k+1);
        }
    }
    
    // Pour celle-ci, pas d'amélioration de complexité possible.
    public void clear() {
        modCount++;
        if (boards == null) {
            item = null;
        } else {
            for (AleaBoard<E> b : boards) {
                b.clear();
            }
        }
    }
    
    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new NArrayIterator();
        return it;
    }
    
    public Iterator<IBoard<E>> dimZeroIterator() {
        DimZeroIterator diz = new DimZeroIterator();
        return diz;
    }
    // OUTILS
    
    private void addBoard(AleaBoard<E> array) {
        boards.add(array);
    }
    
    class NArrayIterator implements Iterator<E> {
        private E next;
        private ArrayList<Iterator<E>> recIts;
        private boolean rec;
        private int nextPointer;
        private int expectedModCount;
        
        NArrayIterator() {
        	expectedModCount = modCount;
            if (boards == null) {
                next = item;
                rec = false;
            } else {
                rec = true;
                recIts = new ArrayList<Iterator<E>>();
                nextPointer = 0;
                for (AleaBoard<E> ar : boards) {
                    recIts.add(ar.iterator());
                }
            }
        }
        @Override
        public boolean hasNext() {
            if (rec) {
                if (nextPointer < recIts.size() - 1) {
                    return true;
                }
                return recIts.get(recIts.size() - 1).hasNext();
            } 
            return next != null;
        }

        @Override
        public E next() {
            checkForComodification();
            if (rec) {
                if (!recIts.get(nextPointer).hasNext()) {
                    nextPointer++;
                    if (nextPointer == recIts.size()) {
                        throw new NoSuchElementException();
                    }
                }
                return recIts.get(nextPointer).next();
            }
            if (next != null) {
                E tmp = next;
                next = null;
                return tmp;
            }
            throw new NoSuchElementException();
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        final void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("" + modCount + " " + expectedModCount);
            }
        }
    }
    
    public static class ItemCoordsCouple<F> {
    	private F itemc;
    	private Coordinates coordsc;
    	
    	public ItemCoordsCouple(F it, Coordinates coords) {
    		coordsc = coords;
    		itemc = it;
    	}
    	
    	public F getItem() {
    		return itemc;
    	}
    	
    	public Coordinates getCoords() {
    		return coordsc;
    	}
    }
    
    class DimZeroIterator implements Iterator<IBoard<E>> {
        private IBoard<E> next;
        private ArrayList<Iterator<IBoard<E>>> recIts;
        private boolean rec;
        private int nextPointer;
        
        DimZeroIterator() {
            if (boards == null) {
                next = AleaBoard.this;
                rec = false;

            } else {
                rec = true;
                recIts = new ArrayList<Iterator<IBoard<E>>>();
                nextPointer = 0;
                for (AleaBoard<E> ar : boards) {
                    recIts.add(ar.dimZeroIterator());
                }
            }
        }
        @Override
        public boolean hasNext() {
            if (rec) {
                if (nextPointer < recIts.size() - 1) {
                    return true;
                }
                return recIts.get(recIts.size() - 1).hasNext();
            } 
            return next != null;
        }

        @Override
        public IBoard<E> next() {
            if (rec) {
                if (!recIts.get(nextPointer).hasNext()) {
                    nextPointer++;
                    if (nextPointer == recIts.size()) {
                        throw new NoSuchElementException();
                    }
                }
                return recIts.get(nextPointer).next();
            }
            if (next != null) {
                IBoard<E> tmp = next;
                next = null;
                return tmp;
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
