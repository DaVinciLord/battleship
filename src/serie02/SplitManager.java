package serie02;

import java.io.File;
import java.io.IOException;

/**
 * Modélise le type abstrait SplitManager.
 * Un split manager est un objet capable de découper un gros fichier
 *  en fichiers plus petits.
 * Le principe est le suivant : on configure le split manager en lui
 *  appliquant autant de fois que l'on souhaite une des méthodes setSplits*
 *  (en changeant la taille des morceaux à chaque fois).
 * Puis on commande au split manager de casser le fichier selon la dernière
 *  configuration effectuée (méthode split()).
 * Le nom des petits fichiers produits est formé sur la base du nom du
 *  fichier source, augmenté d'un numéro de séquence.
 * Remarque : on fait l'hypothèse que le fichier géré par le split manager
 *  n'est pas modifié pendant qu'il est pris en charge par le split manager
 *  (comme si le fichier était verrouillé durant toute son utilisation).
 * 
 * @inv <pre>
 *     getFile() != null
 *     Soit gSS ::= getSplitsSizes()
 *     gSS != null
 *     gSS.length == 0 <==> getFile().length() == 0
 *     0 <= gSS.length <= getMaxFragmentNb()
 *     getMaxFragmentNb() == min(
 *         MAX_FRAGMENT_NB,
 *         max(1, ceil(getFile().length() / MIN_FRAGMENT_SIZE))
 *     )
 *     forall i:[0..gSS.length - 1[ : gSS[i] >= MIN_FRAGMENT_SIZE
 *     1 <= gSS[gSS.length - 1]
 *     getFile().length() == sum(i:[0..gSS.length[, gSS[i])
 *     getSplitsNames() != null
 *     getSplitsNames().length == gSS.length
 *     forall i:[0..getSplitsNames().length] : getSplitsNames()[i].equals(
 *             getFile().getAbsolutePath() + "." + (i + 1)
 *     ) </pre>
 * @cons <pre>
 * $DESC$
 *     Un gestionnaire de scission basé sur le fichier file.
 * $ARGS$
 *     File file
 * $PRE$
 *     file != null
 * $POST$
 *     file.equals(getFile())
 *     getSplitsSizes().length <= 1 </pre>
 */
public interface SplitManager {
    
    // CONSTANTES
   
    /**
     * Nombre maximal de fragments souhaité.
     */
    int MAX_FRAGMENT_NB = 100;
    
    /**
     * Taille minimale d'un fragment (en octets).
     */
    int MIN_FRAGMENT_SIZE = 1024;

    // REQUETES
   
    /**
     * Le fichier à fragmenter.
     */
    File getFile();
    
    /**
     * Le nombre maximal de fragments que supporte la configuration courante de
     *  ce fragmenteur.
     */
    int getMaxFragmentNb();
    
    /**
     * Les noms des fragments de fichier.
     */
    String[] getSplitsNames();
    
    /**
     * Les tailles des fragments de fichiers.
     */
    long[] getSplitsSizes();
   
    // COMMANDES
   
    /**
     * Fixe le fichier à fragmenter.
     * @pre
     *     f != null
     * @post
     *     getFile().equals(f)
     */
    void setFile(File f);
    
    /**
     * Fixe la taille des fragments de fichier.
     * Seul le dernier fragment peut être de taille inférieure à
     *  MIN_FRAGMENT_SIZE.
     * @pre <pre>
     *     getFile().length() >= MIN_FRAGMENT_SIZE
     *     fragSize >= MIN_FRAGMENT_SIZE </pre>
     * @post <pre>
     *     Soit gSS ::= getSplitsSizes()
     *     forall i:[0..gSS.length - 1[ : gSS[i] == fragSize </pre>
     */
    void setSplitsSizes(long fragSize);
    
    /**
     * Fixe la taille des fragments de fichier.
     * Si la somme des tailles passées est inférieure à la taille
     *  du fichier à fragmenter on rajoute un dernier fragment qui contient
     *  ce qu'il reste.
     * Si la somme des tailles passées est supérieure à la taille
     *  du fichier à fragmenter on tronque l'argument.
     * @pre <pre>
     *     getFile().length() >= MIN_FRAGMENT_SIZE
     *     fragSizes != null
     *     fragSizes.length >= 1
     *     forall i:[0..fragSizes.length[ :
     *         fragSizes[i] >= MIN_FRAGMENT_SIZE </pre>
     * @post <pre>
     *     Soit gSS ::= getSplitsSizes()
     *     getFile().length() <= sum(i:[0..fragSizes.length], fragSizes[i])
     *         ==> gSS.length <= fragSizes.length
     *             forall i:[0..gSS.length - 1] : gSS[i] == fragSizes[i]
     *             0 < gSS[gSS.length - 1] <= fragSizes[gSS.length - 1]
     *     getFile().length() > sum(i:[0..fragSizes.length], fragSizes[i])
     *         ==> gSS.length == fragSizes.length + 1
     *             forall i:[0..fragSizes.length] : gSS[i] == fragSizes[i]
     *             gSS[fragSizes.length] ==
     *                 getFile().length()
     *                     - sum(i:[0..fragSizes.length], fragSizes[i]) </pre>
     */
    void setSplitsSizes(long[] fragSizes);
    
    /**
     * Fixe le nombre des fragments de fichier, qui sont alors tous à peu
     *  près de la même taille (à un octet près).
     * @pre <pre>
     *     getFile().length() > 0
     *     1 <= number <= getMaxFragmentNumber() </pre>
     * @post <pre>
     *     Soit gSS ::= getSplitsSizes()
     *          n   ::= getFile().length()
     *          q   ::= n / number
     *     q < MIN_FRAGMENT_SIZE
     *         ==> gss.length == ceil(n / MIN_FRAGMENT_SIZE)
     *             forall i:[0..gss.length - 1] : gSS[i] == MIN_FRAGMENT_SIZE
     *             gSS[gss.length - 1] == n - sum(i:0..gss.length - 2, gSS[i])
     *     q >= MIN_FRAGMENT_SIZE
     *         ==> gss.length == number
     *             forall i:[0..(n % gSS.length)[ : gSS[i] == q + 1
     *             forall i:[(n % gSS.length)..gSS.length[ : gSS[i] == q </pre>
     */
    void setSplitsNumber(int number);
    
    /**
     * Effectue sur le disque la scission du gros fichier en plus petits.
     * Si le fichier source est de taille 0, il ne s'est rien passé.
     * @pre
     *     getFile().length() > 0
     * @post <pre>
     *     forall i:[0..getSplitsSizes().length[ :
     *         getSplitsSizes()[i] == La taille du fichier sur disque de nom
     *                                getSplitsNames()[i]
     *     le fichier associé à getFile() a même contenu que la
     *         concaténation des fichiers de nom getSplitsNames() </pre>
     * @throws java.io.FileNotFoundException
     *     si getFile() ne représente pas un fichier accessible
     * @throws IOException
     *     s'il s'est produit une erreur d'entrée/sortie durant la scission
     */
    void split() throws IOException;
}
