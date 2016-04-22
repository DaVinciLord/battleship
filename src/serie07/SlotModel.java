package serie07;

/**
 * @inv
 *     moneyLost() >= 0
 *     moneyWon() >= 0
 *     result() != null
 *     lastPayout() >= 0
 *     lastPayout() dépend de la composition de result()
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
     * Le résultat du dernier coup joué.
     */
    String result();
    
    // COMMANDES
    
    /**
     * Joue un coup.
     * @post
     *     moneyLost() == old moneyLost() + 1
     *     result() retourne une chaîne qui vient d'être aléatoirement
     *         sélectionnée et dont les éléments sont dans 'A' - 'Z'
     *     moneyWon() == (old moneyWon()) + lastPayout()
     */
    void gamble();
}
