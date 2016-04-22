package serie07;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Notre mod�le consiste en une Dimension que l'on fait cro�tre et d�cro�tre
 *  � volont�.
 * La Dimension ne peut qu'�voluer dans certaines limites.
 * @inv <pre>
 *     getMinimalDim().width <= getDimension().width
 *     getDimension().width <= MAXIMAL_DIM.width
 *     getMinimalDim().height <= getDimension().height
 *     getDimension().height <= MAXIMAL_DIM.height </pre>
 * @cons <pre>
 * $DESC$ un mod�le de taille DEFAULT_MINIMAL_DIM
 * $POST$ getDimension().equals(DEFAULT_MINIMAL_DIM)
 *        getMinimalDim().equals(DEFAULT_MINIMAL_DIM) </pre>
 */
public interface SwellingModel extends ObservableModel {
    
    // CONSTANTES

    /**
     * La dimension minimale du mod�le.
     */
    Dimension DEFAULT_MINIMAL_DIM = new Dimension();

    /**
     * La dimension maximale du mod�le.
     */
    Dimension MAXIMAL_DIM = Toolkit.getDefaultToolkit().getScreenSize();

    // REQUETES
    
    /**
     * Une dimension �quivalente � la dimension minimale du mod�le.
     */
    Dimension getMinimalDimension();    

    /**
     * Une dimension �quivalente � la dimension courante du mod�le.
     */
    Dimension getDimension();
    
    // COMMANDES
    
    /**
     * Fixe la dimension minimale du mod�le.
     * @pre <pre>
     *     dim != null
     *     0 <= dim.width <= MAXIMAL_DIM.width
     *     0 <= dim.height <= MAXIMAL_DIM.height </pre>
     * @post <pre>
     *     getMinimalDim().equals(dim)
     *     getDimension().width = max(old getDimension().width, dim.width)
     *     getDimension().height = max(old getDimension().height, dim.height)
     *</pre>
     */
    void setMinimalDimension(Dimension dim);

    /**
     * Change la dimension du mod�le en multipliant ses caract�ristiques par
     *  1 + factor.
     * Si factor est positif, cela constitue une augmentation de taille.
     * Si factor est entre -1 et 0, cela constitue une diminution de taille.
     * @pre <pre>
     *     factor >= -1 </pre>
     * @post <pre>
     *     getDimension().width == min(
     *         max(
     *             old getDimension().width * (1 + factor),
     *             getMinimalDimension().width
     *         ),
     *         MAXIMAL_DIM.width
     *     )
     *     getDimension().height == min(
     *         max(
     *             old getDimension().height * (1 + factor),
     *             getMinimalDimension().height
     *         ),
     *         MAXIMAL_DIM.height
     *     ) </pre>
     */
    void scale(double factor);
}
