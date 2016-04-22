package serie06;

/**
 * Modélise les stocks d'éléments.
 * Un stock est composé du type des éléments qu'il contient et du nombre
 *  d'éléments pour chacun d'eux.
 * @inv
 *     forall e:E : getNumber(e) >= 0
 *     getTotalNumber() == sum(e:E, getNumber(e))
 * @cons
 *     $ARGS$
 *     $POST$
 *         getTotalNumber() == 0
 */  
public interface Stock<E> {
    
    // REQUETES
    
    /**
     * Le nombre d'éléments de type <code>e</code> dans ce stock.
     * @pre
     *     e != null
     */
    int getNumber(E e);
    /**
     * Le nombre total d'éléments dans ce stock.
     */
    int getTotalNumber();

    // COMMANDES
   
    /**
     * Ajoute une nouvelle fois l'élément <code>e</code>.
     * @pre
     *     e != null
     * @post
     *     getNumber(e) == old getNumber(e) + 1
     */
    void addElement(E e);
    /**
     * Ajoute <code>qty</code> fois l'élément <code>e</code>.
     * @pre
     *     e != null
     *     qty > 0
     * @post
     *     getNumber(e) == old getNumber(e) + qty
     */
    void addElement(E e, int qty);
    /**
     * Retire une fois l'élément <code>e</code>.
     * @pre
     *     e != null
     *     getNumber(e) >= 1
     * @post
     *     getNumber(e) == old getNumber(e) - 1
     */
    void removeElement(E e);
    /**
     * Retire <code>qty</code> fois l'élément <code>e</code>.
     * @pre
     *     e != null
     *     qty > 0
     *     getNumber(e) >= qty
     * @post
     *     getNumber(e) == old getNumber(e) - qty
     */
    void removeElement(E e, int qty);
    /**
     * Vide ce stock en mettant à zéro toutes les quantités.
     * @post
     *     getTotalNumber() == 0
     */
    void reset();
}
