package serie04;

import java.util.List;
import java.util.NavigableSet;

/**
 * Modélise un annuaire téléphonique permettant d'associer une liste de
 *  numéros de téléphone à un <code>Contact</code>.
 * Une entrée au plus par contact.
 * @inv <pre>
 *     contacts() != null
 *     contacts() reflète à chaque instant l'ensemble des contacts qui se
 *       trouvent dans l'annuaire et le retrait d'un élément de cet ensemble
 *       entraine une modification dans l'annuaire, et réciproquement toute
 *       modification de l'annuaire est visible sur cet ensemble
 *     isEmpty() == (contacts().size() == 0)
 *     forall p:Contact :
 *         contains(p) == contacts().contains(p)
 *     forall p:contacts() :
 *         (phoneNumbers(p) != null) && (phoneNumbers(p).size() > 0)
 *         firstPhoneNumber(p) != null
 *         firstPhoneNumber(p).equals(phoneNumbers(p).get(0))
 *         phoneNumbers(p) est non modifiable mais reflète à chaque instant
 *           les numéros de p dans l'annuaire (même lorsque ce dernier évolue)
 *         forall n:phoneNumbers(p) :
 *             phoneNumbers(p).lastIndexOf(n) == phoneNumbers(p).indexOf(n)
 * </pre>
 * @cons <pre>
 *     $DESC$ Un annuaire vide.
 *     $POST$ isEmpty() </pre>
 */
public interface PhoneBook {
    
    // REQUETES
    
    /**
     * Retourne le premier numéro d'un contact.
     * @pre <pre>
     *     (p != null) && contains(p) </pre>
     */
    String firstPhoneNumber(Contact p);
    
    /**
     * Retourne une vue non mutable de la liste des numéros de p.
     * Cette liste est partagée avec l'annuaire de sorte que tout changement 
     *  dans l'annuaire est reflété sur cette liste.
     * On ne peut pas modifier cette liste.
     * @pre <pre>
     *     (p != null) && contains(p) </pre>
     */
    List<String> phoneNumbers(Contact p);
    
    /**
     * Retourne l'ensemble ordonné des personnes contenues dans
     *  l'annuaire.
     * Cet ensemble est partagé avec l'annuaire de sorte que tout changement
     *  dans l'annuaire est reflété sur cet ensemble, et réciproquement, si l'on
     *  retire des éléments à cet ensemble l'annuaire est modifié
     *  en conséquence.
     * (Remarque : les ajouts sur cet ensemble sont impossibles.)
     */
    NavigableSet<Contact> contacts();
    
    /**
     * Retourne vrai ssi p est dans l'annuaire.
     * @pre <pre>
     *     p != null </pre>
     */
    boolean contains(Contact p);
    
    /**
     * Retourne vrai ssi l'annuaire est vide.
     */
    boolean isEmpty();

    // COMMANDES
    
    /**
     * Ajoute une nouvelle entrée dans l'annuaire.
     * Si la liste des numéros contient des doublons, ils seront éliminés mais
     *  l'ordre des numéros reste inchangé.
     * @pre <pre>
     *     (p != null) && !contains(p)
     *     (nums != null) && (nums.size() > 0) </pre>
     * @post <pre>
     *     contains(p)
     *     phoneNumbers(p) respecte l'ordre des éléments de nums et ne contient
     *      pas de doublon </pre>
     */
    void addEntry(Contact p, List<String> nums);
    
    /**
     * Ajoute un numéro à la fin de la liste des numéros d'un contact.
     * Si la personne n'existe pas on crée une nouvelle entrée pour cette
     *  personne avec comme liste de numéros associée la liste constituée
     *  du numéro passé en paramètre.
     * Si le numéro est déjà dans la liste de p, on ne fait rien.
     * @pre <pre>
     *     p != null
     *     (n != null) && !n.equals("") </pre>
     * @post <pre>
     *     contains(p)
     *     !old contains(p)
     *         ==> phoneNumbers(p).size() == 1
     *             firstPhoneNumber(p).equals(n)
     *     old contains(p)
     *         ==> Soit oldSize ::= old phoneNumbers(p).size()
     *             !old phoneNumbers(p).contains(n)
     *                 ==> phoneNumbers(p).size() == oldSize + 1
     *                     phoneNumbers(p).indexOf(n) == oldSize </pre>
     */
    void addPhoneNumber(Contact p, String n);
    
    /**
     * Supprime un contact de l'annuaire.
     * @pre <pre>
     *     (p != null) && contains(p) </pre>
     * @post <pre>
     *     !contains(p) </pre>
     */
    void removeEntry(Contact p);
    
    /**
     * Supprime un numéro donné pour un contact donné.
     * Si ce numéro est le seul numéro pour la personne, la personne est
     *  retirée de l'annuaire.
     * Si ce numéro était le premier, l'ancien second devient le nouveau
     *  premier.
     * Ne fait rien si le numéro n'est pas dans la liste des numéros de p.
     * @pre <pre>
     *     (p != null) && contains(p)
     *     (n != null) && !n.equals("") </pre>
     * @post <pre>
     *     old phoneNumbers(p).contains(n) 
     *         ==> old phoneNumbers(p).size() == 1
     *                 ==> !contains(p)
     *             old phoneNumbers(p).size() > 1
     *                 ==> Soit oldSize ::= old phoneNumbers(p).size()
     *                     Soit oldIndex ::= old  phoneNumbers(p).indexOf(n)
     *                     phoneNumbers(p).size() == oldSize - 1
     *                     forall i:[oldIndex..phoneNumbers(p).size()[ :
     *                         phoneNumbers(p).get(i).equals(
     *                             old phoneNumbers(p).get(i + 1)
     *                         ) </pre>
     */
    void deletePhoneNumber(Contact p, String n);
    
    /**
     * Réinitialise l'annuaire.
     * @post <pre>
     *     isEmpty() </pre>
     */
    void clear();
}
