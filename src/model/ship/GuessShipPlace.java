package model.ship;

import model.coordinates.Coordinates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GuessShipPlace {
    
    // ATTRIBUTS
    
    /**
     * Liste des longueurs de vaisseaux susceptibles d'être présents
     */
    private List<Integer> ships;
    
    /**
     * liste des coordonnées touchées.
     */
    private List<Coordinates> hits;
    
    /**
     * Liste des Coordinatesonnées pour lesquelles le tir a achevé un vaisseau.
     */
    private List<Coordinates> sunks;
        
    // CONSTRUCTEUR
    
    /**
     * Construit un objet mémorisant une ou plusieurs zones de coordonnées de
     * touches circonscrites. Notez qu'il est possible d'avoir plusieurs zones
     * indépendantes et qu'un vaisseau n'est que dans une zone à la fois.
     * @param shipsLengths : liste des vaisseaux de la partie (ou des vaisseaux restants).
     * @param hits : liste des coordonnées touchées circonscrites.
     * @param sunks : liste des coordonnées où un vaisseau à été coulée, inclue dans hits.
     * @pre <pre>
     * Les préconditions suivantes sont nécessaires au bon fonctionnement de la
     * classe mais non vérifiées.
     *     forall (e : hits) :
     *         e.length == hits.get(0).length
     *         pour tout int[] f tel que f == e +-1 sur une coordonnée : 
     *             hits.contains(f) 
     *             || f correspond à un tir raté 
     *             || f est hors de la grille.
     *     sunk != null ==> forall (e : sunk) : hits.contains(e).
     */
    public GuessShipPlace(List<Integer> shipsLengths, List<Coordinates> hits, List<Coordinates> sunks) {
    	if (shipsLengths == null || hits == null) {
    		throw new AssertionError("hits et shipsLengths doivent être non nuls");
    	}
        this.ships = new ArrayList<Integer>(shipsLengths);
        this.hits = new ArrayList<Coordinates>(hits);
        if (sunks != null) {
	        this.sunks = new ArrayList<Coordinates>(sunks);
        } else {
        	this.sunks = null;
        }
    }
    
    /**
     * Même constructeur sans les sunks :
     * ils ne seront pas pris en compte dans la résolution des possibilités.
     * Pour déterminer les probabilités des navires restants, il suffira 
     * donc de donner dans hits toutes les coordonnées des cases non visées.
     */
    public GuessShipPlace(List<Integer> shipsLengths, List<Coordinates> hits) {
        this(shipsLengths, hits, null);
    }
    
    
    /**
     * Donne les différentes possibilités de placement des navires, y compris incomplètes.
     */
    public Set<Possibility> allPossibilities() {
        Set<Possibility> inter = new HashSet<Possibility>();
        Possibility init = new Possibility(hits, new HashSet<List<Coordinates>>());
        inter.add(init);
        Set<Possibility> inter2;
        for (int len : ships) {
            inter2 = new HashSet<Possibility>();
            for (Possibility poss : inter) {
                inter2.addAll(poss.addShipLength(len));
            }
            inter2.addAll(inter);
            inter = inter2;
        }
        return inter;
    }
    
    /**
     * Donne les différentes possibilités complètes de placement.
     */
    public Set<Possibility> fullPossibilities() {
    	Set<Possibility> result = new HashSet<Possibility>();
    	for (Possibility poss : allPossibilities()) {
            if (poss.isFull()) {
                result.add(poss);
            }
        }
    	return result;
    }
    
    /**
     * Donne les différentes possibilités où tous les navires sont placés.
     */
    public Set<Possibility> fullPlacedPossibilities() {
    	Set<Possibility> result = new HashSet<Possibility>();
    	for (Possibility poss : allPossibilities()) {
            if (poss.allShipPlaced()) {
                result.add(poss);
            }
        }
    	return result;
    }
    
    // TYPES IMBRIQUÉS
    
    /**
     * Chaque instance représente une possibilité de placement de vaisseaux.
     * @inv <pre>
     *     freeCases() et les listes contenues dans placedShips() sont toutes
     *     disjointes deux à deux.</pre>
     */
    public class Possibility {
        
        /**
         * cases encore libres dans la possibilité.
         */
        private List<Coordinates> remainings;
        
        /**
         * ensemble des vaisseaux placés dans la possibilité.
         */
        private Set<List<Coordinates>> placed;
        
        /**
         * Code dépendant des cases.
         */
        private int hashCode;
        
        /**
         * Constructeur de possibilités.
         * Les List<Coordinates> de placedShips doivent être triées selon l'ordre
         * déterminé par la composante variable de chaque coordonnée (il n'y en
         * a qu'une puisque ces coordonnées sont alignées.)
         */
        public Possibility(List<Coordinates> freeCases, Set<List<Coordinates>> placedShips) {
            remainings = new ArrayList<Coordinates>(freeCases);
            placed = new HashSet<List<Coordinates>>();
            for (List<Coordinates> l : placedShips) {
                placed.add(new ArrayList<Coordinates>(l));
            }
            /*
             * Le hashCode d'un Set est la somme des hashCodes de ses éléments
             * le hashCode d'une liste est défini comme ceci :
             *     int hashCode = 1;
             *     Iterator<E> i = list.iterator();
             *     while (i.hasNext()) {
             *         E obj = i.next();
             *         hashCode = 31*hashCode + (obj==null ? 0 : obj.hashCode());
             *     }
             * (source : javadoc)
             * Les listes de coordonnées sont toujours ordonnées de la même
             * façon, le contrat equals() ==> hashCode() est donc respecté.
             * Les cases libres peuvent être dans n'importe quel ordre : c'est
             * pourquoi on utilise size() de remainings plutôt que son hashcode.
             */
            hashCode = remainings.size() + placed.hashCode();
        }
        
        
        /**
         * indique si la possibilité est complète.
         */
        public boolean isFull() {
            return remainings.isEmpty();
        }
        
        /**
         * indique si tous les navires sont placés.
         */
        public boolean allShipPlaced() {
        	return placed.size() == ships.size();
        }
        
        /**
         * Retourne la liste des cases encore libres dans cette possibilité.
         */
        public List<Coordinates> freeCases() {
            return new ArrayList<Coordinates>(remainings);
        }
        
        /**
         * Retourne l'ensemble des listes de cases occupées par un navire dans cette possibilité.
         */
        public Set<List<Coordinates>> placedShips() {
            Set<List<Coordinates>> res = new HashSet<List<Coordinates>>();
            for (List<Coordinates> l : placed) {
                res.add(new ArrayList<Coordinates>(l));
            }
            return res;
        }
        
        /**
         * Construit des possibilités à partir de this, auquel on ajoute ou non
         * un vaisseau de longueur length.
         * @post <pre>
         *     forall (Possibility p : result) :
         *         L'union de p.freeCases() et de toutes les listes de p.placedShips()
         *             == 
         *         L'union de this.freeCases() et de toutes les listes de this.placedShips()
         */
        public Set<Possibility> addShipLength(int length) {
            Set<Possibility> res =  new HashSet<Possibility>();
            // res.add(this); // dans le cas où on choisit de ne pas placer ce vaisseau pour le moment.
            if (!isFull() && !allShipPlaced()) {
                for (Coordinates coord : remainings) {
                    Set<List<Coordinates>> poss = PositionsBeginsIn(coord, length);
                    for (List<Coordinates> lship : poss) {
                        Set<List<Coordinates>> resultShips = new HashSet<List<Coordinates>>(); // construction de l'ensemble avec le nouveau vaisseau.
                        for (List<Coordinates> ll : placed) {
                            resultShips.add(ll);
                        }
                        resultShips.add(lship);
                        List<Coordinates> resultFreeCases = new ArrayList<Coordinates>(); // construction des cases libres moins celles du nouveau vaisseau.
                        for (Coordinates c : remainings) {
                            if (!isInList(c, lship)) {
                                resultFreeCases.add(c);
                            }
                        }
                        res.add(new Possibility(resultFreeCases, resultShips));
                    }
                }
            }
            return res;
        }
        
        /**
         * teste si la coordonnée est dans la liste indiquée : 
         * comparaison avec Arrays.equal
         */
        public boolean isInList(Coordinates coords, List<Coordinates> list) {
            return list.contains(coords);
        }
        
        /**
         * pour que deux possibilités soient égales, il faut que les coordonnées
         * libres soient égales deux à deux et que les listes de coordonnées de
         * navires soient aussi égales deux à deux.
         */
        public boolean equals(Object o) {
            if (o.getClass() != this.getClass()) {
                return false;
            }
            Possibility p = (Possibility) o;
            if (!freeCases().containsAll(p.freeCases())
                    || !p.freeCases().containsAll(freeCases())) {
                return false;
            }
            return findEqualsList(placedShips(), p.placedShips());
        }
        
        private boolean findEqualsList(Set<List<Coordinates>> s1, Set<List<Coordinates>> s2) {
            if (s1.size() != s2.size()) {
                return false;
            }
            int n = 0; 
            // stocke les listes de s2 qui ont été comparés avec succès avec une liste de s1.
            // Set<List<Coordinates>> compared = new HashSet<List<Coordinates>>(); // inutile : les List<Coordinates> sont toutes différentes dans un ensemble.
            for (List<Coordinates> lc1 : s1) {
                for (List<Coordinates> lc2 : s2) {
                    if (lc1.containsAll(lc2) && lc2.containsAll(lc1)) {
                        // if (!compared.contains(lc2)) {
                        //    compared.add(lc2);
                        //}
                        n++;
                    }
                }
            }
            // compared ne peut contenir que des listes appartenant à s1 et s2
            return n == s1.size();
        }
        
        public int hashCode() {
            return hashCode;
        }
        
        /**
         * Construit les possibilités de placement d'un vaisseau partant d'une
         * coordonnée. On ne teste que dans le sens augmentation d'une coordonnée
         * puisque la diminution, si elle donne un résultat valide, fera doublon
         * avec l'augmentation partant de l'autre extrémité.
         */
        private Set<List<Coordinates>> PositionsBeginsIn(Coordinates coord, int length) {
            Set<List<Coordinates>> res = new HashSet<List<Coordinates>>();
            for (int j = 0; j < coord.length; j++) { // pour chaque composante de la coordonnée.
                boolean valid = true;
                int sunkNb = sunks != null ? 0 : 1;
                List<Coordinates> l= new ArrayList<Coordinates>();
                for (int k = 0; k < length; k++) { // on essaie de placer le vaisseau dans la direction choisie.
                    int[] cpy = Arrays.copyOf(coord.getCoordinates(), coord.length);
                    cpy[j] = coord.get(j) + k;
                    Coordinates c = new Coordinates(cpy);
                    l.add(c);
                    if (sunks != null) { // cette partie n'est prise en compte que si on a des sunks
	                    if (isInList(c, sunks)) { // on vérifie que le vaisseau est coulé une seule fois.
	                        sunkNb++;
	                    }
                    }
                    if (!isInList(c, remainings)) { // on vérifie que la suite du vaisseau se place bien sur des cases touchées non occupées.
                        valid = false;
                        break;
                    }
                }
                if (valid && sunkNb == 1) { 
                    res.add(l);
                }
            }
            return res;
        }
    }

}
