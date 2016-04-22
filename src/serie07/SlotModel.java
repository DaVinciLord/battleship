package serie07;

/**
 * @inv
 *     moneyLost() >= 0
 *     moneyWon() >= 0
 *     result() != null
 *     lastPayout() >= 0
 *     lastPayout() d�pend de la composition de result()
 *     forall i != j : result().charAt(i) != result().charAt(j)
 *         ==> lastPayout() == 0
 *     forall i : result().charAt(i) == ' ' ==> lastPayout() == 0
 * @cons
 *     $POST$
 *         moneyLost() == 0
 *         moneyWon() == 0
 *         forall i : result().charAt(i) == ' '
 */
public interface SlotModel extends ObservableModel {
    
    // REQUETES

    /**
     * Le dernier gain obtenu.
     */
    int lastPayout();
    
    /**
     * Le montant des pertes.
     */
    int moneyLost();
    
    /**
     * Le montant des gains.
     */
    int moneyWon();
    
    /**
     * Le r�sultat du dernier coup jou�.
     */
    String result();
    
    // COMMANDES
    
    /**
     * Joue un coup.
     * @post
     *     moneyLost() == old moneyLost() + 1
     *     result() retourne une cha�ne qui vient d'�tre al�atoirement
     *         s�lectionn�e et dont les �l�ments sont dans 'A' - 'Z'
     *     moneyWon() == (old moneyWon()) + lastPayout()
     */
    void gamble();
}
