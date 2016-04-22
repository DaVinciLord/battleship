package serie08;

import serie06.CoinTypes;
import serie06.DrinkTypes;


/**
 * Modélise un distributeur automatique de boissons.
 * Un distributeur contient :
 *  - un stock de boissons de différents types ;
 *  - une caisse (cash) contenant une certaine quantité de chaque type de
 *     pièces utilisables ;
 *  - une réserve permettant de stocker le credit inséré par le client (credit).
 *  - une réserve permettant de stocker la monnaie à rendre au client
 *     (change).
 * Lorsque la caisse est insuffisamment remplie, la machine signale 
 *  qu'elle ne rendra pas la monnaie (canGetChange).
 * Le client désirant obtenir une boisson introduit des pièces (insertCoin).
 * Lorsqu'il sélectionne une boisson (selectDrink), son crédit est reversé dans
 *  la caisse, sa boisson est débitée du stock et la monnaie lui est 
 *  éventuellement reversée.
 * @inv <pre>
 *     forall d:DrinkTypes : 0 <= getDrinkNb(d) <= MAX_DRINK
 *     forall c:CoinTypes : 0 <= getCashNb(c) <= MAX_COIN
 *     forall c:CoinTypes : 0 <= getCreditNb(c) <= MAX_COIN
 *     forall c:CoinTypes : 0 <= getChangeNb(c)
 *     getCashAmount() == sum(c:CoinTypes, getCashNb(c) * c.getFaceValue())
 *     getCreditAmount() == sum(c:CoinTypes, getCreditNb(c) * c.getFaceValue())
 *     getChangeAmount() == sum(c:CoinTypes, getChangeNb(c) * c.getFaceValue())
 *     canGetChange()
 *         <==> la machine peut rendre la monnaie pour tout
 *              crédit fourni et pour toute boisson demandée. </pre>
 * @cons
 * $POST$
 *     forall d:DrinkTypes : getDrinkNb(d) == MAX_DRINK
 *     forall c:CoinTypes : getCashNb(c) == MAX_COIN - 10
 *     forall c:CoinTypes : getCreditNb(c) == 0
 *     forall c:CoinTypes : getChangeNb(c) == 0
 */
public interface DrinksMachineModel extends ObservableModel {
    
    /**
     * Nombre maximal de boissons de chaque type.
     */
    int MAX_DRINK = 50;
    
    /**
     * Nombre maximal de pièces de chaque type.
     */
    int MAX_COIN = 100;

    // REQUETES
    
    /**
     * Le nombre de boissons du type d actuellement stockées dans la machine.
     * @pre
     *     d != null
     */
    int getDrinkNb(DrinkTypes d);
    
    /**
     * Indique le type de la dernière boisson obtenue du modèle.
     * Retourne null si la boisson a été prise, ou si le modèle n'a pas encore
     *  délivré de boisson.
     */
    DrinkTypes getLastDrink();
    
    /**
     * Le montant total du crédit du client actuellement stocké dans la machine.
     * On peut annuler ce crédit en faisant resetCredit(), ce qui a pour effet
     *  de remplir d'autant getChange().
     */
    int getCreditAmount();
    
    /**
     * Le nombre de pièces de type c paticipant au credit actuellement stocké
     *  dans la machine.
     * @pre
     *     c != null
     */
    int getCreditNb(CoinTypes c);
    
    /**
     * Le montant total actuellement stocké dans la caisse de la machine.
     */
    int getCashAmount();
    
    /**
     * Le nombre de pièces de type c présentes dans la caisse de la machine.
     * @pre
     *     c != null
     */
    int getCashNb(CoinTypes c);
    
    /**
     * La monnaie actuellement rendue par la machine.
     * On peut prendre cette monnaie en faisant takeChange().
     */
    int getChangeAmount();
    
    /**
     * Le nombre de pièces de type c présentes dans la monnaie rendue par
     *  la machine.
     * @pre
     *     c != null
     */
    int getChangeNb(CoinTypes c);
    
    /**
     * La machine pourra-t-elle rendre la monnaie quels que soient la boisson
     *  commandée et le montant crédité ?
     */
    boolean canGetChange();
    
    // COMMANDES
    
    /**
     * La machine fournit une boisson de type <code>d</code>.
     * Le crédit est mis en caisse et la monnaie est éventuellement rendue.
     * @pre
     *     d != null
     *     getDrinkNb(d) >= 1
     *     getCreditAmount() >= d.getPrice()
     *     getLastDrink() == null
     * @post
     *     getDrinkNb(d) == old getDrinkNb(d) - 1
     *     getLastDrink() == d
     *     getCreditAmount() == 0
     *     old canGetChange() ==> 
     *         getCashAmount() == old getCashAmount() + d.getPrice()
     *         getChangeAmount()  ==
     *             old (getChangeAmount() + getCreditAmount()) - d.getPrice()
     *     !old canGetChange() ==> 
     *         getCashAmount() == old (getCashAmount() + getCreditAmount())
     */
    void selectDrink(DrinkTypes d);
    
    /**
     * Remplit le stock de boissons de type d dans la machine.
     * @pre
     *     d != null
     *     q > 0 && getDrinkNb(d) + q <= MAX_DRINK
     * @post
     *     getDrinkNb(d) == old getDrinkNb(d) + q
     */
    void fillStock(DrinkTypes d, int q);
    
    /**
     * Remplit la caisse de monnaie de la machine.
     * @pre
     *     c != null
     *     q > 0 && getCashNb(c) + q <= MAX_COIN
     * @post
     *     getCashNb(c) == old getCashNb(c) + q
     */
    void fillCash(CoinTypes c, int q);
    
    /**
     * Augmente le crédit d'une pièce de type c si possible.
     * @pre
     *     c != null
     * @post
     *     old getCashNb(c) + getCreditNb(c) == MAX_COIN
     *         ==> getChangeNb(c) == old getChangeNb(c) + 1
     *     old getCashNb(c) + getCreditNb(c) < MAX_COIN
     *         ==> getCreditNb(c) == old getCreditNb(c) + 1
     */
    void insertCoin(CoinTypes c);
    
    /**
     * Annule le crédit.
     * @post
     *     getCreditAmount() == 0
     *     getChangeAmount() == old (getChangeAmount() + getCreditAmount())
     */
    void cancelCredit();
    
    /**
     * Simule la prise de la boisson délivrée.
     * @post
     *     getLastDrink() == null
     */
    void takeDrink();
    
    /**
     * Simule la prise de la monnaie rendue.
     * @post
     *     getChangeAmount() == 0
     */
    void takeChange();
    
    /**
     * Vide totalement la machine.
     * @post
     *     getCashAmount() == 0
     *     getCreditAmount() == 0
     *     getChangeAmount() == 0
     *     getLastDrink() == null
     *     forall d:DrinkTypes : getDrinkNb(d) == 0
     */
    void reset();
}
