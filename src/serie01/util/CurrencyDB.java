package serie01.util;

/**
 * Modélise les bases de données de monnaies du monde.
 * @inv <pre>
 *     forall id:CurrencyId :
 *         getExchangeRate(id) > 0
 *         (getIsoCode(id) != null) && (getIsoCode(id).length() == 3)
 *         (getLand(id) != null) && !getLand(id).equals("")
 *         (getName(id) != null) && !getName(id).equals("") </pre>
 */
interface CurrencyDB {
    // REQUETES
    /**
     * Le taux de change de la monnaie d'identificateur <code>id</code> par
     * rapport à l'euro.
     * @pre <pre>
     *     id != null </pre>
     */
    double getExchangeRate(CurrencyId id);

    /**
     * Le code ISO (chaîne de caractères) de la monnaie d'identificateur
     *  <code>id</code>.
     * @pre <pre>
     *     id != null </pre>
     */
    String getIsoCode(CurrencyId id);
    
    /**
     * Le pays dans lequel la monnaie d'identificateur <code>id</code> 
     *  a cours.
     * @pre <pre>
     *     id != null </pre>
     */
    String getLand(CurrencyId id);
    
    /**
     * Le nom de la monnaie d'identificateur <code>id</code>.
     * @pre <pre>
     *     id != null </pre>
     */
    String getName(CurrencyId id);

    // COMMANDES
    /**
     * Fixe à <code>rate</code> le taux de change de la monnaie 
     *  d'identificateur <code>id</code>.
     * @pre <pre>
     *     id != null
     *     rate > 0 </pre>
     * @post <pre>
     *     getExchangeRate(id) == rate </pre>
     */
    void setExchangeRate(CurrencyId id, double rate);
}
