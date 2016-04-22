package serie04;

/**
 * Modélise un contact.
 * Tout contact est une personne décrite par :
 * <ul>
 *      <li> son nom</li>
 *      <li> son prénom</li>
 *      <li> sa civilité</li>
 * </ul>
 * @inv <pre>
 *     (getLastName() != null) && (getFirstName() != null)
 *     !getLastName().equals("") || !getFirstName().equals("")
 *     getCivility() != null
 *     toString().equals(
 *         getCivility() + " " + getFirstName() + " " + getLastName()
 *     )
 *     forall c:Contact :
 *         this.compareTo(c) < 0
 *             <==> this.getLastName().compareTo(c.getLastName()) < 0
 *                  || (this.getLastName().compareTo(c.getLastName()) == 0
 *                      && this.getFirstName().compareTo(c.getFirstName()) < 0)
 *                  || (this.getLastName().compareTo(c.getLastName()) == 0
 *                      && this.getFirstName().compareTo(c.getFirstName()) == 0
 *                      && this.getCivility().compareTo(c.getCivility()) < 0)
 *         this.compareTo(c) == 0
 *             <==> this.getLastName().compareTo(c.getLastName()) == 0
 *                  && this.getFirstName().compareTo(c.getFirstName()) == 0
 *                  && this.getCivility().compareTo(c.getCivility()) == 0
 *         this.compareTo(c) > 0
 *             <==> this.getLastName().compareTo(c.getLastName()) > 0
 *                  || (this.getLastName().compareTo(c.getLastName()) == 0
 *                      && this.getFirstName().compareTo(c.getFirstName()) > 0)
 *                  || (this.getLastName().compareTo(c.getLastName()) == 0
 *                      && this.getFirstName().compareTo(c.getFirstName()) == 0
 *                      && this.getCivility().compareTo(c.getCivility()) > 0)
 *         this.equals(c)
 *             <==> this.getLastName().equals(c.getLastName()
 *                  && this.getFirstName().equals(c.getFirstName()
 *                  && this.getCivility().equals(c.getCivility()
 *         this.equals(c) ==> this.hashcode() == c.hashCode() </pre>
 * @cons
 *     $DESC$ Un contact de nom n et de prénom p.
 *     $ARGS$ String n, String p
 *     $PRE$
 *         n != null && p != null
 *         !n.equals("") || !p.equals("")
 *     $POST$
 *         getLastName().equals(n)
 *         getFirstName().equals(p)
 *         getCivility() == Civ.UKN
 * @cons
 *     $DESC$ Un contact de civilité c, de nom n et de prénom p.
 *     $ARGS$ Civ c, String n, String p
 *     $PRE$
 *         c != null
 *         n != null && p != null
 *         !n.equals("") || !p.equals("")
 *     $POST$
 *         getLastName().equals(n)
 *         getFirstName().equals(p)
 *         getCivility() == c
 */
public interface Contact extends Comparable<Contact> {

    // REQUETES

    /**
     * L'ordre naturel sur les contacts.
     * @pre
     *     c != null
     * @throws NullPointerException si c == null
     */
    int compareTo(Contact c);
    
    /**
     * Teste l'égalité de ce contact avec obj.
     */
    boolean equals(Object obj);
    
    /**
     * La civilité du contact.
     */
    Civ getCivility();
    
    /**
     * Le prénom du contact.
     */
    String getFirstName();
    
    /**
     * Le nom de famille du contact.
     */
    String getLastName();
    
    /**
     * Fonction de dispersion définie sur les contacts.
     */
    int hashCode();
    
    /**
     * Représentation textuelle de ce contact.
     */
    String toString();

    // COMMANDES
    
    /**
     * Modifie la civilité de ce contact lorsque cela est possible. 
     * Seules les modification suivantes sont autorisées :
     * <pre>
     *   UKN --> MR, MRS ou MS
     *   MRS --> MS
     *   MS  --> MRS
     * </pre>
     * Toute autre modification est interdite (voir {@link serie04.Civ}).
     * @pre <pre>
     *     civility != null && getCivility().canEvolveTo(civility) </pre>
     * @post <pre>
     *     getCivility() == civility </pre>
     */
    void setCivility(Civ civility);
}
