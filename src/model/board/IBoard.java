package model.board;

import model.coordinates.Coordinates;

import java.util.Iterator;

/**
 * Représente les tableaux d'éléments à nombre de dimensions quelconque.
 * La taille du tableau pour une dimension donnée est la même quels que soit les
 * paramêtres dans les autres dimensions.
 * Par exemple, un tableau à 3 dimensions pourra toujours être représenté 
 * graphiquement par un pavé droit.
 * @author vimard
 *
 * @param <E>
 * 
 * @inv <pre>
 *     dimensionNb() >= 0
 *     dimensionNb() == getDimensionsSizes().length</pre>
 *     
 * @cons <pre>
 *     $DESC$ un tableau à N dimensions.
 *     $ARGS$ int[] sizes
 *     $PRE$
 *         sizes != null
 *         forall k of sizes :
 *             k > 0
 *     $POST$
 *         dimensionNb() == sizes.length
 *         getDimensionsSizes().equals(sizes)
 *         forall int[] coord possible :
 *             getItem(coords) == null</pre>
 */
public interface IBoard<E> extends Iterable<E> {


    /**
     * Retourne le nombre de dimensions du tableau.
     */
    public int dimensionNb();
    
    
    /**
     * Retourne une liste des tailles dans chaque dimension.
     */
    public Coordinates getDimensionsSizes();
    
    /**
     * retourne l'élément aux coordonnées indiquées.
     * @pre<pre>
     *     coords.length == dimensionNb()
     *     forall 0 <= i < dimensionNb() :
     *         0 <= coords.get(i) < getDimensionsSizes().get(i)</pre>
     */
    public E getItem(Coordinates coords);
    
    /**
     * Place l'élément item aux coordonnées indiquées.
     * @pre<pre>
     *     coords.length == dimensionNb()
     *     forall 0 <= i < dimensionNb() :
     *         0 <= coords[i] < getDimensionsSizes()[i]</pre>
     * @post<pre>
     *     getItem(coords) == item</pre>
     */
    public void setItem(Coordinates coords, E item);
    
    
    /**
     * Retire et retourne l'élément aux coordonnées indiquées.
     * @pre<pre>
     *     coords.length == dimensionNb()
     *     forall 0 <= i < dimensionNb() :
     *         0 <= coords[i] < getDimensionsSizes()[i]</pre>
     * @post<pre>
     *     getItem(coords) == null</pre>
     */
    public E removeItem(Coordinates coords);
    
    /**
     * Vide le tableau.
     * @post<pre>
     *     forall coords possible :
     *         getItem(coords) == null</pre>
     */
    public void clear();
    
    /**
     * Renvois la taille du tableau (le nombre de cases)
     * @return <pre>
     * size = le produit des dimensions.
     * </pre>
     */
    public int size();
    /**
     * Retourne une vue d'un sous-tableau du tableau pour une ou 
     * plusieurs dimensions fixées.
     * @args<pre>
     *     int[] dimCoords : tableau d'entiers tel que -1 
     *     définit une dimension non fixée et un entier >= 0 définit une
     *     dimension fixée.</pre>
     * @pre<pre>
     *     dimCoords.size() == dimensionNb()
     *     forall 0 <= i < dimensionNb() :
     *         -1 <= coords[i] < getDimensionsSizes()[i]</pre>
     * @result<pre>
     *     result.dimensionNb() == nombre de -1 dans dimCoords
     *     Exemple :
     *         Soit this.getDimensionsSizes() == {d1, d2, d3, d4, d5}
     *         et dimCoords == {x1, -1, x3, -1, x5}
     *         avec 0 <= xi < di pour i == 1, 3 et 5
     *         
     *         alors result.getDimensionsSizes() == {d2, d4}
     *         et pour toute coordonnée valable : 
     *              result.getItem( {a, b} ) ==
     *                this.getItem( {x1, a, x3, b, x5} )</pre>
     */
    public IBoard<E> getBoard(Coordinates dimCoords);
    
    /**
     * Itérateur renvoyant tous les IBoard de dimension zéro, correspondant aux
     * cases. Nécessaire pour remplir un tableau à nombre de dimensions
     * variable et inconnu.
     */
    public Iterator<IBoard<E>> dimZeroIterator();
    
}
