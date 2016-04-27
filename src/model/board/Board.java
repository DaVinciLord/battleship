package model.board;

import model.coordinates.Coordinates;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class Board<E> implements IBoard<E> {
    // ATTRIBUTS
    
    private final List<Board<E>> boards;
    private E item;
    private int nbDim;
    protected transient int modCount;
    
    // CONSTRUCTEUR
    
    public Board(Coordinates sizes) {
        this(sizes, 0);
        if (sizes == null) {
            throw new AssertionError("size null");
        }
    }
    
    private Board(Coordinates sizes, int k) {
        item = null;
        if (sizes.length == k) {
            boards = null;
        } else {
            if (sizes.get(k) < 0) {
                throw new AssertionError("taille négative pour " + k + " : " + sizes.get(k));
            }
            boards = new ArrayList<Board<E>>();
            for (int i = 0; i < sizes.get(k); i++) {
                boards.add(new Board<E>(sizes, k + 1));
            }
        }
        modCount = 0;
        nbDim = sizes.length - k;
    }
    
    /**
     * Constructeur simple utilisé pour getBoard.
     */
    private Board() {
        item = null;
        boards = new ArrayList<Board<E>>();
        modCount = 0;
    }
    
    // REQUETES
    
    public int dimensionNb() {
        return nbDim;
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
        if (k == coords.length) {
            return item;
        }
        if (coords.get(k) < 0) {
            throw new AssertionError("coordonnée négative pour " + k + " : " + coords.get(k));
        }
        return boards.get(coords.get(k)).getItem(coords, k+1);
    }

    public Board<E> getBoard(Coordinates dimCoords) {
        if (dimCoords == null) {
            throw new AssertionError("coord null");
        }
        if (dimCoords.length != nbDim) {
            throw new AssertionError("mauvais nombre de coordonnées");
        }
        return getBoard(dimCoords, 0);
    }
    
    private Board<E> getBoard(Coordinates dimCoords, int k) {
        if (dimCoords.length == k) {
            return this;
        }
        if (dimCoords.get(k) == -1) {
            Board<E> b = new Board<E>();
            for (Board<E> board : boards) {
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
        modCount++;
        if (k == coords.length) {
            this.item = item;
        } else {
            if (coords.get(k) < 0) {
                throw new AssertionError("coordonnée négative pour " + k + " : " + coords.get(k));
            }
            boards.get(coords.get(k)).setItem(coords, item, k+1);
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
    
    public int size(){
        int size = 1;
        for (int i : getDimensionsSizes()) {
            size *= i;
        }
        return size;
    }
    
    public void clear() {
        modCount++;
        if (boards == null) {
            item = null;
        } else {
            for (Board<E> b : boards) {
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
    
    private void addBoard(Board<E> array) {
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
                for (Board<E> ar : boards) {
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
    
    class DimZeroIterator implements Iterator<IBoard<E>> {
        private IBoard<E> next;
        private ArrayList<Iterator<IBoard<E>>> recIts;
        private boolean rec;
        private int nextPointer;
        
        DimZeroIterator() {
            if (boards == null) {
                next = Board.this;
                rec = false;

            } else {
                rec = true;
                recIts = new ArrayList<Iterator<IBoard<E>>>();
                nextPointer = 0;
                for (Board<E> ar : boards) {
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
