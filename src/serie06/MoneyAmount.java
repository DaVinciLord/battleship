package serie06;

/**
 * Modélise les sommes d'argent.
 * Une telle somme est constituée uniquement d'un certain nombre de cents
 *  et d'euros.
 * @inv
 *     forall c:CoinTypes : getValue(c) == getNumber(c) * c.getFaceValue()
 *     getTotalValue() == sum(c:CoinTypes, getValue(c))
 * @cons
 * $POST$ forall c:CoinTypes : getNumber(c) == 0
 */
public interface MoneyAmount extends Stock<CoinTypes> {

    // REQUETES
    
    /**
     * Le montant constitué par l'ensemble des pièces de type 
     *  <code>c</code>.
     * @pre
     *     c != null
     */
    int getValue(CoinTypes c);
    /**
     * Le montant total de cette somme, tous types de pièce confondus.
     */
    int getTotalValue();
    
    // COMMANDES
    
    /**
     * Ajoute la somme <code>amount</code> à la somme courante.
     * @pre
     *     amount != null
     * @post
     *     forall c:CoinTypes :
     *         getNumber(c) == old getNumber(c) + amount.getNumber(c)
     */
    void addAmount(MoneyAmount amount);
    /**
     * Une somme d'argent dont le montant vaut <code>s</code>
     *  et qui peut être prélevée de la somme courante.
     * Retourne null s'il n'est pas possible de faire la monnaie.
     * @pre
     *     s > 0
     * @post
     *     result != this
     *     result == null <==> il n'est pas possible de faire la monnaie
     *     result != null
     *         ==> result.getTotalValue() == s
     *             forall c:CoinTypes :
     *                 result.getNumber(c) <= getNumber(c)
     */
    MoneyAmount computeChange(int s);
    /**
     * Retire la somme <code>amount</code> de la somme courante.
     * @pre
     *     amount != null
     *     forall c:CoinTypes :
     *         getNumber(c) >= amount.getNumber(c)
     * @post
     *     forall c:CoinTypes :
     *         getNumber(c) == old getNumber(c) - amount.getNumber(c)
     */
    void removeAmount(MoneyAmount amount);
}
